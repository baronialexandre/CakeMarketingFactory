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
import java.util.ArrayList;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.WorldActivity;
import l3info.projet.cakemarketingfactory.modele.Factory;
import l3info.projet.cakemarketingfactory.modele.Line;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class EnterFactoryTask extends AsyncTask<String, Void, Factory>{

    private static final String TAG = "EnterFactoryTask";

    private final Factory factory;
    private long userId;
    private final WeakReference<Context> ctx;

    public EnterFactoryTask(long userId, Factory factory, Context ctx) {
        this.userId = userId;
        this.factory = factory;

        this.ctx = new WeakReference<>(ctx);
    }
    /*
    @Override
    protected Factory doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL", "OKHTTP");
            Log.i("BANDOL", Contents.API_URL + Contents.ENTER_FACTORY_URL + "?userId=" + userId + "&factorySpot=" + factory.getFactorySpot() + "&apipass=" + Contents.API_PASS);
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.ENTER_FACTORY_URL + "?userId=" + userId + "&factorySpot=" + factory.getFactorySpot() + "&apipass=" + Contents.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.i("BANDOL", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);

            ArrayList<Line> lineList = new ArrayList<Line>();

            factory.setLineList();
            userId = jsonObj.getLong("userId");
            return jsonObj.getBoolean("auth");
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Factory factory) {
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
    }*/
}
