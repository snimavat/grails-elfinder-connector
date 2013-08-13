package grails.plugin.elfinder.filemanager;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author Sudhir Nimavat
 *
 */
public interface ElFinderFileManager {
	
	String getRoot() 

	/**
	 * Returns file info
	 * 	
	 * @param path - Path relative to root
	 * @return
	 */
	Map file(String path)
	
	/**
	 * Returns file info for all the files in given directory
	 * 
	 * @param path
	 * @return
	 */
	List files(String path, boolean tree)
	
	/**
	 * Return directory tree for given path
	 * 
	 * @param path
	 * @return
	 */
	List tree(String path)
		
	/**
	 * Retruns parents folder up to root for given path
	 * 
	 * @param path
	 * @return
	 */
	List parents(String path)
	
	
	Map options(String path)
	
	String hash(String str)	
	String unhash(String str)
	
	List mkdir(String name, String target)
	List mkfile(String name, String target)
	String rename(String name, String target)
	
	/**
	 * Return file input stream for given path
	 * @param path
	 * @return
	 */
	InputStream getFileInputStream(String path)
	
	/**
	 * Upload file to target directory and return file info if successful.
	 * @param file
	 * @param targetDir
	 * @return
	 */
	Map uploadFile(CommonsMultipartFile file, String targetDir)
}
