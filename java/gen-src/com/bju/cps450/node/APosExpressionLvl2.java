/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.bju.cps450.node;

import com.bju.cps450.analysis.*;

@SuppressWarnings("nls")
public final class APosExpressionLvl2 extends PExpressionLvl2
{
    private TPlus _plus_;
    private PExpressionLvl2 _expressionLvl2_;

    public APosExpressionLvl2()
    {
        // Constructor
    }

    public APosExpressionLvl2(
        @SuppressWarnings("hiding") TPlus _plus_,
        @SuppressWarnings("hiding") PExpressionLvl2 _expressionLvl2_)
    {
        // Constructor
        setPlus(_plus_);

        setExpressionLvl2(_expressionLvl2_);

    }

    @Override
    public Object clone()
    {
        return new APosExpressionLvl2(
            cloneNode(this._plus_),
            cloneNode(this._expressionLvl2_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAPosExpressionLvl2(this);
    }

    public TPlus getPlus()
    {
        return this._plus_;
    }

    public void setPlus(TPlus node)
    {
        if(this._plus_ != null)
        {
            this._plus_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._plus_ = node;
    }

    public PExpressionLvl2 getExpressionLvl2()
    {
        return this._expressionLvl2_;
    }

    public void setExpressionLvl2(PExpressionLvl2 node)
    {
        if(this._expressionLvl2_ != null)
        {
            this._expressionLvl2_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expressionLvl2_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._plus_)
            + toString(this._expressionLvl2_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._plus_ == child)
        {
            this._plus_ = null;
            return;
        }

        if(this._expressionLvl2_ == child)
        {
            this._expressionLvl2_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._plus_ == oldChild)
        {
            setPlus((TPlus) newChild);
            return;
        }

        if(this._expressionLvl2_ == oldChild)
        {
            setExpressionLvl2((PExpressionLvl2) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}