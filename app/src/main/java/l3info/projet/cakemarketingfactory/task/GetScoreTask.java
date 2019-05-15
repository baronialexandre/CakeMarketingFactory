package l3info.projet.cakemarketingfactory.task;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.FunctionUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//GetMoneyTask refactor

public class GetScoreTask extends AsyncTask<String, Void, Integer> {
    private static final String TAG = "GetScoreTask";

    private final long userId;
    private final WeakReference<Context> ctx;
    private final WeakReference<TextView> score;
    public GetScoreTask(long userId, TextView score, Context ctx) {
        this.userId = userId;
        this.score = new WeakReference<>(score);
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    protected Integer doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.GET_SCORE_URL + "?apipass=" + Contents.API_PASS + "&userId="+userId)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            JSONObject jsonObj = new JSONObject(rawJson);

            return jsonObj.getInt("score");
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return 42;
        }
    }

    @Override
    protected void onPostExecute(Integer userScore) {
        super.onPostExecute(userScore);
        Context ctx = this.ctx.get();
        TextView score = this.score.get();
        String scoreText = FunctionUtil.scoreShortner(userScore)+" $";
        score.setText(scoreText);
        SharedPreferences shr = ctx.getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = shr.edit();
        ed.putInt("score",userScore);
        ed.apply();
    }
}