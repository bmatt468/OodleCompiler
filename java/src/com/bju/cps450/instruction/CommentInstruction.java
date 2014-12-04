package com.bju.cps450.instruction;

public class CommentInstruction extends InstructionCommand {
	private String comment;
		
		public CommentInstruction(String comment) {
			this.comment = comment;
		}
		
		/* emit
		 * Arguments:
		 *   
		 * Purpose: returns the instruction as a formatted string
		 */
		@Override
		public String emit() {
			return "\n# %C%\n".replace("%C%", this.comment);
		}
}
