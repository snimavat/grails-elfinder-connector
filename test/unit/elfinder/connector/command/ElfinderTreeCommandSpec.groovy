
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
		control.demand.tree {String path -> ["mock list"] }
		
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
		command.responseMap.tree == ["mock list"]
	}
		
}
