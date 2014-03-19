/**
 * 
 */
package testparser2;

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
	
	public CoreInfo() {
		this.mClasses = new HashSet<ClassInfo>();
		this.mInterfaces = new HashSet<CoreInterfaces>();
		this.mTargetFiles = new HashSet<String>();
	}

	/**
	 * Get core Files
	 */
	public void getFiles(String path) {
		GetJavaFiles getter = new GetJavaFiles(mTargetFiles);
		try {
			getter.getFiles(path);
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void parseFile() {
		ClassParser parser = new ClassParser();
//		parser.parseCoreFile("WebView.java");
		parser.testParseCoreFile("WebView.java");
		
	}
	
	
	// test code
	public void testGetFiles() {
		this.getFiles("G:\\svn\\ucm\\9.7.0\\core");
		System.out.println(mTargetFiles.toString());
		System.out.println(mTargetFiles.size());
	}
}
