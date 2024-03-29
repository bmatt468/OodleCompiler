/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.bju.cps450.node;

import com.bju.cps450.analysis.*;

@SuppressWarnings("nls")
public final class AIfStatement extends PIfStatement
{
    private TIf _beginIf_;
    private PExpression _expression_;
    private TThen _then_;
    private PNewlines _newlines_;
    private PStatementList _statementList_;
    private PElseStatement _elseStatement_;
    private TEnd _end_;
    private TIf _endIf_;

    public AIfStatement()
    {
        // Constructor
    }

    public AIfStatement(
        @SuppressWarnings("hiding") TIf _beginIf_,
        @SuppressWarnings("hiding") PExpression _expression_,
        @SuppressWarnings("hiding") TThen _then_,
        @SuppressWarnings("hiding") PNewlines _newlines_,
        @SuppressWarnings("hiding") PStatementList _statementList_,
        @SuppressWarnings("hiding") PElseStatement _elseStatement_,
        @SuppressWarnings("hiding") TEnd _end_,
        @SuppressWarnings("hiding") TIf _endIf_)
    {
        // Constructor
        setBeginIf(_beginIf_);

        setExpression(_expression_);

        setThen(_then_);

        setNewlines(_newlines_);

        setStatementList(_statementList_);

        setElseStatement(_elseStatement_);

        setEnd(_end_);

        setEndIf(_endIf_);

    }

    @Override
    public Object clone()
    {
        return new AIfStatement(
            cloneNode(this._beginIf_),
            cloneNode(this._expression_),
            cloneNode(this._then_),
            cloneNode(this._newlines_),
            cloneNode(this._statementList_),
            cloneNode(this._elseStatement_),
            cloneNode(this._end_),
            cloneNode(this._endIf_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAIfStatement(this);
    }

    public TIf getBeginIf()
    {
        return this._beginIf_;
    }

    public void setBeginIf(TIf node)
    {
        if(this._beginIf_ != null)
        {
            this._beginIf_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._beginIf_ = node;
    }

    public PExpression getExpression()
    {
        return this._expression_;
    }

    public void setExpression(PExpression node)
    {
        if(this._expression_ != null)
        {
            this._expression_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._expression_ = node;
    }

    public TThen getThen()
    {
        return this._then_;
    }

    public void setThen(TThen node)
    {
        if(this._then_ != null)
        {
            this._then_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._then_ = node;
    }

    public PNewlines getNewlines()
    {
        return this._newlines_;
    }

    public void setNewlines(PNewlines node)
    {
        if(this._newlines_ != null)
        {
            this._newlines_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._newlines_ = node;
    }

    public PStatementList getStatementList()
    {
        return this._statementList_;
    }

    public void setStatementList(PStatementList node)
    {
        if(this._statementList_ != null)
        {
            this._statementList_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._statementList_ = node;
    }

    public PElseStatement getElseStatement()
    {
        return this._elseStatement_;
    }

    public void setElseStatement(PElseStatement node)
    {
        if(this._elseStatement_ != null)
        {
            this._elseStatement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._elseStatement_ = node;
    }

    public TEnd getEnd()
    {
        return this._end_;
    }

    public void setEnd(TEnd node)
    {
        if(this._end_ != null)
        {
            this._end_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._end_ = node;
    }

    public TIf getEndIf()
    {
        return this._endIf_;
    }

    public void setEndIf(TIf node)
    {
        if(this._endIf_ != null)
        {
            this._endIf_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._endIf_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._beginIf_)
            + toString(this._expression_)
            + toString(this._then_)
            + toString(this._newlines_)
            + toString(this._statementList_)
            + toString(this._elseStatement_)
            + toString(this._end_)
            + toString(this._endIf_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._beginIf_ == child)
        {
            this._beginIf_ = null;
            return;
        }

        if(this._expression_ == child)
        {
            this._expression_ = null;
            return;
        }

        if(this._then_ == child)
        {
            this._then_ = null;
            return;
        }

        if(this._newlines_ == child)
        {
            this._newlines_ = null;
            return;
        }

        if(this._statementList_ == child)
        {
            this._statementList_ = null;
            return;
        }

        if(this._elseStatement_ == child)
        {
            this._elseStatement_ = null;
            return;
        }

        if(this._end_ == child)
        {
            this._end_ = null;
            return;
        }

        if(this._endIf_ == child)
        {
            this._endIf_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._beginIf_ == oldChild)
        {
            setBeginIf((TIf) newChild);
            return;
        }

        if(this._expression_ == oldChild)
        {
            setExpression((PExpression) newChild);
            return;
        }

        if(this._then_ == oldChild)
        {
            setThen((TThen) newChild);
            return;
        }

        if(this._newlines_ == oldChild)
        {
            setNewlines((PNewlines) newChild);
            return;
        }

        if(this._statementList_ == oldChild)
        {
            setStatementList((PStatementList) newChild);
            return;
        }

        if(this._elseStatement_ == oldChild)
        {
            setElseStatement((PElseStatement) newChild);
            return;
        }

        if(this._end_ == oldChild)
        {
            setEnd((TEnd) newChild);
            return;
        }

        if(this._endIf_ == oldChild)
        {
            setEndIf((TIf) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
