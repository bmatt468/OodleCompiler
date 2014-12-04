/* LabelInstruction.java
 * Author: Ethan McGee
 * Date: 2014-03-13
 * Purpose: produces an assembly label
 */
package com.bju.cps450.instruction;

public class LabelInstruction extends InstructionCommand {
	private String label;
	
	public LabelInstruction(String label) {
		this.label = label;
	}
	
	/* emit
	 * Arguments:
	 *   
	 * Purpose: returns the instruction as a formatted string
	 */
	@Override
	public String emit() {
		return label + ":\n";
	}
}
