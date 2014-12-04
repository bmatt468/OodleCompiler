/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.bju.cps450.node;

import com.bju.cps450.analysis.*;

@SuppressWarnings("nls")
public final class AOtherExpressionLvl2 extends PExpressionLvl2
{
    private PExpressionLvl1 _expressionLvl1_;

    public AOtherExpressionLvl2()
    {
        // Constructor
    }

    public AOtherExpressionLvl2(
        @SuppressWarnings("hiding") PExpressionLvl1 _expressionLvl1_)
    {
        // Constructor
        setExpressionLvl1(_expressionLvl1_);

    }

    @Override
    public Object clone()
    {
        return new AOtherExpressionLvl2(
            cloneNode(this._expressionLvl1_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAOtherExpressionLvl2(this);
    }

    public PExpressionLvl1 getExpressionLvl1()
    {
        return this._expressionLvl1_;
    }

    public void setExpressionLvl1(PExpressionLvl1 node)
    {
        if(this._expressionLvl1_ != null)
        {
            this._expressionLvl1_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expressionLvl1_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._expressionLvl1_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._expressionLvl1_ == child)
        {
            this._expressionLvl1_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._expressionLvl1_ == oldChild)
        {
            setExpressionLvl1((PExpressionLvl1) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
