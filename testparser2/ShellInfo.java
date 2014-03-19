/**
 * 
 */
package testparser2;

import java.util.HashSet;

/**
 * @author tangjp
 *
 */
public class ShellInfo {

	private final String TAG = "TJPLOG: ShellInfo";
	private HashSet<ShellClassInfo> mShellClasses;
	private HashSet<ClassInfo> mImportedClasses;
	private HashSet<CoreInterfaces> mCalledMethods;

}
