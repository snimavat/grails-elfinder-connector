package grails.plugin.elfinder.command

/**
 * 
 * @author Sudhir Nimavat
 *
 */
class ElFinderMkdirCommand extends ElfinderBaseCommand {

	@Override
	void execute() {
		String target =  params['target']
		String name = params['name']

		putResponse("added", elFinderFileManager.mkdir(name, unhash(target)))
	}
}
