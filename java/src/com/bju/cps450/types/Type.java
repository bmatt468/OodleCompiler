package com.bju.cps450.types;

import java.util.HashMap;

import com.bju.cps450.declarations.ClassDeclaration;

public class Type {
	private static HashMap<String, Type> types = new HashMap<String, Type>();
	private ClassDeclaration classDecl;
	private boolean isPrimitave;
	// create types to be checked by semantic checker
	public static final Type ooderror = new Type("<error>"),
			oodint = new Type("int",true),
			oodbool = new Type("bool", true),
			oodstring = new Type("String", false),
			oodnull = new Type("null", true),			
			oodvoid = new Type("void", true),
			oodduck = new Type("duck", false),
			oodme 	= new Type("me",false),
			oodfloat = new Type("Float",false);
	
	// name of type
	protected String name;
	
	// constructor
	public Type(String name) {
		this.name = name;
	}
	
	public Type(String name, boolean isPrimitave) {
		this.name = name;
		this.isPrimitave = isPrimitave;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public static Type CreateType(ClassDeclaration decl) {
		try {			
			Type t = new Type(decl.getName(),false);
			t.setClassDecl(decl);
			types.put(decl.getName(), t);
			return t;
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Type GetTypeForName(String className) {
		try {
			return types.get(className);
		} catch (Exception e) {
			return null;
		}
	}

	public ClassDeclaration getClassDecl() {
		return classDecl;
	}

	public void setClassDecl(ClassDeclaration classDecl) {
		this.classDecl = classDecl;
	}

	public boolean isPrimitave() {
		return isPrimitave;
	}

	public void setPrimitave(boolean isPrimitave) {
		this.isPrimitave = isPrimitave;
	}
}
