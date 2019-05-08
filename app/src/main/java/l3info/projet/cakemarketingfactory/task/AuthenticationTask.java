package l3info.projet.cakemarketingfactory.task;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.WorldActivity;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationTask extends AsyncTask<String, Void, Boolean>{

    private static final String TAG = "AuthenticationTask";

    private final String username;
    private final String password;
    private final WeakReference<TextView> feedbackTextView;
    private final WeakReference<Context> ctx;
    private long userId;

    public AuthenticationTask(String username, String password, TextView feedbackTextView, Context ctx) {
        this.username = username;
        this.password = password;

        this.feedbackTextView = new WeakReference<>(feedbackTextView);
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {

            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL", "OKHTTP");
            Log.i("BANDOL", Contents.API_URL + Contents.AUTH_API_URL + "?username=" + username + "&password=" + password + "&apipass=" + Contents.API_PASS);
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.AUTH_API_URL + "?username=" + username + "&password=" + password + "&apipass=" + Contents.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.i("BANDOL", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);

            userId = jsonObj.getLong("userId");
            return jsonObj.getBoolean("auth");
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean authenticationResponse) {
        super.onPostExecute(authenticationResponse);
        Context ctx = this.ctx.get();
        TextView feedbackTextView = this.feedbackTextView.get();
        Log.i("BANDOL","IF");
        if(authenticationResponse) {
            feedbackTextView.setText(R.string.login_feedback_success);
            feedbackTextView.setTextColor(Color.GREEN);
            Log.i("BANDOL","true green");

            //Store id into shared preferences
            SharedPreferences shr = ctx.getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = shr.edit();
            ed.putLong("userId",userId);
            ed.apply();

            //Enter world requete ici ou dans on create world activity?

            //Redirect to main user activity
            Intent intent;
            intent = new Intent(ctx, WorldActivity.class);
            ctx.startActivity(intent);

        } else {
            feedbackTextView.setText(R.string.login_feedback_failed);
            feedbackTextView.setTextColor(Color.RED);
            Log.i("BANDOL","wrong red");
        }
    }
}
