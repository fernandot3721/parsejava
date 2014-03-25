/**
 * 
 */
package javaparser;

import japa.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.Callable;

import javax.swing.plaf.basic.BasicScrollPaneUI.HSBChangeListener;

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

	// packages to be test if imported
	private HashMap<String, ClassInfo> mTargetPackages;
	
	// class to be test
//	private HashSet<ClassInfo> mTargetClasses;

	// interfaces to be test if called
	private HashSet<CoreInterfaces> mTargetInterfaces;

	public void setTargetPackages(HashMap<String, ClassInfo> targetPackages) {
		this.mTargetPackages = targetPackages;
	}
	
	public void setTargetClasses(HashSet<ClassInfo> targetClasses) {
//		this.mTargetClasses = targetClasses;
	}
	
	public void setTargetInterfaces(HashSet<CoreInterfaces> targetInterfaces) {
		this.mTargetInterfaces = targetInterfaces;
	}

	public ShellInfo(HashMap<String, ClassInfo> targetPackages,
//	public ShellInfo(HashSet<String> targetPackages, HashSet<ClassInfo> targetClasses,
			HashSet<CoreInterfaces> targetInterfaces) {
		this.mTargetPackages = targetPackages;
//		this.mTargetClasses = targetClasses;
		this.mTargetInterfaces = targetInterfaces;
		this.mShellClasses = new HashSet<>();
		this.mImportedClasses = new HashSet<>();
		this.mCalledMethods = new HashSet<>();
		this.mTargetFiles = new HashSet<>();
	}

	/**
	 * Get Shell Files
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
			} catch (MyException e) {
//				System.out.println(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mShellClasses.add(tmpClass);
		}
		System.out.println("total shell files: " + mShellClasses.size());
		this.scanImportedCoreClass();
	}
	
	private void test() {
		HashSet<String> importList = new HashSet<>();
		HashSet<String> callMethod = new HashSet<>();
				
		for (ShellClassInfo shellClass : mShellClasses) {
			importList.addAll(shellClass.getImportList());
			callMethod.addAll(shellClass.getCalledMethods());
		}

		String msg = null;

		PrintWriter pr = null;
		try {
			pr = new PrintWriter("printTool.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		pr.println("Shell import " + importList.size() + " pacages as below: ");
		for (String im : importList) {
			pr.println(im);
		}
		pr.println("Shell call " + callMethod.size() + " methods as below: ");
		for (String ca : callMethod) {
			pr.println(ca);
		}

	}
	
	private void scanImportedCoreClass() {
		HashSet<ClassInfo> importedClasses = new HashSet<>();
		HashSet<String> importList = null;
		ClassInfo coreClass = null;
		
//		int i = 0;
		for (ShellClassInfo shellClass : mShellClasses) {
			importList = shellClass.getImportList();
			for (String importName : importList) {
				if (mTargetPackages.containsKey(importName)) {
					coreClass = mTargetPackages.get(importName);
					mImportedClasses.add(coreClass);
//					i++;kkkkkkk
//					System.out.println(importName);
				}
			}
		}
		
		//test
		System.out.println(mImportedClasses.size());
	}
	
	private void scanCalledInterfaces() {
		// filter out not imported classes
		HashSet<ClassInfo> coreClasses = new HashSet<ClassInfo>();
		for (CoreInterfaces method : mTargetInterfaces) {
			if (mImportedClasses.contains(method.getBelongClass())) {
				// TODO match shell call methods and targetinterfaces
			}
		}
	}
}
