/**
 * 
 */
package testparser2;

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
		new CoreInfo().parseFile("glGetTransformFeedbackVarying.java");
//		new CoreInfo().parseFile("G:\\svn\\ucm\\9.7.0\\core");
//		new ClassParser().testParseCoreFile("SettingFlags.java");

    	Date end = cal.getTime();
    	System.out.println( sdf.format(start) );
    	System.out.println( sdf.format(end) );
		System.out.println("end");
	}

}
