package l3info.projet.cakemarketingfactory.task;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import l3info.projet.cakemarketingfactory.activity.MarketActivity;
import l3info.projet.cakemarketingfactory.model.Demand;
import l3info.projet.cakemarketingfactory.model.Market;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EnterMarketTask extends AsyncTask<String, Void, Market>
{
    private static final String TAG = "EnterMarketTask";

    private final WeakReference<Context> ctx;

    public EnterMarketTask(Context ctx)
    {
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    protected Market doInBackground(String... strings)
    {
        try
        {
            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL_MARKET_TASK", Contents.API_URL + Contents.ENTER_MARKET_URL + "&apipass=" + Contents.API_PASS);

            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.ENTER_MARKET_URL + "?apipass=" + Contents.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }

            Log.i("BANDOL_MARKET_TASK", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);

            JSONArray productsArray = jsonObj.getJSONArray("demands");

            Market market = new Market();

            for(int i = 0; i < productsArray.length(); i++)
            {
                JSONObject product = productsArray.getJSONObject(i);
                int productId = product.getInt("productId");

                JSONArray demandsArray = product.getJSONArray("data");
                for(int j = 0; j < demandsArray.length(); j++)
                {
                    JSONObject demand = demandsArray.getJSONObject(j);
                    String demandDate = demand.getString("demandDate");
                    int price = demand.getInt("price");
                    market.addDemand(productId, new Demand(demandDate, price));
                }
            }
            market.order();

            return market;
        }
        catch(IOException | JSONException e)
        {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return null;
        }

    }

    @Override
    protected void onPostExecute(Market market)
    {
        super.onPostExecute(market);
        Context ctx = this.ctx.get();

        Intent intent;
        intent = new Intent(ctx, MarketActivity.class);
        intent.putExtra("market", market);
        ctx.startActivity(intent);
    }
}
