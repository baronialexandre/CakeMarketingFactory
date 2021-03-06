package l3info.projet.cakemarketingfactory.activity.view;

import android.content.Context;
import android.graphics.Canvas;
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

    Paint backgroundLinePaint;
    Paint backgroundLineLargePaint;
    Paint backgroundLineThinPaint;
    Paint endLinePaint;
    Paint endCirclePaint;
    Paint endCircleBorderPaint;

    Paint insideBordersPaint;

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
        // BACKGROUND LINES PAINT
        backgroundLinePaint = new Paint();
        backgroundLinePaint.setColor(0xff337733);

        backgroundLineLargePaint = new Paint(backgroundLinePaint);
        backgroundLineLargePaint.setStrokeWidth(5.0f);
        backgroundLineThinPaint = new Paint(backgroundLinePaint);
        backgroundLineThinPaint.setStrokeWidth(1.0f);

        // BORDERS
        insideBordersPaint = new Paint();
        insideBordersPaint.setColor(0xff404040);
        insideBordersPaint.setStrokeWidth(15.0f);

        // GRAPH LINES PAINT
        Paint lineThicknessPaint = new Paint();
        lineThicknessPaint.setStrokeWidth(9.0f);

        lineIncreasePaint = new Paint(lineThicknessPaint);
        lineStagnatePaint = new Paint(lineThicknessPaint);
        lineDecreasePaint = new Paint(lineThicknessPaint);

        lineIncreasePaint.setColor(0xff00dd00);
        lineStagnatePaint.setColor(0xffff8800);
        lineDecreasePaint.setColor(0xffff0000);

        // ENDING PAINTS
        endLinePaint = new Paint();
        endLinePaint.setColor(0xff00aa66);
        endLinePaint.setStrokeWidth(7.0f);

        endCirclePaint = new Paint();
        endCirclePaint.setColor(0xff00ff99);
        endCircleBorderPaint = new Paint();
        endCircleBorderPaint.setColor(0xff202020);


        productIdToDisplay = 0;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        drawBackground(canvas, productIdToDisplay);

        drawProductGraph(productIdToDisplay, canvas);

        drawBorders(canvas);
    }

    private void drawBackground(Canvas canvas, int productId)
    {
        ArrayList<Demand> demands = market.demands.get(productId);
        canvas.drawColor(0xff2a2a2a);

        // HORIZONTAL LINES
        for(int y = 1; y < 20; y++)
        {
            Paint paint;
            if(y%5 == 0)
                paint = backgroundLineLargePaint;
            else
                paint = backgroundLineThinPaint;
            canvas.drawLine(0, yPosFromPrice(y), getWidth(), yPosFromPrice(y), paint);
        }

        // VERTICAL LINES
        Paint paint;
        float xPos = (float)(getWidth()/10);
        for(int i = 0; i < 9; i++)
        {
            if(i == demands.size() - 2)
                paint = endLinePaint;
            else
                paint = backgroundLineThinPaint;

            canvas.drawLine(xPos, 0, xPos, getHeight(), paint);
            xPos = xPos + (float)(getWidth()/10);
        }
    }

    private void drawBorders(Canvas canvas)
    {
        // INSIDE BORDERS
        canvas.drawLine(0,0,0,getHeight(),insideBordersPaint);
        canvas.drawLine(0,0,getWidth(),0,insideBordersPaint);
        canvas.drawLine(getWidth(),0,getWidth(),getHeight(),insideBordersPaint);
        canvas.drawLine(0,getHeight(),getWidth(),getHeight(),insideBordersPaint);
    }

    private void drawProductGraph(int productId, Canvas canvas)
    {
        ArrayList<Demand> demands = market.demands.get(productId);

        float startX = 0;
        float startY = yPosFromPrice(demands.get(0).getPrice());
        for(int i = 1; i < demands.size(); i++)
        {
            Demand demand = demands.get(i);

            float stopX = startX + (float)(getWidth()/10);
            float stopY = yPosFromPrice(demand.getPrice());

            traceLine(startX, startY, stopX, stopY, canvas);

            startX = stopX;
            startY = stopY;

            if(i == demands.size() - 1)
            {
                canvas.drawCircle(stopX,stopY,16.0f,endCircleBorderPaint);
                canvas.drawCircle(stopX,stopY,12.0f,endCirclePaint);
            }
        }
    }

    private void traceLine(float startX, float startY, float stopX, float stopY, Canvas canvas)
    {
        Paint paint = determinePaint(startY, stopY);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
    }

    private float yPosFromPrice(int price)
    {
        return (float)getHeight() - ((price*(float)getHeight())/20);
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
