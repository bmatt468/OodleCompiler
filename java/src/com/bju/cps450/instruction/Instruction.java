/* Instruction.java
 * Author: Ethan McGee
 * Date: 2014-03-13
 * Purpose: wraps assembly commands
 */
package com.bju.cps450.instruction;

public class Instruction {
	public static Instruction add = new Instruction("addl"),
			                  sub = new Instruction("subl"),
			                  push = new Instruction("pushl"),
			                  pop = new Instruction("popl"),
			                  call = new Instruction("call"),
							  ret = new Instruction("ret"),
							  cmp = new Instruction("cmpl"),
							  jne = new Instruction("jne"),
							  jmp = new Instruction("jmp"),
							  or = new Instruction("orl"),
							  and = new Instruction("andl"),
							  je = new Instruction("je"),
							  jg = new Instruction("jg"),
							  jge = new Instruction("jge"),
							  mul = new Instruction("imull"),
							  div = new Instruction("idivl"),
							  cdq = new Instruction("cdq"),
							  neg = new Instruction("neg"),
							  not = new Instruction("not"),
							  mov = new Instruction("movl"),
							  fld = new Instruction("fld"),
							  fst = new Instruction("fstp"),
							  fadd = new Instruction("fadd"),
							  fsub = new Instruction("fsubr"),
							  fmul = new Instruction("fmul"),
							  fdiv = new Instruction("fdiv"),
							  fabs = new Instruction("fabs"),
							  fchs = new Instruction("fchs"),
							  fcompp = new Instruction("fcompp");
	
	private String name;
	
	public Instruction(String name) {
		this.name = name;
	}
	
	/* getName
	 * Arguments:
	 *   
	 * Purpose: returns the name of an instruction
	 */
	public String getName() {
		return this.name;
	}
	
}
