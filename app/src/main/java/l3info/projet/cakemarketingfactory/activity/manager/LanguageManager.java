package l3info.projet.cakemarketingfactory.activity.manager;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {
    public static void changeLocale(Activity activity, String language)
    {
        language = language.toLowerCase().substring(0,2);
        final Resources res = activity.getResources();
        final Configuration conf = res.getConfiguration();
        conf.locale = new Locale(language);
        res.updateConfiguration(conf, null);
    }
}
