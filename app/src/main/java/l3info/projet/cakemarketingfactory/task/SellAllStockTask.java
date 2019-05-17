package l3info.projet.cakemarketingfactory.task;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.FunctionUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.provider.Settings.Secure.getString;


public class SellAllStockTask extends AsyncTask<String, Void, Boolean>
{
    private long userId;
    private int productId;
    private long earnedScore;
    private long stockSold;

    private WeakReference<Dialog> dialogWeakReference;
    private WeakReference<Context> contextWeakReference;
    private WeakReference<TextView> scoreViewWeakReference;

    public SellAllStockTask(long userId, int productId, Dialog dialog, Context ctx, TextView scoreView)
    {
        this.userId = userId;
        this.productId = productId;
        this.dialogWeakReference = new WeakReference<>(dialog);
        this.contextWeakReference = new WeakReference<>(ctx);
        this.scoreViewWeakReference = new WeakReference<>(scoreView);
    }

    @Override
    protected Boolean doInBackground(String... strings)
    {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.SELL__ALL_STOCK_URL + "?apipass=" + Contents.API_PASS + "&userId="+userId+ "&productId=" + productId)
                    .build();
            Log.i("BANDOL_SELL_ALL_TASK", Contents.API_URL + Contents.SELL__ALL_STOCK_URL + "?apipass=" + Contents.API_PASS + "&userId="+userId + "&productId=" + productId);
            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.i("BANDOL_SELL_ALL_TASK", rawJson);

            JSONObject jsonObj = new JSONObject(rawJson);
            if(jsonObj.has("earnedScore")) earnedScore = jsonObj.getLong("earnedScore");
            if(jsonObj.has("stockSold")) stockSold = jsonObj.getLong("stockSold");
            return jsonObj.getBoolean("checkSale");
        }
        catch (IOException | JSONException e)
        {
            return false;
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onPostExecute(Boolean saleCheck)
    {
        super.onPostExecute(saleCheck);
        Dialog dialog = dialogWeakReference.get();
        Context ctx = contextWeakReference.get();
        TextView marketSellAlert = dialog.findViewById(R.id.marketSellAlert);
        if(saleCheck)
        {
            marketSellAlert.setText(ctx.getString(R.string.sell_all_info, stockSold,  FunctionUtil.idToProduct(productId),earnedScore));
            TextView popupSellNb;
            switch(productId)
            {
                case 1:
                    popupSellNb = dialog.findViewById(R.id.popupSellNbCupcake);
                    break;
                case 2:
                    popupSellNb = dialog.findViewById(R.id.popupSellNbDonut);
                    break;
                default:
                    popupSellNb = dialog.findViewById(R.id.popupSellNbCookie);
            }
            popupSellNb.setText(R.string.number);

            GetScoreTask getScore = new GetScoreTask(userId, scoreViewWeakReference.get(), ctx);
            getScore.execute();
        }
        else
        {
            marketSellAlert.setText(R.string.market_sell_failed);
        }
    }
}
