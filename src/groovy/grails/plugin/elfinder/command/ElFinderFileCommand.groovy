package grails.plugin.elfinder.command

/**
 * @author Sudhir Nimavat
 */
class ElFinderFileCommand extends ElfinderBaseCommand {

	@Override
	void execute() {
		String target =  params['target']
		String download = params['download']

		Map info = elFinderFileManager.cwd(unhash(target))

		response.setContentType("application/octet-stream")
		response.setHeader("Content-disposition", "attachment; filename=\"$info.name\"")
		response.outputStream << elFinderFileManager.getFileInputStream(unhash(target))

		responseOutputDone = true
	}
}
