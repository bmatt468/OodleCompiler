/* OffsetRegister.java
 * Author: Ethan McGee
 * Date: 2014-03-13
 * Purpose: provides an object oriented representation of an offset register
 */
package com.bju.cps450.instruction.parameters;

public class OffsetRegister extends Parameter {
	
	private Register register;
	private int offset;
	
	public OffsetRegister(Register register, int offset) {
		this.register = register;
		this.offset = offset;
	}
	
	/* toString
	 * Arguments:
	 *   
	 * returns the register and offset as a formatted string
	 */
	public String toString() {
		return this.offset + "(" + this.register.toString() + ")";
	}
}
