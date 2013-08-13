package grails.plugin.elfinder.filemanager

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author Sudhir Nimavat
 *
 */
class ElfinderLocalFileSystemFileManager implements ElFinderFileManager {

	Logger log = LoggerFactory.getLogger("grails.plugin.elfinder.filemanager.ElfinderLocalFileSystemFileManager")
			
	String root
	
	int treeDeepLevel = 1
	
	@Override
	public String getRoot() {
		return root
	}

	@Override
	public Map file(String path) {
		println path
		return file(toFile(path))
	}

	public file(File f) {
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
	public List files(String path, boolean showTree) {
		log.debug("Files : $path" )
		
		File f = toFile(path)
		if(!f.isDirectory()) return null
						
		List files = []		

		files << file(f)
		if(showTree) {
			files.addAll(tree(getRootDir(), f))
		}		
		
		
		f.eachFile {child ->
			def info = file(child)
			if(!files.contains(info)) {
				files << info
			}
		}
		
		
		return files
	}

	
	
	@Override
	public List tree(String path) {
		return tree(toFile(path))
	}

	public List tree(File f, exclude = null) {
		if(!f.isDirectory()) return null

		List l = []
		List dirs = gettree(f, 0, exclude)
	
		l << file(f)
		l.addAll(dirs)
		
		return l
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
			def info = file(dir)
			
			tree.add(0, info)
			
			if(!isRoot(dir)) {
				gettree(dir, 0).each {
					if(!tree.contains(it)) {
						tree << it
					}
				}
			}
			
		}
		
		return tree ?: [file(current)]
	}
	
	private List gettree(File f, int deep, exclude = null) {
		List dirs = []
				
		f.eachFile {child ->
			if(child.isDirectory()) {
			def info = file(child)
			dirs << info
			if(deep > 0) {
				dirs.addAll(gettree(child), deep -1)
			}
			}
		}
		
		return dirs
	}
	

	List mkdir(String name, String target) {
		String parent = toFile(target)
		File newDir = new File(parent, name)
		def success = newDir.mkdir()
		
		def resp = []
		
		if(success) {
			resp << file(newDir)
		}
	}

	List mkfile(String name, String target) {
		String parent = toFile(target)
		File newFile = new File(parent, name)
		def success = newFile.createNewFile()		
		def resp = []		
		if(success) {
			resp << file(newFile)
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
		options.url = "http://localhost/files/1"
		
		
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
		
		log.debug("uploading file $name to $targetDir")
		
		File f = new File(directory, name)
		boolean success = f.createNewFile()
		if(success) {
			multiPartFile.transferTo(f)
			return file(f)	
		}
	   return null;
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
}
