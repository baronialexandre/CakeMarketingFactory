package l3info.projet.cakemarketingfactory.utils;

import android.annotation.SuppressLint;
import android.content.res.Configuration;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FunctionUtil {

    //@pattern : DD:HH:mm ; HH:mm ...
    public static String getCurrent(String pattern)
    {
        Calendar now = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(now.getTime());
    }

    //@pattern : DD:HH:mm ; HH:mm ...
    //getCountDown avant le reset
    public static Long getCountDown()
    {
        Calendar calendar = Calendar.getInstance();
        int daysInterval = Calendar.SUNDAY - calendar.get(Calendar.DAY_OF_WEEK);
        if(daysInterval < 0)
            daysInterval += 7;
        calendar.add(Calendar.DAY_OF_YEAR, daysInterval);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 0);

        Date now = new Date();
        Date next = calendar.getTime();

        return Math.abs(now.getTime() - next.getTime());
    }

    private static final String[] cashNotation = {
            "",
            "K", // One Thousand
            "M", // One Million
            "B", // One Billion
            "T", // One Trillion
            "+"  // More !
    };

    public static String scoreShorten(long score) {

        if(score < 0) return "---";
        if(score < 1000) return String.format(Locale.ROOT,"%d", score);
        boolean locker = true;
        int i = 0;
        while (locker) {
            if (1000000 <= score) {
                score /= 1000;
                i++;
            }
            else locker = false;
        }
        String formatted;
        if(i<cashNotation.length)
        {
            formatted =  String.format(Locale.ROOT, "%1.3f", (score/1000.0))+FunctionUtil.cashNotation[i];
            formatted = formatted.replaceAll("\\.", " ");
            return formatted;
        }

        else return "+++"; //too much
    }

    public static String idToProduct(int productId)
    {
        String productName;
        switch(productId)
        {
            case 0:
                productName = "cookies";
                break;
            case 1:
                productName = "cupcakes";
                break;
            case 2:
                productName = "donuts";
                break;
            default:
                productName = "product error";
        }
        return productName;

    }

}
