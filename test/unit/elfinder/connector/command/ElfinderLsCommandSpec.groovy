package elfinder.connector.command

import grails.plugin.elfinder.command.ElfinderLsCommand
import grails.plugin.elfinder.filemanager.ElFinderFileManager
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class ElfinderLsCommandSpec extends Specification {

	ElFinderFileManager mockFileManager
	ElfinderLsCommand command = new ElfinderLsCommand()

	def setup() {
		def control = mockFor(ElFinderFileManager, true)
		control.demand.unhash() {String target -> return target }
		control.demand.scanDir {String path -> [[name:"f1"], [name:"f2"]] }

		mockFileManager = control.createMock()

		command.elFinderFileManager = mockFileManager
	}

	void "parents: returns list with key:tree"() {
		setup:
		command.params = ['target':'foo']

		when:
		command.execute()

		then:
		command.responseMap.list != null
		command.responseMap.list == ["f1", "f2"]
	}
}
