/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.bju.cps450.node;

import com.bju.cps450.analysis.*;

@SuppressWarnings("nls")
public final class AOtherExpressionLvl8 extends PExpressionLvl8
{
    private PExpressionLvl7 _expressionLvl7_;

    public AOtherExpressionLvl8()
    {
        // Constructor
    }

    public AOtherExpressionLvl8(
        @SuppressWarnings("hiding") PExpressionLvl7 _expressionLvl7_)
    {
        // Constructor
        setExpressionLvl7(_expressionLvl7_);

    }

    @Override
    public Object clone()
    {
        return new AOtherExpressionLvl8(
            cloneNode(this._expressionLvl7_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAOtherExpressionLvl8(this);
    }

    public PExpressionLvl7 getExpressionLvl7()
    {
        return this._expressionLvl7_;
    }

    public void setExpressionLvl7(PExpressionLvl7 node)
    {
        if(this._expressionLvl7_ != null)
        {
            this._expressionLvl7_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expressionLvl7_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._expressionLvl7_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._expressionLvl7_ == child)
        {
            this._expressionLvl7_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._expressionLvl7_ == oldChild)
        {
            setExpressionLvl7((PExpressionLvl7) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
