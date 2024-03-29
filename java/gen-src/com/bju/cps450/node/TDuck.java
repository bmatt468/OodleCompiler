/* This file was generated by SableCC (http://www.sablecc.org/). */

package com.bju.cps450.node;

import com.bju.cps450.analysis.*;

@SuppressWarnings("nls")
public final class TDuck extends Token
{
    public TDuck()
    {
        super.setText("duck");
    }

    public TDuck(int line, int pos)
    {
        super.setText("duck");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TDuck(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTDuck(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TDuck text.");
    }
}
