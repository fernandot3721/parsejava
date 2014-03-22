/**
 * 
 */
package testparser2;

import java.util.HashSet;

/**
 * @author tangjp
 *
 */
public class ClassInfo {
	private final String TAG = "TJPLOG: ClassInfo";
	private String mPackage;
	private String mClassName;

	private HashSet<String> mMethods;
	
	public HashSet<String> getMethods() {
		return mMethods;
	}

	public ClassInfo(String mPackage, String mClassName,
			HashSet<String> mMethods) {
		super();
		this.mPackage = mPackage;
		this.mClassName = mClassName;
		this.mMethods = mMethods;
	}

	public void addMethod(String methodName) {
		this.mMethods.add(methodName);
	}
	
	public String getClassFullName() {
		return mPackage + "." + mClassName;
	}
	
	public String getPackageName() {
		return mPackage;
	}
}
