/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.bju.cps450.node;

import com.bju.cps450.analysis.*;

@SuppressWarnings("nls")
public final class AOtherExpressionLvl5 extends PExpressionLvl5
{
    private PExpressionLvl4 _expressionLvl4_;

    public AOtherExpressionLvl5()
    {
        // Constructor
    }

    public AOtherExpressionLvl5(
        @SuppressWarnings("hiding") PExpressionLvl4 _expressionLvl4_)
    {
        // Constructor
        setExpressionLvl4(_expressionLvl4_);

    }

    @Override
    public Object clone()
    {
        return new AOtherExpressionLvl5(
            cloneNode(this._expressionLvl4_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAOtherExpressionLvl5(this);
    }

    public PExpressionLvl4 getExpressionLvl4()
    {
        return this._expressionLvl4_;
    }

    public void setExpressionLvl4(PExpressionLvl4 node)
    {
        if(this._expressionLvl4_ != null)
        {
            this._expressionLvl4_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expressionLvl4_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._expressionLvl4_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._expressionLvl4_ == child)
        {
            this._expressionLvl4_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._expressionLvl4_ == oldChild)
        {
            setExpressionLvl4((PExpressionLvl4) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
