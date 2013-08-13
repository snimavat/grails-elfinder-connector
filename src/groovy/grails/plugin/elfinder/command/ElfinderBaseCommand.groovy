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
		putResponse("cwd", elFinderFileManager.file(unhash(target)))
	}
	
	void files(String target, boolean tree = false) {
		def files = elFinderFileManager.files(unhash(target), tree)
		putResponse("files", files)
	}
	
	
	void tree(String target) {
		List tree = elFinderFileManager.tree(unhash(target))
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
}
