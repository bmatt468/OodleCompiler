/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.bju.cps450.node;

import java.util.*;
import com.bju.cps450.analysis.*;

@SuppressWarnings("nls")
public final class AClasses extends PClasses
{
    private PNewlines _newlines_;
    private PClassDefinition _classDefinition_;
    private final LinkedList<PClassesTail> _classesTail_ = new LinkedList<PClassesTail>();
    private final LinkedList<TNewline> _newline_ = new LinkedList<TNewline>();

    public AClasses()
    {
        // Constructor
    }

    public AClasses(
        @SuppressWarnings("hiding") PNewlines _newlines_,
        @SuppressWarnings("hiding") PClassDefinition _classDefinition_,
        @SuppressWarnings("hiding") List<?> _classesTail_,
        @SuppressWarnings("hiding") List<?> _newline_)
    {
        // Constructor
        setNewlines(_newlines_);

        setClassDefinition(_classDefinition_);

        setClassesTail(_classesTail_);

        setNewline(_newline_);

    }

    @Override
    public Object clone()
    {
        return new AClasses(
            cloneNode(this._newlines_),
            cloneNode(this._classDefinition_),
            cloneList(this._classesTail_),
            cloneList(this._newline_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAClasses(this);
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

    public PClassDefinition getClassDefinition()
    {
        return this._classDefinition_;
    }

    public void setClassDefinition(PClassDefinition node)
    {
        if(this._classDefinition_ != null)
        {
            this._classDefinition_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._classDefinition_ = node;
    }

    public LinkedList<PClassesTail> getClassesTail()
    {
        return this._classesTail_;
    }

    public void setClassesTail(List<?> list)
    {
        for(PClassesTail e : this._classesTail_)
        {
            e.parent(null);
        }
        this._classesTail_.clear();

        for(Object obj_e : list)
        {
            PClassesTail e = (PClassesTail) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._classesTail_.add(e);
        }
    }

    public LinkedList<TNewline> getNewline()
    {
        return this._newline_;
    }

    public void setNewline(List<?> list)
    {
        for(TNewline e : this._newline_)
        {
            e.parent(null);
        }
        this._newline_.clear();

        for(Object obj_e : list)
        {
            TNewline e = (TNewline) obj_e;
            if(e.parent() != null)
            {
                e.parent().removeChild(e);
            }

            e.parent(this);
            this._newline_.add(e);
        }
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._newlines_)
            + toString(this._classDefinition_)
            + toString(this._classesTail_)
            + toString(this._newline_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._newlines_ == child)
        {
            this._newlines_ = null;
            return;
        }

        if(this._classDefinition_ == child)
        {
            this._classDefinition_ = null;
            return;
        }

        if(this._classesTail_.remove(child))
        {
            return;
        }

        if(this._newline_.remove(child))
        {
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._newlines_ == oldChild)
        {
            setNewlines((PNewlines) newChild);
            return;
        }

        if(this._classDefinition_ == oldChild)
        {
            setClassDefinition((PClassDefinition) newChild);
            return;
        }

        for(ListIterator<PClassesTail> i = this._classesTail_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((PClassesTail) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        for(ListIterator<TNewline> i = this._newline_.listIterator(); i.hasNext();)
        {
            if(i.next() == oldChild)
            {
                if(newChild != null)
                {
                    i.set((TNewline) newChild);
                    newChild.parent(this);
                    oldChild.parent(null);
                    return;
                }

                i.remove();
                oldChild.parent(null);
                return;
            }
        }

        throw new RuntimeException("Not a child.");
    }
}
