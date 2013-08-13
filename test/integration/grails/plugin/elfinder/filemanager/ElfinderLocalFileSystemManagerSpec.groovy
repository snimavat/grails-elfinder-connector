package grails.plugin.elfinder.filemanager

import spock.lang.Specification


class ElfinderLocalFileSystemManagerSpec extends Specification {
	
	ElfinderLocalFileSystemFileManager manager
	
	def setup() {
		manager = new ElfinderLocalFileSystemFileManager()
	}
	
	void "relativePathToRoot"() {
		setup:
		manager.root = "test/integration/resources"
		
		expect:
		manager.getPathRelativeToRoot(path) == relative
		
		where:
		path                                        | relative
		"test/integration/resources"                | ""
		"test/integration/resources/dir1"           | "dir1"
		"test/integration/resources/dir1/dir2"      | "dir1/dir2"
		"test/integration/resources/dir1/file.txt"  | "dir1/file.txt"				
	}
	
	void "file: verify file info"() {
		setup:
		manager.root = "test/integration/resources"
		
		when:
		Map info = manager.file(path)
		
		then:
		info != null
		info.name == name
		info.hash == hash
		info.phash == phash
		
		where:
		path									| name				| hash								| phash					| mime	
		"test/integration/resources"	| "resources"		| hash("")							| null					| "directory"
		"/dir1"								| "dir1"				| hash("dir1")						| hash("")				| "directory"
		"/dir1/dir2/file.txt"			| "file.txt"		| hash("dir1/dir2/file.txt")	| hash("dir1/dir2")	| "text/plain"
	}
	
	void "files: verify files list"() {
		setup:
		manager.root = "test/integration/resources"
		
		when:
		List files = manager.files(path, false)
		
		then:
		files.size == size
				
		where:
		path									| size
		"test/integration/resources"	| 2
		"/dir1"								| 2
		"/dir1/dir2"						| 1
		

	}

	void "files: verify file and directories info"() {
		setup:
		manager.root = "test/integration/resources"
		
		when:
		List files = manager.files("test/integration/resources", true)
		
		then:
		files.size == 2
		files.find({ it instanceof Map && it.name == "3.txt"}) != null
		files.find({ it instanceof List && it[0].name == "dir1"}) != null

	}

	void "tree: verify file and directories info"() {
		setup:
		manager.root = "test/integration/resources"
		
		when:
		List files = manager.tree("test/integration/resources")
		
		then:
		files[0].name == "resources"
		files[1][0].name == "dir1"
		files[1][1][0].name == "dir2"

	}

	
	void "parents: verify parents tree"() {
		
	}
	
		
	String hash(String str) {
		str.encodeAsBase64()
	}
	
	String unhash(String str) {
		return new String(hash.decodeBase64())
	}
}
