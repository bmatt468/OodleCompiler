/* Label.java
 * Author: Ethan McGee
 * Date: 2014-03-13
 * Purpose: provides a representation of an assembly label
 */
package com.bju.cps450.instruction.parameters;

public class Label extends Parameter {

	private String label;
	
	public Label(String label) {
		this.label = label;
	}
	
	/* toString
	 * Arguments:
	 *   
	 * returns the label as a formatted string
	 */
	public String toString() {
		return this.label;
	}
}
