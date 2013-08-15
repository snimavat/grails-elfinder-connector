package elfinder.connector.command

import grails.plugin.elfinder.command.ElfinderParentsCommand
import grails.plugin.elfinder.filemanager.ElFinderFileManager
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class ElfinderParentsCommandSpec extends Specification {

	ElFinderFileManager mockFileManager
	ElfinderParentsCommand command

	def setup() {
		def control = mockFor(ElFinderFileManager)
		control.demand.unhash(0..1) {String target -> return target }
		control.demand.parents {String path -> ["mock list"] }

		mockFileManager = control.createMock()

		command = new ElfinderParentsCommand()
		command.elFinderFileManager = mockFileManager
	}

	void "parents: returns list with key:tree"() {
		setup:
		command.params = ['target':'foo']

		when:
		command.execute()

		then:
		command.responseMap.tree != null
		command.responseMap.tree == ["mock list"]
	}
}
