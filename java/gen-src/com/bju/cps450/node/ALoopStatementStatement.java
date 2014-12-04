/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.bju.cps450.node;

import com.bju.cps450.analysis.*;

@SuppressWarnings("nls")
public final class ALoopStatementStatement extends PStatement
{
    private PLoopStatement _loopStatement_;

    public ALoopStatementStatement()
    {
        // Constructor
    }

    public ALoopStatementStatement(
        @SuppressWarnings("hiding") PLoopStatement _loopStatement_)
    {
        // Constructor
        setLoopStatement(_loopStatement_);

    }

    @Override
    public Object clone()
    {
        return new ALoopStatementStatement(
            cloneNode(this._loopStatement_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseALoopStatementStatement(this);
    }

    public PLoopStatement getLoopStatement()
    {
        return this._loopStatement_;
    }

    public void setLoopStatement(PLoopStatement node)
    {
        if(this._loopStatement_ != null)
        {
            this._loopStatement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._loopStatement_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._loopStatement_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._loopStatement_ == child)
        {
            this._loopStatement_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._loopStatement_ == oldChild)
        {
            setLoopStatement((PLoopStatement) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
