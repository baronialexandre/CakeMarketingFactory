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

import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.FunctionUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetMoneyTask extends AsyncTask<String, Void, Integer> {
    private static final String TAG = "GetMoneyTask";

    private final long userId;
    private final WeakReference<Context> ctx;
    private final WeakReference<TextView> money;

    public GetMoneyTask(long userId, TextView money, Context ctx) {
        this.userId = userId;
        this.money = new WeakReference<>(money);
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    protected Integer doInBackground(String... params) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.GET_MONEY_URL + "?apipass=" + Contents.API_PASS + "&userId="+userId)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            JSONObject jsonObj = new JSONObject(rawJson);

            return jsonObj.getInt("money");
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return 42;
        }
    }

    @Override
    protected void onPostExecute(Integer userMoney) {
        super.onPostExecute(userMoney);
        Context ctx = this.ctx.get();
        TextView money = this.money.get();
        String moneyText = FunctionUtil.cashShortner(userMoney)+" $";
        money.setText(moneyText);
        SharedPreferences shr = ctx.getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = shr.edit();
        ed.putInt("money",userMoney);
        ed.apply();
    }
}
