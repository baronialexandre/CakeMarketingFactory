package l3info.projet.cakemarketingfactory.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Objects;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.manager.LanguageManager;
import l3info.projet.cakemarketingfactory.activity.manager.SoundManager;
import l3info.projet.cakemarketingfactory.model.Factory;
import l3info.projet.cakemarketingfactory.model.World;
import l3info.projet.cakemarketingfactory.task.BuyFactoryTask;
import l3info.projet.cakemarketingfactory.task.EnterFactoryTask;
import l3info.projet.cakemarketingfactory.task.EnterMarketTask;
import l3info.projet.cakemarketingfactory.task.EnterMessagesTask;
import l3info.projet.cakemarketingfactory.task.GetScoreTask;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.ImageContent;
import l3info.projet.cakemarketingfactory.utils.ViewContent;

public class WorldActivity  extends AppCompatActivity {
    Context context;
    SoundManager soundManager;
    World world;

    boolean musicState;
    boolean soundState;
    MediaPlayer mediaPlayerMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);
        context = this;
        soundManager = new SoundManager(this);

        //Log.i("BANDOL",getIntent().getSerializableExtra("world").toString());
        world = (World) getIntent().getSerializableExtra("world");
        //Log.i("BANDOL",world.factories.toString());
        //access to the userId in shared preferences
        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        long userId = shr.getLong("userId",0L);
        //Log.i("BANDOL","world sharedprefid:"+userId );

        //Initialisation des différents sons
        musicState= shr.getBoolean("music", true);
        soundState = shr.getBoolean("sound",true);
        mediaPlayerMusic = MediaPlayer.create(context, R.raw.cakeloop);
        mediaPlayerMusic.setLooping(true);
        if (musicState) { mediaPlayerMusic.start(); }

        TextView userScore = findViewById(R.id.worldCapital);

        GetScoreTask getScore = new GetScoreTask(userId, userScore, context);
        getScore.execute();


        boolean[] spotMask = new boolean[]{false,false,false,false,false,false};
        for(Factory factory : world.factories) {
            int factorySpot = factory.getFactorySpot()-1; //a cause du putain de décalage
            ImageView factoryV = findViewById(ViewContent.factoryId[factorySpot]);
            factoryV.setImageResource(ImageContent.factoryId[factorySpot]);
            factoryV.setVisibility(View.VISIBLE);

            factoryV.setOnClickListener(v -> {
                soundManager.playSoundIn();
                //Entrer dans une usine
                Toast.makeText(context, "FACTORY 1 + " + factorySpot, Toast.LENGTH_SHORT).show();
                EnterFactoryTask task = new EnterFactoryTask(userId, factory, context);
                task.execute();
            });
            spotMask[factorySpot] = true;
        }
        boolean isTheFirst = true;
        for(int i=0; i<spotMask.length; i++)
        {
            if(!spotMask[i]) {
                ImageView sign = findViewById(ViewContent.signId[i]);
                sign.setVisibility(View.VISIBLE);
                int finalI = i;
                sign.setOnClickListener(v -> {
                    soundManager.playSoundIn();
                    //cliquer sur un panneau $
                    Toast.makeText(context, "BUY sign 1 + " + finalI, Toast.LENGTH_SHORT).show();
                    //montre le prix et demande si tu veux acheter
                    openPopupSign(finalI, shr.getInt("score", 0));
                });
                if(isTheFirst)
                {
                    sign.setImageDrawable(getResources().getDrawable(R.drawable.world_dollard_sign_next));
                    isTheFirst=false;
                }
            }
        }

        ImageView market = findViewById(R.id.worldMarket);
        market.setOnClickListener(view -> {
            soundManager.playSoundIn();
            //Changer d'activity
            EnterMarketTask enterMarketTask = new EnterMarketTask(userId, context);
            enterMarketTask.execute();
        });

        ImageView messages = findViewById(R.id.worldLetter);
        messages.setOnClickListener(view -> {
            soundManager.playSoundIn();
            //Changer d'activity
            EnterMessagesTask enterMessagesTask = new EnterMessagesTask(context);
            enterMessagesTask.execute();
        });

        ImageView settings = findViewById(R.id.worldSettings);
        settings.setOnClickListener(view -> {
            soundManager.playSoundIn();
            openSettingsPopup();
        });

        ImageView profile = findViewById(R.id.worldProfile);
        profile.setOnClickListener(view -> {
            soundManager.playSoundIn();
            openProfilePopup();
        });
    }

    void openSettingsPopup()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_settings);
        Button close = dialog.findViewById(R.id.popupSettingsOk);
        close.setOnClickListener(v -> {
            soundManager.playSoundOut();
            dialog.dismiss();
        });

        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);

        Button disconnect = dialog.findViewById(R.id.popupSettingsDisconnect);
        disconnect.setOnClickListener(v -> {
            soundManager.playSoundOut();
            SharedPreferences.Editor ed = shr.edit();
            ed.remove("username");
            ed.remove("password");
            ed.remove("userId");
            ed.apply();

            Intent intentApp = new Intent(WorldActivity.this, LoginActivity.class);
            WorldActivity.this.startActivity(intentApp);
        });

        ImageView sound = dialog.findViewById(R.id.popupSettingsSound);
        ImageView music = dialog.findViewById(R.id.popupSettingsMusic);
        ImageView flag = dialog.findViewById(R.id.popupSettingsFlag);

        String language = shr.getString("language", Locale.getDefault().getDisplayLanguage().substring(0,2));

        //init start
        if(soundState)
            sound.setImageDrawable(getResources().getDrawable(R.drawable.ic_sound));
        else
            sound.setImageDrawable(getResources().getDrawable(R.drawable.ic_sound_off));
        if(musicState)
            music.setImageDrawable(getResources().getDrawable(R.drawable.ic_music));
        else
            music.setImageDrawable(getResources().getDrawable(R.drawable.ic_music_off));
        assert language != null;
        switch (language){
            case "fr":
                flag.setImageDrawable(getResources().getDrawable(R.drawable.flag_fr));
                break;
            case "en":
                flag.setImageDrawable(getResources().getDrawable(R.drawable.flag_en));
                break;
            default:
                flag.setImageDrawable(getResources().getDrawable(R.drawable.flag_en));
                break;
        }
        //init end

        flag.setOnClickListener(v -> {
            soundManager.playSoundIn();
            SharedPreferences shr1 = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = shr1.edit();
            String languageB = shr.getString("language", Locale.getDefault().getDisplayLanguage().substring(0,2));

            assert languageB != null;
            switch (languageB){
                case "fr":
                    ed.putString("language","en");
                    ed.apply();
                    flag.setImageDrawable(getResources().getDrawable(R.drawable.flag_en));
                    LanguageManager.changeLocale(this,"en");
                    break;
                case "en":
                    ed.putString("language","fr");
                    ed.apply();
                    flag.setImageDrawable(getResources().getDrawable(R.drawable.flag_fr));
                    LanguageManager.changeLocale(this,"fr");
                    break;
                default:
                    ed.putString("language","English");
                    ed.apply();
                    flag.setImageDrawable(getResources().getDrawable(R.drawable.flag_en));
                    LanguageManager.changeLocale(this, Locale.getDefault().getDisplayLanguage().substring(0,2));
                    break;
            }
            disconnect.setText(R.string.disconnect);
        });

        sound.setOnClickListener(v -> {
            soundManager.playSoundIn();
            SharedPreferences shr1 = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = shr1.edit();
            boolean soundStateB = shr.getBoolean("sound",true);
            if(soundStateB) {
                soundState = false;
                ed.putBoolean("sound", false);
                sound.setImageDrawable(getResources().getDrawable(R.drawable.ic_sound_off));
            }
            else {
                soundState=true;
                ed.putBoolean("sound", true);
                sound.setImageDrawable(getResources().getDrawable(R.drawable.ic_sound));
            }
            ed.apply();
            soundManager.setSound(soundState);
        });

        music.setOnClickListener(v -> {
            soundManager.playSoundIn();
            SharedPreferences shr1 = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = shr1.edit();
            boolean musicStateB = shr.getBoolean("music",true);
            if(musicStateB) {
                mediaPlayerMusic.stop();
                musicState = false;
                ed.putBoolean("music", false);
                music.setImageDrawable(getResources().getDrawable(R.drawable.ic_music_off));
            }
            else {
                mediaPlayerMusic = MediaPlayer.create(context, R.raw.cakeloop);
                mediaPlayerMusic.setLooping(true);
                mediaPlayerMusic.start();
                musicState = true;
                ed.putBoolean("music", true);
                music.setImageDrawable(getResources().getDrawable(R.drawable.ic_music));
            }
            ed.apply();
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openProfilePopup()
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_profile);

        TextView popupProfileValues = dialog.findViewById(R.id.popupProfileValues);

        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        String username = shr.getString("username","");
        String registerDate = shr.getString("registerDate","");
        int level = shr.getInt("level",0);
        long maxScore = shr.getLong("maxScore",0);
        int maxRank = shr.getInt("maxRank",0);

        popupProfileValues.setText(getString(R.string.profile_values, username, registerDate, level, maxScore, maxRank));

        Button popupMessageClose = dialog.findViewById(R.id.popupProfileOk);
        popupMessageClose.setOnClickListener(v -> {
            soundManager.playSoundOut();
            dialog.dismiss();
        });
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openPopupSign(int factorySpot, int score)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_question);
        Button popupMessageCancel = dialog.findViewById(R.id.popupQuestionCancel);
        popupMessageCancel.setOnClickListener(v -> {
            soundManager.playSoundOut();
            dialog.dismiss();
        });

        TextView popupQuestionMessage = dialog.findViewById(R.id.popupQuestionMessage);
        popupQuestionMessage.setText(getString(R.string.factory_buy_alert, Factory.getFactoryPrice(factorySpot+1)));

        ImageView popupQuestionImage = dialog.findViewById(R.id.popupQuestionImage);
        popupQuestionImage.setImageResource(R.drawable.world_dollard_sign);

        Button popupMessageOk = dialog.findViewById(R.id.popupQuestionOk);
        popupMessageOk.setOnClickListener(v -> {
            //Propose de selectionner le gateau de la 1ere ligne lors de l'achat
            soundManager.playSoundIn();
            openPopupSelection(factorySpot,score);
            dialog.dismiss();
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    void openPopupSelection(int factorySpot, int score)
    {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.popup_cake_sel);
        ImageView popupMessageCancel = dialog.findViewById(R.id.popupCakeSelBack);
        popupMessageCancel.setOnClickListener(v -> dialog.dismiss());
        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        long userId = shr.getLong("userId",0L);
        //clic sur cookie
        ImageButton cookieSelect = dialog.findViewById(R.id.popupCakeSelCookie);
        cookieSelect.setOnClickListener(v -> {
            /*Do something*/
            BuyFactoryTask task = new BuyFactoryTask(userId,factorySpot+1,0,
                    findViewById(ViewContent.factoryId[factorySpot]),findViewById(ViewContent.signId[factorySpot]),findViewById(R.id.worldCapital),context);
            task.execute();
            //bien garder le dismiss qui suit //remove this comment
            dialog.dismiss();
        });

        //clic sur cupcake
        ImageButton cupcakeSelect = dialog.findViewById(R.id.popupCakeSelCupcake);
        cupcakeSelect.setOnClickListener(v -> {
            /*Do something*/
            BuyFactoryTask task = new BuyFactoryTask(userId,factorySpot+1,1,
                    findViewById(ViewContent.factoryId[factorySpot]),findViewById(ViewContent.signId[factorySpot]),findViewById(R.id.worldCapital),context);
            task.execute();
            //bien garder le dismiss qui suit //remove this comment
            dialog.dismiss();
        });

        //clic sur donut
        ImageButton donutSelect = dialog.findViewById(R.id.popupCakeSelDonut);
        donutSelect.setOnClickListener(v -> {
            /*Do something*/
            BuyFactoryTask task = new BuyFactoryTask(userId,factorySpot+1,2,
                    findViewById(ViewContent.factoryId[factorySpot]),findViewById(ViewContent.signId[factorySpot]),findViewById(R.id.worldCapital),context);
            task.execute();
            //bien garder le dismiss qui suit //remove this comment
            dialog.dismiss();
        });

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();
    }

    //declaré dans le manifest > android:configChanges="locale"
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }
}
