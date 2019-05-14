package l3info.projet.cakemarketingfactory.activity.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import java.util.Locale;

import l3info.projet.cakemarketingfactory.utils.FunctionUtil;

public class MarketTimeView extends android.support.v7.widget.AppCompatTextView
{
    CountDownTimer timer;

    public MarketTimeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        long diff = FunctionUtil.getCountDown();

        timer = new CountDownTimer(diff,2000)
        {
            @Override
            public void onTick(long elapsed)
            {
                long hours = (elapsed/3600000);
                long minutes = (elapsed/60000)%60;
                if(hours < 1)
                    hours = 0;
                setText(String.format(Locale.FRANCE,"%dH %dm",hours,minutes));
            }

            @Override
            public void onFinish() {

            }
        };
    }

    public void start()
    {
        timer.start();
    }
}
