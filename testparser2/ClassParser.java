/**
 * 
 */
package testparser2;

import japa.parser.JavaParser;
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
import japa.parser.ast.visitor.VoidVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * @author tangjp
 *
 */
public class ClassParser {

	/**
	 * 
	 */
	public ClassParser() {
		// TODO Auto-generated constructor stub
	}

    public ClassInfo parseCoreFile(String fileName) {
    	CompilationUnit cu = null;
    	try {
    		cu = getCompilcationUnit(fileName);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	String className = new String();
    	new GetNameParser().visit(cu, className);
    	String packageName = cu.getPackage().getName().toString();
    	//String importPacks = cu.getImports().toString();
    	
    	return null;
    }
    	
	/**
	 * Get Complication Unit from file
	 * @param fileName
	 * @return Complication Unit
	 * @throws Exception
	 */
	private CompilationUnit getCompilcationUnit(String fileName) throws Exception {

		FileInputStream in = new FileInputStream(fileName);
        CompilationUnit cu = null;
        try {
            // parse the file
            cu = JavaParser.parse(in);
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            in.close();
        }
        return cu;
	}
	
	 
    private static class GetNameParser extends VoidVisitorAdapter<String> {
    	public void visit(ClassOrInterfaceDeclaration n, String name) {
            name = n.getName();
    	}
    }
    
    private static class GetMethodParser extends VoidVisitorAdapter<String> {

        @Override
        public void visit(MethodDeclaration n, String method) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this 
            // CompilationUnit, including inner class methods
            //System.out.println(n.getName());
        	n.getName();
        }
    }
	
    /**
     * Simple visitor implementation for visiting MethodDeclaration nodes. 
     */
    private static class MethodVisitor extends VoidVisitorAdapter {

        @Override
        public void visit(MethodDeclaration n, Object arg) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this 
            // CompilationUnit, including inner class methods
            //System.out.println(n.getName());
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
    	CompilationUnit cu = null;
    	try {
    		cu = getCompilcationUnit(fileName);
    	} catch (Exception e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
    	String className = null;
    	String packageName = cu.getPackage().getName().toString();
    	String importPacks = cu.getImports().toString();
    	
        if (cu.getTypes() != null) {
            for (TypeDeclaration typeDeclaration : cu.getTypes()) {
                System.out.println(typeDeclaration);
            }
        }
    	
    	int c =1;
    	new MethodVisitor().visit(cu, null);
    	new ClassOrInterfaceVistitor().visit(cu, null);
    	new ConstructorVistitor().visit(cu, null);
    	new EmptyMemberVistitor().visit(cu, null);
    	new FieldVistitor().visit(cu, null);
    	return;
    	
    }
    
}
