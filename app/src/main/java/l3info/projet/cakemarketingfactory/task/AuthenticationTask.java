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

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.WorldActivity;
import l3info.projet.cakemarketingfactory.utils.Contants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationTask extends AsyncTask<String, Void, Boolean>{

    private static final String TAG = "AuthenticationTask";

    private final String username;
    private final String password;
    private final TextView feedbackTextView;
    private final Context ctx;

    public AuthenticationTask(String username, String password, TextView feedbackTextView, Context ctx) {
        this.username = username;
        this.password = password;

        this.feedbackTextView = feedbackTextView;
        this.ctx = ctx;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {

            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL","OKHTTP");
            Request request = new Request.Builder()
                    .url(Contants.API_URL + Contants.AUTH_API_URL + "?username=" + username + "&password=" + password + "&apipass=" + Contants.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = response.body().string();
            JSONObject jsonObj = new JSONObject(rawJson);


            return jsonObj.getBoolean("auth");
        } catch(IOException | JSONException e) {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return false;
        }
    }

    protected void onPostExecute(boolean authenticationResponse) {
        super.onPostExecute(authenticationResponse);

        if(authenticationResponse) {
            feedbackTextView.setText(R.string.login_feedback_success);
            feedbackTextView.setTextColor(Color.GREEN);


            //Redirect to main user activity
            Intent intent;

            //Store id into shared preferences
            /*
            Long id = authenticationResponse.getID();
            SharedPreferences shr = ctx.getSharedPreferences("ID", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = shr.edit();
            ed.putLong("ID", id);
            ed.commit();
            */

            intent = new Intent(ctx, WorldActivity.class);

            ctx.startActivity(intent);
        } else {
            feedbackTextView.setText(R.string.login_feedback_failed);
            feedbackTextView.setTextColor(Color.RED);
        }
    }
}
