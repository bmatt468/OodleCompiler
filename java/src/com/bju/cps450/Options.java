/* Options.java
 * Author: Ethan McGee
 * Date: 2014-01-23
 * 
 * Purpose: A class holding the options passed to the compiler
 */

package com.bju.cps450;

public class Options {
	private boolean shouldPrintTokens;
	private boolean leaveSourceFile;
	
	public Options() {
		this.shouldPrintTokens = false;
		this.leaveSourceFile = false;
	}
	
	/* printTokens
	 * Arguments:
	 *   
	 * Purpose: alerts the compiler that it should print tokens
	 */
	public void printTokens() {
		this.shouldPrintTokens = true;
	}
	
	/* shouldPrintTokens
	 * Arguments:
	 *   
	 * Purpose: retrieve the value of shouldPrintTokens
	 */
	public boolean shouldPrintTokens() {
		return this.shouldPrintTokens;
	}
	
	/* printTokens
	 * Arguments:
	 *   
	 * Purpose: alerts the compiler that it should keep source file
	 */
	public void keepFile() {
		this.leaveSourceFile = true;
	}
	
	/* shouldPrintTokens
	 * Arguments:
	 *   
	 * Purpose: retrieve the value of leaveSourceFile
	 */
	public boolean shouldKeepFile() {
		return this.leaveSourceFile;
	}
	
}
