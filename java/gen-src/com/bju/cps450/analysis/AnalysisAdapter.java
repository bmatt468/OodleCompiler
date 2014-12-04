/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.bju.cps450.analysis;

import java.util.*;
import com.bju.cps450.node.*;

public class AnalysisAdapter implements Analysis
{
    private Hashtable<Node,Object> in;
    private Hashtable<Node,Object> out;

    @Override
    public Object getIn(Node node)
    {
        if(this.in == null)
        {
            return null;
        }

        return this.in.get(node);
    }

    @Override
    public void setIn(Node node, Object o)
    {
        if(this.in == null)
        {
            this.in = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.in.put(node, o);
        }
        else
        {
            this.in.remove(node);
        }
    }

    @Override
    public Object getOut(Node node)
    {
        if(this.out == null)
        {
            return null;
        }

        return this.out.get(node);
    }

    @Override
    public void setOut(Node node, Object o)
    {
        if(this.out == null)
        {
            this.out = new Hashtable<Node,Object>(1);
        }

        if(o != null)
        {
            this.out.put(node, o);
        }
        else
        {
            this.out.remove(node);
        }
    }

    @Override
    public void caseStart(Start node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAClasses(AClasses node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAClassesTail(AClassesTail node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANewlines(ANewlines node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAClassDefinition(AClassDefinition node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAInheritsClause(AInheritsClause node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAVariableDeclarations(AVariableDeclarations node)
    {
        defaultCase(node);
    }

    @Override
    public void caseATypeDeclaration(ATypeDeclaration node)
    {
        defaultCase(node);
    }

    @Override
    public void caseABooleanType(ABooleanType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIntType(AIntType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStringType(AStringType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAClassType(AClassType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArrayType(AArrayType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADuckType(ADuckType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFloatType(AFloatType node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAInitializer(AInitializer node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMethodDeclarations(AMethodDeclarations node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArgumentList(AArgumentList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArgumentListTail(AArgumentListTail node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArgument(AArgument node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStatementList(AStatementList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStatementListTail(AStatementListTail node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIfStatementStatement(AIfStatementStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALoopStatementStatement(ALoopStatementStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACallStatementStatement(ACallStatementStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAssignStatementStatement(AAssignStatementStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIfStatement(AIfStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAElseStatement(AElseStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseALoopStatement(ALoopStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACallStatement(ACallStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAssignmentStatement(AAssignmentStatement node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAExpressionList(AExpressionList node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAExpressionListTail(AExpressionListTail node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAExpression(AExpression node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOrExpressionLvl8(AOrExpressionLvl8 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOtherExpressionLvl8(AOtherExpressionLvl8 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAndExpressionLvl7(AAndExpressionLvl7 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOtherExpressionLvl7(AOtherExpressionLvl7 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAEqualsExpressionLvl6(AEqualsExpressionLvl6 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAGreaterExpressionLvl6(AGreaterExpressionLvl6 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAGtEqualExpressionLvl6(AGtEqualExpressionLvl6 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOtherExpressionLvl6(AOtherExpressionLvl6 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAConcatExpressionLvl5(AConcatExpressionLvl5 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOtherExpressionLvl5(AOtherExpressionLvl5 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAAddExpressionLvl4(AAddExpressionLvl4 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseASubtractExpressionLvl4(ASubtractExpressionLvl4 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOtherExpressionLvl4(AOtherExpressionLvl4 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMultExpressionLvl3(AMultExpressionLvl3 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseADivideExpressionLvl3(ADivideExpressionLvl3 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOtherExpressionLvl3(AOtherExpressionLvl3 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAPosExpressionLvl2(APosExpressionLvl2 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANegExpressionLvl2(ANegExpressionLvl2 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANotExpressionLvl2(ANotExpressionLvl2 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAOtherExpressionLvl2(AOtherExpressionLvl2 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIdentifierExpressionLvl1(AIdentifierExpressionLvl1 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAStringExpressionLvl1(AStringExpressionLvl1 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAIntegerExpressionLvl1(AIntegerExpressionLvl1 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseATrueExpressionLvl1(ATrueExpressionLvl1 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFalseExpressionLvl1(AFalseExpressionLvl1 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANullExpressionLvl1(ANullExpressionLvl1 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAMeExpressionLvl1(AMeExpressionLvl1 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseANewExpressionLvl1(ANewExpressionLvl1 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseACallExpressionLvl1(ACallExpressionLvl1 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArrayExpressionLvl1(AArrayExpressionLvl1 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAParenExpressionLvl1(AParenExpressionLvl1 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAFloatExpressionLvl1(AFloatExpressionLvl1 node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAExpressionDot(AExpressionDot node)
    {
        defaultCase(node);
    }

    @Override
    public void caseAArray(AArray node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNewline(TNewline node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTComment(TComment node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTWhitespace(TWhitespace node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTContinuation(TContinuation node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIntegerLiteral(TIntegerLiteral node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFloatLiteral(TFloatLiteral node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTStringLiteral(TStringLiteral node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTUnterminatedString(TUnterminatedString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIllegalString(TIllegalString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTBoolean(TBoolean node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTBegin(TBegin node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTClasskey(TClasskey node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTElse(TElse node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEnd(TEnd node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFalse(TFalse node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFrom(TFrom node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIf(TIf node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTInherits(TInherits node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTInt(TInt node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIs(TIs node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLoop(TLoop node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMe(TMe node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNew(TNew node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNot(TNot node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTNull(TNull node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTString(TString node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTThen(TThen node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTTrue(TTrue node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTWhile(TWhile node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAnd(TAnd node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTOr(TOr node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDuck(TDuck node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTFloat(TFloat node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTConcatenate(TConcatenate node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTPlus(TPlus node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMinus(TMinus node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTMultiply(TMultiply node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDivide(TDivide node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTGreater(TGreater node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTGreaterEqual(TGreaterEqual node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTEquals(TEquals node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTAssignment(TAssignment node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLParen(TLParen node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRParen(TRParen node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTLBracket(TLBracket node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTRBracket(TRBracket node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTComma(TComma node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTSemicolon(TSemicolon node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTColon(TColon node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTDot(TDot node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTIdentifier(TIdentifier node)
    {
        defaultCase(node);
    }

    @Override
    public void caseTUnknownCharacter(TUnknownCharacter node)
    {
        defaultCase(node);
    }

    @Override
    public void caseEOF(EOF node)
    {
        defaultCase(node);
    }

    @Override
    public void caseInvalidToken(InvalidToken node)
    {
        defaultCase(node);
    }

    public void defaultCase(@SuppressWarnings("unused") Node node)
    {
        // do nothing
    }
}
