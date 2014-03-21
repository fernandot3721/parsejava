/**
 * 
 */
package testparser2;

import java.util.HashSet;

/**
 * @author tangjp
 *
 */
/**
 * @author tangjp
 *
 */
/**
 * @author tangjp
 *
 */
public class ShellInfo {

	private final String TAG = "TJPLOG: ShellInfo";

	/**
	 * All shell classes
	 */
	private HashSet<ShellClassInfo> mShellClasses;

	/**
	 * All core classes imported
	 */
	private HashSet<ClassInfo> mImportedClasses;

	/**
	 * All core interfaces called
	 */
	private HashSet<CoreInterfaces> mCalledMethods;
	
	// files to analyze
	private HashSet<String> mTargetFiles;

	public ShellInfo() {
		this.mShellClasses = new HashSet<>();
		this.mImportedClasses = new HashSet<>();
		this.mCalledMethods = new HashSet<>();
	}

	/**
	 * Get Shell Files
	 */
	public void getFiles(String path) {
		GetJavaFiles getter = new GetJavaFiles(mTargetFiles);
		try {
			getter.getFiles(path);
		} catch (MyException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Parse all shell files
	 * @param path Shell java file diretory
	 */
	public void parseFile(String path) {
		this.getFiles(path);
		ClassParser parser = new ClassParser();
		ShellClassInfo tmpClass = null;

		CoreInterfaces tmpInter = null;
		HashSet<String> tmpInterList = null;

		// parse shell files
		for (String file : mTargetFiles) {
			try {
				tmpClass = parser.parseShellFile(file);
				mShellClasses.add(tmpClass);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/*
		for (ClassInfo coreClass : mClasses) {
			tmpInterList = coreClass.getMethods();
			for (String method : tmpInterList) {
				tmpInter = new CoreInterfaces(method, coreClass);
				mInterfaces.add(tmpInter);
			}
		}
		
		String msg = null;

		PrintWriter pr = null;
		try {
			pr = new PrintWriter("print.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (CoreInterfaces method : mInterfaces) {
			msg = "Method: " + method.getName() + " Class: " + method.getBelongClass().getClassFullName();
			pr.println(msg);
//			System.out.println(msg);
		}
		pr.close();
		
		System.out.println("total: " + mInterfaces.size());
		*/
	}
}
