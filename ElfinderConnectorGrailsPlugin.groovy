import grails.plugin.elfinder.command.ElFinderFileCommand
import grails.plugin.elfinder.command.ElFinderMkdirCommand
import grails.plugin.elfinder.command.ElFinderMkfileCommand
import grails.plugin.elfinder.command.ElFinderRenameCommand
import grails.plugin.elfinder.command.ElFinderRmCommand
import grails.plugin.elfinder.command.ElFinderUploadCommand
import grails.plugin.elfinder.command.ElfinderLsCommand
import grails.plugin.elfinder.command.ElfinderOpenCommand
import grails.plugin.elfinder.command.ElfinderParentsCommand
import grails.plugin.elfinder.command.ElfinderTreeCommand
import grails.plugin.elfinder.filemanager.ElfinderLocalFileSystemFileManager


/**
 * @author Sudhir Nimavat
 *
 */
class ElfinderConnectorGrailsPlugin {
    // the plugin version
    def version = "0.1"
    def grailsVersion = "2.2 > *"
    def title = "Elfinder Connector Plugin"
    def author = "Your name"
    def authorEmail = ""
    def description = "Brief summary/description of the plugin"
    def documentation = "http://grails.org/plugin/elfinder-connector"

    def doWithSpring = {
		 //configure file manger
		 def pluginConfig = application.config.grails.plugin.elfinder
		 elfinderFileManager(ElfinderLocalFileSystemFileManager) {
			 root = pluginConfig.rootDir
		 }
		 
		 //configure commands
		 elfinderOpenCommand(ElfinderOpenCommand) { bean ->
			 bean.scope = "prototype"
			 elFinderFileManager = ref("elfinderFileManager")
		 }

		 elfinderParentsCommand(ElfinderParentsCommand) { bean ->
			 bean.scope = "prototype"
			 elFinderFileManager = ref("elfinderFileManager")
		 }

		 elfinderTreeCommand(ElfinderTreeCommand) { bean ->
			 bean.scope = "prototype"
			 elFinderFileManager = ref("elfinderFileManager")
		 }

		 elfinderMkdirCommand(ElFinderMkdirCommand) { bean ->
			 bean.scope = "prototype"
			 elFinderFileManager = ref("elfinderFileManager")
		 }

		 elfinderMkfileCommand(ElFinderMkfileCommand) { bean ->
			 bean.scope = "prototype"
			 elFinderFileManager = ref("elfinderFileManager")
		 }

		 elfinderRenameCommand(ElFinderRenameCommand) { bean ->
			 bean.scope = "prototype"
			 elFinderFileManager = ref("elfinderFileManager")
		 }

		 elfinderRmCommand(ElFinderRmCommand) { bean ->
			 bean.scope = "prototype"
			 elFinderFileManager = ref("elfinderFileManager")
		 }
		 
		 elfinderLsCommand(ElfinderLsCommand) { bean ->
			 bean.scope = "prototype"
			 elFinderFileManager = ref("elfinderFileManager")
		 }

		 elfinderFileCommand(ElFinderFileCommand) { bean ->
			 bean.scope = "prototype"
			 elFinderFileManager = ref("elfinderFileManager")
		 }

		 elfinderUploadCommand(ElFinderUploadCommand) { bean ->
			 bean.scope = "prototype"
			 elFinderFileManager = ref("elfinderFileManager")
		 }

    }


    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }

}
