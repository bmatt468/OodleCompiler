/* Oodle.java
 * Author: Ethan McGee
 * Date: 2014-01-23
 * 
 * Purpose: parser class for Oodle compiler project
 */

package com.bju.cps450;

import java.io.IOException;

import com.bju.cps450.lexer.Lexer;
import com.bju.cps450.lexer.LexerException;
import com.bju.cps450.node.EOF;
import com.bju.cps450.node.Start;
import com.bju.cps450.parser.Parser;

public class OodleParser extends Parser {

	private Lexer lexer;
	
	public OodleParser(Lexer lexer) {
		super(lexer);
		this.lexer = lexer;
	}

	/* parse
	 * Arguments:
	 *  
	 * Purpose: wrapper around the parser that catches exceptions and displays error messages
	 */
	@Override
	public Start parse() throws LexerException, IOException {
		Start s = null;
		try {
			s = super.parse();
		} catch (Exception ex) {
			Application.getErrors().addParserErrors();
			System.out.println(Application.getFileAndLineNumbers().getFile(lexer.peek().getLine()) + ":" + Application.getFileAndLineNumbers().getLine(lexer.peek().getLine()) + "," + lexer.peek().getPos() + ":" + ex.getMessage().split("] ")[1] + " but got " + lexer.peek().getText());
			while(!(lexer.peek() instanceof EOF)) {
				lexer.next();
			}
		} 
		return s;
	}
	
	

}