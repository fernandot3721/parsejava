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
 * 1. 接受buildbot调用
 * 2. 传入参数：basename, shellpath, corepath, checkname, checkshellpath, checkcorepath, logdown
 * 		basename 对比的基准的标识，用于建立目录（将来可能用于checout代码）
 * 		shellpath	对比基准的外壳路径，用于扫描
 * 		corepath	对比基准的外壳路径，用于扫描
 * 		checkname	检查目标的标识， 用于建立目录（将来可能用于checout代码）
 * 		checkshellpath 检查目标的外壳路径，用于扫描
 * 		checkcorepath 检查目标的外壳路径，用于扫描
 * 		logdown 是否记录下原始类，否则只记录文本结果
 * 
 * 
 * currentpath|--base|--interfaces.dat
 * 								 |--base.txt
 *   			  		    	 |--core.dat
 *					  |--last|--result.txt
 *   			  		   	    |--interfaces.dat
 *   			  		   	    |--core.dat
 * 					  |--r1234|--result.txt
 * 								    |--interfaces.dat
 * 	
 * 
 * 2. 传入参数：basecodepath, codepath, tagname
 * 
 * 3. 准备：根据tagname计算运行路径，检查路径是否存在，不存在则新建
 * 4. 基准处理：检查运行路径下base目录，base.txt信息是否与参数basecodepath一致，是否存在dat文件，否则扫描basecode
 * 5. 扫描新代码：扫描codepath代码，生产dat文件
 * 5. 对比last：将新的dat文件与旧的dat文件进行对比，
 * 		core: 有新增或者减少的core则加入负责人记录（BCPI.txt）
 * 		shell: 有新增调用或减少调用的接口则记录（BCI.txt），对比core负责人列表，分类是否需要即使更改并发出警告
 * 6. 对比base：将新的dat文件与旧的dat文件进行对比，有新增或者减少的接口则记录（BCPI.txt），有新增调用或减少调用的接口则记录（BCI.txt）
 * 	
 *
 */
public class CheckInterface {

	public static boolean LOG = false;
	private static String VERSION = "97r_";
	private static String OLD_VERSION = "970_";
	private static boolean COMPARE = true;
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
	
	
	private static final String BASE_PATH = "J:\\workspace\\InterfaceChecker";
	
	private static String tag_name = null;
	private static String base_name = null;
	private static String tag_shell_path = null;
	private static String tag_core_path = null;
	private static String base_shell_path = null;
	private static String base_core_path = null;
			
	
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
		
		if (CheckInterface.LOG) {
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
		
		if (CheckInterface.LOG) {
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
		
		if (CheckInterface.LOG) {
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
		
		if (CheckInterface.LOG) {
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
