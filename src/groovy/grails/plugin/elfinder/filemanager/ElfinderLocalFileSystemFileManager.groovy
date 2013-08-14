package grails.plugin.elfinder.filemanager

import org.springframework.web.multipart.commons.CommonsMultipartFile

/**
 * @author Sudhir Nimavat
 *
 */
class ElfinderLocalFileSystemFileManager implements ElFinderFileManager {

	String root

	@Override
	public String getRoot() {
		return root
	}

	@Override
	public Map cwd(String path) {
		return cwd(toFile(path))
	}

	public cwd(File f) {
		File file  = f
		Map info = [:]
		info.name = file.name
		info.hash = hash(getPathRelativeToRoot(file))
		if(!isRoot(f)) {
			info.phash = hash(getPathRelativeToRoot(file.parent))
		} else {
			info.volumeid = "v1_"
		}
		info.mime = file.isDirectory() ? "directory" :"text/plain"
		info.ts = file.lastModified()
		info.size = file.size()
		info.dirs = 1
		info.read = 1
		info.write = 1
		info.locked = 0

		return info
	}

	@Override
	List<Map> scanDir(String path) {
		File dir = toFile(path)
		return scanDir(dir)
	}

	
	List<Map> scanDir(File dir) {
		List files = []
		if(dir.isDirectory()) {
			dir.eachFile {child ->
				files << cwd(child)
			}
		}
		return files
	}


	public List<Map> getTree(String path, int deep) {
		return getTree(toFile(path), deep)
	}

	public List<Map> getTree(File f, int deep) {
		List dirs = []
		f.eachFile {File child ->
			if(child.isDirectory()) {
				def info = cwd(child)
				dirs << info
				if(deep > 0) {
					dirs.addAll(getTree(child, deep -1))
				}
			}
		}
		return dirs
	}

	@Override
	public List parents(String path) {
		File f = toFile(path)
		return parents(f)
	}

	public List parents(File current) {
		List tree = []
		File dir = current
		while(!isRoot(dir)) {
			dir = dir.parentFile
			def info = cwd(dir)
			tree.add(0, info)
			if(!isRoot(dir)) {
				getTree(dir, 0).each {
					if(!tree.contains(it)) {
						tree << it
					}
				}
			}
		}

		return tree ?: [cwd(current)]
	}


	List mkdir(String name, String target) {
		String parent = toFile(target)
		File newDir = new File(parent, name)
		def success = newDir.mkdir()

		def resp = []

		if(success) {
			resp << cwd(newDir)
		}
	}

	List mkfile(String name, String target) {
		String parent = toFile(target)
		File newFile = new File(parent, name)
		def success = newFile.createNewFile()
		def resp = []
		if(success) {
			resp << cwd(newFile)
		}
	}

	String rename(String name, String target) {
		File old = toFile(target)
		File newFile = new File(old.parentFile, name)
		old.renameTo(newFile)
		return hash(getPathRelativeToRoot(newFile))
	}

	Map options(String path) {
		Map options = [:]
		options.seperator = "/"
		options.path = (getRootDir().name) + (isRoot(toFile(path)) ? "" : "/"+getPathRelativeToRoot(toFile(path)))
		//options.url = "http://localhost/files/1"		
		return options
	}

	
	@Override
	public InputStream getFileInputStream(String path) {
		File file = toFile(path)
		return new FileInputStream(file)
	}

	@Override
	public Map uploadFile(CommonsMultipartFile multiPartFile, String targetDir) {
		String name = multiPartFile.getOriginalFilename()
		String directory = toFile(targetDir)

		File f = new File(directory, name)
		boolean success = f.createNewFile()
		if(success) {
			multiPartFile.transferTo(f)
			return cwd(f)
		}
		return null
	}


	String hash(String str) {
		String hashed = str.encodeAsBase64()
		hashed = hashed.replace("=", "")
		hashed = hashed.replace("+", "-")
		hashed = hashed.replace("/", "_")

		hashed = "v1_"+hashed

		return hashed
	}

	String unhash(String hashed) {

		hashed = hashed.substring(3)

		hashed = hashed.replace(".", "=")
		hashed = hashed.replace("-", "+")
		hashed = hashed.replace("_", "/")
		return new String(hashed.decodeBase64())
	}
	
	private File toFile(String path) {
		if(path.trim() == root || path == "root") {
			return rootDir
		} else {
			return new File(root, path)
		}
	}


	public boolean isRoot(File f) {
		f == rootDir
	}

	public File getRootDir() {
		return new File(root)
	}

	public String getPathRelativeToRoot(File file) {
		String path = rootDir.toURI().relativize(file.toURI()).getPath()
		if(path.endsWith("/")) {
			path = path.substring(0, path.length() - 1)
		}

		if(path.trim() == "") {
			return "root"
		}
		return path
	}

	public String getPathRelativeToRoot(String path) {
		File file = new File(path)
		return getPathRelativeToRoot(file)
	}

}
