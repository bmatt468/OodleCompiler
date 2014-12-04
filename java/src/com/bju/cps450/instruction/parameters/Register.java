/* Register.java
 * Author: Ethan McGee
 * Date: 2014-03-13
 * Purpose: provides an object oriented representation of registers  
 */
package com.bju.cps450.instruction.parameters;

public class Register extends Parameter {

	private String name;
	
	public static Register eax = new Register("eax"),
			               ebx = new Register("ebx"),
			               ecx = new Register("ecx"),
			               edx = new Register("edx"),
			               edi = new Register("edi"),
			               esi = new Register("esi"),
			               esp = new Register("esp"),
			               ebp = new Register("ebp"),
			               eip = new Register("eip");
	
	public Register(String name) {
		this.name = name;
	}
	
	/* toString
	 * Arguments:
	 *   
	 * returns the register as a string
	 */
	public String toString() {
		return "%" + this.name;
	}
	
}
