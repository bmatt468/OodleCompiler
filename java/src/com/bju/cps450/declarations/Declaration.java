/* Declaration.java
 * Author: Ethan McGee
 * Date: 2014-03-10
 * Purpose: provides an abstract class that all declarations extend from 
 */
package com.bju.cps450.declarations;

import com.bju.cps450.types.Type;

public abstract class Declaration {
	protected String declName;
	protected Type declType;
	protected String prefix = "";
	protected boolean isDuck = false;
	
	/**
	 * @return the isDuck
	 */
	public boolean isDuck() {
		return isDuck;
	}

	/**
	 * @param isDuck the isDuck to set
	 */
	public void setDuck(boolean isDuck) {
		this.isDuck = isDuck;
	}

	protected Declaration() {
		this.declName = "";
		this.declType = null;
	}
	
	protected Declaration(String name, Type t) {
		this.declName = name;
		this.declType = t;
	}
	
	/* setName
	 * Arguments:
	 *   name : String - the name of the declaration
	 * Purpose: sets the name of the declaration
	 */
	public void setName(String name) {
		this.declName = name;
	}
	
	/* setType
	 * Arguments:
	 *   t : Type - the type of the declaration
	 * Purpose: sets the type of the declaration
	 */
	public void setType(Type t) {
		this.declType = t;
	}
	
	/* getName
	 * Arguments:
	 *   
	 * Purpose: gets the name of the declaration
	 */
	public String getName() {
		return this.declName;
	}
	
	/* getType
	 * Arguments:
	 *   
	 * Purpose: gets the type of the declaration
	 */
	public Type getType() {
		return this.declType;
	}
	
	/* setPrefix
	 * Arguments:
	 *   s : String - the prefix for the variable
	 * Purpose: sets the prefix for the variable
	 */
	public void setPrefix(String s) {
		this.prefix = s;
	}
	
	/* getPrefix
	 * Arguments:
	 *   
	 * Purpose: gets the prefix for the variable
	 */
	public String getPrefix() {
		return this.prefix;
	}
	
	/* getAssemblyName
	 * Arguments:
	 *   
	 * Purpose: generates the prefixed assembly name
	 */
	public String getAssemblyName() {
		return this.prefix + this.getName();
	}
}
