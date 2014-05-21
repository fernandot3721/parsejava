/**
 * 
 */
package javaparser;

import java.io.Serializable;
import java.util.Vector;

/**
 * @author tangjp
 *
 */
public class ClassInfo implements Serializable, Comparable<ClassInfo> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final String TAG = "TJPLOG: ClassInfo";
	private String mPackage;
	private String mClassName;

	private Vector<CoreInterfaces> mMethods;
	
	public Vector<CoreInterfaces> getMethods() {
		return mMethods;
	}

	public ClassInfo(String mPackage, String mClassName,
			Vector<CoreInterfaces> mMethods) {
		super();
		this.mPackage = mPackage;
		this.mClassName = mClassName;
		this.mMethods = mMethods;
		
		if (null != mMethods) {
			for (CoreInterfaces inter : mMethods) {
				inter.setBelongClass(this);
			}
		}
	}
	
	public String getClassFullName() {
		return mPackage + "." + mClassName;
	}
	
	public String getPackageName() {
		return mPackage;
	}
	
	public String getClassName() {
		return mClassName;
	}

	@Override
	public boolean equals(Object arg0) {
		ClassInfo target = (ClassInfo) arg0;
		if (null != mClassName && !this.mClassName.equals(target.getClassName())) {
//			System.out.println(mClassName);
//			System.out.println(target.getClassName());
			return false;
		}
		if (null != mPackage && !this.mPackage.equals(target.getPackageName())) {
//			System.out.println(mPackage);
//			System.out.println(target.getPackageName());
			return false;
		}
		return true;
	}
	
    @Override
    public int hashCode() {
    	int result = 17;
    	result = 31 * result + ((null == mPackage) ? 0 : mPackage.hashCode() )
    			+ ((null == mClassName) ? 0 : mClassName.hashCode());
    	return result;
    }

	@Override
	public int compareTo(ClassInfo o) {
		if (null != mClassName) {
			return this.mClassName.compareTo(o.getClassName());
		}
		return 0;
	}
		
}
