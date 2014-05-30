package javaparser;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Vector;

public class ChangeChecker {

	private Vector<CoreInterfaces> mCoreInterfaces; // interfaces that imported from shell
	private Vector<CoreInterfaces> mCalledCoreInterfaces; // interfaces that imported from shell
	private Vector<ShellCalledInterfaces> mShellCalled; //interfaces that call by shell 
	
	private HashSet<String> coreInterfacesSet;
	private HashSet<String> calledCoreInterfacesSet;
	
	public ChangeChecker() {
		mCoreInterfaces = new Vector<CoreInterfaces>();
		mCalledCoreInterfaces = new Vector<CoreInterfaces>();
		mShellCalled = new Vector<ShellCalledInterfaces>();
		coreInterfacesSet = new HashSet<String>();
		calledCoreInterfacesSet = new HashSet<String>();
	}
	
	public void addCoreInterfaces(CoreInterfaces method) {
		mCoreInterfaces.add(method);
		coreInterfacesSet.add(method.getName() + String.valueOf(method.getParamCount()));
	}
	
	public void addShellCalledInterfaces(ShellCalledInterfaces method) {
		String methodName = method.getName() + String.valueOf(method.getParamCount());
		if (coreInterfacesSet.contains(methodName)) {
			mShellCalled.add(method);
			calledCoreInterfacesSet.add(methodName);
		}
	}
	
	public int getCalledCoreCount() {
		return mCalledCoreInterfaces.size();
	}
	
	public int getCoreCount() {
		return mCoreInterfaces.size();
	}
	
	public int getShellCallCount() {
		return mShellCalled.size();
	}
	
	public void checkCalledInterfaces() {
		for (CoreInterfaces method : mCoreInterfaces) {
			if (calledCoreInterfacesSet.contains(method.getName() + String.valueOf(method.getParamCount()))) {
				mCalledCoreInterfaces.add(method);
			}
		}
	}
	
	public Vector<CoreInterfaces> getCalledCoreInterfaces() {
		return mCalledCoreInterfaces;
	}

	public Vector<ShellCalledInterfaces> getShellCallInterfaces() {
		return mShellCalled;
	}

}
