/**
 * 
 */
package javaparser;

/**
 * @author tangjp
 *
 */
public class CoreInterfaces {

	private final String TAG = "TJPLOG: CoreInterfaces";

	private String mName;
	private ClassInfo mBelongClass;

	public CoreInterfaces(String mName, ClassInfo mBelongClass) {
		super();
		this.mName = mName;
		this.mBelongClass = mBelongClass;
	}

	public String getName() {
		return mName;
	}
	
	public ClassInfo getBelongClass() {
		return mBelongClass;
	}
}
