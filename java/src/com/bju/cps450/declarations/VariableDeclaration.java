/* VariableDeclaration.java
 * Author: Ethan McGee
 * Date: 2014-03-10
 * Purpose: a declaration holder for variable declarations 
 */
package com.bju.cps450.declarations;

import com.bju.cps450.types.Type;

public class VariableDeclaration extends Declaration {
	
	private Integer offset;
	private Boolean isClassVariable;
	private Boolean isParameter;
	
	public VariableDeclaration() {
		super();
		this.offset = -1;
		this.isClassVariable = false;
		this.isParameter = false;
	}
	
	public VariableDeclaration(String name, Type t) {
		super(name, t);
		this.offset = -1;
		this.isClassVariable = false;
		this.isParameter = false;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public boolean getIsClassVar() {
		return isClassVariable;
	}
	
	public void setIsClassVar(boolean isClassVariable) {
		this.isClassVariable = isClassVariable;
	}
	
	public boolean isParameter() {
		return isParameter;
	}
	
	public void setIsParameter(boolean isParameter) {
		this.isParameter = isParameter;
	}
}
