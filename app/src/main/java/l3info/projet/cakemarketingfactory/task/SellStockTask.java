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


import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.model.Factory;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.FunctionUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SellStockTask extends AsyncTask<String, Void, Integer> {
    private final long userId;
    private WeakReference<Context> ctx;
    private Factory factory;
    private int productId;
    private long scoreEarned;
    private long stockSold;
    private WeakReference<TextView> stockView;
    private WeakReference<TextView> score;
    private WeakReference<TextView> allStock;
    private WeakReference<TextView> marketSellAlert;

    public SellStockTask(long userId, Factory factory, int productId, Context ctx, TextView stock, TextView score, TextView allStock, TextView marketSellAlert) {
        this.userId = userId;
        this.factory = factory;
        this.productId = productId;
        this.ctx = new WeakReference<>(ctx);
        this.stockView=new WeakReference<>(stock);
        this.score=new WeakReference<>(score);
        this.allStock=new WeakReference<>(allStock);
        this.marketSellAlert = new WeakReference<>(marketSellAlert);
    }

    @Override
    protected Integer doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.SELL_STOCK_URL + "?apipass=" + Contents.API_PASS + "&userId="+userId+ "&factorySpot=" + factory.getFactorySpot() + "&productId=" + productId)
                    .build();
            Log.i("BANDOL_SELL_STOCK_TASK", Contents.API_URL + Contents.SELL_STOCK_URL + "?apipass=" + Contents.API_PASS + "&userId="+userId+ "&factorySpot=" + factory.getFactorySpot() + "&productId=" + productId);
            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.i("BANDOL_SELL_STOCK_TASK", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);
            if(jsonObj.has("scoreEarned")) scoreEarned = jsonObj.getLong("scoreEarned");
            if(jsonObj.has("stockSold")) stockSold = jsonObj.getLong("stockSold");
            return jsonObj.getInt("test");
        } catch (IOException | JSONException e) {
            return 0;
        }
    }

    @Override
    protected void onPostExecute(Integer test) {
        super.onPostExecute(test);
        Context ctx = this.ctx.get();
        int productId = this.productId;
        if (test==1){
            factory.getCurrentStocks().set(productId,0);
            String stockText = 0+"";
            String allStockText = factory.getCurrentStocks().get(0)+factory.getCurrentStocks().get(1)+factory.getCurrentStocks().get(2)+"/"+(factory.getCapacityLevel()+1)*1000;
            TextView stock = stockView.get();
            stock.setText(stockText);
            allStock.get().setText(allStockText);
            marketSellAlert.get().setText(ctx.getString(R.string.sell_all_info, stockSold,  FunctionUtil.idToProduct(productId),scoreEarned));
            GetScoreTask getScore = new GetScoreTask(userId, score.get(), ctx);
            getScore.execute();
        }
    }
}
