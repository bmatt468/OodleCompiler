package com.bju.cps450;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

import com.bju.cps450.declarations.ClassDeclaration;
import com.bju.cps450.declarations.Declaration;
import com.bju.cps450.declarations.MethodDeclaration;
import com.bju.cps450.declarations.ParameterDeclaration;
import com.bju.cps450.declarations.VariableDeclaration;
import com.bju.cps450.types.Type;

public class SymbolTable {
	public class Symbol {
		private String name;
		private Declaration decl;

		public Symbol(String name, Declaration decl) {
			this.name = name;
			this.decl = decl;
		}

		public String getName() {
			return this.name;
		}

		public Declaration getDeclaration() {
			return this.decl;
		}
	}
	
	private Stack<HashMap<String, Symbol>> symbols = new Stack<HashMap<String, Symbol>>(); // create stack for symbols
	
	// create declarations to store the last
	// of each type of declaration that was
	// stored on the stack
	private Declaration topDeclaration;
	private ClassDeclaration topClass;
	private MethodDeclaration topMethod;
	private VariableDeclaration topVariable;

	private int curscope = 0;
	private int gloscope = 0;
	private int clsscope = 1;
	private int varscope = 2;

	public SymbolTable() {
		// prep symbol stack
		symbols.push(new HashMap<String, Symbol>());		
		symbols.peek().put("in", new Symbol("in",new VariableDeclaration("in", new Type("Reader"))));
		symbols.peek().put("out", new Symbol("out",new VariableDeclaration("out",new Type("Writer"))));
		
	}
	
	// pushes symbol of a specified name and declaration onto the stack
	// returns the symbol that was pushed on
	public Symbol push(String name, Declaration decl) {
		Symbol sym = new Symbol(name, decl); // create symbol to be pushed
		symbols.peek().put(name, sym); // put symbol on the stack
		
		// update top symbol
		if (decl instanceof ClassDeclaration) {
			this.topClass = (ClassDeclaration)decl;			
			if (name.equals("Reader")) {
				MethodDeclaration decl2 = new MethodDeclaration("io_read", Type.oodint);
				MethodDeclaration decl4 = new MethodDeclaration("io_readfloat", Type.oodfloat);
				try {
					decl2.setParent(this.topClass);
					this.topClass.addMethod(decl2);
					decl4.setParent(this.topClass);
					this.topClass.addMethod(decl4);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (name.equals("Writer")) {
				MethodDeclaration decl3 = new MethodDeclaration("io_write", Type.oodvoid);
				MethodDeclaration decl4 = new MethodDeclaration("io_writefloat", Type.oodvoid);
				try {
					decl3.setParent(this.topClass);
					decl3.addParameter(new ParameterDeclaration("int", Type.oodint));					
					this.topClass.addMethod(decl3);		
					decl4.setParent(this.topClass);
					decl4.addParameter(new ParameterDeclaration("float", Type.oodfloat));					
					this.topClass.addMethod(decl4);		
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}		
		if (decl instanceof MethodDeclaration) {
			this.topMethod = (MethodDeclaration)decl;
		}		
		if (decl instanceof VariableDeclaration) {
			this.topVariable = (VariableDeclaration)decl;
		}
		
		this.topDeclaration = decl;
		
		return sym; // return symbol to program
	}

	// returns symbol 'name' from the stack
	// if symbol does not exist in the stack and exception is thrown
	public Symbol lookup(String name) throws Exception {
		// pass through stack looking for symbol
		// start with a loop iterating through each stack level
		for (int i = symbols.size() -1; i >= 0; --i) {
			// inside each level, check to see if symbol exists
			if (symbols.get(i).get(name) != null) {
				return symbols.get(i).get(name);
			}
		}
		
		// if symbol was not found, throw an exception
		throw new Exception("ERROR: Symbol %SYM% not found".replace("%SYM%", name));
	}
	
	// control your lookup to check for a variable in a specific scope
	public Symbol controlledLookup(String name, int startlvl) throws Exception {
		// pass through stack looking for symbol
		// start with a loop iterating through each stack level
		for (int i = startlvl; i >= 0; --i) {
			// inside each level, check to see if symbol exists
			if (symbols.get(i).get(name) != null) {
				return symbols.get(i).get(name);
			}
		}
		
		// if symbol was not found, throw an exception
		throw new Exception("ERROR: Symbol %SYM% not found in the specified level(s)".replace("%SYM%", name));
	}
	
	// control your lookup to check for a variable in a specific level
		public Symbol controlledLevelLookup(String name, int lvl) throws Exception {
				if (symbols.get(lvl).get(name) != null) {
					return symbols.get(lvl).get(name);
				}			
			
			// if symbol was not found, throw an exception
			throw new Exception("ERROR: Symbol %SYM% not found in the specified level(s)".replace("%SYM%", name));
		}

	// create a new level of the stack
	// first checks to see if the stack will be overflown
	// if not, creates a new hashmap to handle the symbols for that scope
	public void beginScope() throws Exception {
		// check for potential overflow		
		if(symbols.size() == 3) {
			throw new Exception("ERROR: Stack Overflow");
		} else {
			// increment scope and push onto the stack
			symbols.push(new HashMap<String, Symbol>());
			curscope++;
		}
	}

	// removes layer of the stack
	// first checks for potential stack underflow
	// if stack will not be underflown, the layer is popped
	// finally, retains info so methods and variables can be accessed
	public void endScope() throws Exception {
		// check for stack underflow
		if(symbols.size() == 0) {
			throw new Exception("ERROR: Stack Underflow");
		} else {
			// decrement scope and pop off the stack
			curscope--;
			symbols.pop();
		}		
	}

	// return the current scope
	public int getScope() {
		return this.curscope;
	}

	// return the top of the stack
	public Declaration getLastPushed() {
		return this.topDeclaration;
	}

	// return the top class of the stack
	public ClassDeclaration getLastPushedClass() {
		return this.topClass;
	}

	// return the top method of the stack
	public MethodDeclaration getLastPushedMethod() {
		return this.topMethod;
	}

	// return the top variable of the stack
	public VariableDeclaration getLastPushedVariable() {
		return this.topVariable;
	}

}
