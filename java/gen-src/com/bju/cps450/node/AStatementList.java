/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.bju.cps450.node;

import java.util.*;
import com.bju.cps450.analysis.*;

@SuppressWarnings("nls")
public final class AStatementList extends PStatementList
{
    private PStatement _statement_;
    private final LinkedList<PStatementListTail> _statementListTail_ = new LinkedList<PStatementListTail>();
    private PNewlines _newlines_;

    public AStatementList()
    {
        // Constructor
    }

    public AStatementList(
        @SuppressWarnings("hiding") PStatement _statement_,
        @SuppressWarnings("hiding") List<?> _statementListTail_,
        @SuppressWarnings("hiding") PNewlines _newlines_)
    {
        // Constructor
        setStatement(_statement_);

        setStatementListTail(_statementListTail_);

        setNewlines(_newlines_);

    }

    @Override
    public Object clone()
    {
        return new AStatementList(
            cloneNode(this._statement_),
            cloneList(this._statementListTail_),
            cloneNode(this._newlines_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAStatementList(this);
    }

    public PStatement getStatement()
    {
        return this._statement_;
    }

    public void setStatement(PStatement node)
    {
        if(this._statement_ != null)
        {
            this._statement_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._statement_ = node;
    }

    public LinkedList<PStatementListTail> getStatementListTail()
    {
        return this._statementListTail_;
    }

    public void setStatementListTail(List<?> list)
    {
        for(PStatementListTail e : this._statementListTail_)
        {
            e.parent(null);
        }
        this._statementListTail_.clear();

        for(Object obj_e : list)
        {
            PStatementListTail e = (PStatementListTail) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._statementListTail_.add(e);
        }
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

    @Override
    public String toString()
    {
        return ""
            + toString(this._statement_)
            + toString(this._statementListTail_)
            + toString(this._newlines_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._statement_ == child)
        {
            this._statement_ = null;
            return;
        }

        if(this._statementListTail_.remove(child))
        {
            return;
        }

        if(this._newlines_ == child)
        {
            this._newlines_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._statement_ == oldChild)
        {
            setStatement((PStatement) newChild);
            return;
        }

        for(ListIterator<PStatementListTail> i = this._statementListTail_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PStatementListTail) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        if(this._newlines_ == oldChild)
        {
            setNewlines((PNewlines) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}