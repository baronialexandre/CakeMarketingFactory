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
    int productIdToDisplay;

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

        productIdToDisplay = 0;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        drawProductGraph(productIdToDisplay, canvas);
    }

    private void drawProductGraph(int productId, Canvas canvas)
    {
        ArrayList<Demand> demands = market.demands.get(productId);

        float startX = 0;
        float startY = (float)getHeight() - ((demands.get(0).getPrice()*(float)getHeight())/20);
        for(int i = 1; i < demands.size(); i++)
        {
            Demand demand = demands.get(i);

            float stopX = startX + (float)(getWidth()/9);
            float stopY = (float)getHeight() - ((demand.getPrice()*(float)getHeight())/20);

            traceLine(startX, startY, stopX, stopY, canvas);

            startX = stopX;
            startY = stopY;
        }
    }

    private void traceLine(float startX, float startY, float stopX, float stopY, Canvas canvas)
    {
        Paint paint = determinePaint(startY, stopY);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    private Paint determinePaint(float startY, float stopY)
    {
        Paint paint;
        if(startY == stopY)
            paint = lineStagnatePaint;
        else if(startY > stopY)
            paint = lineIncreasePaint;
        else
            paint = lineDecreasePaint;
        return paint;
    }

    public void setProductIdToDisplay(int productIdToDisplay)
    {
        this.productIdToDisplay = productIdToDisplay;
        invalidate();
    }

}
