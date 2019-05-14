package l3info.projet.cakemarketingfactory.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import l3info.projet.cakemarketingfactory.model.Factory;
import l3info.projet.cakemarketingfactory.model.Line;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BuyFactoryTask extends AsyncTask<String, Void, Factory>{

    private static final String TAG = "EnterFactoryTask";

    private final long userId;
    private final int factorySpot;
    private final int productId;
    private final int money;
    private final WeakReference<Context> ctx;

    public BuyFactoryTask(long userId, int factorySpot, int productId, int money, Context ctx) {
        this.userId = userId;
        this.factorySpot = factorySpot;
        this.productId = productId;
        this.money = money;
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    protected Factory doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL_BUY_FACTORYTSK", "OKHTTP");
            Log.i("BANDOL_BUY_FACTORYTSK", Contents.API_URL + Contents.BUY_FACTORY_URL + "?userId=" + userId + "&factorySpot=" + factorySpot + "&score=" + money  + "&productId=" + productId + "&apipass=" + Contents.API_PASS);
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.BUY_FACTORY_URL + "?userId=" + userId + "&factorySpot=" + factorySpot + "&score=" + money  + "&productId=" + productId + "&apipass=" + Contents.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.i("BANDOL_BUY_FACTORYTSK", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);


            Factory factory = new Factory(null);

            factory.setCapacityLevel(jsonObj.getInt("capacityLevel"));
            JSONArray arr = jsonObj.getJSONArray("stocks");
            for (int i=0; i < arr.length(); i++) {
                factory.getCurrentStocks().set(arr.getJSONObject(i).getInt("productId"),arr.getJSONObject(i).getInt("quantity"));
            }

            JSONArray arr2 = jsonObj.getJSONArray("lines");
            for (int i=0; i < arr2.length(); i++) {
                factory.getLines().set(arr2.getJSONObject(i).getInt("lineSlot")-1,
                        new Line(arr2.getJSONObject(i).getInt("beltLevel"),
                                arr2.getJSONObject(i).getInt("robotLevel"),
                                arr2.getJSONObject(i).getInt("ovenLevel"),
                                arr2.getJSONObject(i).getInt("productId")));
            }
            Log.i("BANDOL_BUY_FACTORYTSK", factory.toString());

            //userId = jsonObj.getLong("userId");
            return factory;
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Factory factory) {/*
        super.onPostExecute(factory);
        Context ctx = this.ctx.get();
        //Redirect to main user activity
        Intent intent;
        intent = new Intent(ctx, FactoryActivity.class);
        intent.putExtra("factory", factory);
        ctx.startActivity(intent);*/
    }
}
