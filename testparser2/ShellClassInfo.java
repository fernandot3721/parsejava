/**
 * 
 */
package testparser2;

import java.util.HashSet;

/**
 * @author tangjp
 *
 */
public class ShellClassInfo extends ClassInfo {

	private final String TAG = "TJPLOG: ShellClassInfo";
	private HashSet<String> mImportList;
	private HashSet<String> mCalledMethods;

	// These members links to core after parse
	private HashSet<ClassInfo> mImportListCore;
	private HashSet<CoreInterfaces> mCalledMethodsCore;

	public ShellClassInfo(String packageName, String className,
			HashSet<String> methods, HashSet<String> calledMethods,
			HashSet<String> importList) {
		super(packageName, className, methods);
		this.mImportList = importList;
		this.mCalledMethods = calledMethods;
	}
}
