package grails.plugin.elfinder

import grails.plugin.elfinder.filemanager.ElFinderFileManager;
import grails.plugin.spock.IntegrationSpec
import grails.test.mixin.*

class ElfinderConnectorControllerSpec extends IntegrationSpec {

	ElfinderConnectorController controller
	ElFinderFileManager elfinderFileManager
	
	def setup() {
		controller = new ElfinderConnectorController()
	}

	
	void "test index: invalid command "() {	
		setup:
		controller.params.cmd = "foo"
		
		when:		
		controller.index()

		then:
		controller.response.status == 400
	}
	
	void "index action: valid command"() {
		setup:		
		controller.params.cmd = "open"
		controller.params.init = "1"
		controller.params.tree = "1"
		
		when:
		controller.index()
		
		then:
		controller.response.status == 200
		def json = controller.response.json
		json != null			
		json.api == "2.0"		
		json.cwd != null
		json.cwd.hash == elfinderFileManager.hash("root")
		json.cwd.volumeid == "v1_"
		json.cwd.name == "resources"
		json.files != null
		json.files[0].name == "resources"
		json.files[0].mime == "directory"
		json.files[1].name == "dir1"
		json.files[2].name == "3.txt"
	}
}