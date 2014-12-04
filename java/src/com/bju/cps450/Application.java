/* Application.java
 * Author: Ethan McGee
 * Date: 2014-01-23
 * 
 * Purpose: A singleton class that shares resources across the entire compiler
 */

package com.bju.cps450;

import java.util.ArrayList;
import java.util.HashMap;

public class Application {
	private static Options options;
	private static FileAndLineNumbers fileAndLineNumbers;
	private static Errors errors;
	private static SymbolTable symboltable;
	private static HashMap<String, ArrayList<String>> VFT = new HashMap<String, ArrayList<String>>();
	
	/* getOptions
	 * Arguments:
	 * 
	 * Purpose: grabs a copy of the Options class, creating one if the 
	 *          current class is null
	 */
	public static Options getOptions() {
		if(options == null) {
			options = new Options();
		}
		return options;
	}
	
	/* getFileAndLineNumbers
	 * Arguments:
	 * 
	 * Purpose: grabs a copy of the FileAndLineNumbers class, creating one if the 
	 *          current class is null
	 */
	public static FileAndLineNumbers getFileAndLineNumbers() {
		if(fileAndLineNumbers == null) {
			fileAndLineNumbers = new FileAndLineNumbers();
		}
		return fileAndLineNumbers;
	}
	
	/* getErrors
	 * Arguments:
	 * 
	 * Purpose: grabs a copy of the Errors class, creating one if the 
	 *          current class is null
	 */
	public static Errors getErrors() {
		if(errors == null) {
			errors = new Errors();
		}
		return errors;
	}
	
	/*
	 * getSymbolTable
	 * 
	 * Purpose: grabs a copy of the Errors class, creating one if the 
	 *          current class is null
	 */
	public static SymbolTable getSymbolTable() {
		if(symboltable == null) {
			symboltable = new SymbolTable();
		}
		return symboltable;
	}

	public static HashMap<String, ArrayList<String>> getVFT() {
		return VFT;
	}

	public static void setVFT(HashMap<String, ArrayList<String>> vFT) {
		VFT = vFT;
	}
}
