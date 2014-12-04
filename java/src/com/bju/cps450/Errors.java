/* Errors.java
 * Author: Ethan McGee
 * Date: 2014-01-23
 * 
 * Purpose: Keeps track of the number of errors encountered so far
 */

package com.bju.cps450;

public class Errors {

	private int lexicalErrors = 0;
	private int parserErrors = 0;
	private int semanticErrors = 0;
	private boolean unsupportedFeatures = false;
	
	public Errors() {
		
	}
	
	/* addLexicalError
	 * Arguments:
	 * 
	 * Purpose: increments number of lexical errors by 1
	 */
	public void addLexicalError() {
		this.lexicalErrors += 1;
	}
	
	/* addParserError
	 * Arguments:
	 * 
	 * Purpose: increments number of parser errors by 1
	 */
	public void addParserErrors() {
		this.parserErrors += 1;
	}
	
	/* addSemanticError
	 * Arguments:
	 * 
	 * Purpose: increments number of semantic errors by 1
	 */
	public void addSemanticErrors() {
		this.semanticErrors += 1;
	}
	
	/* getLexicalErrors
	 * Arguments:
	 * 
	 * Purpose: returns number of lexical errors
	 */
	public int getLexicalErrors() {
		return this.lexicalErrors;
	}
	
	/* getParserErrors
	 * Arguments:
	 * 
	 * Purpose: returns number of parser errors
	 */
	public int getParserErrors() {
		return this.parserErrors;
	}
	
	/* getSemanticErrors
	 * Arguments:
	 * 
	 * Purpose: returns number of semantic errors
	 */
	public int getSemanticErrors() {
		return this.semanticErrors;
	}
	
	/* getTotalErrors
	 * Arguments:
	 * 
	 * Purpose: returns total number of errors
	 */
	public int getTotalErrors() {
		return this.lexicalErrors + this.parserErrors + this.semanticErrors;
	}
	
	public void setUnsupportedFeatures() {
		this.unsupportedFeatures = true;
	}
	
	public boolean getUnsupportedFeatures() {
		return this.unsupportedFeatures;
	}
}
