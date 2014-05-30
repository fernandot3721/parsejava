package javaparser;

import java.io.Serializable;

public class ShellCalledInterfaces implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final String TAG = "TJPLOG: CoreInterfaces";

	private String mName;
	private ShellClassInfo mBelongClass;
	private int mParamCount;

	public ShellCalledInterfaces(String name, ShellClassInfo belongClass, int paramCount) {
		this.mName = name;
		this.mBelongClass = belongClass;
		this.mParamCount = paramCount;
	}

	public String getName() {
		return mName;
	}
	
	public void setBelongClass(ShellClassInfo belong) {
		mBelongClass = belong;
	}
	
	public ShellClassInfo getBelongClass() {
		return mBelongClass;
	}
	
	public int getParamCount() {
		return mParamCount;
	}

	@Override
	public boolean equals(Object obj) {
		ShellCalledInterfaces target = (ShellCalledInterfaces) obj;
		if (!this.mName.equals(target.getName())) {
			return false;
		}
		if (!this.mBelongClass.equals(target.getBelongClass())) {
			return false;
		}
		if (this.mParamCount != target.getParamCount()) {
			return false;
		}
		return true;
	}
	
    @Override
    public int hashCode() {
    	int result = 17;
    	result = 31 * result + mName.hashCode() + mParamCount + mBelongClass.hashCode();
    	return result;
    }
}
