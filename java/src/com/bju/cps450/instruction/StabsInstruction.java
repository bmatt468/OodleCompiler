/* StabsInstruction.java
 * Author: Ethan McGee
 * Date: 2014-03-13
 * Purpose: produces a GNU stabs argument
 */
package com.bju.cps450.instruction;

public class StabsInstruction extends InstructionCommand {
	private String stab;
	private String parameter = null;
	
	public StabsInstruction(String stab) {
		this.stab = stab;
	}
	
	public StabsInstruction(String stab, String parameter) {
		this.stab = stab;
		this.parameter = parameter;
	}
	
	/* emit
	 * Arguments:
	 *   
	 * Purpose: returns the instruction as a formatted string
	 */
	@Override
	public String emit() {
		if(this.parameter == null) {
			return "." + stab + "\n";
		} else {
			return "." + stab + " " + parameter + "\n";
		}
	}
}
