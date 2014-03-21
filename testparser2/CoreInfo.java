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
			e.printStackTrace();
		}
	}
	
	public void parseFile(String path) {
		this.getFiles(path);
		ClassParser parser = new ClassParser();
		ClassInfo tmpClass = null;
		CoreInterfaces tmpInter = null;
		HashSet<String> tmpInterList = null;
		for (String file : mTargetFiles) {
			
			try {
				tmpClass = parser.parseCoreFile(file);
				mClasses.add(tmpClass);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		for (ClassInfo coreClass : mClasses) {
			tmpInterList = coreClass.getMethods();
			for (String method : tmpInterList) {
				tmpInter = new CoreInterfaces(method, coreClass);
				mInterfaces.add(tmpInter);
			}
		}
		
		String msg = null;
		for (CoreInterfaces method : mInterfaces) {
			msg = "Method: " + method.getName() + " Class: " + method.getBelongClass().getClassFullName();
			System.out.println(msg);
		}
		System.out.println("total: " + mInterfaces.size());
	}
	
//================================================================================	
	// test code
	public void testGetFiles() {
		this.getFiles("G:\\svn\\ucm\\9.7.0\\core");
		System.out.println(mTargetFiles.toString());
		System.out.println(mTargetFiles.size());
	}
}
