/**
 * 
 */
package javaparser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author tangjp
 *
 */
public class Testoutput {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

    	Calendar cal = Calendar.getInstance();
    	Date start = cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	
		//new MethodPrinter().printMethod();
		//GetJavaFiles.test();
//		new CoreInfo().testGetFiles();
//		new CoreInfo().parseFile("glGetTransformFeedbackVarying.java");
		CoreInfo coreInfo = new CoreInfo();
		coreInfo.parseFile("G:\\svn\\ucm\\9.7.0\\core");
//		new ClassParser().testParseCoreFile("SettingFlags.java");
//
//		PrintWriter pr = new PrintWriter("print.txt");
//		pr.println("shit");
//		pr.println("shiiiit");
//		pr.close();
		
//		new ShellInfo().parseFile("IntentSource.java");
//		ShellInfo shellInfo = new ShellInfo(coreInfo.getPackages(), coreInfo.getClasses(),
		ShellInfo shellInfo = new ShellInfo(coreInfo.getPackages(), 
				coreInfo.getInterfaces());
		shellInfo.parseFile("G:\\svn\\ucm\\9.7.0\\BrowserShell");
    	Date end = cal.getTime();
    	System.out.println( sdf.format(start) );
    	System.out.println( sdf.format(end) );
		System.out.println("end");
	}

}
