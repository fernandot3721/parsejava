/**
 * 
 */
package javaparser;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Vector;

/**
 * @author tangjp
 *
 */
public class ShellClassInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String TAG = "TJPLOG: ShellClassInfo";

	private String mPackage;
	private String mClassName;
	
	private HashSet<String> mImportList;
	private Vector<ShellCalledInterfaces> mCalledMethods;

	// These members links to core after parse

	public ShellClassInfo(String packageName, String className,
			Vector<ShellCalledInterfaces> calledMethods,	HashSet<String> importList) {
		
		this.mPackage = packageName;
		this.mClassName = className;
		this.mImportList = importList;
		this.mCalledMethods = calledMethods;
		
		if (null != mCalledMethods) {
			for (ShellCalledInterfaces call : mCalledMethods) {
				call.setBelongClass(this);
			}
		}
	}
	
	public HashSet<String> getImportList() {
		return mImportList;
	}
	
	public Vector<ShellCalledInterfaces> getCalledMethods() {
		return mCalledMethods;
	}
	
	public String getClassFullName() {
		return mPackage + "." + mClassName;
	}
	
	public String getClassName() {
		return mClassName;
	}
	
	public String getPackageName() {
		return mPackage;
	}
	
	@Override
	public boolean equals(Object arg0) {
		ShellClassInfo target = (ShellClassInfo) arg0;
		if (null != mClassName && !this.mClassName.equals(target.getClassName())) {
			return false;
		}
		if (null != mPackage && !this.mPackage.equals(target.getPackageName())) {
			return false;
		}
		return true;
	}
	
    @Override
    public int hashCode() {
    	int result = 17;
    	result = 31 * result + ((mPackage == null) ? 0 : mPackage.hashCode())
    			+ ((mClassName == null) ? 0 : mClassName.hashCode());
    	return result;
    }
    
}
