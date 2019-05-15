package l3info.projet.cakemarketingfactory.task;

import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Locale;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetStockTask extends AsyncTask<String, Void, Boolean>
{
    private static final String TAG = "GetStockTask";

    private long userId;
    private int[] productStock;
    private WeakReference<Dialog> dialogWeakReference;

    public GetStockTask(long userId, Dialog dialog)
    {
        this.userId = userId;
        productStock = new int[3];
        this.dialogWeakReference = new WeakReference<>(dialog);
    }

    @Override
    protected Boolean doInBackground(String... strings)
    {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.GET_STOCK_URL + "?apipass=" + Contents.API_PASS + "&userId="+userId)
                    .build();
            Log.i("BANDOL_GET_STOCK_TASK", Contents.API_URL + Contents.GET_STOCK_URL + "?apipass=" + Contents.API_PASS + "&userId="+userId);

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.i("BANDOL_GET_STOCK_TASK", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);
            JSONArray stocks = jsonObj.getJSONArray("stocks");

            for(int i = 0; i < stocks.length(); i++)
            {
                JSONObject stock = stocks.getJSONObject(i);

                int productId = stock.getInt("productId");
                int quantity = stock.getInt("quantity");
                productStock[productId] = quantity;
            }
            return true;
        }
        catch (IOException | JSONException e)
        {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean getCheck)
    {
        super.onPostExecute(getCheck);
        Dialog dialog = dialogWeakReference.get();
        TextView marketSellAlert = dialog.findViewById(R.id.marketSellAlert);

        TextView popupSellNbCookie = dialog.findViewById(R.id.popupSellNbCookie);
        TextView popupSellNbCupcake = dialog.findViewById(R.id.popupSellNbCupcake);
        TextView popupSellNbDonut = dialog.findViewById(R.id.popupSellNbDonut);

        if(getCheck)
        {
            popupSellNbCookie.setText(String.format(Locale.ROOT, "(%d)", productStock[0]));
            popupSellNbCupcake.setText(String.format(Locale.ROOT, "(%d)", productStock[1]));
            popupSellNbDonut.setText(String.format(Locale.ROOT, "(%d)", productStock[2]));
        }
        else
        {
            marketSellAlert.setText(R.string.market_sell_error);
        }
    }
}
