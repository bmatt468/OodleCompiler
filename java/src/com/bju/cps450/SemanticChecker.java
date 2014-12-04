package com.bju.cps450;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bju.cps450.node.*;
import com.bju.cps450.types.Type;
import com.bju.cps450.analysis.DepthFirstAdapter;
import com.bju.cps450.declarations.ClassDeclaration;
import com.bju.cps450.declarations.Declaration;
import com.bju.cps450.declarations.MethodDeclaration;
import com.bju.cps450.declarations.ParameterDeclaration;
import com.bju.cps450.declarations.VariableDeclaration;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

public class SemanticChecker extends DepthFirstAdapter {
	private HashMap<Node, HashMap<String,Object>> attributeGrammarMap = new HashMap<Node, HashMap<String,Object>>();
	private HashMap<String, ArrayList<String>> vft = new HashMap<String, ArrayList<String>>();

	private void initNode(Node node) {
		if (attributeGrammarMap.get(node) == null) {
			attributeGrammarMap.put(node, new HashMap<String, Object>());
		}
	}
		
	// case methods
	
	@Override
	public void caseAClassDefinition(AClassDefinition node) {
		inAClassDefinition(node);
        if(node.getClasskey() != null)
        {
            node.getClasskey().apply(this);
        }
        if(node.getBeginId() != null)
        {
            node.getBeginId().apply(this);
        }
        if(node.getInheritsClause() != null)
        {
            node.getInheritsClause().apply(this);
            try {           
            	Application.getSymbolTable().getLastPushedClass().setParent((ClassDeclaration) Application.getSymbolTable().lookup(node.getInheritsClause().toString().replace("inherits from ", "").replace(" ", "")).getDeclaration());            	
            } catch (Exception e) {
            	
            }
            try {           
            	ClassDeclaration parent = (ClassDeclaration) Application.getSymbolTable().lookup(node.getInheritsClause().toString().replace("inherits from ", "").replace(" ", "")).getDeclaration();
            	vft = Application.getVFT();            	
            	ArrayList<String> parentMethods = vft.get(parent.getName());            	
            	try {
            	
            	for (String method : parentMethods) {
            		if (!method.equals("VFTood") && !method.equals("_ood_toString")) {
            			vft.get(node.getBeginId().getText()).add(method);
            		}            		
            	}
            	} catch (Exception e) {            		
            	
            	}            	
            	Application.setVFT(vft);
            } catch (Exception e) {
            	
            }
        }
        if(node.getIs() != null)
        {
            node.getIs().apply(this);
        }
        if(node.getNewlines() != null)
        {
            node.getNewlines().apply(this);
        }
        {
            List<PVariableDeclarations> copy = new ArrayList<PVariableDeclarations>(node.getVariableDeclarations());
            for(PVariableDeclarations e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PMethodDeclarations> copy = new ArrayList<PMethodDeclarations>(node.getMethodDeclarations());
            for(PMethodDeclarations e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getEnd() != null)
        {
            node.getEnd().apply(this);
        }
        if(node.getEndId() != null)
        {
            node.getEndId().apply(this);
        }
        outAClassDefinition(node);
	}
	
	@Override
	public void caseAMethodDeclarations(AMethodDeclarations node) {
		inAMethodDeclarations(node);
        if(node.getBeginId() != null)
        {            
        	node.getBeginId().apply(this);                  	
        }
        if(node.getLParen() != null)
        {
            node.getLParen().apply(this);
        }
        if(node.getArgumentList() != null)
        {
            node.getArgumentList().apply(this);
            ArrayList<ParameterDeclaration> l = (ArrayList<ParameterDeclaration>) attributeGrammarMap.get(node.getArgumentList()).get("vars");
            for (int i = 0; i < l.size(); ++i) {
            	try {
					Application.getSymbolTable().getLastPushedMethod().addParameter(l.get(i));
					VariableDeclaration d = new VariableDeclaration();
					d.setName(l.get(i).getName());					
					Application.getSymbolTable().getLastPushedMethod().addVariable(d);
				} catch (Exception e) {
					e.getMessage();
				}
            }
        }
        if(node.getRParen() != null)
        {
            node.getRParen().apply(this);
        }
        if(node.getTypeDeclaration() != null)
        {            
        	node.getTypeDeclaration().apply(this);
        	try {
        		Type t = (Type)attributeGrammarMap.get(node.getTypeDeclaration()).get("type");
        		Application.getSymbolTable().getLastPushedMethod().setType(t);
        		VariableDeclaration d = new VariableDeclaration(node.getBeginId().getText(),t);        		
        		Application.getSymbolTable().getLastPushedMethod().addVariable(d);
        	} catch (Exception e) {
        		System.err.println(e.getMessage());
        	}
        } else {
        	Application.getSymbolTable().getLastPushedMethod().setType(Type.oodvoid);
        }
        if(node.getIs() != null)
        {
            node.getIs().apply(this);
        }
        if(node.getBeginNewlines() != null)
        {
            node.getBeginNewlines().apply(this);
        }
        {
            List<PVariableDeclarations> copy = new ArrayList<PVariableDeclarations>(node.getVariableDeclarations());
            for(PVariableDeclarations e : copy)
            {
                e.apply(this);
            }
        }
        if(node.getBegin() != null)
        {
            node.getBegin().apply(this);
        }
        if(node.getMiddleNewlines() != null)
        {
            node.getMiddleNewlines().apply(this);
        }
        if(node.getStatementList() != null)
        {
            node.getStatementList().apply(this);
        }
        if(node.getEnd() != null)
        {
            node.getEnd().apply(this);
        }
        if(node.getEndId() != null)
        {
            node.getEndId().apply(this);
        }
        if(node.getEndNewlines() != null)
        {
            node.getEndNewlines().apply(this);
        }
        outAMethodDeclarations(node);
	}
	
	@Override
	public void caseAArray(AArray node) {		
		super.caseAArray(node);
		Application.getErrors().setUnsupportedFeatures();
		System.err.println("WARNING[%LN%,%POS%]: array indexing unsupported in this version"
				.replace("%LN%", Integer.toString(node.getLBracket().getLine()))
        		.replace("%POS%", Integer.toString(node.getLBracket().getPos())));
	}

	@Override
	public void caseAArrayType(AArrayType node) {		
		super.caseAArrayType(node);
		Application.getErrors().setUnsupportedFeatures();
		System.err.println("WARNING[%LN%,%POS%]: arrays unsupported in this version"
				.replace("%LN%", Integer.toString(node.getLBracket().getLine()))
        		.replace("%POS%", Integer.toString(node.getLBracket().getPos())));
	}	
	
	@Override
	public void caseAVariableDeclarations(AVariableDeclarations node) {		
		super.caseAVariableDeclarations(node);
		if ((node.getTypeDeclaration() == null) && (node.getInitializer() == null)) {
			Application.getErrors().addSemanticErrors();
			System.err.println("ERROR[%LN%,%POS%]: type definition required"
					.replace("%LN%", Integer.toString(node.getIdentifier().getLine()))
	        		.replace("%POS%", Integer.toString(node.getIdentifier().getPos())));
		}
	}
	
	// in methods	
	@Override
	public void inAClassDefinition(AClassDefinition node) {		
		super.inAClassDefinition(node);
		try {
			// check to make sure class does not previously exist
			String name = node.getBeginId().getText();
			try {
				Declaration d = Application.getSymbolTable().lookup(name).getDeclaration();
				if (d instanceof ClassDeclaration) {			
					Application.getErrors().addSemanticErrors();
					System.err.println("ERROR[%LN%,%POS%]: Class %C% previously defined"
							.replace("%C%",name)
							.replace("%LN%", Integer.toString(node.getBeginId().getLine()))
			        		.replace("%POS%", Integer.toString(node.getBeginId().getPos())));
					return;
				}
			} catch (Exception e) {
				
			}
			
			// create a new ClassDeclaration and a new scope
			ClassDeclaration decl = new ClassDeclaration(name, new Type(name));			
			Application.getSymbolTable().push(name, decl);
			Application.getSymbolTable().beginScope();
			vft = Application.getVFT();
			vft.put(name, new ArrayList<String>());
			if (name.equals("ood")) {
				vft.get(name).add("0");
			} else if ((node.getInheritsClause() == null)) {
				vft.get(name).add("VFTood");
				vft.get(name).add("_ood_toString");
			} else {
				vft.get(name).add("_ood_toString");
			} 
			Application.setVFT(vft);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void inAMethodDeclarations(AMethodDeclarations node) {		
		super.inAMethodDeclarations(node);		
		initNode(node);
		try {
			// check to make sure method does not previously exist
			String name = node.getBeginId().getText();
			try {
				Declaration d = Application.getSymbolTable().lookup(name).getDeclaration();
				if (d instanceof MethodDeclaration) {
					Application.getErrors().addSemanticErrors();
					System.err.println("ERROR[%LN%,%POS%]: Method %M% previously defined"
							.replace("%M%",name)
							.replace("%LN%", Integer.toString(node.getBeginId().getLine()))
			        		.replace("%POS%", Integer.toString(node.getBeginId().getPos())));
					return;
				}
			} catch (Exception e) {
				
			}
			
			// create a new MethodDeclaration and a new scope
			MethodDeclaration decl = new MethodDeclaration();			
			decl.setName(name);			
			if (node.getTypeDeclaration() != null) {				
				try {					
					//Type t = (Type) attributeGrammarMap.get(node.getTypeDeclaration()).get("type");					
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}		
			
			Application.getSymbolTable().push(name, decl);
			Application.getSymbolTable().getLastPushedClass().addMethod(decl);
			Application.getSymbolTable().beginScope();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	// out methods
	@Override
	public void outAVariableDeclarations(AVariableDeclarations node) {		
		super.outAVariableDeclarations(node);		
		// check to see if variable is previously defined
		try {
			String varname = node.getIdentifier().getText();			
			try {
				Declaration d = Application.getSymbolTable().controlledLevelLookup(varname, Application.getSymbolTable().getScope()).getDeclaration();
				if (d instanceof VariableDeclaration) {				
					Application.getErrors().addSemanticErrors();					
					System.err.println("ERROR[%LN%,%POS%]: variable %VAR% predefined"
							.replace("%VAR%", varname)
							.replace("%LN%", Integer.toString(node.getIdentifier().getLine()))
			        		.replace("%POS%", Integer.toString(node.getIdentifier().getPos())));
					return;
				}
			} catch (Exception e) {
				
			}
			
			// push variable declaration onto stack
			Type t = null;
			if (node.getInitializer() != null) {
				t = (Type) attributeGrammarMap.get(node.getInitializer()).get("type");
			}
			else {
				t = (Type) attributeGrammarMap.get(node.getTypeDeclaration()).get("type");			
			}
			VariableDeclaration d = new VariableDeclaration(varname,t);
			if (t.getName() == "duck") {
				d.setDuck(true);
			}
			switch (Application.getSymbolTable().getScope()) {
			case 1:				
				Application.getSymbolTable().getLastPushedClass().addVariable(d);
				break;
			case 2:
				Application.getSymbolTable().getLastPushedMethod().addVariable(d);
			}
			Application.getSymbolTable().push(varname, d);			
		} catch (Exception e) {
			
		}			
	}
	
	@Override
	public void outAAssignmentStatement(AAssignmentStatement node) {		
		super.outAAssignmentStatement(node);
		initNode(node);
		try {
			// check to see if variable exists		
			String name = node.getIdentifier().getText();
			try {
				Declaration d = Application.getSymbolTable().lookup(name).getDeclaration();
			} catch (Exception e) {			
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: Variable %VAR% undefined"
						.replace("%VAR%",name)
						.replace("%LN%", Integer.toString(node.getIdentifier().getLine()))
		        		.replace("%POS%", Integer.toString(node.getIdentifier().getPos())));
				return;
			}		
			
			if (Application.getSymbolTable().lookup(node.getIdentifier().getText()).getDeclaration().isDuck()) {
				Type t = (Type)attributeGrammarMap.get(node.getExpression()).get("type");
				Application.getSymbolTable().lookup(name).getDeclaration().setType(t);
			} else {
				try {
					attributeGrammarMap.get(node.getExpression()).get("duckdecl");
					attributeGrammarMap.get(node.getExpression()).put("type", Application.getSymbolTable().lookup(node.getIdentifier().getText()).getDeclaration().getType());
				} catch (Exception e) {
					// not duck
				}
			}
			// handle assignment			
			Type t1 = Application.getSymbolTable().lookup(node.getIdentifier().getText()).getDeclaration().getType();			
			Type t2 = (Type)attributeGrammarMap.get(node.getExpression()).get("type");			
			//if (Application.getSymbolTable().lookup(node.getIdentifier().getText()).getDeclaration().getType() != (Type)attributeGrammarMap.get(node.getExpression()).get("type")) {
			if (t1.getName() != t2.getName()) {
				if (!((Type) attributeGrammarMap.get(node.getExpression()).get("type") == Type.ooderror)) {
					Application.getErrors().addSemanticErrors();
					System.err.println("ERROR[%LN%,%POS%]: type mismatch"
							.replace("%LN%", Integer.toString(node.getIdentifier().getLine()))
			        		.replace("%POS%", Integer.toString(node.getIdentifier().getPos())));
				}
			} else {
				attributeGrammarMap.get(node).put("type", t1);
			}
			
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void outAClassDefinition(AClassDefinition node) {		
		super.outAClassDefinition(node);
		try {
			Application.getSymbolTable().endScope();
			vft = Application.getVFT();
			if (node.getInheritsClause() != null) {
				ClassDeclaration parent = (ClassDeclaration) attributeGrammarMap.get(node.getInheritsClause()).get("parent");				
				vft.get(node.getBeginId().getText()).add(0, "VFT" + parent.getName());				
			}		
			Application.setVFT(vft);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void outAMethodDeclarations(AMethodDeclarations node) {		
		super.outAMethodDeclarations(node);
		try {
			Application.getSymbolTable().endScope();
			try {
				vft = Application.getVFT();
				MethodDeclaration decl = Application.getSymbolTable().getLastPushedMethod();
				ClassDeclaration decl2 = decl.getParent();
				ClassDeclaration decl3 = decl2.getParent();
				if (decl3 != null) { // there is inheritance
					try {
						decl3.lookupMethod(decl.getName()); // will throw exception
						int indexofold = vft.get(decl2.getName()).indexOf("_" + decl3.getName() + "_" + decl.getName());						
						vft.get(decl2.getName()).set(indexofold, "_" + decl2.getName() + "_" + decl.getName());
						decl.setOffset(indexofold);
					} catch (Exception e) {
						vft.get(decl2.getName()).add("_" + decl2.getName() + "_" + decl.getName());
						decl.setOffset(vft.get(decl2.getName()).indexOf("_" + decl2.getName() + "_" + decl.getName()));
					} // method does not exist					
				} else {
					vft.get(decl2.getName()).add("_" + decl2.getName() + "_" + decl.getName());
					decl.setOffset(vft.get(decl2.getName()).indexOf("_" + decl2.getName() + "_" + decl.getName()) - 1);
				}
				Application.setVFT(vft);				
			} catch (Exception e) {;}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void outACallStatement(ACallStatement node) {
		super.outACallStatement(node);
		initNode(node);
		// check for method existence
		try {
			String name = node.getIdentifier().getText();
			ClassDeclaration cd = null;
			
			cd = Application.getSymbolTable().getLastPushedClass();
			MethodDeclaration md = null;
			try {
				if(node.getExpressionDot() != null) {
					Type t = (Type) attributeGrammarMap.get(node.getExpressionDot()).get("type");
					if (t != Type.oodme) {
						cd = (ClassDeclaration)Application.getSymbolTable().controlledLookup(t.getName(), 0).getDeclaration();
					} else {
						//cd = null;
					}
				} 
				
				md = cd.lookupMethod(name);
				
				if (md != null) {					
					try { 
							ArrayList<Type> l = null;						
							try {l = (ArrayList<Type>) attributeGrammarMap.get(node.getExpressionList()).get("vars");} catch (Exception e) {}						
							
							// check to see if parameter checking is necessary
							if (l != null) {
								if (md.getParamters().size() != l.size()){						
									throw new Exception("%MN% expected %NP% parameters but received %PR%"
											.replace("%MN%", node.getIdentifier().getText())
											.replace("%NP%", Integer.toString(md.getParamters().size()))
											.replace("%PR%", Integer.toString(l.size())));
								}
								
								// check parameter types
								for (int i = 0; i < md.getParamters().size(); ++i) {
									Type lhs = md.getParamters().get(i).getType();
									Type rhs = l.get(i);
									if (!lhs.getName().equals(rhs.getName())) {
										throw new Exception("%MN% parameter %PN% expected type %ET% but received %RT%"
												.replace("%MN%", node.getIdentifier().getText())
												.replace("%PN%", Integer.toString(i+1))
												.replace("%ET%", lhs.getName())
												.replace("%RT%", rhs.getName()));
									}
								}
						}
					} catch (Exception e) {
						Application.getErrors().addSemanticErrors();
						System.err.println("ERROR[%LN%,%POS%]: "
								.replace("%M%", name)
								.replace("%LN%", Integer.toString(node.getIdentifier().getLine()))
				        		.replace("%POS%", Integer.toString(node.getIdentifier().getPos())) + e.getMessage());	
						if (e.getMessage() == null) {
							e.printStackTrace();
						}
					}
				}
						
			} catch (Exception e) {
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: method %M% does not exist in this scope"
						.replace("%M%", name)
						.replace("%LN%", Integer.toString(node.getIdentifier().getLine()))
		        		.replace("%POS%", Integer.toString(node.getIdentifier().getPos())));				
				return;
			}			
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void outACallExpressionLvl1(ACallExpressionLvl1 node) {
		super.outACallExpressionLvl1(node);
		initNode(node);
		// check for method existence
		try {
			String name = node.getIdentifier().getText();
			ClassDeclaration cd = Application.getSymbolTable().getLastPushedClass();
			MethodDeclaration md = null;
			try {
				if(node.getExpressionDot() != null) {
					Type t = (Type) attributeGrammarMap.get(node.getExpressionDot()).get("type");
					cd = (ClassDeclaration)Application.getSymbolTable().controlledLookup(t.getName(), 0).getDeclaration();
				} 
				
				md = cd.lookupMethod(name);	
				
				if (md != null) {					
					try { 
							ArrayList<Type> l = null;						
							try {l = (ArrayList<Type>) attributeGrammarMap.get(node.getExpressionList()).get("vars");} catch (Exception e) {}						
							
							// check to see if parameter checking is necessary
							if (l != null) {
								if (md.getParamters().size() != l.size()){						
									throw new Exception("%MN% expected %NP% parameters but received %PR%"
											.replace("%MN%", node.getIdentifier().getText())
											.replace("%NP%", Integer.toString(md.getParamters().size()))
											.replace("%PR%", Integer.toString(l.size())));
								}
								
								// check parameter types
								for (int i = 0; i < md.getParamters().size(); ++i) {
									Type lhs = md.getParamters().get(i).getType();
									Type rhs = l.get(i);
									if (!lhs.getName().equals(rhs.getName())) {
										throw new Exception("%MN% parameter %PN% expected type %ET% but received %RT%"
												.replace("%MN%", node.getIdentifier().getText())
												.replace("%PN%", Integer.toString(i+1))
												.replace("%ET%", lhs.getName())
												.replace("%RT%", rhs.getName()));
									}
								}
						}
					} catch (Exception e) {
						Application.getErrors().addSemanticErrors();
						System.err.println("ERROR[%LN%,%POS%]: "
								.replace("%M%", name)
								.replace("%LN%", Integer.toString(node.getIdentifier().getLine()))
				        		.replace("%POS%", Integer.toString(node.getIdentifier().getPos())) + e.getMessage());	
						if (e.getMessage() == null) {
							e.printStackTrace();
						}
					}
				}
						
			} catch (Exception e) {
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: method %M% does not exist in this scope" + e.getMessage()
						.replace("%M%", name)
						.replace("%LN%", Integer.toString(node.getIdentifier().getLine()))
		        		.replace("%POS%", Integer.toString(node.getIdentifier().getPos())));				
				return;
			}			
			try {
				attributeGrammarMap.get(node).put("type", md.getType());
			} catch (Exception e) {
				attributeGrammarMap.get(node).put("type", Type.ooderror);
			}
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void outAArgument(AArgument node) {		
		super.outAArgument(node);
		initNode(node);
		String name = node.getIdentifier().getText();
		Type t = (Type) attributeGrammarMap.get(node.getTypeDeclaration()).get("type");		
		VariableDeclaration decl = new VariableDeclaration(name,t);
		ParameterDeclaration decl2 = new ParameterDeclaration(name,t);
		
		Application.getSymbolTable().push(name, decl);	
		
		attributeGrammarMap.get(node).put("vars", decl2);
	}
	
	@Override
	public void outABooleanType(ABooleanType node) {		
		super.outABooleanType(node);
		initNode(node);		
		attributeGrammarMap.get(node).put("type", Type.oodbool);
	}
	
	@Override
	public void outAIntType(AIntType node) {
		super.outAIntType(node);
		initNode(node);
		attributeGrammarMap.get(node).put("type", Type.oodint);
	}
	
	@Override
	public void outAStringType(AStringType node) {
		super.outAStringType(node);
		initNode(node);
		attributeGrammarMap.get(node).put("type", Type.oodstring);
	}
	
	@Override
	public void outADuckType(ADuckType node) {
		super.outADuckType(node);
		initNode(node);
		attributeGrammarMap.get(node).put("type", Type.oodduck);
	}
	
	@Override
	public void outAFloatType(AFloatType node) {
		super.outAFloatType(node);
		initNode(node);
		attributeGrammarMap.get(node).put("type", Type.oodfloat);
	}
	
	@Override
	public void outAClassType(AClassType node) {
		super.outAClassType(node);
		initNode(node);
		
		try {
			Declaration d = Application.getSymbolTable().lookup(node.getIdentifier().getText()).getDeclaration();
			
			if (d instanceof ClassDeclaration) {				
				attributeGrammarMap.get(node).put("type", Type.CreateType((ClassDeclaration) d));
			}
		} catch (Exception e) {
			attributeGrammarMap.get(node).put("type", Type.ooderror);
		}		
	}
	
	@Override
	public void outAArrayType(AArrayType node) {
		super.outAArrayType(node);		
		initNode(node);
		attributeGrammarMap.get(node).put("type", Type.ooderror);
	}

	@Override
	public void outATypeDeclaration(ATypeDeclaration node) {		
		super.outATypeDeclaration(node);
		initNode(node);		
		attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getType()).get("type"));
	}
	
	@Override
	public void outAExpression(AExpression node) {		
		super.outAExpression(node);		
		initNode(node);
		try {
			attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl8()).get("type"));
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOrExpressionLvl8(AOrExpressionLvl8 node) {		
		super.outAOrExpressionLvl8(node);
		initNode(node);
		try {			
			if (attributeGrammarMap.get(node.getExpressionLvl7()).get("type") == attributeGrammarMap.get(node.getExpressionLvl8()).get("type") 
					&& (Type) attributeGrammarMap.get(node.getExpressionLvl7()).get("type") == Type.oodbool) {
				attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl7()).get("type"));				
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getOr().getLine()))
		        		.replace("%POS%", Integer.toString(node.getOr().getPos())));
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOtherExpressionLvl8(AOtherExpressionLvl8 node) {		
		super.outAOtherExpressionLvl8(node);
		initNode(node);
		try {
			attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl7()).get("type"));
		} catch (Exception e) {
			
		}
	}

	
	@Override
	public void outAAndExpressionLvl7(AAndExpressionLvl7 node) {		
		super.outAAndExpressionLvl7(node);
		initNode(node);
		try {			
			if (attributeGrammarMap.get(node.getExpressionLvl6()).get("type") == attributeGrammarMap.get(node.getExpressionLvl7()).get("type") 
					&& (Type) attributeGrammarMap.get(node.getExpressionLvl7()).get("type") == Type.oodbool) {
				attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl7()).get("type"));
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getAnd().getLine()))
		        		.replace("%POS%", Integer.toString(node.getAnd().getPos())));
			}
		} catch (Exception e) {
			
		}
	}

	
	@Override
	public void outAOtherExpressionLvl7(AOtherExpressionLvl7 node) {		
		super.outAOtherExpressionLvl7(node);
		initNode(node);
		try {
			attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl6()).get("type"));
		} catch (Exception e) {
			
		}
	}

	
	@Override
	public void outAEqualsExpressionLvl6(AEqualsExpressionLvl6 node) {
		super.outAEqualsExpressionLvl6(node);
		initNode(node);
		try {			
			if ((attributeGrammarMap.get(node.getFirst()).get("type") == attributeGrammarMap.get(node.getSecond()).get("type") 
					&& ((Type) attributeGrammarMap.get(node.getFirst()).get("type") == Type.oodint
							|| (Type) attributeGrammarMap.get(node.getFirst()).get("type") == Type.oodstring
							|| (Type) attributeGrammarMap.get(node.getFirst()).get("type") == Type.oodbool
							|| (Type) attributeGrammarMap.get(node.getFirst()).get("type") == Type.oodfloat))
					|| (attributeGrammarMap.get(node.getSecond()).get("type") == Type.oodnull)) {
				attributeGrammarMap.get(node).put("type", Type.oodbool);
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getEquals().getLine()))
		        		.replace("%POS%", Integer.toString(node.getEquals().getPos())));
			}
		} catch (Exception e) {
			
		}
	}

	
	@Override
	public void outAGreaterExpressionLvl6(AGreaterExpressionLvl6 node) {
		super.outAGreaterExpressionLvl6(node);
		initNode(node);
		try {			
			if (attributeGrammarMap.get(node.getFirst()).get("type") == attributeGrammarMap.get(node.getSecond()).get("type") 
					&& ((Type) attributeGrammarMap.get(node.getFirst()).get("type") == Type.oodint
					|| (Type) attributeGrammarMap.get(node.getFirst()).get("type") == Type.oodstring
					|| (Type) attributeGrammarMap.get(node.getFirst()).get("type") == Type.oodfloat)) {
				attributeGrammarMap.get(node).put("type", Type.oodbool);
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getGreater().getLine()))
		        		.replace("%POS%", Integer.toString(node.getGreater().getPos())));
			}
		} catch (Exception e) {
			
		}
	}

	
	@Override
	public void outAGtEqualExpressionLvl6(AGtEqualExpressionLvl6 node) {		
		super.outAGtEqualExpressionLvl6(node);
		initNode(node);
		try {			
			if (attributeGrammarMap.get(node.getFirst()).get("type") == attributeGrammarMap.get(node.getSecond()).get("type") 
					&& ((Type) attributeGrammarMap.get(node.getFirst()).get("type") == Type.oodint
							|| (Type) attributeGrammarMap.get(node.getFirst()).get("type") == Type.oodstring
							|| (Type) attributeGrammarMap.get(node.getFirst()).get("type") == Type.oodfloat)) {
				attributeGrammarMap.get(node).put("type", Type.oodbool);
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getGreaterEqual().getLine()))
		        		.replace("%POS%", Integer.toString(node.getGreaterEqual().getPos())));
			}
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void outAOtherExpressionLvl6(AOtherExpressionLvl6 node) {		
		super.outAOtherExpressionLvl6(node);
		initNode(node);
		try {
			attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl5()).get("type"));
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAConcatExpressionLvl5(AConcatExpressionLvl5 node) {
		super.outAConcatExpressionLvl5(node);
		initNode(node);
		try {			
			if (attributeGrammarMap.get(node.getExpressionLvl5()).get("type") == attributeGrammarMap.get(node.getExpressionLvl4()).get("type") 
					&& (Type) attributeGrammarMap.get(node.getExpressionLvl5()).get("type") == Type.oodstring) {
				attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl5()).get("type"));				
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getConcatenate().getLine()))
		        		.replace("%POS%", Integer.toString(node.getConcatenate().getPos())));
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOtherExpressionLvl5(AOtherExpressionLvl5 node) {
		super.outAOtherExpressionLvl5(node);
		initNode(node);
		try {
			attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl4()).get("type"));
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void outAAddExpressionLvl4(AAddExpressionLvl4 node) {		
		super.outAAddExpressionLvl4(node);
		initNode(node);
		try {			
			if ((attributeGrammarMap.get(node.getExpressionLvl4()).get("type") == attributeGrammarMap.get(node.getExpressionLvl3()).get("type") 
					&& (Type) attributeGrammarMap.get(node.getExpressionLvl4()).get("type") == Type.oodint)
					|| (attributeGrammarMap.get(node.getExpressionLvl4()).get("type") == attributeGrammarMap.get(node.getExpressionLvl3()).get("type") 
							&& (Type) attributeGrammarMap.get(node.getExpressionLvl4()).get("type") == Type.oodfloat)) {
				attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl4()).get("type"));
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getPlus().getLine()))
		        		.replace("%POS%", Integer.toString(node.getPlus().getPos())));
			}
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void outASubtractExpressionLvl4(ASubtractExpressionLvl4 node) {
		super.outASubtractExpressionLvl4(node);
		initNode(node);
		try {			
			if ((attributeGrammarMap.get(node.getExpressionLvl4()).get("type") == attributeGrammarMap.get(node.getExpressionLvl3()).get("type") 
					&& (Type) attributeGrammarMap.get(node.getExpressionLvl4()).get("type") == Type.oodint)
					|| (attributeGrammarMap.get(node.getExpressionLvl4()).get("type") == attributeGrammarMap.get(node.getExpressionLvl3()).get("type") 
							&& (Type) attributeGrammarMap.get(node.getExpressionLvl4()).get("type") == Type.oodfloat)) {
				attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl4()).get("type"));
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getMinus().getLine()))
		        		.replace("%POS%", Integer.toString(node.getMinus().getPos())));
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOtherExpressionLvl4(AOtherExpressionLvl4 node) {		
		super.outAOtherExpressionLvl4(node);
		initNode(node);
		try {
			attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl3()).get("type"));
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void outAMultExpressionLvl3(AMultExpressionLvl3 node) {
		super.outAMultExpressionLvl3(node);
		initNode(node);
		try {			
			if ((attributeGrammarMap.get(node.getExpressionLvl3()).get("type") == attributeGrammarMap.get(node.getExpressionLvl2()).get("type") 
					&& (Type) attributeGrammarMap.get(node.getExpressionLvl3()).get("type") == Type.oodint)
					|| (attributeGrammarMap.get(node.getExpressionLvl3()).get("type") == attributeGrammarMap.get(node.getExpressionLvl2()).get("type") 
							&& (Type) attributeGrammarMap.get(node.getExpressionLvl3()).get("type") == Type.oodfloat)) {
				attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl3()).get("type"));
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getMultiply().getLine()))
		        		.replace("%POS%", Integer.toString(node.getMultiply().getPos())));
			}
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void outADivideExpressionLvl3(ADivideExpressionLvl3 node) {
		super.outADivideExpressionLvl3(node);
		initNode(node);
		try {			
			if ((attributeGrammarMap.get(node.getExpressionLvl3()).get("type") == attributeGrammarMap.get(node.getExpressionLvl2()).get("type") 
					&& (Type) attributeGrammarMap.get(node.getExpressionLvl3()).get("type") == Type.oodint)
					|| (attributeGrammarMap.get(node.getExpressionLvl3()).get("type") == attributeGrammarMap.get(node.getExpressionLvl2()).get("type") 
							&& (Type) attributeGrammarMap.get(node.getExpressionLvl3()).get("type") == Type.oodfloat)) {
				attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl3()).get("type"));
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getDivide().getLine()))
		        		.replace("%POS%", Integer.toString(node.getDivide().getPos())));
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOtherExpressionLvl3(AOtherExpressionLvl3 node) {		
		super.outAOtherExpressionLvl3(node);
		initNode(node);
		try {
			attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl2()).get("type"));
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAPosExpressionLvl2(APosExpressionLvl2 node) {
		super.outAPosExpressionLvl2(node);
		initNode(node);
		try {			
			if (((Type) attributeGrammarMap.get(node.getExpressionLvl2()).get("type") == Type.oodint)
			|| ((Type) attributeGrammarMap.get(node.getExpressionLvl2()).get("type") == Type.oodfloat)){
				attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl2()).get("type"));
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getPlus().getLine()))
		        		.replace("%POS%", Integer.toString(node.getPlus().getPos())));
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outANegExpressionLvl2(ANegExpressionLvl2 node) {
		super.outANegExpressionLvl2(node);
		initNode(node);
		try {			
			if (((Type) attributeGrammarMap.get(node.getExpressionLvl2()).get("type") == Type.oodint)
					|| ((Type) attributeGrammarMap.get(node.getExpressionLvl2()).get("type") == Type.oodfloat)) {
				attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl2()).get("type"));
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getMinus().getLine()))
		        		.replace("%POS%", Integer.toString(node.getMinus().getPos())));
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outANotExpressionLvl2(ANotExpressionLvl2 node) {
		super.outANotExpressionLvl2(node);
		initNode(node);
		try {			
			if ((Type) attributeGrammarMap.get(node.getExpressionLvl2()).get("type") == Type.oodbool) {
				attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl2()).get("type"));
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getNot().getLine()))
		        		.replace("%POS%", Integer.toString(node.getNot().getPos())));
			}
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAOtherExpressionLvl2(AOtherExpressionLvl2 node) {		
		super.outAOtherExpressionLvl2(node);		
		initNode(node);
		try {
			attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl1()).get("type"));
		} catch (Exception e) {
			
		}
	}
	
	@Override
	public void outAIdentifierExpressionLvl1(AIdentifierExpressionLvl1 node) {		
		super.outAIdentifierExpressionLvl1(node);
		// check to see if identifier exists
		try {
			initNode(node);
			String name = node.getIdentifier().getText();
			
			try {
				Declaration d = Application.getSymbolTable().controlledLookup(name,Application.getSymbolTable().getScope()).getDeclaration();
				if (!(d instanceof VariableDeclaration)) {
					attributeGrammarMap.get(node).put("type", Type.ooderror);
				} else {
					attributeGrammarMap.get(node).put("type", d.getType());
					if (d.isDuck()) {
						attributeGrammarMap.get(node).put("duckdecl", d);
					}
				}
			} catch (Exception e) {
				Application.getErrors().addLexicalError();
				System.err.println("ERROR[%LN%,%POS%]: %N% has not been defined".replace("%N%",name)
						.replace("%LN%", Integer.toString(node.getIdentifier().getLine()))
						.replace("%POS%", Integer.toString(node.getIdentifier().getPos())));
				attributeGrammarMap.get(node).put("type", Type.ooderror);
			}
		} catch (Exception e){
			
		}
	}

	@Override
	public void outAStringExpressionLvl1(AStringExpressionLvl1 node) {		
		super.outAStringExpressionLvl1(node);
		initNode(node);
		try {
			attributeGrammarMap.get(node).put("type", Type.oodstring);
			//attributeGrammarMap.get(node).put("type", Type.ooderror);
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAIntegerExpressionLvl1(AIntegerExpressionLvl1 node) {
		super.outAIntegerExpressionLvl1(node);		
		initNode(node);
		try {			
			attributeGrammarMap.get(node).put("type", Type.oodint);
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outATrueExpressionLvl1(ATrueExpressionLvl1 node) {
		super.outATrueExpressionLvl1(node);		
		initNode(node);
		try {			
			attributeGrammarMap.get(node).put("type", Type.oodbool);
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAFalseExpressionLvl1(AFalseExpressionLvl1 node) {
		super.outAFalseExpressionLvl1(node);
		initNode(node);
		try {			
			attributeGrammarMap.get(node).put("type", Type.oodbool);
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outANullExpressionLvl1(ANullExpressionLvl1 node) {
		super.outANullExpressionLvl1(node);
		initNode(node);
		try {			
			attributeGrammarMap.get(node).put("type", Type.oodnull);
			//attributeGrammarMap.get(node).put("type", Type.ooderror);
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAMeExpressionLvl1(AMeExpressionLvl1 node) {
		super.outAMeExpressionLvl1(node);
		initNode(node);
		try {			
			//attributeGrammarMap.get(node).put("type", Type.oodme);
			attributeGrammarMap.get(node).put("type", Type.oodme);
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outANewExpressionLvl1(ANewExpressionLvl1 node) {
		super.outANewExpressionLvl1(node);
		initNode(node);
		try {			
			attributeGrammarMap.get(node).put("type", Type.GetTypeForName(node.getType().toString().replace(" ", "")));			
		} catch (Exception e) {
			attributeGrammarMap.get(node).put("type", Type.ooderror);
		}
	}

	@Override
	public void outAArrayExpressionLvl1(AArrayExpressionLvl1 node) {
		super.outAArrayExpressionLvl1(node);
		initNode(node);
		try {			
			//attributeGrammarMap.get(node).put("type", Type.oodarray);
			attributeGrammarMap.get(node).put("type", Type.ooderror);
		} catch (Exception e) {
			
		}
	}

	@Override
	public void outAParenExpressionLvl1(AParenExpressionLvl1 node) {
		super.outAParenExpressionLvl1(node);
		initNode(node);
		try {			
			attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpression()).get("type"));
		} catch (Exception e) {
			
		}
	}

	
	@Override
	public void outAIfStatement(AIfStatement node) {		
		super.outAIfStatement(node);
		initNode(node);
		try {			
			if ((Type) attributeGrammarMap.get(node.getExpression()).get("type") == Type.oodbool) {
				attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpression()).get("type"));
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch. Expecting %A% but got %B%"
						.replace("%LN%", Integer.toString(node.getBeginIf().getLine()))
		        		.replace("%POS%", Integer.toString(node.getBeginIf().getPos()))
		        		.replace("%A%", "bool")
		        		.replace("%B%", ((Type)attributeGrammarMap.get(node.getExpression()).get("type")).getName()));
			}
		} catch (Exception e) {
			
		}
	}

	
	@Override
	public void outALoopStatement(ALoopStatement node) {		
		super.outALoopStatement(node);
		initNode(node);
		try {			
			if ((Type) attributeGrammarMap.get(node.getExpression()).get("type") == Type.oodbool) {
				attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpression()).get("type"));
			} else {
				//error
				attributeGrammarMap.get(node).put("type", Type.ooderror);
				Application.getErrors().addSemanticErrors();
				System.err.println("ERROR[%LN%,%POS%]: type mismatch"
						.replace("%LN%", Integer.toString(node.getBeginLoop().getLine()))
		        		.replace("%POS%", Integer.toString(node.getBeginLoop().getPos())));
			}
		} catch (Exception e) {
			
		}
	}

	
	@Override
	public void outAExpressionDot(AExpressionDot node) {
		super.outAExpressionDot(node);
		initNode(node);
		try {			
			attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpressionLvl1()).get("type"));
			if (((Type)attributeGrammarMap.get(node).get("type")).isPrimitave()) {
				System.err.println("Primitive type specified");
				Application.getErrors().addSemanticErrors();
			}
		} catch (Exception e) {
			
		}
		
	}
	
	@Override
	public void outAExpressionList(AExpressionList node) {
		super.outAExpressionList(node);
		initNode(node);		
		ArrayList<Type> l = new ArrayList<Type>();		
		l.add(0,(Type) attributeGrammarMap.get(node.getExpression()).get("type"));
		if (node.getExpressionListTail() != null) {
			for (int i = 0; i < node.getExpressionListTail().size(); ++i) {
				Type t = (Type) attributeGrammarMap.get(node.getExpressionListTail().get(i)).get("vars");
				l.add(t);
			}		
		}
		attributeGrammarMap.get(node).put("vars", l);	
	}
	
	@Override
	public void outAExpressionListTail(AExpressionListTail node) {
		super.outAExpressionListTail(node);
		initNode(node);
		attributeGrammarMap.get(node).put("vars", attributeGrammarMap.get(node.getExpression()).get("type"));	
	}

	
	@Override
	public void outAArgumentList(AArgumentList node) {
		super.outAArgumentList(node);
		initNode(node);				
		ArrayList<ParameterDeclaration> l = new ArrayList<ParameterDeclaration>();		
		l.add(0,(ParameterDeclaration) attributeGrammarMap.get(node.getArgument()).get("vars"));
		if (node.getArgumentListTail() != null) {
			for (int i = 0; i < node.getArgumentListTail().size(); ++i) {
				ParameterDeclaration t = (ParameterDeclaration) attributeGrammarMap.get(node.getArgumentListTail().get(i)).get("vars");
				l.add(t);
			}		
		}
		attributeGrammarMap.get(node).put("vars", l);		
	}
	
	@Override
	public void outAArgumentListTail(AArgumentListTail node) {
		super.outAArgumentListTail(node);
		initNode(node);				
				
		//l.add(0,);
		attributeGrammarMap.get(node).put("vars", attributeGrammarMap.get(node.getArgument()).get("vars"));		
	}

	@Override
	public void outAInitializer(AInitializer node) {
		super.outAInitializer(node);
		initNode(node);
		attributeGrammarMap.get(node).put("type", attributeGrammarMap.get(node.getExpression()).get("type"));
	}
	
	@Override
	public void outAFloatExpressionLvl1(AFloatExpressionLvl1 node) {
		super.outAFloatExpressionLvl1(node);
		initNode(node);
		attributeGrammarMap.get(node).put("type", Type.oodfloat);
	}
	
	@Override
	public void outAInheritsClause(AInheritsClause node) {
		super.outAInheritsClause(node);
		initNode(node);
		ClassDeclaration d = null;
		try {
			d = (ClassDeclaration) Application.getSymbolTable().lookup(node.getIdentifier().getText()).getDeclaration();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		attributeGrammarMap.get(node).put("parent", d);				
	}	
}
