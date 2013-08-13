package elfinder.connector.command

import grails.plugin.elfinder.command.ElfinderLsCommand
import grails.plugin.elfinder.filemanager.ElFinderFileManager
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class ElfinderLsCommandSpec extends Specification {
	
	ElFinderFileManager mockFileManager
	ElfinderLsCommand command
	
	def setup() {
		def control = mockFor(ElFinderFileManager)
		control.demand.files {String path, boolean tree -> [[name:"f1"], [name:"f2"]] }
		
		mockFileManager = control.createMock()
		
		command = new ElfinderLsCommand()
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
