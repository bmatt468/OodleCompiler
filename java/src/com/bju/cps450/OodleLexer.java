/* OodleLexer.java
 * Author: Ethan McGee
 * Date: 2014-01-23
 * 
 * Purpose: Lexer class for the Oodle compiler project
 */

package com.bju.cps450;

import java.io.IOException;
import java.io.PushbackReader;

import com.bju.cps450.lexer.Lexer;
import com.bju.cps450.lexer.LexerException;
import com.bju.cps450.node.*;

public class OodleLexer extends Lexer {

	/* shouldNotPrint
	 * Arguments:
	 *   @t - the current token
	 * Purpose: should we completely ignore this token
	 */
	private boolean shouldNotPrint(Token t) {
		return (t instanceof TComment || t instanceof TContinuation || t instanceof TWhitespace || t instanceof EOF);
	}
	
	/* isError
	 * Arguments:
	 *   @t - the current token
	 * Purpose: does this token present a lexical error
	 */
	private boolean isError(Token t) {
		return (t instanceof TUnknownCharacter || t instanceof TUnterminatedString || t instanceof TIllegalString);
	}
	
	/* isUnknownCharacter
	 * Arguments:
	 *   @t - the current token
	 * Purpose: is this token an unknown character
	 */
	private boolean isUnknownCharacter(Token t) {
		return t instanceof TUnknownCharacter;
	}
	
	/* isUnterminatedString
	 * Arguments:
	 *   @t - the current token
	 * Purpose: is this token an unterminated string
	 */
	private boolean isUnterminatedString(Token t) {
		return t instanceof TUnterminatedString;
	}
	
	/* isIllegalString
	 * Arguments:
	 *   @t - the current token
	 * Purpose: is this token an illegal string
	 */
	private boolean isIllegalString(Token t) {
		return t instanceof TIllegalString;
	}
	
	/* isEndOfLine
	 * Arguments:
	 *   @t - the current token
	 * Purpose: is this token an end of line character
	 */
	private boolean isEndOfLine(Token t) {
		return t instanceof TNewline;
	}
	
	/* isKeyword
	 * Arguments:
	 *   @t - the current token
	 * Purpose: is this token a keyword
	 */
	private boolean isKeyword(Token t) {
		return (t instanceof TBoolean || t instanceof TBegin || t instanceof TClasskey ||
				t instanceof TElse || t instanceof TEnd || t instanceof TFalse ||
				t instanceof TFrom || t instanceof TIf || t instanceof TInherits ||
				t instanceof TInt || t instanceof TIs || t instanceof TLoop || 
				t instanceof TMe || t instanceof TNew || t instanceof TNot ||
				t instanceof TNull || t instanceof TString || t instanceof TThen ||
				t instanceof TTrue || t instanceof TWhile || t instanceof TAnd ||
				t instanceof TOr || t instanceof TNot);
	}
	
	/* isOperator
	 * Arguments:
	 *   @t - the current token
	 * Purpose: is this token an operator
	 */
	private boolean isOperator(Token t) {
		return (t instanceof TConcatenate || t instanceof TPlus || t instanceof TMinus ||
				t instanceof TMultiply || t instanceof TDivide || t instanceof TGreater ||
				t instanceof TGreaterEqual || t instanceof TEquals);
	}
	
	/* isMicellaneous
	 * Arguments:
	 *   @t - the current token
	 * Purpose: is this token an micellaneous token
	 */
	private boolean isMicellaneous(Token t) {
		return (t instanceof TAssignment|| t instanceof TRParen || t instanceof TLParen ||
				t instanceof TLBracket || t instanceof TRBracket || t instanceof TComma ||
				t instanceof TSemicolon || t instanceof TColon || t instanceof TDot);
	}
	
	/* isIdentifier
	 * Arguments:
	 *   @t - the current token
	 * Purpose: is this token an identifier
	 */
	private boolean isIdentifier(Token t) {
		return t instanceof TIdentifier;
	}
	
	/* isStringLiteral
	 * Arguments:
	 *   @t - the current token
	 * Purpose: is this token a string literal
	 */
	private boolean isStringLiteral(Token t) {
		return t instanceof TStringLiteral;
	}
	
	/* isIntLiteral
	 * Arguments:
	 *   @t - the current token
	 * Purpose: is this token an int literal
	 */
	private boolean isIntegerLiteral(Token t) {
		return t instanceof TIntegerLiteral;
	}
	
	public OodleLexer(PushbackReader in) {
		super(in);
		// TODO Auto-generated constructor stub
	}
	
	/* handleError
	 * Arguments:
	 *   @t - the current token
	 * Purpose: returns what to print for each error token
	 */
	public String handleError(Token t) {
		if(isUnknownCharacter(t)) {
			return "unrecognized char: ";
		} else if (isUnterminatedString(t)) {
			return "unterminated string:";
		} else if (isIllegalString(t)) {
			return "illegal string:";
		}
		return "";
	}
	
	/* handleNormal
	 * Arguments:
	 *   @t - the current token
	 * Purpose: returns what to print for each normal token
	 */
	public String handleNormal(Token t) {
		if(isStringLiteral(t)) {
			return "string lit:" + t.getText();
		} else if (isIntegerLiteral(t)) {
			return "int lit:" + t.getText();
		} else if (isKeyword(t)) {
			return "keyword:" + t.getText();
		} else if (isOperator(t)) {
			return "operator:'" + t.getText() + "'";
		} else if (isIdentifier(t)) {
			return "identifier:" + t.getText();
		}
		return "";
	}

	@Override
	protected void filter() throws LexerException, IOException {
		super.filter();
		
		boolean shouldPrint = false;
		String output = Application.getFileAndLineNumbers().getFile(this.token.getLine()) + ":" + Application.getFileAndLineNumbers().getLine(this.token.getLine()) + "," + this.token.getPos() + ":";
		
		if(isError(this.token)) { //always print errors
			shouldPrint = true;
			output += handleError(this.token) + this.token.getText();
			Application.getErrors().addLexicalError();
		} else if (!shouldNotPrint(this.token)) { //some tokens are ignorable
			if (Application.getOptions().shouldPrintTokens()) {
				shouldPrint = true;
				if(!isMicellaneous(this.token) && !isEndOfLine(this.token)) {
					output += handleNormal(this.token);
				} else if(isMicellaneous(this.token)) {
					output += "'" + this.token.getText() + "'";
				} else {
					output += "cr"; //newlines just display as whitespace
				}
			}
		}
		
		if(shouldPrint) {
			System.out.println(output);
		}
	}
}
