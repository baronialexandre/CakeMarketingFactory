package l3info.projet.cakemarketingfactory.activity.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import java.util.Random;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.utils.Contents;

public class SoundManager {

    private Context context;
    private boolean sound;

    private static final int[] resIn = {
            R.raw.in1,
            R.raw.in2,
            R.raw.in3,
            R.raw.in4
    };
    private static final int[] resOut = {
            R.raw.out1,
            R.raw.out2,
            R.raw.out3,
            R.raw.out4
    };
    private Random random;


    public SoundManager(Context context) {
        this.context = context;
        SharedPreferences shr = context.getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        sound = shr.getBoolean("sound", true);
        random = new Random();
    }

    public void playSoundIn() {
        if(sound)
        {
            playSound(MediaPlayer.create(context, resIn[random.nextInt(resIn.length)]));
        }
    }

    public void playSoundOut() {
        playSound(MediaPlayer.create(context, resOut[random.nextInt(resOut.length)]));
    }

    public void playSoundSell() {
        playSound(MediaPlayer.create(context, R.raw.melo1));
    }

    public void playSoundVote() {
        playSound(MediaPlayer.create(context, R.raw.melo2));
    }

    private void playSound(MediaPlayer mediaPlayer){
        if(sound)
        {
            mediaPlayer.setOnCompletionListener(MediaPlayer::release);
            mediaPlayer.start();
        }
    }

    public void setSound(boolean soundState) {
        sound = soundState;
    }

}
