/* InstructionSet.java
 * Author: Ethan McGee
 * Date: 2014-03-13
 * Purpose: wraps a set of assembly instruction
 */package com.bju.cps450.instruction;

import java.util.ArrayList;

public class InstructionSet {
	private ArrayList<InstructionCommand> commands = new ArrayList<InstructionCommand>();
	
	public InstructionSet() {
		
	}
	
	/* appendInstruction
	 * Arguments:
	 *   
	 * Purpose: adds an instruction to the instruction set
	 */
	public void appendInstruction(InstructionCommand command) {
		commands.add(command);
	}
	
	/* appendInstructionSet
	 * Arguments:
	 *   
	 * Purpose: adds an instruction set to the instruction set
	 */
	public void appendInstructionSet(InstructionSet set) {
		for(InstructionCommand cmd : set.getCommands()) {
			commands.add(cmd);
		}
	}
	
	/* getCommands
	 * Arguments:
	 *   
	 * Purpose: returns the list of commands
	 */
	public ArrayList<InstructionCommand> getCommands() {
		return this.commands;
	}
	
	/* toAssembly
	 * Arguments:
	 *   
	 * Purpose: converts an instruction set to assembly
	 */
	public String toAssembly() {
		String s = "";
		for(InstructionCommand cmd : commands) {
			s += cmd.emit();
		}
		return s;
	}
}
