package com.bju.cps450;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import com.bju.cps450.analysis.DepthFirstAdapter;
import com.bju.cps450.declarations.*;
import com.bju.cps450.instruction.*;
import com.bju.cps450.instruction.parameters.*;
import com.bju.cps450.node.AAddExpressionLvl4;
import com.bju.cps450.node.AAndExpressionLvl7;
import com.bju.cps450.node.AArgument;
import com.bju.cps450.node.AArgumentList;
import com.bju.cps450.node.AArgumentListTail;
import com.bju.cps450.node.AAssignStatementStatement;
import com.bju.cps450.node.AAssignmentStatement;
import com.bju.cps450.node.ACallExpressionLvl1;
import com.bju.cps450.node.ACallStatement;
import com.bju.cps450.node.ACallStatementStatement;
import com.bju.cps450.node.AClassDefinition;
import com.bju.cps450.node.AConcatExpressionLvl5;
import com.bju.cps450.node.ADivideExpressionLvl3;
import com.bju.cps450.node.AElseStatement;
import com.bju.cps450.node.AEqualsExpressionLvl6;
import com.bju.cps450.node.AExpression;
import com.bju.cps450.node.AExpressionDot;
import com.bju.cps450.node.AExpressionList;
import com.bju.cps450.node.AExpressionListTail;
import com.bju.cps450.node.AFalseExpressionLvl1;
import com.bju.cps450.node.AFloatExpressionLvl1;
import com.bju.cps450.node.AGreaterExpressionLvl6;
import com.bju.cps450.node.AGtEqualExpressionLvl6;
import com.bju.cps450.node.AIdentifierExpressionLvl1;
import com.bju.cps450.node.AIfStatement;
import com.bju.cps450.node.AIfStatementStatement;
import com.bju.cps450.node.AInitializer;
import com.bju.cps450.node.AIntegerExpressionLvl1;
import com.bju.cps450.node.ALoopStatement;
import com.bju.cps450.node.ALoopStatementStatement;
import com.bju.cps450.node.AMeExpressionLvl1;
import com.bju.cps450.node.AMethodDeclarations;
import com.bju.cps450.node.AMultExpressionLvl3;
import com.bju.cps450.node.ANegExpressionLvl2;
import com.bju.cps450.node.ANewExpressionLvl1;
import com.bju.cps450.node.ANotExpressionLvl2;
import com.bju.cps450.node.ANullExpressionLvl1;
import com.bju.cps450.node.AOrExpressionLvl8;
import com.bju.cps450.node.AOtherExpressionLvl2;
import com.bju.cps450.node.AOtherExpressionLvl3;
import com.bju.cps450.node.AOtherExpressionLvl4;
import com.bju.cps450.node.AOtherExpressionLvl5;
import com.bju.cps450.node.AOtherExpressionLvl6;
import com.bju.cps450.node.AOtherExpressionLvl7;
import com.bju.cps450.node.AOtherExpressionLvl8;
import com.bju.cps450.node.AParenExpressionLvl1;
import com.bju.cps450.node.APosExpressionLvl2;
import com.bju.cps450.node.AStatementList;
import com.bju.cps450.node.AStatementListTail;
import com.bju.cps450.node.AStringExpressionLvl1;
import com.bju.cps450.node.ASubtractExpressionLvl4;
import com.bju.cps450.node.ATrueExpressionLvl1;
import com.bju.cps450.node.AVariableDeclarations;
import com.bju.cps450.node.Node;
import com.bju.cps450.node.PStatement;
import com.bju.cps450.node.PStatementListTail;
import com.bju.cps450.node.PVariableDeclarations;
import com.bju.cps450.types.Type;
import com.sun.org.apache.bcel.internal.generic.InstructionList;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class CodeGenerator extends DepthFirstAdapter {
	private HashMap<Node, HashMap<String, Object>> attributeGrammarMap = new HashMap<Node, HashMap<String, Object>>();
	private ArrayList<InstructionSet> classAssembly = new ArrayList<InstructionSet>();
	private ArrayList<String> classNames = new ArrayList<String>();
	private HashMap<String,ArrayList<String>> methodNames = new HashMap<String, ArrayList<String>>();
	private ClassDeclaration currentClass;
	private MethodDeclaration currentMethod;
	private String startMethod;
	private int ifstmts = 0;
	private int whilestmts = 0;
	private int labelcount = 0;
	
	/* initNode
	* Arguments:
	* node : Node - the node to init
	* Purpose: helper to prevent null point exceptions
	*/
	private void initNode(Node node) {
		if(attributeGrammarMap.get(node) == null) {
		attributeGrammarMap.put(node, new HashMap<String, Object>());
		}
	}
	
	/* writeAssembly
	* Arguments:
	*
	* Purpose: generates a temporary assembly file containing the assembly code for the classes
	*/
	public void writeAssembly() throws IOException {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("%FN%.s"
					.replace("%FN%", Application.getFileAndLineNumbers().getLastFile())));		
			
			writer.write("STDOUT = 1\n");
			writer.write("STDIN = 0\n");
			
			writer.write(".data\n");
			writer.write(".comm _in ,4,4\n");
			writer.write(".comm _out ,4,4\n");
			
			writer.write("\n#begin vft work\n");
			for(String name : classNames) {
				writer.write("VFT" + name + ":\n");
				for (String methodName : Application.getVFT().get(name)) {
					writer.write(".long  " + methodName + "\n");
				}
				
			}
			
			for(InstructionSet assm : classAssembly) {
				writer.write(assm.toAssembly());
			}			
						
			writer.write(".text\n");
			writer.write(".global main\n");
			writer.write("main:\n");
			try {			
				ClassDeclaration rd = (ClassDeclaration) Application.getSymbolTable().lookup("Reader").getDeclaration();
				ClassDeclaration wd = (ClassDeclaration) Application.getSymbolTable().lookup("Writer").getDeclaration();
								
				writer.write("\tpushl $" + Integer.toString((rd.getVariables().size() + 4) * 4) + "\n");
				writer.write("\tpushl $1\n");
				writer.write("\tcall calloc\n");
				writer.write("\taddl $8, %esp\n");
				writer.write("\tpushl %eax\n");
				writer.write("\tcall _init_Reader\n");
				writer.write("\tpopl _in\n");
				
				writer.write("\tpushl $" + Integer.toString((wd.getVariables().size() + 4) * 4) + "\n");
				writer.write("\tpushl $1\n");
				writer.write("\tcall calloc\n");
				writer.write("\taddl $8, %esp\n");
				writer.write("\tpushl %eax\n");
				writer.write("\tcall _init_Writer\n");
				writer.write("\tpopl _out\n");
			} catch (Exception e) {
				e.printStackTrace();
			}			
			
			writer.write("\tpushl $" + Integer.toString((Application.getSymbolTable().getLastPushedClass().getVariables().size() + 4) * 4) + "\n");
			writer.write("\tpushl $1\n");
			writer.write("\tcall calloc\n");
			writer.write("\taddl $8, %esp\n");
			writer.write("\tpushl %eax\n");
			writer.write("\tcall _init_" + Application.getSymbolTable().getLastPushedClass().getName() + "\n");
			writer.write("\tcall " + startMethod + "\n");
			
			writer.write("\tpushl $0\n");
			writer.write("\tcall exit\n");
			writer.close();
		} catch (IOException e) {
			System.out.println("could not generate assembly due to " + e.getMessage());
			throw new IOException();
		}	
	}
		
	@Override
	public void inAClassDefinition(AClassDefinition node) {
		super.inAClassDefinition(node);
		try {
			currentClass = (ClassDeclaration)Application.getSymbolTable().controlledLookup(node.getBeginId().getText(), 0).getDeclaration();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
		
	@Override
	public void inAMethodDeclarations(AMethodDeclarations node) {
		super.inAMethodDeclarations(node);
		try {
			currentMethod = currentClass.lookupMethod(node.getBeginId().getText());
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void outAClassDefinition(AClassDefinition node) {
		super.outAClassDefinition(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin variable declarations for class %C%".replace("%C%", node.getBeginId().getText())));
		s.appendInstruction(new StabsInstruction("text"));	
		
		s.appendInstruction(new LabelInstruction("_init_"+node.getBeginId().getText()));
		s.appendInstruction(new InstructionCommand(Instruction.push, Register.ebp));
		s.appendInstruction(new InstructionCommand(Instruction.mov, Register.esp, Register.ebp));
		s.appendInstruction(new InstructionCommand(Instruction.mov, new OffsetRegister(Register.ebp, 8), Register.ecx));
		for(int i = 0; i < node.getVariableDeclarations().size(); ++i) {			
			AVariableDeclarations asdf = (AVariableDeclarations) node.getVariableDeclarations().get(i);
			if(asdf.getInitializer() != null) {
				s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getVariableDeclarations().get(i)).get("code"));
			}
			else {
				s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(0)));
			}
			int x = -1;
			try {
				x = currentClass.lookupVariable(asdf.getIdentifier().getText()).getOffset();
			} catch (Exception e) {
			}
			s.appendInstruction(new InstructionCommand(Instruction.pop, new OffsetRegister(Register.ecx, (x+4) * 4)));
		}
		s.appendInstruction(new InstructionCommand(Instruction.mov, Register.ebp, Register.esp));
		s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebp));	
		s.appendInstruction(new InstructionCommand(Instruction.ret));
		
		s.appendInstruction(new CommentInstruction("begin method declarations for class %C%".replace("%C%", node.getBeginId().getText())));
		for(int i = 0; i < node.getMethodDeclarations().size(); ++i) {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getMethodDeclarations().get(i)).get("code"));
		}
		classAssembly.add(s);
		classNames.add(node.getBeginId().getText());
		currentClass = null;
	}

	@Override
	public void outAVariableDeclarations(AVariableDeclarations node) {
		super.outAVariableDeclarations(node);
		initNode(node);
		try {
			initNode(node);
			InstructionSet s = new InstructionSet();
			VariableDeclaration var = null;
			
			if(currentMethod == null) {
				var = currentClass.lookupVariable(node.getIdentifier().getText());				
			} else {
				var = currentMethod.lookupVariable(node.getIdentifier().getText());				
			}
			
			if (node.getInitializer() != null) {
				s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getInitializer()).get("code"));
				if(!var.getIsClassVar()) {
					s.appendInstruction(new InstructionCommand(Instruction.pop, new OffsetRegister(Register.ebp, (var.getOffset()+1) * -4)));
				}			
			}
			attributeGrammarMap.get(node).put("code", s);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void outAMethodDeclarations(AMethodDeclarations node) {
		super.outAMethodDeclarations(node);
		initNode(node);

		if(currentMethod.getName().equals("start")) {
			startMethod = currentMethod.getAssemblyName();
		}

		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin declaration for method %M%".replace("%M%", node.getBeginId().getText())));
		s.appendInstruction(new StabsInstruction("text"));
		s.appendInstruction(new LabelInstruction(currentMethod.getAssemblyName()));
		s.appendInstruction(new CommentInstruction("begin variable and code declarations for method %M%".replace("%M%", node.getBeginId().getText())));
		
		s.appendInstruction(new InstructionCommand(Instruction.push, Register.ebp));
		s.appendInstruction(new InstructionCommand(Instruction.mov, Register.esp, Register.ebp));
		//s.appendInstruction(new InstructionCommand(Instruction.sub, new IntegerLiteral(currentMethod.getVariables().size() * 4 + 4), Register.esp));
		for (int i = 0; i < currentMethod.getVariables().size(); ++i) {
			s.appendInstruction(new InstructionCommand(Instruction.push,new IntegerLiteral(0)));
			try {				
				s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getVariableDeclarations().get(i)).get("code"));				
			} catch (Exception e) {
				
			}
		}
		
		try {
			s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getStatementList()).get("code"));		
		} catch (Exception e) {;} // no statement list
		try {
			int loc = currentMethod.lookupVariable(currentMethod.getName()).getOffset();
			s.appendInstruction(new InstructionCommand(Instruction.mov, new OffsetRegister(Register.ebp, (loc + 1) * -4), Register.eax));
		} catch (Exception e) {
			
		}		
		
		s.appendInstruction(new InstructionCommand(Instruction.mov, Register.ebp, Register.esp));
		s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebp));		
		s.appendInstruction(new InstructionCommand(Instruction.ret));
		s.appendInstruction(new CommentInstruction("end method %M%".replace("%M%", node.getBeginId().getText())));
		attributeGrammarMap.get(node).put("code", s);		
		currentMethod = null;		
	}

	@Override
	public void outAStatementList(AStatementList node) {
		super.outAStatementList(node);
		initNode(node);		
		InstructionSet s = new InstructionSet();		
		s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getStatement()).get("code"));
		if (node.getStatementListTail() != null) {
			for (int i = 0; i < node.getStatementListTail().size(); ++i) {
				InstructionSet is = (InstructionSet) attributeGrammarMap.get(node.getStatementListTail().get(i)).get("code");				
				s.appendInstructionSet(is);
			}		
		}
		attributeGrammarMap.get(node).put("size", node.getStatementListTail().size() + 1);
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAStatementListTail(AStatementListTail node) {
		super.outAStatementListTail(node);
		initNode(node);
		try {
			InstructionSet s = new InstructionSet();
			s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getStatement()).get("code"));
			attributeGrammarMap.get(node).put("code", s);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void outAIfStatement(AIfStatement node) {		
		super.outAIfStatement(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin if statement %N%".replace("%N%", Integer.toString(ifstmts + 1))));
		// get expression code
		s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getExpression()).get("code"));
		// pop expression into eax
		s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		// compare eax to see if expression is true
		s.appendInstruction(new InstructionCommand(Instruction.cmp, new IntegerLiteral(0), Register.eax));
		// jump if true
		s.appendInstruction(new InstructionCommand(Instruction.jne, new Label("_if%N%".replace("%N%", Integer.toString(++ifstmts)))));
		// add else handling
		s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_else%N%".replace("%N%", Integer.toString(ifstmts)))));
		// add if code
		s.appendInstruction(new LabelInstruction("_if%N%".replace("%N%", Integer.toString(ifstmts))));
		try {
			s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getStatementList()).get("code"));
		} catch (Exception e) {;}
		// add jump to endif
		s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_endif%N%".replace("%N%", Integer.toString(ifstmts)))));
		// add else code
		s.appendInstruction(new LabelInstruction("_else%N%".replace("%N%", Integer.toString(ifstmts))));
		try {
			s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getElseStatement()).get("code"));
		} catch (Exception e) {;} // no else statement
		// add jump to endif
		s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_endif%N%".replace("%N%", Integer.toString(ifstmts)))));
		// add endif
		s.appendInstruction(new LabelInstruction("_endif%N%".replace("%N%", Integer.toString(ifstmts))));
		s.appendInstruction(new CommentInstruction("end if statement %N%".replace("%N%", Integer.toString(ifstmts))));
		// insert into grammar map
		attributeGrammarMap.get(node).put("code", s); 
	}	
	
	@Override
	public void outAElseStatement(AElseStatement node) {
		super.outAElseStatement(node);
		initNode(node);		
		attributeGrammarMap.get(node).put("code", attributeGrammarMap.get(node.getStatementList()).get("code"));
	}
	
	@Override
	public void outALoopStatement(ALoopStatement node) {
		super.outALoopStatement(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin while statement %N%".replace("%N%", Integer.toString(whilestmts + 1))));
		// insert while label
		s.appendInstruction(new LabelInstruction("_while%N%".replace("%N%", Integer.toString(++whilestmts))));
		// insert condition code
		s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getExpression()).get("code"));
		// test condition
		s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		s.appendInstruction(new InstructionCommand(Instruction.cmp, new IntegerLiteral(0), Register.eax));
		// jump to while body if needed
		s.appendInstruction(new InstructionCommand(Instruction.jne, new Label("_startwhilebody%N%".replace("%N%", Integer.toString(whilestmts)))));
		// else jump to endwhile
		s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_endwhile%N%".replace("%N%", Integer.toString(whilestmts)))));
		// while body
		s.appendInstruction(new LabelInstruction("_startwhilebody%N%".replace("%N%", Integer.toString(whilestmts))));
		s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getStatementList()).get("code"));
		// jump to while
		s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_while%N%".replace("%N%", Integer.toString(whilestmts)))));
		// insert endwhile
		s.appendInstruction(new LabelInstruction("_endwhile%N%".replace("%N%", Integer.toString(whilestmts))));
		s.appendInstruction(new CommentInstruction("end while statement %N%".replace("%N%", Integer.toString(whilestmts))));
		// insert into grammar map
		attributeGrammarMap.get(node).put("code", s); 
	}

	@Override
	public void outACallStatement(ACallStatement node) {
		super.outACallStatement(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin %S% call statement".replace("%S%", node.getIdentifier().getText())));
		try {
			s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getExpressionList()).get("code"));
		} catch (Exception e) {;}//no expression list
		InstructionSet dotAssembly = null;
		try {
			dotAssembly = (InstructionSet)attributeGrammarMap.get(node.getExpressionDot()).get("code");
		} catch (Exception e) {
			dotAssembly = new InstructionSet();
		}
		
		try {
			MethodDeclaration method = null;
			if (node.getExpressionDot() != null) {
				method = ((ClassDeclaration) attributeGrammarMap.get(node.getExpressionDot()).get("classname")).lookupMethod(node.getIdentifier().getText());
				s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getExpressionDot()).get("code"));
				
			} else {
				method = currentClass.lookupMethod(node.getIdentifier().getText());
				s.appendInstruction(new InstructionCommand(Instruction.push, new OffsetRegister(Register.ebp, 8)));				
			}				
			// null check
			s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(node.getIdentifier().getLine())));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label("nullpointertest")));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(Integer.parseInt("4")), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label(method.getAssemblyName())));
			
			// begin a level test			
			/*s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ecx));
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.ecx));
			s.appendInstruction(new InstructionCommand(Instruction.mov, new OffsetRegister(Register.ecx, 0), Register.edx));
			s.appendInstruction(new InstructionCommand(Instruction.call, new OffsetRegister(Register.edx, (method.getOffset()+1) * 4)));*/
			
			
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral((method.getParamters().size()+1) * 4), Register.esp));
			
			attributeGrammarMap.get(node).put("classname", method.getType().getClassDecl());
		} catch (Exception ex) { 
			ex.printStackTrace(); 
		}
		
		s.appendInstruction(new CommentInstruction("end %S% call statement".replace("%S%", node.getIdentifier().getText())));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAAssignmentStatement(AAssignmentStatement node) {
		super.outAAssignmentStatement(node);
		initNode(node);
		try {
			VariableDeclaration var = currentMethod.lookupVariable(node.getIdentifier().getText());
			InstructionSet instructions = new InstructionSet();
			instructions.appendInstruction(new CommentInstruction("begin assignment of %S%".replace("%S%", node.getIdentifier().getText())));
			instructions.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpression()).get("code"));			
			if(var.getIsClassVar()) {
				var = currentClass.lookupVariable(node.getIdentifier().getText());
				instructions.appendInstruction(new InstructionCommand(Instruction.mov, new OffsetRegister(Register.ebp, 8), Register.ecx));
				instructions.appendInstruction(new InstructionCommand(Instruction.pop, new OffsetRegister(Register.ecx, (var.getOffset()+4) * 4)));
			} else {
				var = currentMethod.lookupVariable(node.getIdentifier().getText());
				if (!(var.isParameter())) {
					instructions.appendInstruction(new InstructionCommand(Instruction.pop, new OffsetRegister(Register.ebp, (var.getOffset() + 1) * -4)));
				} else {
					instructions.appendInstruction(new InstructionCommand(Instruction.pop, new OffsetRegister(Register.ebp, (var.getOffset() + 3) * 4)));
				}
				
			}			
			instructions.appendInstruction(new CommentInstruction("end assignment of %S%".replace("%S%", node.getIdentifier().getText())));
			attributeGrammarMap.get(node).put("code", instructions);			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void outAExpressionList(AExpressionList node) {
		super.outAExpressionList(node);
		initNode(node);		
		InstructionSet s = new InstructionSet();		
		if (node.getExpressionListTail() != null) {
			for (int i = node.getExpressionListTail().size()-1; i >= 0; --i) {
				InstructionSet is = (InstructionSet) attributeGrammarMap.get(node.getExpressionListTail().get(i)).get("code");				
				s.appendInstructionSet(is);
			}		
		}
		s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getExpression()).get("code"));
		attributeGrammarMap.get(node).put("code", s);

	}

	@Override
	public void outAExpressionListTail(AExpressionListTail node) {
		super.outAExpressionListTail(node);
		initNode(node);
		attributeGrammarMap.get(node).put("code", (InstructionSet) attributeGrammarMap.get(node.getExpression()).get("code"));
	}

	@Override
	public void outAExpression(AExpression node) {
		super.outAExpression(node);
		initNode(node);		
		attributeGrammarMap.get(node).put("code", (InstructionSet) attributeGrammarMap.get(node.getExpressionLvl8()).get("code"));
		try {
			attributeGrammarMap.get(node).put("classname", attributeGrammarMap.get(node.getExpressionLvl8()).get("classname"));
		} catch (Exception e) {
			
		}
		try {
			attributeGrammarMap.get(node).put("isFloat", attributeGrammarMap.get(node.getExpressionLvl8()).get("isFloat"));
		} catch (Exception e) {
			
		}
		
	}

	@Override
	public void outAOtherExpressionLvl8(AOtherExpressionLvl8 node) {
		super.outAOtherExpressionLvl8(node);
		initNode(node);
		attributeGrammarMap.get(node).put("code",(InstructionSet) attributeGrammarMap.get(node.getExpressionLvl7()).get("code"));
		try {
			attributeGrammarMap.get(node).put("classname", attributeGrammarMap.get(node.getExpressionLvl7()).get("classname"));
		} catch (Exception e) {
			
		}
		try {
			attributeGrammarMap.get(node).put("isFloat", attributeGrammarMap.get(node.getExpressionLvl7()).get("isFloat"));
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOtherExpressionLvl7(AOtherExpressionLvl7 node) {
		super.outAOtherExpressionLvl7(node);
		initNode(node);
		attributeGrammarMap.get(node).put("code",(InstructionSet) attributeGrammarMap.get(node.getExpressionLvl6()).get("code"));
		try {
			attributeGrammarMap.get(node).put("classname", attributeGrammarMap.get(node.getExpressionLvl6()).get("classname"));
		} catch (Exception e) {
			
		}
		try {
			attributeGrammarMap.get(node).put("isFloat", attributeGrammarMap.get(node.getExpressionLvl6()).get("isFloat"));
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOtherExpressionLvl6(AOtherExpressionLvl6 node) {
		super.outAOtherExpressionLvl6(node);
		initNode(node);
		attributeGrammarMap.get(node).put("code",(InstructionSet) attributeGrammarMap.get(node.getExpressionLvl5()).get("code"));
		try {
			attributeGrammarMap.get(node).put("classname", attributeGrammarMap.get(node.getExpressionLvl5()).get("classname"));
		} catch (Exception e) {
			
		}
		try {
			attributeGrammarMap.get(node).put("isFloat", attributeGrammarMap.get(node.getExpressionLvl5()).get("isFloat"));
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOtherExpressionLvl5(AOtherExpressionLvl5 node) {
		super.outAOtherExpressionLvl5(node);
		initNode(node);
		attributeGrammarMap.get(node).put("code",(InstructionSet) attributeGrammarMap.get(node.getExpressionLvl4()).get("code"));
		try {
			attributeGrammarMap.get(node).put("classname", attributeGrammarMap.get(node.getExpressionLvl4()).get("classname"));
		} catch (Exception e) {
			
		}
		try {
			attributeGrammarMap.get(node).put("isFloat", attributeGrammarMap.get(node.getExpressionLvl4()).get("isFloat"));
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOtherExpressionLvl4(AOtherExpressionLvl4 node) {
		super.outAOtherExpressionLvl4(node);
		initNode(node);
		attributeGrammarMap.get(node).put("code",(InstructionSet) attributeGrammarMap.get(node.getExpressionLvl3()).get("code"));
		try {
			attributeGrammarMap.get(node).put("classname", attributeGrammarMap.get(node.getExpressionLvl3()).get("classname"));
		} catch (Exception e) {
			
		}
		try {
			attributeGrammarMap.get(node).put("isFloat", attributeGrammarMap.get(node.getExpressionLvl3()).get("isFloat"));
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOtherExpressionLvl3(AOtherExpressionLvl3 node) {
		super.outAOtherExpressionLvl3(node);
		initNode(node);
		attributeGrammarMap.get(node).put("code",(InstructionSet) attributeGrammarMap.get(node.getExpressionLvl2()).get("code"));
		try {
			attributeGrammarMap.get(node).put("classname", attributeGrammarMap.get(node.getExpressionLvl2()).get("classname"));
		} catch (Exception e) {
			
		}
		try {
			attributeGrammarMap.get(node).put("isFloat", attributeGrammarMap.get(node.getExpressionLvl2()).get("isFloat"));
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOtherExpressionLvl2(AOtherExpressionLvl2 node) {
		super.outAOtherExpressionLvl2(node);
		initNode(node);
		attributeGrammarMap.get(node).put("code",(InstructionSet) attributeGrammarMap.get(node.getExpressionLvl1()).get("code"));
		try {
			attributeGrammarMap.get(node).put("classname", attributeGrammarMap.get(node.getExpressionLvl1()).get("classname"));
		} catch (Exception e) {
			
		}
		
		try {
			attributeGrammarMap.get(node).put("isFloat", attributeGrammarMap.get(node.getExpressionLvl1()).get("isFloat"));
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOrExpressionLvl8(AOrExpressionLvl8 node) {
		super.outAOrExpressionLvl8(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% or statement".replace("%N%", Integer.toString(node.getOr().getLine()))));
		// get code from expressions
		s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl8()).get("code"));
		s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl7()).get("code"));
		// prep for or		
		s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
		// perform or
		s.appendInstruction(new InstructionCommand(Instruction.or, Register.ebx, Register.eax));
		// push result into eax
		s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		// store in grammar map
		s.appendInstruction(new CommentInstruction("end line %N% or statement".replace("%N%", Integer.toString(node.getOr().getLine()))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAAndExpressionLvl7(AAndExpressionLvl7 node) {
		super.outAAndExpressionLvl7(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% and statement".replace("%N%", Integer.toString(node.getAnd().getLine()))));
		// get code from expressions
		s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl7()).get("code"));
		s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl6()).get("code"));
		// prep for and		
		s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
		// perform and
		s.appendInstruction(new InstructionCommand(Instruction.and, Register.ebx, Register.eax));
		// push result into eax
		s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		// store in grammar map
		s.appendInstruction(new CommentInstruction("end line %N% and statement".replace("%N%", Integer.toString(node.getAnd().getLine()))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAEqualsExpressionLvl6(AEqualsExpressionLvl6 node) {
		super.outAEqualsExpressionLvl6(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% equals statement".replace("%N%", Integer.toString(node.getEquals().getLine()))));
		
		boolean isFloat = false;
		boolean isString = false;
		try {			
			if ((Boolean) attributeGrammarMap.get(node.getFirst()).get("isFloat")) {
				isFloat = true;
			}
		} catch (Exception e) {
			
		}
		try {
			isString = ((ClassDeclaration) attributeGrammarMap.get(node.getFirst()).get("classname")).getName().equals("String");
		} catch (Exception e) {
		
		}
		
		if (isString) {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getSecond()).get("code"));	
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getFirst()).get("code"));		
			
			s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(node.getEquals().getLine())));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label("nullpointertest")));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4) , Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label("_String_eq")));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(8), Register.esp));			
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		} else if (!isFloat) {
			// get code from expressions
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getFirst()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getSecond()).get("code"));
			// prep for comparison	
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
			// perform comparison
			s.appendInstruction(new InstructionCommand(Instruction.cmp, Register.ebx, Register.eax));
			// check result
			s.appendInstruction(new InstructionCommand(Instruction.je, new Label("_equal%N%".replace("%N%", Integer.toString(++labelcount)))));
			s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_notequal%N%".replace("%N%", Integer.toString(labelcount)))));		
			// if equal
			s.appendInstruction(new LabelInstruction("_equal%N%".replace("%N%", Integer.toString(labelcount))));
			s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(1)));
			s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_end%N%".replace("%N%", Integer.toString(labelcount)))));	
			// if not equal
			s.appendInstruction(new LabelInstruction("_notequal%N%".replace("%N%", Integer.toString(labelcount))));
			s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(0)));
			s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_end%N%".replace("%N%", Integer.toString(labelcount)))));
			// end
			s.appendInstruction(new LabelInstruction("_end%N%".replace("%N%", Integer.toString(labelcount))));
		} else {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getSecond()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getFirst()).get("code"));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label("_Float_isEqual")));
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		}
		
		// store in grammar map
		s.appendInstruction(new CommentInstruction("end line %N% equals statement".replace("%N%", Integer.toString(node.getEquals().getLine()))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAGreaterExpressionLvl6(AGreaterExpressionLvl6 node) {
		super.outAGreaterExpressionLvl6(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% greater statement".replace("%N%", Integer.toString(node.getGreater().getLine()))));
		
		boolean isFloat = false;
		boolean isString = false;
		try {			
			if ((Boolean) attributeGrammarMap.get(node.getFirst()).get("isFloat")) {
				isFloat = true;
			}
		} catch (Exception e) {
			
		}
		try {
			isString = ((ClassDeclaration) attributeGrammarMap.get(node.getFirst()).get("classname")).getName().equals("String");
		} catch (Exception e) {
		
		}
		if (isString) {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getSecond()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getFirst()).get("code"));		
				
			s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(node.getGreater().getLine())));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label("nullpointertest")));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4) , Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label("_String_gt")));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(8), Register.esp));			
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		} else if (!isFloat) {
			// get code from expressions
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getFirst()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getSecond()).get("code"));
			// prep for comparison	
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
			// perform comparison
			s.appendInstruction(new InstructionCommand(Instruction.cmp, Register.ebx, Register.eax));
			// check result
			s.appendInstruction(new InstructionCommand(Instruction.jg, new Label("_equal%N%".replace("%N%", Integer.toString(++labelcount)))));
			s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_notequal%N%".replace("%N%", Integer.toString(labelcount)))));		
			// if equal
			s.appendInstruction(new LabelInstruction("_equal%N%".replace("%N%", Integer.toString(labelcount))));
			s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(1)));
			s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_end%N%".replace("%N%", Integer.toString(labelcount)))));	
			// if not equal
			s.appendInstruction(new LabelInstruction("_notequal%N%".replace("%N%", Integer.toString(labelcount))));
			s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(0)));
			s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_end%N%".replace("%N%", Integer.toString(labelcount)))));
			// end
			s.appendInstruction(new LabelInstruction("_end%N%".replace("%N%", Integer.toString(labelcount))));
		} else {			
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getSecond()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getFirst()).get("code"));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label("_Float_isGreater")));
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		}
		
		// store in grammar map
		s.appendInstruction(new CommentInstruction("end line %N% greater statement".replace("%N%", Integer.toString(node.getGreater().getLine()))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAGtEqualExpressionLvl6(AGtEqualExpressionLvl6 node) {
		super.outAGtEqualExpressionLvl6(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% greater equals statement".replace("%N%", Integer.toString(node.getGreaterEqual().getLine()))));
		
		boolean isFloat = false;
		boolean isString = false;
		try {			
			if ((Boolean) attributeGrammarMap.get(node.getFirst()).get("isFloat")) {
				isFloat = true;
			}			
		} catch (Exception e) {
			
		}
		try {
			isString = ((ClassDeclaration) attributeGrammarMap.get(node.getFirst()).get("classname")).getName().equals("String");
		} catch (Exception e) {
		
		}		
		if (isString) {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getSecond()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getFirst()).get("code"));		
				
			s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(node.getGreaterEqual().getLine())));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label("nullpointertest")));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4) , Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label("_String_gteq")));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(8), Register.esp));			
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		} else if (!isFloat) {
			// get code from expressions
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getFirst()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getSecond()).get("code"));
			// prep for comparison	
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
			// perform comparison
			s.appendInstruction(new InstructionCommand(Instruction.cmp, Register.ebx, Register.eax));
			// check result
			s.appendInstruction(new InstructionCommand(Instruction.jge, new Label("_equal%N%".replace("%N%", Integer.toString(++labelcount)))));
			s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_notequal%N%".replace("%N%", Integer.toString(labelcount)))));		
			// if equal
			s.appendInstruction(new LabelInstruction("_equal%N%".replace("%N%", Integer.toString(labelcount))));
			s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(1)));
			s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_end%N%".replace("%N%", Integer.toString(labelcount)))));	
			// if not equal
			s.appendInstruction(new LabelInstruction("_notequal%N%".replace("%N%", Integer.toString(labelcount))));
			s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(0)));
			s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_end%N%".replace("%N%", Integer.toString(labelcount)))));
			// end
			s.appendInstruction(new LabelInstruction("_end%N%".replace("%N%", Integer.toString(labelcount))));
		} else {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getSecond()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getFirst()).get("code"));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label("_Float_isGreaterEqual")));
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		}
		
		// store in grammar map
		s.appendInstruction(new CommentInstruction("end line %N% greater equals statement".replace("%N%", Integer.toString(node.getGreaterEqual().getLine()))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAConcatExpressionLvl5(AConcatExpressionLvl5 node) {
		super.outAConcatExpressionLvl5(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% concat expression"
				.replace("%N%", Integer.toString(node.getConcatenate().getLine())
				.replace("%P%", Integer.toString(node.getConcatenate().getPos())))));
		
		s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl4()).get("code"));		
		s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl5()).get("code"));	
		s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(node.getConcatenate().getLine())));
		s.appendInstruction(new InstructionCommand(Instruction.call, new Label("nullpointertest")));
		s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4) , Register.esp));
		s.appendInstruction(new InstructionCommand(Instruction.call, new Label("_String_cat")));
		s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(8), Register.esp));			
		s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% concat expression"
				.replace("%N%", Integer.toString(node.getConcatenate().getLine())
				.replace("%P%", Integer.toString(node.getConcatenate().getPos())))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAAddExpressionLvl4(AAddExpressionLvl4 node) {
		super.outAAddExpressionLvl4(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% add expression"
				.replace("%N%", Integer.toString(node.getPlus().getLine())
				.replace("%P%", Integer.toString(node.getPlus().getPos())))));
		
		boolean isFloat = false;
		try {			
			if ((Boolean) attributeGrammarMap.get(node.getExpressionLvl4()).get("isFloat")) {
				isFloat = true;
			}
		} catch (Exception e) {
			
		}
		
		if (!isFloat) {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl3()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl4()).get("code"));
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
			s.appendInstruction(new InstructionCommand(Instruction.add, Register.ebx, Register.eax));
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		} else {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl3()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl4()).get("code"));			
			s.appendInstruction(new InstructionCommand(Instruction.fld, new OffsetRegister(Register.esp, 0)));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fld, new OffsetRegister(Register.esp, 0)));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fadd));
			s.appendInstruction(new InstructionCommand(Instruction.sub, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fst, new OffsetRegister(Register.esp,0)));
		}
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% add expression"
				.replace("%N%", Integer.toString(node.getPlus().getLine())
				.replace("%P%", Integer.toString(node.getPlus().getPos())))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outASubtractExpressionLvl4(ASubtractExpressionLvl4 node) {
		super.outASubtractExpressionLvl4(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% subtract expression"
				.replace("%N%", Integer.toString(node.getMinus().getLine())
				.replace("%P%", Integer.toString(node.getMinus().getPos())))));
		
		boolean isFloat = false;
		try {			
			if ((Boolean) attributeGrammarMap.get(node.getExpressionLvl4()).get("isFloat")) {
				isFloat = true;
			}
		} catch (Exception e) {
			
		}
		if (!isFloat) {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl3()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl4()).get("code"));
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
			s.appendInstruction(new InstructionCommand(Instruction.sub, Register.ebx, Register.eax));
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		} else {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl3()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl4()).get("code"));			
			s.appendInstruction(new InstructionCommand(Instruction.fld, new OffsetRegister(Register.esp, 0)));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fld, new OffsetRegister(Register.esp, 0)));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fsub));
			s.appendInstruction(new InstructionCommand(Instruction.sub, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fst, new OffsetRegister(Register.esp,0)));
		}
		
		
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% subtract expression"
			.replace("%N%", Integer.toString(node.getMinus().getLine())
			.replace("%P%", Integer.toString(node.getMinus().getPos())))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAMultExpressionLvl3(AMultExpressionLvl3 node) {
		super.outAMultExpressionLvl3(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% multiply expression"
			.replace("%N%", Integer.toString(node.getMultiply().getLine())
			.replace("%P%", Integer.toString(node.getMultiply().getPos())))));
		boolean isFloat = false;
		try {			
			if ((Boolean) attributeGrammarMap.get(node.getExpressionLvl3()).get("isFloat")) {
				isFloat = true;
			}
		} catch (Exception e) {
			
		}
		if (!isFloat) {		
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl3()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl2()).get("code"));
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
			s.appendInstruction(new InstructionCommand(Instruction.mul, Register.ebx, Register.eax));
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		} else {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl3()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl2()).get("code"));			
			s.appendInstruction(new InstructionCommand(Instruction.fld, new OffsetRegister(Register.esp, 0)));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fld, new OffsetRegister(Register.esp, 0)));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fmul));
			s.appendInstruction(new InstructionCommand(Instruction.sub, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fst, new OffsetRegister(Register.esp,0)));
		}
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% multiply expression"
				.replace("%N%", Integer.toString(node.getMultiply().getLine())
				.replace("%P%", Integer.toString(node.getMultiply().getPos())))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outADivideExpressionLvl3(ADivideExpressionLvl3 node) {
		super.outADivideExpressionLvl3(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% divide expression"
				.replace("%N%", Integer.toString(node.getDivide().getLine())
				.replace("%P%", Integer.toString(node.getDivide().getPos())))));
		
		boolean isFloat = false;
		try {			
			if ((Boolean) attributeGrammarMap.get(node.getExpressionLvl3()).get("isFloat")) {
				isFloat = true;
			}
		} catch (Exception e) {
			
		}
		if (!isFloat) {	
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl2()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl3()).get("code"));
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ebx));
			// do some assembly magic to prep for divisiion.
			s.appendInstruction(new InstructionCommand(Instruction.cdq));
			// perform division
			s.appendInstruction(new InstructionCommand(Instruction.div, Register.ebx));
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		} else {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl3()).get("code"));
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl2()).get("code"));			
			s.appendInstruction(new InstructionCommand(Instruction.fld, new OffsetRegister(Register.esp, 0)));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fld, new OffsetRegister(Register.esp, 0)));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fdiv));
			s.appendInstruction(new InstructionCommand(Instruction.sub, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fst, new OffsetRegister(Register.esp,0)));
		}
		
		
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% divide expression"
				.replace("%N%", Integer.toString(node.getDivide().getLine())
				.replace("%P%", Integer.toString(node.getDivide().getPos())))));
		attributeGrammarMap.get(node).put("code", s);
	}
	
	@Override
	public void outAPosExpressionLvl2(APosExpressionLvl2 node) {
		super.outAPosExpressionLvl2(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% positive expression"
				.replace("%N%", Integer.toString(node.getPlus().getLine())
				.replace("%P%", Integer.toString(node.getPlus().getPos())))));
		
		boolean isFloat = false;
		try {			
			if ((Boolean) attributeGrammarMap.get(node.getExpressionLvl2()).get("isFloat")) {
				isFloat = true;
			}
		} catch (Exception e) {
			
		}
		if (!isFloat) {			
			// load expression
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl2()).get("code"));
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
			// check to see if expression is positive
			s.appendInstruction(new InstructionCommand(Instruction.cmp, new IntegerLiteral(0)));
			s.appendInstruction(new InstructionCommand(Instruction.jge, new Label("_positive%N%".replace("%N%", Integer.toString(++labelcount)))));
			s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_notpositive%N%".replace("%N%", Integer.toString(labelcount)))));	
			// if not positive, then make positive
			s.appendInstruction(new LabelInstruction("_notpositive%N%".replace("%N%", Integer.toString(labelcount))));
			s.appendInstruction(new InstructionCommand(Instruction.not, Register.eax));
			s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_end%N%".replace("%N%", Integer.toString(labelcount)))));
			// if positive do nothing
			s.appendInstruction(new LabelInstruction("_positive%N%".replace("%N%", Integer.toString(labelcount))));		
			s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_end%N%".replace("%N%", Integer.toString(labelcount)))));	
			// insert end marker		
			s.appendInstruction(new LabelInstruction("_end%N%".replace("%N%", Integer.toString(labelcount))));		
		} else {			
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl2()).get("code"));			
			s.appendInstruction(new InstructionCommand(Instruction.fld, new OffsetRegister(Register.esp, 0)));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fabs));
			s.appendInstruction(new InstructionCommand(Instruction.sub, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fst, new OffsetRegister(Register.esp,0)));
		}
		
		
		// store in grammar map
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% positive expression"
				.replace("%N%", Integer.toString(node.getPlus().getLine())
				.replace("%P%", Integer.toString(node.getPlus().getPos())))));
		attributeGrammarMap.get(node).put("code", s);
	}
	
	@Override
	public void outANegExpressionLvl2(ANegExpressionLvl2 node) {
		super.outANegExpressionLvl2(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% negative expression"
				.replace("%N%", Integer.toString(node.getMinus().getLine())
				.replace("%P%", Integer.toString(node.getMinus().getPos())))));
		
		boolean isFloat = false;
		try {			
			if ((Boolean) attributeGrammarMap.get(node.getExpressionLvl2()).get("isFloat")) {
				isFloat = true;
			}
		} catch (Exception e) {
			
		}
		if (!isFloat) {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl2()).get("code"));		
			s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));		
			s.appendInstruction(new InstructionCommand(Instruction.neg, Register.eax));
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		} else {
			s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl2()).get("code"));			
			s.appendInstruction(new InstructionCommand(Instruction.fld, new OffsetRegister(Register.esp, 0)));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fchs));
			s.appendInstruction(new InstructionCommand(Instruction.sub, new IntegerLiteral(4), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.fst, new OffsetRegister(Register.esp,0)));
		}
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% negative expression"
				.replace("%N%", Integer.toString(node.getMinus().getLine())
				.replace("%P%", Integer.toString(node.getMinus().getPos())))));
		attributeGrammarMap.get(node).put("code", s);
	}
	
	@Override
	public void outANotExpressionLvl2(ANotExpressionLvl2 node) {
		super.outANotExpressionLvl2(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% not expression"
				.replace("%N%", Integer.toString(node.getNot().getLine())
				.replace("%P%", Integer.toString(node.getNot().getPos())))));
		// load expression
		s.appendInstructionSet((InstructionSet)attributeGrammarMap.get(node.getExpressionLvl2()).get("code"));
		s.appendInstruction(new InstructionCommand(Instruction.pop, Register.eax));
		// check to see if expression is true
		s.appendInstruction(new InstructionCommand(Instruction.cmp, new IntegerLiteral(0), Register.eax));
		s.appendInstruction(new InstructionCommand(Instruction.jg, new Label("_true%N%".replace("%N%", Integer.toString(++labelcount)))));
		s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_false%N%".replace("%N%", Integer.toString(labelcount)))));	
		// if false, then make true
		s.appendInstruction(new LabelInstruction("_false%N%".replace("%N%", Integer.toString(labelcount))));
		s.appendInstruction(new InstructionCommand(Instruction.mov,new IntegerLiteral(1) ,Register.eax));
		s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_end%N%".replace("%N%", Integer.toString(labelcount)))));
		// if true make false
		s.appendInstruction(new LabelInstruction("_true%N%".replace("%N%", Integer.toString(labelcount))));		
		s.appendInstruction(new InstructionCommand(Instruction.mov,new IntegerLiteral(0) ,Register.eax));
		s.appendInstruction(new InstructionCommand(Instruction.jmp, new Label("_end%N%".replace("%N%", Integer.toString(labelcount)))));	
		// insert end marker		
		s.appendInstruction(new LabelInstruction("_end%N%".replace("%N%", Integer.toString(labelcount))));		
		// store in grammar map
		s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% not expression"
				.replace("%N%", Integer.toString(node.getNot().getLine())
				.replace("%P%", Integer.toString(node.getNot().getPos())))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAIdentifierExpressionLvl1(AIdentifierExpressionLvl1 node) {
		super.outAIdentifierExpressionLvl1(node);
		try {
			initNode(node);
			InstructionSet s = new InstructionSet();
			//s.appendInstruction(new CommentInstruction("begin line %N% pos %P% identifier"
					//.replace("%N%", Integer.toString(node.getIdentifier().getLine())
					//.replace("%P%", Integer.toString(node.getIdentifier().getPos())))));
			if(node.getIdentifier().getText().equals("out") || node.getIdentifier().getText().equals("in")) {
				if (node.getIdentifier().getText().equals("out")) {
					ClassDeclaration decl = (ClassDeclaration) Application.getSymbolTable().lookup("Writer").getDeclaration();
					attributeGrammarMap.get(node).put("classname", decl);					
					s.appendInstruction(new InstructionCommand(Instruction.push, new Label("_out")));
				} else {
					ClassDeclaration decl = (ClassDeclaration) Application.getSymbolTable().lookup("Reader").getDeclaration();
					attributeGrammarMap.get(node).put("classname", decl);
					s.appendInstruction(new InstructionCommand(Instruction.push, new Label("_in")));
				}
				
			} else {
				VariableDeclaration var;
				if(currentMethod == null) {
					var = currentClass.lookupVariable(node.getIdentifier().getText());
				} else {
					var = currentMethod.lookupVariable(node.getIdentifier().getText());
				}
				//if var is primitive
				//lookup associated class
				if (!var.getType().isPrimitave()) {
					attributeGrammarMap.get(node).put("classname", var.getType().getClassDecl());
				}
				
				if (var.getIsClassVar()) {
					//s.appendInstruction(new InstructionCommand(Instruction.push, new Label(var.getAssemblyName())));
					s.appendInstruction(new InstructionCommand(Instruction.mov, new OffsetRegister(Register.ebp, 8),Register.ecx));
					s.appendInstruction(new InstructionCommand(Instruction.push, new OffsetRegister(Register.ecx, (var.getOffset()+4) * 4)));
				} else {
					if (!(var.isParameter())) {
						s.appendInstruction(new InstructionCommand(Instruction.push, new OffsetRegister(Register.ebp, (var.getOffset() + 1) * -4)));
					} else {
						s.appendInstruction(new InstructionCommand(Instruction.push, new OffsetRegister(Register.ebp, (var.getOffset() + 3) * 4)));
					}
				}				
				if (var.getType().getName().equals("String")) {
					attributeGrammarMap.get(node).put("classname", Application.getSymbolTable().lookup("String").getDeclaration());
				} else {
					attributeGrammarMap.get(node).put("classname", var.getType().getClassDecl());
				}
				
				if (var.getType().getName().equals("Float")) {
					attributeGrammarMap.get(node).put("isFloat",true);
				}
			}
			//s.appendInstruction(new CommentInstruction("end line %N% pos %P% identifier %I% expression"
					//.replace("%N%", Integer.toString(node.getIdentifier().getLine())
					//.replace("%P%", Integer.toString(node.getIdentifier().getPos()))
					//.replace("%I%", node.getIdentifier().getText()))));
			attributeGrammarMap.get(node).put("code", s);			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void outAStringExpressionLvl1(AStringExpressionLvl1 node) {
		super.outAStringExpressionLvl1(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% string"
		.replace("%N%", Integer.toString(node.getStringLiteral().getLine())
		.replace("%P%", Integer.toString(node.getStringLiteral().getPos())))));
		
		s.appendInstruction(new StabsInstruction("data"));
		s.appendInstruction(new LabelInstruction("stringlit%N%".replace("%N%", Integer.toString(++labelcount))));
		s.appendInstruction(new StabsInstruction("string",node.getStringLiteral().getText()));
		
		s.appendInstruction(new StabsInstruction("text"));
		s.appendInstruction(new InstructionCommand(Instruction.push, new StringLiteral("stringlit%N%".replace("%N%", Integer.toString(labelcount)))));
		s.appendInstruction(new InstructionCommand(Instruction.call, new Label("string_fromlit")));
		s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(Integer.parseInt("4")), Register.esp));
		s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
		
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% string"
		.replace("%N%", Integer.toString(node.getStringLiteral().getLine())
		.replace("%P%", Integer.toString(node.getStringLiteral().getPos())))));
		attributeGrammarMap.get(node).put("code", s);
		Declaration d = null;
		try {
			d = Application.getSymbolTable().lookup("String").getDeclaration();
		} catch (Exception e) {
		}
		attributeGrammarMap.get(node).put("classname", d);
	}

	@Override
	public void outAIntegerExpressionLvl1(AIntegerExpressionLvl1 node) {
		super.outAIntegerExpressionLvl1(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% integer %I% expression"
				.replace("%N%", Integer.toString(node.getIntegerLiteral().getLine())
				.replace("%P%", Integer.toString(node.getIntegerLiteral().getPos()))
				.replace("%I%", node.getIntegerLiteral().getText()))));
		s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(Integer.parseInt(node.getIntegerLiteral().getText()))));
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% integer %I% expression"
				.replace("%N%", Integer.toString(node.getIntegerLiteral().getLine())
				.replace("%P%", Integer.toString(node.getIntegerLiteral().getPos()))
				.replace("%I%", node.getIntegerLiteral().getText()))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outATrueExpressionLvl1(ATrueExpressionLvl1 node) {
		super.outATrueExpressionLvl1(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% true expression"
				.replace("%N%", Integer.toString(node.getTrue().getLine())
				.replace("%P%", Integer.toString(node.getTrue().getPos())))));
		s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(Integer.parseInt("1"))));
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% true expression"
				.replace("%N%", Integer.toString(node.getTrue().getLine())
				.replace("%P%", Integer.toString(node.getTrue().getPos())))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAFalseExpressionLvl1(AFalseExpressionLvl1 node) {
		super.outAFalseExpressionLvl1(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% false expression"
				.replace("%N%", Integer.toString(node.getFalse().getLine())
				.replace("%P%", Integer.toString(node.getFalse().getPos())))));
		s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(Integer.parseInt("0"))));
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% false expression"
				.replace("%N%", Integer.toString(node.getFalse().getLine())
				.replace("%P%", Integer.toString(node.getFalse().getPos())))));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outANullExpressionLvl1(ANullExpressionLvl1 node) {
		super.outANullExpressionLvl1(node);		
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin %S% null expression".replace("%S%", node.getNull().getText())));
		s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(0)));
		s.appendInstruction(new CommentInstruction("end %S% null expression".replace("%S%", node.getNull().getText())));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAMeExpressionLvl1(AMeExpressionLvl1 node) {
		super.outAMeExpressionLvl1(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin %S% me expression".replace("%S%", node.getMe().getText())));
		s.appendInstruction(new InstructionCommand(Instruction.push, new OffsetRegister(Register.ebp, 8)));
		s.appendInstruction(new CommentInstruction("end %S% me expression".replace("%S%", node.getMe().getText())));
		attributeGrammarMap.get(node).put("code", s);
		attributeGrammarMap.get(node).put("classname", currentClass);
	}

	@Override
	public void outANewExpressionLvl1(ANewExpressionLvl1 node) {
		super.outANewExpressionLvl1(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin new expression"));
		
		Type t = Type.GetTypeForName(node.getType().toString().replace(" ", ""));
		ClassDeclaration d = t.getClassDecl();
		
		
		// begin code
		s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral((d.getVariables().size() + 4) * 4)));
		s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(1)));
		s.appendInstruction(new InstructionCommand(Instruction.call, new Label("calloc")));
		s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(8), Register.esp));
		s.appendInstruction(new InstructionCommand(Instruction.mov, new Label("$VFT" + d.getName()), new OffsetRegister(Register.eax, 0)));
		s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));		
		s.appendInstruction(new InstructionCommand(Instruction.call, new Label("_init_" + d.getName())));
		
		s.appendInstruction(new CommentInstruction("end new expression"));		
		attributeGrammarMap.get(node).put("code", s);
		attributeGrammarMap.get(node).put("classname", d);
	}

	@Override
	public void outACallExpressionLvl1(ACallExpressionLvl1 node) {
		super.outACallExpressionLvl1(node);
		initNode(node);
		InstructionSet s = new InstructionSet();		
		s.appendInstruction(new CommentInstruction("begin %S% call expression".replace("%S%", node.getIdentifier().getText())));
		try {
		s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getExpressionList()).get("code"));
		} catch (Exception e) { ;/* no expression list*/}
		
		InstructionSet dotAssembly = new InstructionSet();
		try {
			dotAssembly = (InstructionSet)attributeGrammarMap.get(node.getExpressionDot()).get("code");
		} catch (Exception e) {;}
		
		try {
			MethodDeclaration method = null;
			if (node.getExpressionDot() != null) {
				method = ((ClassDeclaration) attributeGrammarMap.get(node.getExpressionDot()).get("classname")).lookupMethod(node.getIdentifier().getText());
				s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getExpressionDot()).get("code"));
			} else {
				method = currentClass.lookupMethod(node.getIdentifier().getText());
				s.appendInstruction(new InstructionCommand(Instruction.push, new OffsetRegister(Register.ebp, 8)));				
			}	
			// null check
			s.appendInstruction(new InstructionCommand(Instruction.push, new IntegerLiteral(node.getIdentifier().getLine())));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label("nullpointertest")));
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral(Integer.parseInt("4")), Register.esp));
			s.appendInstruction(new InstructionCommand(Instruction.call, new Label(method.getAssemblyName())));
						
			// begin a level test
			/*s.appendInstruction(new InstructionCommand(Instruction.pop, Register.ecx));
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.ecx));
			s.appendInstruction(new InstructionCommand(Instruction.mov, new OffsetRegister(Register.ecx, 0), Register.edx));
			s.appendInstruction(new InstructionCommand(Instruction.call, new OffsetRegister(Register.edx, (method.getOffset()+1) * 4)));*/
			
			s.appendInstruction(new InstructionCommand(Instruction.add, new IntegerLiteral((method.getParamters().size()+1) * 4), Register.esp));			
			s.appendInstruction(new InstructionCommand(Instruction.push, Register.eax));
						
			attributeGrammarMap.get(node).put("classname", method.getType().getClassDecl());			
		} catch (Exception ex) { ex.printStackTrace(); }
		
		s.appendInstruction(new CommentInstruction("end %S% call statement".replace("%S%", node.getIdentifier().getText())));
		attributeGrammarMap.get(node).put("code", s);		
	}

	@Override
	public void outAParenExpressionLvl1(AParenExpressionLvl1 node) {
		super.outAParenExpressionLvl1(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin Paren Expression"));
		s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getExpression()).get("code"));
		try {
			attributeGrammarMap.get(node).put("classname", attributeGrammarMap.get(node.getExpression()).get("classname"));
		} catch (Exception e) {
			
		}
		s.appendInstruction(new CommentInstruction("end ParenExpression"));
		attributeGrammarMap.get(node).put("code", s);
	}

	@Override
	public void outAExpressionDot(AExpressionDot node) {
		super.outAExpressionDot(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getExpressionLvl1()).get("code"));
		attributeGrammarMap.get(node).put("code", s);
		try {
			attributeGrammarMap.get(node).put("classname", attributeGrammarMap.get(node.getExpressionLvl1()).get("classname"));
		} catch (Exception e) {
			
		}
		
	}
	
	@Override
	public void outAIfStatementStatement(AIfStatementStatement node) {
		super.outAIfStatementStatement(node);
		initNode(node);
		attributeGrammarMap.get(node).put("code",(InstructionSet) attributeGrammarMap.get(node.getIfStatement()).get("code"));
	}
	
	@Override
	public void outALoopStatementStatement(ALoopStatementStatement node) {
		super.outALoopStatementStatement(node);
		initNode(node);
		attributeGrammarMap.get(node).put("code",(InstructionSet) attributeGrammarMap.get(node.getLoopStatement()).get("code"));
	}
	
	@Override
	public void outACallStatementStatement(ACallStatementStatement node) {
		super.outACallStatementStatement(node);
		initNode(node);
		attributeGrammarMap.get(node).put("code",(InstructionSet) attributeGrammarMap.get(node.getCallStatement()).get("code"));
	}
	
	@Override
	public void outAAssignStatementStatement(AAssignStatementStatement node) {
		super.outAAssignStatementStatement(node);
		initNode(node);
		attributeGrammarMap.get(node).put("code",(InstructionSet) attributeGrammarMap.get(node.getAssignmentStatement()).get("code"));
	}
	
	@Override
	public void outAInitializer(AInitializer node) {
		super.outAInitializer(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstructionSet((InstructionSet) attributeGrammarMap.get(node.getExpression()).get("code"));
		s.appendInstruction(new CommentInstruction("end initializer Expression"));
		attributeGrammarMap.get(node).put("code",s);
		try {
			attributeGrammarMap.get(node).put("classname", attributeGrammarMap.get(node.getExpression()).get("classname"));
		} catch (Exception e) {
			
		}
	}	
	
	@Override
	public void outAFloatExpressionLvl1(AFloatExpressionLvl1 node) {
		super.outAFloatExpressionLvl1(node);
		initNode(node);
		InstructionSet s = new InstructionSet();
		s.appendInstruction(new CommentInstruction("begin line %N% pos %P% float"
		.replace("%N%", Integer.toString(node.getFloatLiteral().getLine())
		.replace("%P%", Integer.toString(node.getFloatLiteral().getPos())))));
		
		s.appendInstruction(new StabsInstruction("data"));
		s.appendInstruction(new LabelInstruction("floatlit%N%".replace("%N%", Integer.toString(++labelcount))));
		s.appendInstruction(new StabsInstruction("float",node.getFloatLiteral().getText()));
		s.appendInstruction(new StabsInstruction("text"));
		
		s.appendInstruction(new InstructionCommand(Instruction.fld, new Label("floatlit%N%".replace("%N%", Integer.toString(labelcount)))));	
		s.appendInstruction(new InstructionCommand(Instruction.sub, new IntegerLiteral(4), Register.esp));
		s.appendInstruction(new InstructionCommand(Instruction.fst, new OffsetRegister(Register.esp,0)));
		
					
		s.appendInstruction(new CommentInstruction("end line %N% pos %P% float"
		.replace("%N%", Integer.toString(node.getFloatLiteral().getLine())
		.replace("%P%", Integer.toString(node.getFloatLiteral().getPos())))));
		attributeGrammarMap.get(node).put("code", s);
		Declaration d = null;
		try {
			d = Application.getSymbolTable().lookup("Float").getDeclaration();
		} catch (Exception e) {
		}
		attributeGrammarMap.get(node).put("classname", d);
		attributeGrammarMap.get(node).put("isFloat", true);
	}

	
}
