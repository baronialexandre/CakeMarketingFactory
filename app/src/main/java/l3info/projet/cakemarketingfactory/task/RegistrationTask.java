package l3info.projet.cakemarketingfactory.task;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import l3info.projet.cakemarketingfactory.LoginActivity;
import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.utils.Contants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegistrationTask extends AsyncTask<String, Void, Boolean>{

    private static final String TAG = "RegistrationTask";

    private final Context ctx;
    private final String email;
    private final String username;
    private final String password;
    private final TextView feedbackTextView;
    private Boolean alreadyUsed;

    public RegistrationTask(String email, String username, String password, TextView feedbackTextView, Context ctx) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.feedbackTextView = feedbackTextView;
        this.ctx = ctx;
        alreadyUsed = false;
    }

    @Override
    protected Boolean doInBackground(String... params) {

        try {
            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL", "OKHTTP");
            Log.i("BANDOL", Contants.API_URL + Contants.AUTH_API_URL + "?username=" + username + "&email=" + email + "&password=" + password + "&apipass=" + Contants.API_PASS);
            Request request = new Request.Builder()
                    .url(Contants.API_URL + Contants.REG_API_URL + "?username=" + username + "&email=" + email + "&password=" + password + "&apipass=" + Contants.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = response.body().string();
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
        Log.i("BANDOL","IF");
        if(registrationResponse) {
            feedbackTextView.setText(R.string.registration_feedback_success);
            feedbackTextView.setTextColor(Color.GREEN);
            Log.i("BANDOL","true green");

            // validation du compte???

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
