package grails.plugin.elfinder

import grails.converters.JSON
import grails.plugin.elfinder.command.ElfinderBaseCommand
import grails.plugin.elfinder.filemanager.ElfinderLocalFileSystemFileManager

import org.apache.commons.lang.StringUtils

/**
 * 
 * @author Sudhir Nimavat
 *
 */
class ElfinderConnectorController {

	def grailsApplication

	def index() {
		String cmd = params.cmd ?: "Unknown"
		String commandName = "elfinder"+StringUtils.capitalize(cmd)+"Command"

		log.debug("Executing command $commandName")

		ElfinderBaseCommand command = grailsApplication.mainContext[commandName]

		if(command == null) {
			log.error("Unknown command received : $commandName")
			render(status:400, text:"unknown command")
		} else {
			command.params = params
			command.request = request
			command.response = response
			try {
				command.execute()
				if(!command.responseOutputDone) {
					def resp = command.responseMap as JSON
					render resp
				}
			}catch(Exception e) {
				log.error("Error encountered while executing command $commandName", e)
				def resp = [error:e.message]
				render resp as JSON
			}
		}
	}
}
