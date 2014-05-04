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

public class CoreInterfaces implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String TAG = "TJPLOG: CoreInterfaces";

	private String mName;
	private Vector<String> mParams;
	private ClassInfo mBelongClass;

	public CoreInterfaces(String name, ClassInfo belongClass, Vector<String> params) {
		super();
		this.mName = name;
		this.mParams = params;
		this.mBelongClass = belongClass;
	}

	public String getName() {
		return mName;
	}
	
	public ClassInfo getBelongClass() {
		return mBelongClass;
	}
	
	public void setBelongClass(ClassInfo coreClass) {
		this.mBelongClass = coreClass;
	}
	
	public int getParamCount() {
		if (null != mParams) {
			return mParams.size();			
		} else {
			return 0;
		}
	}
	
	public Vector<String> getParam() {
		return mParams;
	}

	@Override
	public boolean equals(Object obj) {
		CoreInterfaces target = (CoreInterfaces)obj;
		if (null != mName && !this.mName.equals(target.getName())) { 
			return false;
		}
		if (null != mParams && !this.mParams.equals(target.getParam())) {
			return false;
		}
		if (null != mBelongClass && !this.mBelongClass.equals(target.getBelongClass())) {
			return false;
		}
		return true;
	}
	
    @Override
    public int hashCode() {
    	int result = 17;
    	result = 31 * result + mName.hashCode() + mParams.hashCode() + mBelongClass.hashCode();
    	return result;
    }
	
}

