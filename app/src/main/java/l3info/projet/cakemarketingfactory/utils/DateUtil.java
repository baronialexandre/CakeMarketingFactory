package l3info.projet.cakemarketingfactory.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    //@pattern : DD:HH:mm ; HH:mm ...
    public static String getCurrent(String pattern)
    {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(now.getTime());
    }

    //@pattern : DD:HH:mm ; HH:mm ...
    //getCountDown avant le reset
    public static String getCountDown(String pattern)
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Calendar now = Calendar.getInstance();
        Date date = now.getTime();

        int days =    7  - (now.get(Calendar.DAY_OF_WEEK) % 7);
        int hours =   24 - (now.get(Calendar.HOUR_OF_DAY) % 24) - 2; //-1 pour l'heure et -1 encore pour l'ajout des minutes calculées ensuite
        int minutes = 60 - (now.get(Calendar.MINUTE) % 60);
        now.add(Calendar.MINUTE,days*24*60);
        now.add(Calendar.MINUTE,hours*60);
        now.add(Calendar.MINUTE,minutes);
        Date endingDate = now.getTime();

        return simpleDateFormat.format(dateTimeDiff(date, endingDate));
    }

    private static long dateTimeDiff(Date date1, Date date2) {
        return date2.getTime() - date1.getTime();
    }

}
