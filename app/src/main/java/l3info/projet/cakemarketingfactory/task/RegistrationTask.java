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

import l3info.projet.cakemarketingfactory.activity.LoginActivity;
import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegistrationTask extends AsyncTask<String, Void, Boolean>{

    private static final String TAG = "RegistrationTask";

    private final String email;
    private final String username;
    private final String password;
    private final WeakReference<TextView> feedbackTextView;
    private final WeakReference<Context> ctx;
    private Boolean alreadyUsed;

    public RegistrationTask(String email, String username, String password, TextView feedbackTextView, Context ctx) {
        this.email = email;
        this.username = username;
        this.password = password;

        this.feedbackTextView = new WeakReference<>(feedbackTextView);
        this.ctx = new WeakReference<>(ctx);
        alreadyUsed = false;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        try {
            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL", "OKHTTP");
            Log.i("BANDOL", Contents.API_URL + Contents.AUTH_API_URL + "?username=" + username + "&email=" + email + "&password=" + password + "&apipass=" + Contents.API_PASS);
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.REG_API_URL + "?username=" + username + "&email=" + email + "&password=" + password + "&apipass=" + Contents.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.i("BANDOL", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);
            if (jsonObj.has("alreadyUsed"))
                alreadyUsed = jsonObj.getBoolean("alreadyUsed");

            Log.i("BANDOL", String.valueOf(jsonObj.getBoolean("reg")));
            return jsonObj.getBoolean("reg");
        } catch(IOException e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean registrationResponse) {
        super.onPostExecute(registrationResponse);
        Context ctx = this.ctx.get();
        TextView feedbackTextView = this.feedbackTextView.get();
        Log.i("BANDOL","IF");
        if(registrationResponse) {
            feedbackTextView.setText(R.string.registration_feedback_success);
            feedbackTextView.setTextColor(Color.GREEN);
            Log.i("BANDOL","true green");

            // validation du compte???

            //Store username and pwd into shared preferences
            SharedPreferences shr = ctx.getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = shr.edit();
            ed.putString("username",username);
            ed.putString("password",password);
            ed.apply();

            //Redirect to main user activity
            Intent intent;
            intent = new Intent(ctx, LoginActivity.class);
            ctx.startActivity(intent);
        } else {
            if(alreadyUsed)
                feedbackTextView.setText(R.string.registration_feedback_already_used);
            else
                feedbackTextView.setText(R.string.registration_feedback_failed);
            feedbackTextView.setTextColor(Color.RED);
            Log.i("BANDOL","wrong red");
        }
    }
}
