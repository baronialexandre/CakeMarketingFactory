package l3info.projet.cakemarketingfactory.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.manager.SoundManager;
import l3info.projet.cakemarketingfactory.model.Factory;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.ImageContent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BuyFactoryTask extends AsyncTask<String, Void, Boolean>{

    private static final String TAG = "EnterFactoryTask";

    private final long userId;
    private final int factorySpot;
    private final int productId;
    private final WeakReference<ImageView> factoryView;
    private final WeakReference<ImageView> sign;
    private final WeakReference<TextView> userScore;
    private final WeakReference<Context> ctx;

    private Boolean notEnoughScore = false;
    private Boolean alreadyBought = false;
    private Long requiredScore;

    private SoundManager soundManager;

    public BuyFactoryTask(long userId, int factorySpot, int productId,ImageView factoryView, ImageView sign, TextView userScore, Context ctx) {
        this.userId = userId;
        this.factorySpot = factorySpot;
        this.productId = productId;
        this.factoryView = new WeakReference<>(factoryView);
        this.sign = new WeakReference<>(sign);
        this.userScore = new WeakReference<>(userScore);
        this.ctx = new WeakReference<>(ctx);
        soundManager = new SoundManager(ctx);
        requiredScore = 0L;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL_BUY_FACTORYTSK", "OKHTTP");
            Log.i("BANDOL_BUY_FACTORYTSK", Contents.API_URL + Contents.BUY_FACTORY_URL + "?userId=" + userId + "&factorySpot=" + factorySpot + "&productId=" + productId + "&apipass=" + Contents.API_PASS);
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.BUY_FACTORY_URL + "?userId=" + userId + "&factorySpot=" + factorySpot + "&productId=" + productId + "&apipass=" + Contents.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.i("BANDOL_BUY_FACTORYTSK", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);

            Boolean success = jsonObj.getBoolean("success");

            if(jsonObj.has("notEnoughScore")) notEnoughScore = jsonObj.getBoolean("notEnoughScore");
            if(jsonObj.has("alreadyBought")) alreadyBought = jsonObj.getBoolean("notEnoughScore");
            if(jsonObj.has("requiredScore")) requiredScore = jsonObj.getLong("requiredScore");
            return success;

        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        Context ctx = this.ctx.get();
        if(success)
        {
            soundManager.playSoundIn();
            // Toast achat successful
            Toast.makeText(ctx, R.string.purchase_succesful, Toast.LENGTH_LONG).show();
            Log.i("BANDOL_BUY_FACTORYTSK", "success");
            //switch de panneau d'achat à usine achetée
            //public void switchSpot(int spot)
            //{
            //if(factorySpot > 5) return; //il y a 6 usines max
            ImageView factoryView = this.factoryView.get();
            factoryView.setImageResource(ImageContent.factoryId[factorySpot-1]);
            factoryView.setVisibility(View.VISIBLE);
            factoryView.setOnClickListener(v -> {
                soundManager.playSoundIn();
                //Entrer dans une usine
                Toast.makeText(ctx, "FACTORY 1 + "+ (factorySpot-1), Toast.LENGTH_SHORT).show();
                EnterFactoryTask task = new EnterFactoryTask(userId,new Factory(factorySpot), ctx);
                task.execute();
            });

            ImageView sign = this.sign.get();
            sign.setVisibility(View.VISIBLE);
            sign.setClickable(false);
            //}
            GetScoreTask getScore = new GetScoreTask(userId, userScore.get(), ctx);
            getScore.execute();

            EnterFactoryTask task = new EnterFactoryTask(userId,new Factory(factorySpot), ctx);
            task.execute();
        }
        else
        {
            soundManager.playSoundOut();
            if (notEnoughScore) {
                Toast.makeText(ctx, ctx.getString(R.string.purchase_not_enough_score, requiredScore ), Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(ctx, R.string.purchase_failure, Toast.LENGTH_LONG).show();
            }
        }
    }
}
