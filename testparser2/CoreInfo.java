/**
 * 
 */
package testparser2;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.HashSet;

/**
 * @author tangjp
 *
 */
public class CoreInfo {
	
	private final String TAG = "TJPLOG: CoreInfo";
	private HashSet<ClassInfo> mClasses;
	private HashSet<CoreInterfaces> mInterfaces;
	private HashSet<String> mTargetFiles;
	private JavatFilter mFilter;
	
	

	public CoreInfo() {
		this.mClasses = new HashSet<ClassInfo>();
		this.mInterfaces = new HashSet<CoreInterfaces>();
		this.mTargetFiles = new HashSet<String>();
		this.mFilter = new JavatFilter();
	}

	/**
	 * Read from input dir and get all java files out
	 * @throws MyException given paths contain no files
	 */
	public void getFiles(String path) throws MyException {
		File dir = new File(path);
		
		// For files, add to mTargetFiles
		if (dir.isFile()) {
			mTargetFiles.add(dir.getAbsolutePath());
			return;
		}

		File[] files = dir.listFiles(mFilter);
		
		if (null == files) {
			throw new MyException(TAG + "Given path: " + path + "contains no files");
		}
		
		// For directoris, search recursively
		for (int i=0; i<files.length; i++) {
			if (files[i].isDirectory()) {
				this.getFiles(files[i].getAbsolutePath());
			} else {
				mTargetFiles.add(files[i].getAbsolutePath());
			}
		}
	}
	
	public void test() {
		try {
//			this.getFiles("J:\\model");
			this.getFiles("G:\\svn\\ucm\\9.7.0");
			System.out.println(mTargetFiles.toString());
			System.out.println(mTargetFiles.size());
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public class JavatFilter implements FileFilter {
		 
		private final String FILETER= ".java";
 
		public boolean accept(File dir, String name) {
			return (name.endsWith(FILETER));
		}

		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			return (file.getName().endsWith(FILETER));
		}
	}
}
