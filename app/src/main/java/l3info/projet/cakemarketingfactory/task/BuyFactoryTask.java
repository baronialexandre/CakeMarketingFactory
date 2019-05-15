package l3info.projet.cakemarketingfactory.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import l3info.projet.cakemarketingfactory.activity.FactoryActivity;
import l3info.projet.cakemarketingfactory.model.Factory;
import l3info.projet.cakemarketingfactory.model.Line;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BuyFactoryTask extends AsyncTask<String, Void, Boolean>{

    private static final String TAG = "EnterFactoryTask";

    private final long userId;
    private final int factorySpot;
    private final int productId;
    private final int score;
    private final WeakReference<Context> ctx;
    private Boolean notEnoughScore = false;

    public BuyFactoryTask(long userId, int factorySpot, int productId, int score, Context ctx) {
        this.userId = userId;
        this.factorySpot = factorySpot;
        this.productId = productId;
        this.score = score;
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL_BUY_FACTORYTSK", "OKHTTP");
            Log.i("BANDOL_BUY_FACTORYTSK", Contents.API_URL + Contents.BUY_FACTORY_URL + "?userId=" + userId + "&factorySpot=" + factorySpot + "&score=" + score + "&productId=" + productId + "&apipass=" + Contents.API_PASS);
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.BUY_FACTORY_URL + "?userId=" + userId + "&factorySpot=" + factorySpot + "&score=" + score + "&productId=" + productId + "&apipass=" + Contents.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.i("BANDOL_BUY_FACTORYTSK", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);
            if(jsonObj.has("notEnoughScore"))
                notEnoughScore = jsonObj.getBoolean("notEnoughScore");
            return jsonObj.getBoolean("success");
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        if(success) {
            // Toast achat successful
            Log.i("BANDOL_BUY_FACTORYTSK", "success");
            Context ctx = this.ctx.get();
            EnterFactoryTask task = new EnterFactoryTask(userId,new Factory(factorySpot), ctx);
            task.execute();
        } else {
            if (notEnoughScore) Log.i("BANDOL_BUY_FACTORYTSK", "false not enough");
            else Log.i("BANDOL_BUY_FACTORYTSK", "false");
            // Toast pas assez de score
        }
    }
}
