package elfinder.connector.command

import grails.plugin.elfinder.command.ElfinderTreeCommand
import grails.plugin.elfinder.filemanager.ElFinderFileManager
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class ElfinderTreeCommandSpec extends Specification {

	ElFinderFileManager mockFileManager
	ElfinderTreeCommand command

	def setup() {
		def control = mockFor(ElFinderFileManager)
		control.demand.unhash(0..1) {String target -> return target }
		control.demand.cwd(0..1) {String path -> [name:"cwd", mime:"directory"]}
		control.demand.getTree {String path, int deep -> [[name:"dir1", mime:"directory"], [name:"dir2", mime:"directory"]] }

		mockFileManager = control.createMock()

		command = new ElfinderTreeCommand()
		command.elFinderFileManager = mockFileManager
	}

	void "tree: returns list with key:tree"() {
		setup:
		command.params = ['target':'foo']

		when:
		command.execute()

		then:
		command.responseMap.tree != null
		command.responseMap.tree[0].name == "cwd"
		command.responseMap.tree[1].name == "dir1"
		command.responseMap.tree[2].name == "dir2"
	}
}
