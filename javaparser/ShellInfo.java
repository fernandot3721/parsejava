/**
 * 
 */
package javaparser;


import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Vector;

/**
 * @author tangjp
 *
 */

public class ShellInfo {

	private final String TAG = "TJPLOG: ShellInfo";

	/**
	 * All shell classes
	 */
	private TreeSet<ShellClassInfo> mShellClasses;

	
	
	/**
	 * All core classes imported
	 * CoreFilesBeingImport
	 */
	private TreeSet<ClassInfo> mImportedClasses; 
	
	/**
	 * All shell classes which import core class
	 * ShellClassImportingCoreClass
	 */
	private TreeSet<ShellClassInfo> mClassesImportingCore; 

	/**
	 * All core interfaces possible being imported
	 * ImportedInterfaces
	 */
	private Vector<CoreInterfaces> mImportedInterfaces;
	
	/**
	 * All core interfaces being called by shell
	 * CalledCoreInterfaces
	 */
	private Vector<CoreInterfaces> mCalledMethods;
	
	/**
	 * All interfaces that shell calls
	 * ShellCallInterfaces
	 */
	private Vector<ShellCalledInterfaces> mShellCalls;	
	
	
	
	
	// files to analyze
	private Vector<String> mTargetFiles;

	// packages to be test if imported
	private HashMap<String, ClassInfo> mTargetPackages;
	
	// interfaces to be test if called
	private Vector<CoreInterfaces> mTargetInterfaces;
	
	public void setTargetPackages(HashMap<String, ClassInfo> targetPackages) {
		this.mTargetPackages = targetPackages;
	}
	
	public void setTargetClasses(HashSet<ClassInfo> targetClasses) {
//		this.mTargetClasses = targetClasses;
	}
	
	public ShellInfo(HashMap<String, ClassInfo> targetPackages,
			Vector<CoreInterfaces> targetInterfaces) {
		this.mTargetPackages = targetPackages;
		this.mTargetInterfaces = targetInterfaces;
		this.mShellClasses = new TreeSet<>();
		this.mImportedClasses = new TreeSet<>();
		this.mClassesImportingCore = new TreeSet<>();
		this.mTargetFiles = new Vector<>();
		this.mImportedInterfaces = new Vector<CoreInterfaces>();
	}

	/**
	 * Get Shell Files
	 */
	private void getFiles(String path) {
		GetJavaFiles getter = new GetJavaFiles(mTargetFiles);
		try {
			getter.getFiles(path);
		} catch (MyFatalException e) {
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

		// parse shell files
		for (String file : mTargetFiles) {
			try {
				tmpClass = parser.parseShellFile(file);
				mShellClasses.add(tmpClass);
			} catch (MyException e) {
//				System.out.println(e.getMessage());
				//e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MyFatalException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.scanImportedCoreClass();
		this.scanCalledInterfaces();
		if (Testoutput.LOG) {
			System.out.println("######shell files:"+mTargetFiles.size());
			this.test();
		}
	}
	
	private void test() {
		System.out.println(TAG + "total shell classes: " + mShellClasses.size());
		//System.out.println(mClassesImportingCore.size() + " shell files");
		//System.out.println("import " + mImportedClasses.size() + " core files");
		
		//System.out.println(TAG + " Imported classes: " + mImportedClasses.size());
		//System.out.println(TAG + " Called interfaces: " + mCalledMethods.size());
	}
	
	private void scanImportedCoreClass() {
		HashSet<String> importList = null;
		ClassInfo coreClass = null;
		
		for (ShellClassInfo shellClass : mShellClasses) {
			importList = shellClass.getImportList();
			for (String importName : importList) {
				if (mTargetPackages.containsKey(importName)) {
					coreClass = mTargetPackages.get(importName);
					mImportedClasses.add(coreClass);
					mClassesImportingCore.add(shellClass);
				}
			}
		}
		
	}
	
	private void scanCalledInterfaces() {
//		HashSet<String> methodNames = new HashSet<>();
//		int duplicate = 0;

		ChangeChecker cc = new ChangeChecker();
		
		// filter out not imported classes
		// only those public interface from core whose daddy being import from shell survive
		for (CoreInterfaces method : mTargetInterfaces) {// 40
			if (mImportedClasses.contains(method.getBelongClass())) {
				mImportedInterfaces.add(method);//1361
				
				cc.addCoreInterfaces(method);
			}
		}
		
		
		// match method called by shell with imported interfaces
		Vector<ShellCalledInterfaces> callMethod = null;
		
		for (ShellClassInfo shellClass : mClassesImportingCore) {//61
			callMethod = shellClass.getCalledMethods();
			for (ShellCalledInterfaces method : callMethod) { //methods in classes
				cc.addShellCalledInterfaces(method);
			}
			
		}
		cc.checkCalledInterfaces();
		
		/*
		String tempStr = null;
		for (CoreInterfaces inter : mImportedInterfaces) {// 1361
			tempStr = inter.getName();
			if (methodNames.contains(tempStr)) {
				duplicate++;
			} else {
				methodNames.add(tempStr);
			}

		}
		*/
		
		// get unique method name map
		String tmpStr = null;
		HashMap<String, ClassInfo> uniqInterface = new HashMap<>();
		HashSet<String> duplicateName = new HashSet<String>();
		for (CoreInterfaces inter : mImportedInterfaces) {
			tmpStr = inter.getName();
			if (uniqInterface.containsKey(tmpStr)) {
				duplicateName.add(tmpStr);
			} else {
				uniqInterface.put(tmpStr, inter.getBelongClass());
			}

		}

		// get duplicate methods
		/*
		ClassInfo tmpClass = null;
		HashSet<CoreInterfaces> duplicateInterface = new HashSet<CoreInterfaces>();
		for (String name : duplicateName) {
			tmpClass = uniqInterface.remove(name);
		}
		*/
		
		mCalledMethods = cc.getCalledCoreInterfaces();
		mShellCalls = cc.getShellCallInterfaces();
		
		/*
		System.out.println(TAG + " cc core count: " + cc.getCoreCount());
		System.out.println(TAG + " cc core called count: " + cc.getCalledCoreCount());
		System.out.println(TAG + " cc shell count: " + cc.getShellCallCount());

		System.out.println(TAG + " Called interfaces uniq set: " + methodNames.size());
		System.out.println(TAG + " Called interfaces duplicate set: " + duplicate);
		*/
	}
	
	

	public TreeSet<ClassInfo> getImportedClasses() {
		return mImportedClasses;
	}

	public TreeSet<ShellClassInfo> getClassesImportingCore() {
		return mClassesImportingCore;
	}

	public Vector<CoreInterfaces> getImportedInterfaces() {
		return mImportedInterfaces;
	}

	public Vector<CoreInterfaces> getCalledMethods() {
		return mCalledMethods;
	}

	public Vector<ShellCalledInterfaces> getShellCalls() {
		return mShellCalls;
	}

}
