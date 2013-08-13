package grails.plugin.elfinder.command

import org.springframework.jca.cci.object.EisOperation;

/**
 * @author Sudhir Nimavat
 *
 */
class ElfinderLsCommand extends ElfinderBaseCommand {
	
	void execute() {
		String target = params['target']
		
		List files = elFinderFileManager.files(target, false)
		List resp = files.collect { it.name }
		
		putResponse("list", resp)
	}
}
