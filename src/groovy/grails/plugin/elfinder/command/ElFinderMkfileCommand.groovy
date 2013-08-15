package grails.plugin.elfinder.command

/**
 * @author Sudhir Nimavat
 *
 */
class ElFinderMkfileCommand extends ElfinderBaseCommand {

	@Override
	void execute() {
		String target =  params['target']
		String name = params['name']
		putResponse("added", elFinderFileManager.mkfile(name, unhash(target)))
	}
}
