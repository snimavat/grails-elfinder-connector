
package elfinder.connector.command

import grails.plugin.elfinder.command.ElfinderOpenCommand
import grails.plugin.elfinder.filemanager.ElFinderFileManager
import grails.test.mixin.*
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class ElFinderOpenCommandSpec extends Specification {

	ElFinderFileManager mockFileManager
	ElfinderOpenCommand command
	
	def setup() {
		def control = mockFor(ElFinderFileManager)
		control.demand.file {String path -> [name:"mock file"]}
		control.demand.files {String path, boolean tree -> [[name:"f-1"], [name:'f-2']] }
		
		mockFileManager = control.createMock()
		
		command = new ElfinderOpenCommand()
		command.elFinderFileManager = mockFileManager
	}
	
	
	void "open: init = true returns api version and options"() {
		setup:
		command.params = [init:"true"]

		when:
		command.execute()

		then:
		command.responseMap["api"] == 2
		command.responseMap["options"] != null
	}
	
	void "open: init = null and no target results in error"() {
		when:
		command.execute()
		
		then:
		command.responseMap.size() == 1
		command.responseMap["error"] == "File not found"		
	}
	
	void "open: initialize root directory"() {
		setup:		
		command.params = [init:"true", tree:"true"]
		
		when:
		command.execute();
		
		then:
		Map resp = command.responseMap
		resp.error == null
		resp.api == 2

		resp.cwd == [name:"mock file"]
		resp.files == [[name:"f-1"], [name:'f-2']]			
	}
}
