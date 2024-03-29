/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.bju.cps450.node;

import com.bju.cps450.analysis.*;

@SuppressWarnings("nls")
public final class AMultExpressionLvl3 extends PExpressionLvl3
{
    private PExpressionLvl3 _expressionLvl3_;
    private TMultiply _multiply_;
    private PExpressionLvl2 _expressionLvl2_;

    public AMultExpressionLvl3()
    {
        // Constructor
    }

    public AMultExpressionLvl3(
        @SuppressWarnings("hiding") PExpressionLvl3 _expressionLvl3_,
        @SuppressWarnings("hiding") TMultiply _multiply_,
        @SuppressWarnings("hiding") PExpressionLvl2 _expressionLvl2_)
    {
        // Constructor
        setExpressionLvl3(_expressionLvl3_);

        setMultiply(_multiply_);

        setExpressionLvl2(_expressionLvl2_);

    }

    @Override
    public Object clone()
    {
        return new AMultExpressionLvl3(
            cloneNode(this._expressionLvl3_),
            cloneNode(this._multiply_),
            cloneNode(this._expressionLvl2_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAMultExpressionLvl3(this);
    }

    public PExpressionLvl3 getExpressionLvl3()
    {
        return this._expressionLvl3_;
    }

    public void setExpressionLvl3(PExpressionLvl3 node)
    {
        if(this._expressionLvl3_ != null)
        {
            this._expressionLvl3_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expressionLvl3_ = node;
    }

    public TMultiply getMultiply()
    {
        return this._multiply_;
    }

    public void setMultiply(TMultiply node)
    {
        if(this._multiply_ != null)
        {
            this._multiply_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._multiply_ = node;
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
            + toString(this._expressionLvl3_)
            + toString(this._multiply_)
            + toString(this._expressionLvl2_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._expressionLvl3_ == child)
        {
            this._expressionLvl3_ = null;
            return;
        }

        if(this._multiply_ == child)
        {
            this._multiply_ = null;
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
        if(this._expressionLvl3_ == oldChild)
        {
            setExpressionLvl3((PExpressionLvl3) newChild);
            return;
        }

        if(this._multiply_ == oldChild)
        {
            setMultiply((TMultiply) newChild);
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
