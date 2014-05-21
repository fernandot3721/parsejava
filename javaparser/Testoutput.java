/**
 * 
 */
package javaparser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Vector;

/**
 * @author tangjp
 *
 */
public class Testoutput {

	public static boolean LOG = false;
	private static String VERSION = "97r_";
	private static String OLD_VERSION = "970_";
	private static boolean COMPARE = true;
//	private static final String version = "97r_";
//	private static final String version = "978_";
//	private static String COREPATH = "G:\\svn\\ucm\\9.7.0\\core";
	private static String COREPATH = "G:\\svn\\ucm\\9.7-release\\core";
//	private static String COREPATH = "G:\\svn\\ucm\\9.7.8\\core";
//	private static String SHELLPATH = "G:\\svn\\ucm\\9.7.0\\BrowserShell";
	private static String SHELLPATH = "G:\\svn\\ucm\\9.7-release\\BrowserShell";
//	private static String SHELLPATH = "G:\\svn\\ucm\\9.7.8\\BrowserShell";
	private static final String COREFILEBEINGIMPORTED = "CoreFilesBeingImported";
	private static final String SHELLCLASSIMPORTINGCORECLASS = "ShellClassImportingCoreClass";
	private static final String IMPORTEDINTERFACE = "ImportedInterface";
	private static final String CALLEDCOREINTERFACES = "CalledCoreInterfaces";
	private static final String SHELLCALLINTERFACE = "ShellCallInterface";
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

    	Calendar cal = Calendar.getInstance();
    	Date start = cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	
    	CoreInfo coreInfo = new CoreInfo();
    	coreInfo.parseFile(COREPATH);
    	
    	ShellInfo shellInfo = new ShellInfo(coreInfo.getPackages(), 
    			coreInfo.getInterfaces());
    	shellInfo.parseFile(SHELLPATH);
    	
    	if (!COMPARE) {
	    	printClassInfo(VERSION + COREFILEBEINGIMPORTED, shellInfo.getImportedClasses());
	    	printShellClassInfo(VERSION + SHELLCLASSIMPORTINGCORECLASS, shellInfo.getClassesImportingCore());
	    	
	    	printCoreInterface(VERSION + IMPORTEDINTERFACE, shellInfo.getImportedInterfaces());
	    	printCoreInterface(VERSION + CALLEDCOREINTERFACES, shellInfo.getCalledMethods());
	    	printShellCalledInterface(VERSION + SHELLCALLINTERFACE, shellInfo.getShellCalls());
    	} else {
    	
//	    	checkClassInfo(OLD_VERSION + COREFILEBEINGIMPORTED, shellInfo.getImportedClasses());
//	    	checkShellClassInfo(OLD_VERSION + SHELLCLASSIMPORTINGCORECLASS, shellInfo.getClassesImportingCore());
	    	
//	    	checkCoreInterface(OLD_VERSION + IMPORTEDINTERFACE, shellInfo.getImportedInterfaces());
	    	checkCoreInterface(OLD_VERSION + CALLEDCOREINTERFACES, shellInfo.getCalledMethods());
//	    	checkShellCalledInterface(OLD_VERSION + SHELLCALLINTERFACE, shellInfo.getShellCalls());
    	}
    	
