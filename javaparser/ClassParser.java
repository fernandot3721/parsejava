/**
 * 
 */
package javaparser;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.AnnotationDeclaration;
import japa.parser.ast.body.AnnotationMemberDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.ConstructorDeclaration;
import japa.parser.ast.body.EmptyMemberDeclaration;
import japa.parser.ast.body.EnumConstantDeclaration;
import japa.parser.ast.body.EnumDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.InitializerDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;


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
	 */
	public ShellClassInfo parseShellFile(String fileName) throws MyException, IOException {
		ShellClassInfo shellClass = null;
    	CompilationUnit cu = null;
    	
    	// Three element to build up a shell class info
		String packageName = null;
    	AString className = new AString();
    	HashSet<String> calledMethods = new HashSet<>();
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
    	
    	shellClass = new ShellClassInfo(packageName, className.str, null, calledMethods, importList);
		
		return shellClass;
	}

    /**
     * Get core class info from a core file
     * @param fileName file to be parse
     * @return A core class object
     * @throws IOException
     * @throws MyException
     */
    public ClassInfo parseCoreFile(String fileName) throws IOException, MyException {
    	CompilationUnit cu = null;
    	
    	// Three element to build up a core class info
    	String packageName = null;
    	AString className = new AString();
    	HashSet<String> methods = new HashSet<>();
    	
    	cu = getCompilcationUnit(fileName);
    	
    	// Get Class Name
    	new GetNameParser().visit(cu, className);

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
    
    private static class GetPublicInterfaceParser extends VoidVisitorAdapter<HashSet<String>> {
        @Override
        public void visit(MethodDeclaration n, HashSet<String> methods) {
        	if (n.getModifiers()%2 != 0) { // for public methods
        		methods.add(n.getName());
        	}
        }
    }
	
    private static class GetCalledMethodParser extends VoidVisitorAdapter<HashSet<String>> {
    	@Override
    	public void visit(MethodCallExpr n, HashSet<String> calledMethods) {
    		//
//    		System.out.println(n.getBeginLine() + " to " + n.getEndLine() + " : " + n.getName());
    		calledMethods.add(n.getName());
    	}
    }
    
    
    
    
    
    
    
    
    
    
//================================================================================    
    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes. 
     */
    private static class MethodVisitor extends VoidVisitorAdapter<Integer> {
    	private static int cout = 0;

        @Override
        public void visit(MethodDeclaration n, Integer c) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this 
            // CompilationUnit, including inner class methods
            System.out.println(n.getBeginLine() + ": " + n.getName());
            cout++;
            System.out.println("total count: " + cout);
        	n.getName();
        }
    }
    
    private static class ClassOrInterfaceVistitor extends VoidVisitorAdapter {
    	public void visit(ClassOrInterfaceDeclaration n, Object arg) {
            System.out.println(n.getName());
            List<BodyDeclaration> members = new LinkedList<BodyDeclaration>();
            members = n.getMembers();
            int i = 0;
    	}
    }
    private static class ConstructorVistitor extends VoidVisitorAdapter {
    	public void visit(ConstructorDeclaration n, Object arg) {
//            System.out.println(n.getBeginLine() + ": " + n.getName());
    	}
    }
    private static class EmptyMemberVistitor extends VoidVisitorAdapter {
    	public void visit(EmptyMemberDeclaration n, Object arg) {
//            System.out.println(n.getBeginLine() + ": " +  n.toString());
    	}
    }

    private static class FieldVistitor extends VoidVisitorAdapter {
    	public void visit(FieldDeclaration n, Object arg) {
//            System.out.println(n.getBeginLine() + ": " +  n.toString());
    	}
    }
    
    
    public void testParseCoreFile(String fileName) {
    	HashSet<String> calledMethods = new HashSet<>();
    	CompilationUnit cu = null;
    	try {
    		cu = getCompilcationUnit(fileName);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	cu.accept(new GetCalledMethodParser(), calledMethods);
    	
//    	int i = 0;
//    	for (String tmp : calledMethods) {
//    		System.out.println(tmp);
//    		i++;
//    	}
//    	System.out.println("total: " + i);

    	List<ImportDeclaration> imports = new LinkedList<ImportDeclaration>();
    	imports = cu.getImports();
    	String strTmp = null;
    	for (ImportDeclaration tmp : imports) {
    		strTmp = tmp.getName().toString();
    		System.out.println(strTmp);
    	}
    	//String importPacks = cu.getImports().toString();
    	//System.out.println(importPacks);

//    	cu.accept(new VoidVisitorAdapter<HashSet<String>>() {
//    		@Override
//    		public void visit(MethodCallExpr n, HashSet<String> calledMethods) {
//    			//
//    			System.out.println(n);
//    		}
//    	} ,  calledMethods);
//
//    	String className = null;
//    	String packageName = cu.getPackage().getName().toString();
//    	String importPacks = cu.getImports().toString();
//    	
//    	
//    	int c =0;
//    	new MethodVisitor().visit(cu, c);
//    	new ClassOrInterfaceVistitor().visit(cu, null);
//    	new ConstructorVistitor().visit(cu, null);
//    	new EmptyMemberVistitor().visit(cu, null);
//    	new FieldVistitor().visit(cu, null);
    	return;
    	
    }
//================================================================================    
}
