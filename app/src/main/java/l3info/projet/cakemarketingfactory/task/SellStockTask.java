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

import l3info.projet.cakemarketingfactory.model.Factory;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.FunctionUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SellStockTask extends AsyncTask<String, Void, Boolean> {
    private static final String TAG = "SellStockTask";

    private final long userId;
    private final WeakReference<Context> ctx;
    private final WeakReference<Factory> factory;
    private final int productId;
    private final int userScore;


    public SellStockTask(long userId, Factory factory, int productId, int userScore, Context ctx) {
        this.userId = userId;
        this.factory = new WeakReference<>(factory);
        this.productId = productId;
        this.userScore = userScore;
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.SELL_STOCK_URL + "?apipass=" + Contents.API_PASS + "&userId=" + userId + "&factorySpot=" + this.factory.get().getFactorySpot() + "&productId" + productId + "&score=" + userScore)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            JSONObject jsonObj = new JSONObject(rawJson);

            return jsonObj.getBoolean("test");
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean test) {
        super.onPostExecute(test);
        Factory factory = this.factory.get();
        int productId = this.productId;
        if (test){
            factory.getCurrentStocks().set(productId,0);
        }
    }
}
