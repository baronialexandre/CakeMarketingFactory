package l3info.projet.cakemarketingfactory.task;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Locale;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.manager.SoundManager;
import l3info.projet.cakemarketingfactory.model.Votes;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.ViewContent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CastVoteTask extends AsyncTask<String, Void, Boolean>
{
    private static final String TAG = "CastVoteTask";

    private WeakReference<Dialog> dialogWeakReference;
    Votes votes;

    private int productId;
    private long userId;
    private WeakReference<Context> ctx;

    public CastVoteTask(int productId, long userId, Dialog dialog, Context ctx,Votes votes)
    {
        this.productId = productId;
        this.userId = userId;
        this.dialogWeakReference = new WeakReference<>(dialog);
        this.ctx = new WeakReference<>(ctx);
        this.votes = votes;
    }

    @Override
    protected Boolean doInBackground(String... params)
    {
        try
        {
            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL_CAST_VOTE_TASK", Contents.API_URL + Contents.CAST_VOTE_URL + "?apipass=" + Contents.API_PASS + "&userId=" + userId + "&productId=" + productId);
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.CAST_VOTE_URL + "?apipass=" + Contents.API_PASS + "&userId=" + userId + "&productId=" + productId)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null)
            {
                rawJson = response.body().string();
            }
            Log.i("BANDOL_CAST_VOTE_TASK", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);
            return jsonObj.getBoolean("voteCheck");
        }
        catch(IOException | JSONException e)
        {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean voteCheck)
    {
        super.onPostExecute(voteCheck);
        final Dialog dialog = dialogWeakReference.get();
        TextView marketVoteCastAlert = dialog.findViewById(R.id.marketVoteCastAlert);
        Context ctx = this.ctx.get();
        SoundManager soundManager = new SoundManager(ctx);

        if(!voteCheck) {
            soundManager.playSoundOut();
            marketVoteCastAlert.setText(R.string.vote_already_cast);

        }
        else {
            soundManager.playSoundVote();
            marketVoteCastAlert.setText(R.string.vote_cast);

            votes.addVote(productId,1);
            for(int i = 0; i < 3; i++)
            {
                TextView marketAdvertisingCakeText = dialog.findViewById(ViewContent.marketAdvertisingCakeText[i]);
                marketAdvertisingCakeText.setText(String.format(Locale.ROOT,"%.0f%%", votes.getPercentage(i)));

                RelativeLayout barBg = dialog.findViewById(ViewContent.marketAdvertisingVoteBarBg[i]);
                RelativeLayout bar = dialog.findViewById(ViewContent.marketAdvertisingVoteBar[i]);
                float barWidth = votes.getPercentage(i)*barBg.getLayoutParams().width/100;
                bar.getLayoutParams().width = (int)barWidth;
            }

        }
    }
}
