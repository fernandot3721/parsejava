package javaparser;

import java.io.File;
import java.io.FileFilter;
import java.util.HashSet;
import java.util.Vector;

/**
 * @author tangjp
 *
 */
public class GetJavaFiles {

	private final String TAG = "TJPLOG: GetJavaFiles";
	private JavatFilter mFilter;
	private Vector<String> mTargetFiles;

	public GetJavaFiles(Vector<String> mTargetFiles) {
		this.mTargetFiles = mTargetFiles;
		this.mFilter = new JavatFilter();
	}

	/**
	 * Read from input dir and get all java files out
	 * @throws MyFatalException given paths contain no files
	 */
	public void getFiles(String path) throws MyFatalException {
		File dir = new File(path);
		
		// For files, add to mTargetFiles
		if (dir.isFile()) {
			mTargetFiles.add(dir.getAbsolutePath());
			return;
		}

		File[] files = dir.listFiles(mFilter);
		
		if (null == files) {
			throw new MyFatalException(TAG + "Given path: " + path + "contains no files");
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
	
	public static void test() {
		Vector<String> target = new Vector<String>();
		GetJavaFiles getter = new GetJavaFiles(target);
		try {
//			this.getFiles("J:\\model");
//			this.getFiles("G:\\svn\\ucm\\9.7.0");
			getter.getFiles("G:\\svn\\ucm\\9.7.0\\core");
			System.out.println(target.toString());
			System.out.println(target.size());
		} catch (MyFatalException e) {
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
