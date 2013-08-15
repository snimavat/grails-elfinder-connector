
package elfinder.connector.command

import grails.plugin.elfinder.command.ElfinderOpenCommand
import grails.plugin.elfinder.filemanager.ElFinderFileManager
import grails.test.mixin.TestMixin
import grails.test.mixin.support.GrailsUnitTestMixin
import spock.lang.Specification

@TestMixin(GrailsUnitTestMixin)
class ElFinderOpenCommandSpec extends Specification {

	ElFinderFileManager mockFileManager
	ElfinderOpenCommand command
	
	def setup() {
		def control = mockFor(ElFinderFileManager, true)
		control.demand.getRoot(0..2) {-> return "root" }
		control.demand.unhash(0..3) {String target -> return target }
		control.demand.cwd(0..2) {String path -> [name:"cwd", mime:"directory"]}
		control.demand.getTree {String path, int deep -> [[name:"dir1", mime:"directory"], [name:"dir2", mime:"directory"]] }
		control.demand.scanDir {String path -> [[name:"dir1", mime:"directory"], [name:"file.txt", mime:"text"]] }
		control.demand.hash(0..2) {String target -> return target }
		control.demand.options() {String target -> return [:] }
												
		mockFileManager = control.createMock()
		
		command = new ElfinderOpenCommand()
		command.elFinderFileManager = mockFileManager
	}
	
	
	void "open: init = true returns api version and options"() {
		setup:
		command.params = [init:"1"]

		when:
		command.execute()

		then:
		command.responseMap["api"] == "2.0"
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
		command.params = [init:"1", tree:"1"]
		
		when:
		command.execute();
		
		then:
		Map resp = command.responseMap
		resp.error == null
		resp.api == "2.0"

		resp.cwd.name == "cwd" 
		resp.files != null
		resp.files[0].name == "cwd"
		resp.files[1].name == "dir1"
		resp.files[2].name == "dir2"
		resp.files[3].name == "file.txt"
	}
}
