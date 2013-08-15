package grails.plugin.elfinder.command

/**
 * 
 * @author Sudhir Nimavat
 *
 */
class ElfinderOpenCommand extends ElfinderBaseCommand {
	
	public void execute() {		
		boolean init = params['init'] == "1"
		String target =  params['target'] ?: null
		boolean tree = params['tree'] == "1"

		
		if(init) {
			log.debug("Init: Elfinder")			
			putResponse("api", "2.0")
			putResponse("netDrivers", [])
			
			if(!target) {
				target = hash(elFinderFileManager.root)
			}
		}					
		
		if(!target) {
			addError("File not found")
			return			
		} 
		
		putResponse("uplMaxSize ", "1M")
		 		
		cwd(target)
		files(target, tree)
				
		putResponse("options", elFinderFileManager.options(unhash(target)))				
	}
}
