/**
 * 
 */
package javaparser;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

/**
 * @author tangjp
 *
 */
public class CoreInfo {
	
	private final String TAG = "TJPLOG: CoreInfo";

	/**
	 * All Core class info
	 */
	private Vector<ClassInfo> mClasses;
	
	/**
	 * All Core Interfaces
	 */
	private Vector<CoreInterfaces> mInterfaces;
	
	/**
	 * All Core packages
	 */
	private HashMap<String, ClassInfo> mPackages;

	// files to analyze
	private HashSet<String> mTargetFiles;
	
	public CoreInfo() {
		this.mClasses = new Vector<ClassInfo>();
		this.mInterfaces = new Vector<CoreInterfaces>();
		this.mTargetFiles = new HashSet<String>();
		this.mPackages = new HashMap<String, ClassInfo>();
	}

	public Vector<ClassInfo> getClasses() {
		return mClasses;
	}

	public Vector<CoreInterfaces> getInterfaces() {
		return mInterfaces;
	}

	public HashMap<String, ClassInfo> getPackages() {
		return mPackages;
	}

	/**
	 * Get core Files
	 */
	private void getFiles(String path) {
		GetJavaFiles getter = new GetJavaFiles(mTargetFiles);
		try {
			getter.getFiles(path);
		} catch (MyException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parse all core files and get core interfaces
	 * @param path Core java file directory
	 */
	public void parseFile(String path) {
		this.getFiles(path);
		ClassParser parser = new ClassParser();
		ClassInfo tmpClass = null;
		
		// parsse core files
		for (String file : mTargetFiles) {
			try {
				tmpClass = parser.parseCoreFile(file);
			} catch (MyException e) {
			} catch (IOException e) {
//				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			mClasses.add(tmpClass);
		}
		
		this.scanCoreInterfacesAndPackages();
	}
	
	/**
	 * get all core interfaces and packages
	 */
	private void scanCoreInterfacesAndPackages() {
		for (ClassInfo coreClass : mClasses) {
			// core interfaces
			
			for (CoreInterfaces inter : coreClass.getMethods()) {
				mInterfaces.add(inter);
			}
			
			// core packages
			mPackages.put(coreClass.getClassFullName(), coreClass);
		}
//		System.out.println(TAG + " total public interface(may duplicate): " + methoSet.size());
//		System.out.println(TAG + " total duplicate: " + duplicate);
		this.test();
	}
	
//================================================================================	
	private void test() {
		System.out.println(TAG + " total public interface: " + mInterfaces.size());
		System.out.println(TAG + " total packages: " + mPackages.size());
		System.out.println(TAG + " total class: " + mClasses.size());
		
		
	}

	// test code
	public void testGetFiles() {
		this.getFiles("G:\\svn\\ucm\\9.7.0\\core");
		System.out.println(mTargetFiles.toString());
		System.out.println(mTargetFiles.size());
	}
}
