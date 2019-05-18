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

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.FunctionUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetLinePriceTask extends AsyncTask<String, Void, Integer> {

    private final int factorySpot;
    private final int lineSlot;
    private final WeakReference<Context> ctx;
    private final WeakReference<TextView> score;

    public GetLinePriceTask(int factorySpot, int lineSlot, TextView score, Context ctx) {
        this.factorySpot = factorySpot;
        this.lineSlot = lineSlot;
        this.score = new WeakReference<>(score);
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    protected Integer doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.GET_LINE_PRICE_URL + "?apipass=" + Contents.API_PASS + "&factorySpot="+factorySpot + "&lineSlot="+lineSlot)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            JSONObject jsonObj = new JSONObject(rawJson);

            return jsonObj.getInt("price");
        } catch (IOException | JSONException e) {
            return 666;
        }
    }

    @Override
    protected void onPostExecute(Integer price) {
        super.onPostExecute(price);
        Context ctx = this.ctx.get();
        TextView score = this.score.get();
        String text = "Cette ligne coûte\n"+price+" $";
        score.setText(text);
    }
}