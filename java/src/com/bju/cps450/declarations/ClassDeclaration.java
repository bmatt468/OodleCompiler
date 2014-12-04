/* ClassDeclaration.java
 * Author: Ethan McGee
 * Date: 2014-03-10
 * Purpose: a declaration holder for class declarations  
 */
package com.bju.cps450.declarations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.bju.cps450.types.Type;

public class ClassDeclaration extends DeclarationsWithVariables {

	private ClassDeclaration inheritsFrom;
	private HashMap<String, MethodDeclaration> methods = new HashMap<String, MethodDeclaration>();
	
	public ClassDeclaration() {
		super();
		this.inheritsFrom = null;
	}
	
	public ClassDeclaration(String name, Type t) {
		super(name, t);
		this.inheritsFrom = null;
	}
	
	public void addVariable(VariableDeclaration variable) throws Exception {
		variable.setPrefix("_" + this.getName() + "_");
		variable.setIsClassVar(true);
		variable.setOffset(variables.size());
		if(variables.get(variable.getName()) == null) {
			variables.put(variable.getName(), variable);
		} else {
			throw new Exception("a variable with the name " + variable.getName() + " already exists in declaration");
		}
	}
	
	/* addMethod
	 * Arguments:
	 *   param : MethodDeclaration - the method to add
	 * Purpose: attempts to add a new method to the class, throws error if already exists
	 */
	public void addMethod(MethodDeclaration param) throws Exception {
		param.setPrefix("_" + this.getName() + "_");
		if(methods.get(param.getName()) == null) {
			param.setParent(this);
			methods.put(param.getName(), param);
		} else {
			throw new Exception("a method with name " + param.getName() + " already exists in the declaration");
		}
	}
	
	/* getMethods
	 * Arguments:
	 *   
	 * Purpose: returns a list of methods in the class
	 */
	public List<MethodDeclaration> getMethods() {
		List<MethodDeclaration> methodsList = new ArrayList<MethodDeclaration>();
		Iterator<Entry<String, MethodDeclaration>> it = methods.entrySet().iterator();
		while(it.hasNext()) {
			Map.Entry<String, MethodDeclaration> pair = (Map.Entry<String, MethodDeclaration>)it.next();
			methodsList.add(pair.getValue());
		}
		return methodsList;
	}
	
	/* lookupMethod
	 * Arguments:
	 *   name : String - the name of the method
	 * Purpose: looks up the method in the current class, throws error if not found
	 */
	public MethodDeclaration lookupMethod(String name) throws Exception {
		if(methods.get(name) == null) {
			if(inheritsFrom != null) {
				try {
					return inheritsFrom.lookupMethod(name);
				} catch (Exception e) { ; }
			}
			throw new Exception("no method with name " + name + " was found");
		} else {
			return methods.get(name);
		}
	}
	
	/* lookupInParent
	 * Arguments:
	 *   name : String - the name of the variable to be searched for
	 * Purpose: returns the variable if found, null otherwise
	 */
	@Override
	protected VariableDeclaration lookupInParent(String name) {
		if(inheritsFrom != null) {
			try {
				return inheritsFrom.lookupVariable(name);
			} catch (Exception e) { ; }
		}
		return null;
	}
	
	/* setParent
	 * Arguments:
	 *   decl : ClassDeclaration - the parent of the class
	 * Purpose: sets the parent of the class
	 */
	public void setParent(ClassDeclaration decl) {
		this.inheritsFrom = decl;
	}
	
	/* getParent
	 * Arguments:
	 *   
	 * Purpose: gets the parent of the declaration
	 */
	public ClassDeclaration getParent() {
		return this.inheritsFrom;
	}
}
