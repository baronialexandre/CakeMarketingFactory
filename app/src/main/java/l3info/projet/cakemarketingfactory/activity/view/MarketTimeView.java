package l3info.projet.cakemarketingfactory.activity.view;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MarketTimeView extends android.support.v7.widget.AppCompatTextView
{
    CountDownTimer timer;

    public MarketTimeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        // NE PAS CRITIQUER SVP :'(
        Calendar calendar = Calendar.getInstance();
        int daysInterval = Calendar.MONDAY - calendar.get(Calendar.DAY_OF_WEEK);
        if(daysInterval < 0)
            daysInterval += 7;
        calendar.add(Calendar.DAY_OF_YEAR, daysInterval);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 1);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Date now = new Date();
        Date next = calendar.getTime();

        long diff = Math.abs(now.getTime() - next.getTime());

        timer = new CountDownTimer(diff,60000)
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
