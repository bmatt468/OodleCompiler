/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.bju.cps450.node;

import com.bju.cps450.analysis.*;

@SuppressWarnings("nls")
public final class ADuckType extends PType
{
    private TDuck _duck_;

    public ADuckType()
    {
        // Constructor
    }

    public ADuckType(
        @SuppressWarnings("hiding") TDuck _duck_)
    {
        // Constructor
        setDuck(_duck_);

    }

    @Override
    public Object clone()
    {
        return new ADuckType(
            cloneNode(this._duck_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseADuckType(this);
    }

    public TDuck getDuck()
    {
        return this._duck_;
    }

    public void setDuck(TDuck node)
    {
        if(this._duck_ != null)
        {
            this._duck_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._duck_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._duck_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._duck_ == child)
        {
            this._duck_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._duck_ == oldChild)
        {
            setDuck((TDuck) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