    	cal = Calendar.getInstance();
    	Date end = cal.getTime();
    	System.out.println("start: " + sdf.format(start) );
    	System.out.println("end: " + sdf.format(end) );
    	System.out.println("end");
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
		//new MethodPrinter().printMethod();
		//GetJavaFiles.test();
//		new CoreInfo().testGetFiles();
//		new CoreInfo().parseFile("glGetTransformFeedbackVarying.java");
//		new ClassParser().testParseCoreFile("SettingFlags.java");
//
//		PrintWriter pr = new PrintWriter("print.txt");
//		pr.println("shit");
//		pr.println("shiiiit");
//		pr.close();
		
//		new ShellInfo().parseFile("IntentSource.java");
//		ShellInfo shellInfo = new ShellInfo(coreInfo.getPackages(), coreInfo.getClasses(),

    	
		/*
		HashSet<ClassInfo> a = new HashSet<>();
		HashSet<ClassInfo> b = new HashSet<>();
		for (int i=1;i<20;i++) {
			a.add(new ClassInfo("a", String.valueOf(i), null));
			b.add(new ClassInfo("a", String.valueOf(i), null));
		}
		System.out.println(a.equals(b));
		
		HashSet<ShellClassInfo> c = new HashSet<>();
		HashSet<ShellClassInfo> d = new HashSet<>();
		for (int i=1;i<20;i++) {
			c.add(new ShellClassInfo("a", String.valueOf(i), null, null));
			d.add(new ShellClassInfo("a", String.valueOf(i), null, null));
		}
		System.out.println(a.equals(b));
		/*
		
    	/*
    	LinkedList<String> testList1 = new LinkedList<>();
    	testList1.add("a");
    	testList1.add("b");
    	testList1.add("c");
    	
			
    	LinkedList<String> testList2 = new LinkedList<>();
    	testList2.add("a");
    	testList2.add("b");
    	testList2.add("c");
    	
    	if (testList1.equals(testList2)) {
//   		if (testList1.containsAll(testList2) && testList2.containsAll(testList1)) {
//  		if (testList2.containsAll(testList1)) {
//  		if (testList1.containsAll(testList2)) {
    		System.out.println("success");
    	} else {
    		System.out.println("failed");
    	}
    	*/
		
	}
	
	private static void printClassInfo(String name, TreeSet<ClassInfo> classes) throws Exception{
		PrintWriter pr = null;
		try {
			pr = new PrintWriter(name+".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (ClassInfo cl : classes) {
			pr.println(cl.getClassFullName());
		}
		pr.close();
		
		FileOutputStream fos = new FileOutputStream(name+".dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(classes);
		oos.close();
		
		if (Testoutput.LOG) {
			System.out.println(name + ": " + classes.size());
		}
	}
	
	private static void printShellClassInfo(String name, TreeSet<ShellClassInfo> classes) throws Exception {
		PrintWriter pr = null;
		try {
			pr = new PrintWriter(name+".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (ShellClassInfo cl : classes) {
			pr.println(cl.getClassFullName());
		}
		pr.close();
		
		FileOutputStream fos = new FileOutputStream(name+".dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(classes);
		oos.close();
		
		if (Testoutput.LOG) {
			System.out.println(name + ": " + classes.size());
		}
	}
	
	private static void printCoreInterface(String name, Vector<CoreInterfaces> inters) throws Exception {
//		String file = name;
		PrintWriter pr = null;
		try {
			pr = new PrintWriter(name+".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (CoreInterfaces inter: inters) {
			pr.println(inter.getBelongClass().getClassFullName() + "." + inter.getName() + "--" + inter.getParamCount());
		}

		pr.close();
		
		FileOutputStream fos = new FileOutputStream(name+".dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(inters);
		oos.close();
		
		if (Testoutput.LOG) {
			System.out.println(name + ": " + inters.size());
		}
	}
	
	private static void printShellCalledInterface(String name, Vector<ShellCalledInterfaces> inters) throws Exception {
//		String file = name;
		PrintWriter pr = null;
		try {
			pr = new PrintWriter(name+".txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (ShellCalledInterfaces inter: inters) {
			pr.println(inter.getBelongClass().getClassFullName() + "." + inter.getName() + "--" + inter.getParamCount());
		}

		pr.close();
		
		FileOutputStream fos = new FileOutputStream(name+".dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(inters);
		oos.close();
		
		if (Testoutput.LOG) {
			System.out.println(name + ": " + inters.size());
		}
	}
	
	private static void checkClassInfo(String name, TreeSet<ClassInfo> classes) throws Exception {
		
		FileInputStream fis = new FileInputStream(name + ".dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		TreeSet<ClassInfo> test = (TreeSet<ClassInfo>)ois.readObject();

		if (test.equals(classes)) {
			System.out.println(name + " NOT changed");
		} else {
			System.out.println(name + " changes");			
			System.out.println("new: " + classes.size());
			
			// add
			for (ClassInfo cl : classes) {
				if (!test.contains(cl)) {
					System.out.println("Add: " + cl.getClassFullName());
				}
			}
			
			// delete
			for (ClassInfo tt : test) {
				if (!classes.contains(tt)) {
					System.out.println("Delete: " + tt.getClassFullName());
				}
			}
		}
		System.out.println("origin: " + test.size());
		ois.close();
	}
	
	private static void checkShellClassInfo(String name, TreeSet<ShellClassInfo> classes) throws Exception {
		
		FileInputStream fis = new FileInputStream(name + ".dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		TreeSet<ShellClassInfo> test = (TreeSet<ShellClassInfo>)ois.readObject();

		if (test.equals(classes)) {
			System.out.println(name + " NOT changed");
		} else {
			System.out.println(name + " changes");			
			System.out.println("new: " + classes.size());
			
			int i = 0;
			// add
			for (ShellClassInfo cl : classes) {
				if (!test.contains(cl)) {
					System.out.println(cl.getClassFullName());
					i++;
				}
			}
			System.out.println("add total: " + i);
			i = 0;
			
			// delete
			for (ShellClassInfo tt : test) {
				if (!classes.contains(tt)) {
					System.out.println(tt.getClassFullName());
					i++;
				}
			}
			System.out.println("delete total: " + i);
		}
		System.out.println("origin: " + test.size());
		ois.close();
	}
	
	private static void checkCoreInterface(String name, Vector<CoreInterfaces> inter) throws Exception {
		
		FileInputStream fis = new FileInputStream(name + ".dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Vector<CoreInterfaces> test = (Vector<CoreInterfaces>)ois.readObject();

		if (test.equals(inter)) {
			System.out.println(name + " NOT changed");
		} else {
			System.out.println(name + " changes");			
			System.out.println("new: " + inter.size());
			
			
			int i = 0;
			for (CoreInterfaces cl : inter) {
				if (!test.contains(cl)) {
					i++;
					String tmp = "(";
					Vector<String> vStr = cl.getParam();
					if (vStr != null) {
						int paramCount = vStr.size();
						for (String s : cl.getParam()) {
							tmp = tmp + s;
							if (paramCount-- > 1) {
								tmp += ", ";
							}
						}
					}
					tmp += ")";
					System.out.println(cl.getBelongClass().getClassFullName() + "::" + cl.getName() + tmp);
				}
			}
			System.out.println("add total: " + i);
			i = 0;
			
			// delete
			for (CoreInterfaces tt : test) {
				if (!inter.contains(tt)) {
					i++;
					String tmp = "(";
					Vector<String> vStr = tt.getParam();
					if (vStr != null) {
						int paramCount = vStr.size();
						for (String s : tt.getParam()) {
							tmp = tmp + s;
							if (paramCount-- > 1) {
								tmp += ", ";
							}
						}
					}
					tmp += ")";
					System.out.println(tt.getBelongClass().getClassFullName() + "::" + tt.getName() + tmp);
				}
			}
			System.out.println("delete total: " + i);
		}
		System.out.println("origin: " + test.size());
		ois.close();
	}
	
	private static void checkShellCalledInterface(String name, Vector<ShellCalledInterfaces> inter) throws Exception {
		
		FileInputStream fis = new FileInputStream(name + ".dat");
		ObjectInputStream ois = new ObjectInputStream(fis);
		Vector<ShellCalledInterfaces> test = (Vector<ShellCalledInterfaces>)ois.readObject();

		if (test.equals(inter)) {
			System.out.println(name + " NOT changed");
		} else {
			System.out.println(name + " changes");			
			System.out.println("new: " + inter.size());
					
			int i = 0;
			for (ShellCalledInterfaces cl : inter) {
				if (!test.contains(cl)) {
					i++;
					String tmp = "(";
					tmp += String.valueOf(cl.getParamCount());
					tmp += ")";
					System.out.println(cl.getName() + tmp);
				}
			}
			System.out.println("add total: " + i);
			i = 0;
			
			// delete
			for (ShellCalledInterfaces tt : test) {
				if (!inter.contains(tt)) {
					i++;
					String tmp = "(";
					tmp += String.valueOf(tt.getParamCount());
					tmp += ")";
					System.out.println(tt.getName() + tmp);
				}
			}
			System.out.println("delete total: " + i);
		}
		System.out.println("origin: " + test.size());
		ois.close();
	}
}
