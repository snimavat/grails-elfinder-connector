package grails.plugin.elfinder.command

/**
 * 
 * @author Sudhir Nimavat
 *
 */
class ElFinderRmCommand extends ElfinderBaseCommand {
		
	@Override
	void execute() {
		List targets =  params.list('targets[]')	
		
		List removed = []
		targets.each { 
			removed.addAll(elFinderFileManager.delete(unhash(it)))
		}			
		putResponse("removed",removed )	
	}
}
