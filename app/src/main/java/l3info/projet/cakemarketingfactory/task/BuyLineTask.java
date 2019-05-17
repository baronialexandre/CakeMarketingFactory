package l3info.projet.cakemarketingfactory.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;


import l3info.projet.cakemarketingfactory.model.Factory;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BuyLineTask extends AsyncTask<String, Void, Integer> {
    private WeakReference<Context> ctx;
    private Factory factory;
    private final long userId;
    private int lineSlot;
    private int productId;
    private int userScore;

    public BuyLineTask(long userId, int lineSlot, int productId, int userScore, Context ctx, Factory factory) {
        this.userId = userId;
        this.lineSlot = lineSlot;
        this.productId = productId;
        this.userScore = userScore;
        this.ctx = new WeakReference<>(ctx);
        this.factory = factory;
    }

    @Override
    protected Integer doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.BUY_LINE_URL + "?apipass=" + Contents.API_PASS + "&userId="+userId+ "&factorySpot=" + factory.getFactorySpot() + "&lineSlot=" + lineSlot + "&productId=" + productId + "&userScore=" + userScore)
                    .build();
            Log.i("BANDOL_SELL_STOCK_TASK", Contents.API_URL + Contents.BUY_LINE_URL + "?apipass=" + Contents.API_PASS + "&userId="+userId+ "&factorySpot=" + factory.getFactorySpot() + "&lineSlot=" + lineSlot + "&productId=" + productId + "&userScore=" + userScore);

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            JSONObject jsonObj = new JSONObject(rawJson);
            return jsonObj.getInt("succes");
        } catch (IOException | JSONException e) {
            return 0;
        }
    }

    @Override
    protected void onPostExecute(Integer success) {
        super.onPostExecute(success);
        Context ctx = this.ctx.get();
        if (success==1){
            EnterFactoryTask enterFactory = new EnterFactoryTask(userId, factory, ctx);
            enterFactory.execute();
        }else {
            Toast.makeText(ctx, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }
}
