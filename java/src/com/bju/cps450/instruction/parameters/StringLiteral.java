package com.bju.cps450.instruction.parameters;

public class StringLiteral extends Parameter{
	private String string;
		
		public StringLiteral(String string) {
			this.string = string;
		}
		
		/* toString
		 * Arguments:
		 *   
		 * returns the integer literal as a formatted string
		 */
		public String toString() {
			return "$" + this.string;
		}
}
