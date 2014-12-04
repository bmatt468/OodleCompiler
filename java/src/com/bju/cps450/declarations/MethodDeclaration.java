/* MethodDeclaration.java
 * Author: Ethan McGee
 * Date: 2014-03-10
 * Purpose: a declaration holder for method declarations  
 */
package com.bju.cps450.declarations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bju.cps450.types.Type;

public class MethodDeclaration extends DeclarationsWithVariables {

	private ClassDeclaration parent;
	private HashMap<String, ParameterDeclaration> parameters = new HashMap<String, ParameterDeclaration>();
	private String prefix;
	private int offset;
	
	public MethodDeclaration() {
		super();
	}
	
	public MethodDeclaration(String name, Type t) {
		super(name, t);
		this.offset = 0;
	}
	
	/* addParameter
	 * Arguments:
	 *   param : ParameterDeclaration - the parameter to add
	 * Purpose: adds a parameter to the current method
	 */
	public void addParameter(ParameterDeclaration param) throws Exception {
		param.setOrder(parameters.size());
		param.setPrefix("_" + this.parent.getName() + "_" + this.getName() + "_");
		if(parameters.get(param.getName()) == null) {
			parameters.put(param.getName(), param);
		} else {
			throw new Exception("a parameter with name " + param.getName() + " already exists in declaration");
		}
	}
	
	/* addVariable
	 * Arguments:
	 *   variable : VariableDeclaration - the name of the variable to be added
	 * Purpose: adds a variable to the current declaration, throws error if already declared
	 */
	public void addVariable(VariableDeclaration variable) throws Exception {
		variable.setPrefix("_" + this.parent.getName() + "_" + this.getName() + "_");
		variable.setIsClassVar(false);
		variable.setOffset(variables.size());
		if(variables.get(variable.getName()) == null && parameters.get(variable.getName()) == null) {
			variables.put(variable.getName(), variable);
		} else if(variables.get(variable.getName()) != null) {
			throw new Exception("a variable with the name " + variable.getName() + " already exists in declaration");
		} else {
			throw new Exception("a parameter with name " + variable.getName() + " already exists in declaration");
		}
	}
	
	
	/* getParameters
	 * Arguments:
	 * 
	 * Purpose: returns a list of parameters for the current method
	 */
	public List<ParameterDeclaration> getParamters() {
		List<ParameterDeclaration> parametersList = new ArrayList<ParameterDeclaration>();
		for(int i = 0; i < parameters.size(); ++i) {
			Iterator<Entry<String, ParameterDeclaration>> it = parameters.entrySet().iterator();
			while(it.hasNext()) {
				Map.Entry<String, ParameterDeclaration> pair = (Map.Entry<String, ParameterDeclaration>)it.next();
				if(pair.getValue().getOrder() == i) {
					parametersList.add(pair.getValue());
				}
			}
		}
		return parametersList;
	}
	
	/* lookupInParent
	 * Arguments:
	 *   name : String - the name of the variable to be searched for
	 * Purpose: returns the variable if found, null otherwise
	 */
	@Override
	protected VariableDeclaration lookupInParent(String name) {
		if(parameters.get(name) == null) {
			try {
				return parent.lookupVariable(name);
			} catch(Exception e) { ; }
		} else {
			ParameterDeclaration parameter = parameters.get(name);
			VariableDeclaration varDecl = new VariableDeclaration(parameter.getName(), parameter.getType());
			varDecl.setPrefix(parameter.getPrefix());
			varDecl.setOffset(parameter.getOrder());
			varDecl.setIsParameter(true);
			return varDecl;
		}
		return null;
	}
	
	/* setParent
	 * Arguments:
	 *   decl : ClassDeclaration - the class that contains the method
	 * Purpose: sets the parent of the current method
	 */
	public void setParent(ClassDeclaration decl) {
		this.parent = decl;
	}
	
	/* getParent
	 * Arguments:
	 *   
	 * Purpose: gets the parent of the current method
	 */
	public ClassDeclaration getParent() {
		return this.parent;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
