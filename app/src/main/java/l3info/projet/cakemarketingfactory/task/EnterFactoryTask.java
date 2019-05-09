package l3info.projet.cakemarketingfactory.task;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.FactoryActivity;
import l3info.projet.cakemarketingfactory.activity.WorldActivity;
import l3info.projet.cakemarketingfactory.modele.Factory;
import l3info.projet.cakemarketingfactory.modele.Line;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EnterFactoryTask extends AsyncTask<String, Void, Factory>{

    private static final String TAG = "EnterFactoryTask";

    private final Factory factory;
    private long userId;
    private final WeakReference<Context> ctx;

    public EnterFactoryTask(long userId, Factory factory, Context ctx) {
        this.userId = userId;
        this.factory = factory;
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    protected Factory doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL_ENTER_FACTORYTSK", "OKHTTP");
            Log.i("BANDOL_ENTER_FACTORYTSK", Contents.API_URL + Contents.ENTER_FACTORY_URL + "?userId=" + userId + "&factorySpot=" + factory.getFactorySpot() + "&apipass=" + Contents.API_PASS);
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.ENTER_FACTORY_URL + "?userId=" + userId + "&factorySpot=" + factory.getFactorySpot() + "&apipass=" + Contents.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.i("BANDOL_ENTER_FACTORYTSK", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);

            factory.setCapacityLevel(jsonObj.getInt("capacityLevel"));
            JSONArray arr = jsonObj.getJSONArray("stocks");
            for (int i=0; i < arr.length(); i++) {
                factory.getCurrentStocks().set(arr.getJSONObject(i).getInt("productId")-1,arr.getJSONObject(i).getInt("quantity"));
            }

            JSONArray arr2 = jsonObj.getJSONArray("lines");
            for (int i=0; i < arr2.length(); i++) {
                factory.getLines().set(arr2.getJSONObject(i).getInt("lineSlot")-1,
                        new Line(arr2.getJSONObject(i).getInt("beltLevel"),
                                arr2.getJSONObject(i).getInt("robotLevel"),
                                arr2.getJSONObject(i).getInt("ovenLevel"),
                                arr2.getJSONObject(i).getInt("productId")));
            }
            Log.i("BANDOL_ENTER_FACTORYTSK", factory.toString());

            userId = jsonObj.getLong("userId");
            return factory;
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return factory;
        }
    }

    @Override
    protected void onPostExecute(Factory factory) {
        super.onPostExecute(factory);
        Context ctx = this.ctx.get();
        //Redirect to main user activity
        Intent intent;
        intent = new Intent(ctx, FactoryActivity.class);
        intent.putExtra("factory", factory);
        ctx.startActivity(intent);
    }
}
