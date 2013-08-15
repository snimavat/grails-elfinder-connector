package grails.plugin.elfinder.command

/**
 * @author Sudhir Nimavat
 */
class ElFinderRenameCommand extends ElfinderBaseCommand {

	@Override
	void execute() {
		String current =  params['current']
		String target =  params['target']
		String name = params['name']

		String renamed = elFinderFileManager.rename(name, unhash(target))
		putResponse("added", [elFinderFileManager.cwd(unhash(renamed))])
		putResponse("removed", [target])
	}
}
