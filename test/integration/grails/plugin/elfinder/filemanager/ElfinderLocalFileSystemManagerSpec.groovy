package grails.plugin.elfinder.filemanager

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class ElfinderLocalFileSystemManagerSpec extends Specification {

	@Shared ElfinderLocalFileSystemFileManager manager

	def setupSpec() {
		manager = new ElfinderLocalFileSystemFileManager()
	}

	@Unroll()
	void "Relative path of #path is #relative"() {
		setup:
		manager.root = "test/integration/resources"

		expect:
		manager.getPathRelativeToRoot(path) == relative

		where:
		path                                        | relative
		"test/integration/resources"                | "root"
		"test/integration/resources/dir1"           | "dir1"
		"test/integration/resources/dir1/dir2"      | "dir1/dir2"
		"test/integration/resources/dir1/file.txt"  | "dir1/file.txt"
	}

	@Unroll()
	void "Verify file info for path #path"() {
		setup:
		manager.root = "test/integration/resources"

		when:
		Map info = manager.cwd(path)

		then:
		info != null
		info.name == name
		info.hash == pathhash
		info.phash == phash

		where:
		path									| name				| pathhash							| phash					| mime
		"test/integration/resources"	| "resources"		| hash("root")						| null					| "directory"
		"/dir1"								| "dir1"				| hash("dir1")						| hash("root")			| "directory"
		"/dir1/dir2/file.txt"			| "file.txt"		| hash("dir1/dir2/file.txt")	| hash("dir1/dir2")	| "text/plain"
	}

	@Unroll
	void "scanDir: for #path"() {
		setup:
		manager.root = "test/integration/resources"

		when:
		List files = manager.scanDir(path)

		then:
		files.size == size

		where:
		path                           | size
		"test/integration/resources"   | 2
		"/dir1"                        | 2
		"/dir1/dir3"                   | 1
	}

	void "verify isRoot"() {
		setup:
		manager.root = "test/integration/resources"

		expect:
		manager.isRoot(new File("test/integration/resources"))
	}

	@Unroll
	void "verify parents for #path"() {
		setup:
		manager.root = "test/integration/resources"

		when:
		List files = manager.parents(path)

		then:
		files.size == size

		where:
		path                           | size
		"test/integration/resources"   | 1
		"/dir1"                        | 1
		"/dir1/dir3"                   | 4

	}

	void "getTree: verify file and directories info"() {
		setup:
		manager.root = "test/integration/resources"

		when:
		List files = manager.getTree("test/integration/resources", 1)

		then:
		files[0].name == "dir1"
		files[1].name == "dir2"
	}

	String hash(String str) {
		return manager.hash(str)
	}

	String unhash(String str) {
		return manager.unhash(str)
	}
}
