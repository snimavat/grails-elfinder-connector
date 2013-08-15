package grails.plugin.elfinder.filemanager

import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.commons.CommonsMultipartFile

/**
 * @author Sudhir Nimavat
 *
 */
public interface ElFinderFileManager {

	String getRoot()

	/**
	 * Returns file info
	 * 	
	 */
	Map cwd(String path)

	/**
	 * Return list of file info for each file and directory within given directory path
	 */
	List<Map> scanDir(String path)

	/**
	 * Return subdirectory tree for given path
	 */
	List<Map> getTree(String path, int deep)

	/**
	 *	Retruns parents folder up to root for given path
	 */
	List parents(String path)

	/**
	 * Return options
	 */
	Map options(String path)

	/**
	 * Return hash for given path
	 */
	String hash(String str)

	/**
	 *Return path for given hash 
	 */
	String unhash(String str)

	/**
	 * Create new directory 
	 */
	List mkdir(String name, String target)

	/**
	 * Create new file 
	 */
	List mkfile(String name, String target)

	/**
	 * Rename file or directory
	 */
	String rename(String name, String target)

	/**
	 *Delete file or directory for given path, If directory is not empty, recursively delete all contents.
	 * @return List of hash of all files and directories removed. 
	 */
	List delete(String path)

	/**
	 * Return file input stream for given path
	 */
	InputStream getFileInputStream(String path)

	/**
	 * Upload file to target directory and return file info if successful.
	 */
	Map uploadFile(CommonsMultipartFile file, String targetDir)
}
