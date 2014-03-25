/**
 * 
 */
package javaparser;

import japa.parser.ParseException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
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
	
	/**
	 * All Core packages
	 */
	private HashMap<String, ClassInfo> mPackages;

	// files to analyze
	private HashSet<String> mTargetFiles;
	
	public CoreInfo() {
		this.mClasses = new HashSet<ClassInfo>();
		this.mInterfaces = new HashSet<CoreInterfaces>();
		this.mTargetFiles = new HashSet<String>();
		this.mPackages = new HashMap<String, ClassInfo>();
	}

	public HashSet<ClassInfo> getClasses() {
		return mClasses;
	}

	public HashSet<CoreInterfaces> getInterfaces() {
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
//				System.out.println(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
		CoreInterfaces tmpInter = null;
		HashSet<String> tmpInterList = null;
		String packageName = null;
		
		for (ClassInfo coreClass : mClasses) {
			// core interfaces
			tmpInterList = coreClass.getMethods();
			for (String method : tmpInterList) {
				tmpInter = new CoreInterfaces(method, coreClass);
				mInterfaces.add(tmpInter);
			}
			
			// core packages
//			mPackages.add(coreClass.getPackageName());
			mPackages.put(coreClass.getClassFullName(), coreClass);
		}
		this.test();
	}
	
//================================================================================	
	private void test() {
		// for test
		String msg = null;
//		
//		PrintWriter pr = null;
//		try {
//			pr = new PrintWriter("print.txt");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		for (CoreInterfaces method : mInterfaces) {
//			msg = "Method: " + method.getName() + " Class: " + method.getBelongClass().getClassFullName();
//			System.out.println(msg);
//			pr.println(msg);
//			System.out.println(msg);
//		}
//		for (String pa : mPackages) {
//			msg = "Package: " + pa;
//			pr.println(msg);
//		}
//
//		pr.close();
//		
		System.out.println("total interface: " + mInterfaces.size());
		System.out.println("total packages: " + mPackages.size());
		System.out.println("total class: " + mClasses.size());
		
		
	}

	// test code
	public void testGetFiles() {
		this.getFiles("G:\\svn\\ucm\\9.7.0\\core");
		System.out.println(mTargetFiles.toString());
		System.out.println(mTargetFiles.size());
	}
}
