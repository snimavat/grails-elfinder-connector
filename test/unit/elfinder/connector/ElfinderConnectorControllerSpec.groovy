package elfinder.connector

import grails.plugin.elfinder.ElfinderConnectorController
import grails.plugin.elfinder.command.ElfinderBaseCommand
import grails.test.mixin.*
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.web.ControllerUnitTestMixin} for usage instructions
 */
@TestFor(ElfinderConnectorController)
class ElfinderConnectorControllerSpec extends Specification {


	void "test index: invalid command "() {	
		setup:
		controller.params.cmd = "foo"
		
		when:		
		controller.index()

		then:
		response.status == 400
	}
	
	void "index action: valid command"() {
		setup:		
		def control = mockFor(ElfinderBaseCommand)
		control.demand.execute { }
		control.demand.getResponseMap {-> return resp }
				
		params.cmd = "open"
		controller.commandMappings['open'] = control.createMock()
		
		when:
		controller.index()
		
		then:
		response.status == 200
		response.json.key == resp.key
		
		where:
		resp = [key:"hello"]				
	}
}