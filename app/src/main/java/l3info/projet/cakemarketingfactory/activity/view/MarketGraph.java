package l3info.projet.cakemarketingfactory.activity.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import l3info.projet.cakemarketingfactory.model.Demand;
import l3info.projet.cakemarketingfactory.model.Market;

public class MarketGraph extends View
{
    Market market;

    Paint lineIncreasePaint;
    Paint lineStagnatePaint;
    Paint lineDecreasePaint;

    public MarketGraph(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public void setMarket(Market market)
    {
        this.market = market;
    }

    private void init()
    {
        Paint lineThicknessPaint = new Paint();
        lineThicknessPaint.setStrokeWidth(8.0f);

        lineIncreasePaint = new Paint(lineThicknessPaint);
        lineStagnatePaint = new Paint(lineThicknessPaint);
        lineDecreasePaint = new Paint(lineThicknessPaint);

        lineIncreasePaint.setColor(0xff00dd00);
        lineStagnatePaint.setColor(0xffff8800);
        lineDecreasePaint.setColor(0xffff0000);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        ArrayList<Demand> cookieDemands = market.demands.get(0);

        float originX = 0;
        float originY = (float)getHeight() - ((cookieDemands.get(0).getPrice()*(float)getHeight())/20);
        for(int i = 1; i < cookieDemands.size(); i++)
        {
            Demand demand = cookieDemands.get(i);

            float destX = originX + (float)(getWidth()/9);
            float destY = (float)getHeight() - ((demand.getPrice()*(float)getHeight())/20);

            if(originY == destY)
                canvas.drawLine(originX,originY,destX,destY,lineStagnatePaint);
            else if(originY > destY)
                canvas.drawLine(originX,originY,destX,destY,lineIncreasePaint);
            else
                canvas.drawLine(originX,originY,destX,destY,lineDecreasePaint);

            originX = destX;
            originY = destY;
        }
    }

}
