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
import java.util.Vector;

/**
 * @author tangjp
 *
 */
public class Testoutput {

	private static String corePath = "G:\\svn\\ucm\\9.7.0\\core";
//	private static String corePath = "G:\\svn\\ucm\\9.7.8\\core";
	private static String shellPath = "G:\\svn\\ucm\\9.7.0\\BrowserShell";
//	private static String shellPath = "G:\\svn\\ucm\\9.7.8\\BrowserShell";
//	private static String coreFileBeingImported = "CoreFilesBeingImported";
	
	
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
    	coreInfo.parseFile(corePath);
    	
    	ShellInfo shellInfo = new ShellInfo(coreInfo.getPackages(), 
    			coreInfo.getInterfaces());
    	shellInfo.parseFile(shellPath);
    	
    	/*
    	printClassInfo("CoreFilesBeingImported", shellInfo.getImportedClasses());
    	printShellClassInfo("ShellClassImportingCoreClass", shellInfo.getClassesImportingCore());
    	
    	printCoreInterface("ImportedInterface", shellInfo.getImportedInterfaces());
    	printCoreInterface("CalledCoreInterfaces", shellInfo.getCalledMethods());
    	printShellCalledInterface("ShellCallInterface", shellInfo.getShellCalls());
    	*/
    	
    	checkClassInfo("CoreFilesBeingImported.dat", shellInfo.getImportedClasses());
    	checkShellClassInfo("ShellClassImportingCoreClass.dat", shellInfo.getClassesImportingCore());
    	checkCoreInterface("ImportedInterface.dat", shellInfo.getImportedInterfaces());
    	checkCoreInterface("CalledCoreInterfaces.dat", shellInfo.getCalledMethods());
    	checkShellCalledInterface("ShellCallInterface.dat", shellInfo.getShellCalls());
    	
    	
    	
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
	
	private static void printClassInfo(String name, HashSet<ClassInfo> classes) throws Exception{
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
	}
	
	private static void printShellClassInfo(String name, HashSet<ShellClassInfo> classes) throws Exception {
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
	}
	
	private static void checkClassInfo(String name, HashSet<ClassInfo> classes) throws Exception {
		
		FileInputStream fis = new FileInputStream(name);
		ObjectInputStream ois = new ObjectInputStream(fis);
		HashSet<ClassInfo> test = (HashSet<ClassInfo>)ois.readObject();

		if (test.equals(classes)) {
			System.out.println(name + " NOT changed");
		} else {
			System.out.println(name + " changes");			
			System.out.println("new: " + classes.size());
		}
		System.out.println("origin: " + test.size());
		ois.close();
	}
	
	private static void checkShellClassInfo(String name, HashSet<ShellClassInfo> classes) throws Exception {
		
		FileInputStream fis = new FileInputStream(name);
		ObjectInputStream ois = new ObjectInputStream(fis);
		HashSet<ShellClassInfo> test = (HashSet<ShellClassInfo>)ois.readObject();

		if (test.equals(classes)) {
			System.out.println(name + " NOT changed");
		} else {
			System.out.println(name + " changes");			
			System.out.println("new: " + classes.size());
		}
		System.out.println("origin: " + test.size());
		ois.close();
	}
	
	private static void checkCoreInterface(String name, Vector<CoreInterfaces> inter) throws Exception {
		
		FileInputStream fis = new FileInputStream(name);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Vector<CoreInterfaces> test = (Vector<CoreInterfaces>)ois.readObject();

		if (test.equals(inter)) {
			System.out.println(name + " NOT changed");
		} else {
			System.out.println(name + " changes");			
			System.out.println("new: " + inter.size());
		}
		System.out.println("origin: " + test.size());
		ois.close();
	}
	
	private static void checkShellCalledInterface(String name, Vector<ShellCalledInterfaces> inter) throws Exception {
		
		FileInputStream fis = new FileInputStream(name);
		ObjectInputStream ois = new ObjectInputStream(fis);
		Vector<ShellCalledInterfaces> test = (Vector<ShellCalledInterfaces>)ois.readObject();

		if (test.equals(inter)) {
			System.out.println(name + " NOT changed");
		} else {
			System.out.println(name + " changes");			
			System.out.println("new: " + inter.size());
		}
		System.out.println("origin: " + test.size());
		ois.close();
	}
}
