/**
 * 
 */
package testparser2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashSet;

import javax.swing.plaf.metal.MetalIconFactory.FileIcon16;

/**
 * @author tangjp
 *
 */
public class CoreInfo {
	
	private final String TAG = "TJPLOG: CoreInfo";

	/**
	 * All Core class info
	 */
	private HashSet<ClassInfo> mClasses;
	
	/**
	 * All Core Interfaces
	 */
	private HashSet<CoreInterfaces> mInterfaces;
	
	// files to analyze
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
	
	/**
	 * Parse all core files 
	 * @param path Core java file directory
	 */
	public void parseFile(String path) {
		this.getFiles(path);
		ClassParser parser = new ClassParser();
		ClassInfo tmpClass = null;
		CoreInterfaces tmpInter = null;
		HashSet<String> tmpInterList = null;
		
		// parsse core files
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
	}
	
//================================================================================	
	// test code
	public void testGetFiles() {
		this.getFiles("G:\\svn\\ucm\\9.7.0\\core");
		System.out.println(mTargetFiles.toString());
		System.out.println(mTargetFiles.size());
	}
}
