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
	private HashSet<ClassInfo> mImportList;
	private HashSet<CoreInterfaces> mCalledMethods;

	public ShellClassInfo(String mPackage, String mClassName,
			HashSet<String> mMethods) {
		super(mPackage, mClassName, mMethods);
		// TODO Auto-generated constructor stub
	}
}
