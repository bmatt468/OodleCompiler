package com.bju.cps450;

import junit.framework.TestCase;

public class SymbolTableTest extends TestCase {

	public void test() {
		SymbolTable st = new SymbolTable();
		
		assert (st.getScope() == 0);
		
		// push test
		st.push("pushtest", null);
		assert (st.getScope() == 1);
		
		// lookup test
		
		// beginScope test
		
		// endScope test
	}

}
