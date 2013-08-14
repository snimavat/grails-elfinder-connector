package grails.plugin.elfinder.command

import grails.plugin.elfinder.filemanager.ElFinderFileManager

import org.apache.commons.lang.Validate


class ElfinderBaseCommand {
	
	ElFinderFileManager elFinderFileManager
	
	Map params = [:]
	Map responseMap = [:]
	
	def request
	def response
	
	boolean responseOutputDone = false
	
	public void execute() {	}
	
	
	protected void putResponse(def key, def val) {
		Validate.notNull(key, "Key can not be null")		
		responseMap[key] = val
	}
	
	protected void addError(def err) {
		responseMap.clear()
		responseMap["error"] = err
	}
	
	void cwd(String target) {		
		putResponse("cwd", elFinderFileManager.cwd(unhash(target)))
	}
	
	void files(String target, boolean tree = false) {
		String path = unhash(target)
		List files = []
		def currentDir = elFinderFileManager.cwd(path)
		if(isDir(currentDir)) {
			files << currentDir
		}
		
		if(tree) {
			files.addAll(elFinderFileManager.getTree(elFinderFileManager.root, 0))
		}
		
		List scanDir = elFinderFileManager.scanDir(path)
		
		for(f in scanDir) {
			if(!files.contains(f)) files << f
		} 
		
		putResponse("files", files)
	}
	
	
	void tree(String target) {
		String path = unhash(target)
		List tree = []		
		tree << elFinderFileManager.cwd(path)
		tree.addAll(elFinderFileManager.getTree(path, 0))
						
		putResponse("tree", tree)
	}
	
	void parents(String target) {
		List tree = elFinderFileManager.parents(unhash(target))
		putResponse("tree", tree)
	}
	
	String hash(String str) {
		return elFinderFileManager.hash(str)
	}
	
	String unhash(String hash) {
		return elFinderFileManager.unhash(hash)
	} 
	
	private boolean isDir(Map fileInfo) {
		return fileInfo.mime == 'directory'
	}
}
