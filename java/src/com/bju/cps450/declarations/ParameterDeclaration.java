/* ParameterDeclaration.java
 * Author: Ethan McGee
 * Date: 2014-03-10
 * Purpose: a declaration holder for parameter declarations  
 */
package com.bju.cps450.declarations;

import com.bju.cps450.types.Type;

public class ParameterDeclaration extends Declaration {
	private int order;
	private String prefix;
	
	public ParameterDeclaration() {
		super();
	}
	
	public ParameterDeclaration(String name, Type t) {
		super(name, t);
	}
	
	/* setOrder
	 * Arguments:
	 *   order: int - the order of the parameter
	 * Purpose: sets the order of the parameter
	 */
	public void setOrder(int order) {
		this.order = order;
	}
	
	/* getOrder
	 * Arguments:
	 * 
	 * Purpose: gets the order of the parameter
	 */
	public int getOrder() {
		return this.order;
	}
}
