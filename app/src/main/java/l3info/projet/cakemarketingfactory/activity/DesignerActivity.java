package l3info.projet.cakemarketingfactory.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.utils.Contents;

public class DesignerActivity extends AppCompatActivity {

    Context context;
    SharedPreferences shr;

    boolean sound;
    MediaPlayer mediaPlayerIn;
    MediaPlayer mediaPlayerOut;
    MediaPlayer mediaPlayerMelo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        context = this;
        shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);

        //Initialisation des différents sons
        sound = shr.getBoolean("sound", true);
        mediaPlayerIn = MediaPlayer.create(context, R.raw.in1);
        mediaPlayerOut = MediaPlayer.create(context, R.raw.out2);
        mediaPlayerMelo = MediaPlayer.create(context, R.raw.melo1);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer);

        ImageView back = findViewById(R.id.designerBack);
        back.setOnClickListener(v -> {
            if (sound) { mediaPlayerOut.start(); }
            finish();
        });

    }
}
