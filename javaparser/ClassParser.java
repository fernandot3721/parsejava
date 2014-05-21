/**
 * 
 */
package javaparser;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;


/**
 * @author tangjp
 *
 */
public class ClassParser {
	private final String TAG = "TJPLOG: ClassParser";
	
	/**
	 * Get shell class info from a java file
	 * @param fileName file to parse
	 * @return A shell class object
	 * @throws MyException
	 * @throws IOException
	 * @throws MyFatalException 
	 */
	public ShellClassInfo parseShellFile(String fileName) throws MyException, IOException, MyFatalException {
		ShellClassInfo shellClass = null;
    	CompilationUnit cu = null;
    	
    	// Three element to build up a shell class info
		String packageName = null;
    	AString className = new AString();
    	Vector<ShellCalledInterfaces> calledMethods = new Vector<>();
    	HashSet<String> importList = new HashSet<>();

    	cu = getCompilcationUnit(fileName);
    	
    	// Get Class Name
    	new GetNameParser().visit(cu, className);

    	// CHECK VALID POINT
    	if (null == className.str) {
    		//System.out.println(TAG + " class name null! check " + fileName);
    		throw new MyException(TAG + "parse file: " + fileName + " class Name null!");
    	}

    	// Get Packages
    	if (null != cu.getPackage()) {
    		packageName = cu.getPackage().getName().toString();
    	} else {
    		packageName = "UNDEFINED";
    	}

    	// Get import list
    	List<ImportDeclaration> imports =  cu.getImports();
    	if (null != imports) {
    		for (ImportDeclaration importDec : imports) {
    			importList.add(importDec.getName().toString());
    		}
    	}

    	// Get called Methods
    	cu.accept(new GetCalledMethodParser(), calledMethods);
    	
    	shellClass = new ShellClassInfo(packageName, className.str, calledMethods, importList);
		
		return shellClass;
	}

    /**
     * Get core class info from a core file
     * @param fileName file to be parse
     * @return A core class object
     * @throws IOException
     * @throws MyException
     * @throws MyFatalException 
     */
    public ClassInfo parseCoreFile(String fileName) throws IOException, MyException, MyFatalException {
    	CompilationUnit cu = null;
    	
    	// Three element to build up a core class info
    	String packageName = null;
    	AString className = new AString();
    	Vector<CoreInterfaces> methods = new Vector<>();
    	
    	cu = getCompilcationUnit(fileName);
    	
    	// Get Class Name
    	new GetNameParser().visit(cu, className);
    	
    	// CHECK VALID POINT
    	if (null == className.str) {
    		//System.out.println(TAG + " class name null! check " + fileName);
    		throw new MyException(TAG + "parse file: " + fileName + " class Name null!");
    	}

    	// Get Packages
    	if (null != cu.getPackage()) {
    		packageName = cu.getPackage().getName().toString();
    	} else {
    		packageName = "UNDEFINED";
    	}
    	
    	// Get Methods
    	new GetPublicInterfaceParser().visit(cu, methods);
    	
    	// make core class
    	return new ClassInfo(packageName, className.str, methods);
    }
    	
    
    /**
     * @author tangjp
     *
     * I don't know why, but we need a wrapper here to get the same string back
     */
    private class AString {
    	public String str = null;
    }

	/**
	 * Get Complication Unit from file
	 * @param fileName
	 * @return Complication Unit
	 * @throws IOException 
	 * @throws MyException 
	 */
	private CompilationUnit getCompilcationUnit(String fileName) throws IOException, MyException  {

		FileInputStream in = new FileInputStream(fileName);
        CompilationUnit cu = null;
        try {
            // parse the file
            cu = JavaParser.parse(in, "UTF-8");
        } catch (ParseException e) {
        	throw new MyException(TAG + "Parse file " + fileName + " file do not fit JAVA syntax");
        } finally {
            in.close();
        }
        return cu;
	}
	
    private static class GetNameParser extends VoidVisitorAdapter<AString> {
    	@Override
    	public void visit(ClassOrInterfaceDeclaration n, AString name) {
            name.str = n.getName();
    	}
    }
    
    private static class GetPublicInterfaceParser extends VoidVisitorAdapter<Vector<CoreInterfaces>> {
        @Override
        public void visit(MethodDeclaration n, Vector<CoreInterfaces> methods) {
        	List<Parameter> params = null;
        	CoreInterfaces publicMethod = null;
        	Vector<String> paramList = null;
        	if (n.getModifiers()%2 != 0) { // for public methods
//        		System.out.println("method name: " + n.getName());
        		params = n.getParameters();
				if (null != params) {
					paramList = new Vector<String>();
					for (Parameter tmpParam : params) {
						paramList.add(tmpParam.getType().toString());
					}
//					System.out.println("params: " + paramList.size());
        		}
        		publicMethod = new CoreInterfaces(n.getName(), null, paramList);
        		methods.add(publicMethod);
        	}
        }
    }
	
    private static class GetCalledMethodParser extends VoidVisitorAdapter<Vector<ShellCalledInterfaces>> {
    	@Override
    	public void visit(MethodCallExpr n, Vector<ShellCalledInterfaces> calledMethods) {
    		int paramCount = 0;
    		if (null != n.getArgs()) {
    			paramCount = n.getArgs().size();
    		}
    		calledMethods.add(new ShellCalledInterfaces(n.getName(), null, paramCount));
    	}
    }
    
}
