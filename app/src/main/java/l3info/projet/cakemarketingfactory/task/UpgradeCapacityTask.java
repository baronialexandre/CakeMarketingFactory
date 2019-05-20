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
import l3info.projet.cakemarketingfactory.activity.manager.SoundManager;
import l3info.projet.cakemarketingfactory.model.Factory;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UpgradeCapacityTask extends AsyncTask<String, Void, Boolean>
{
    private static final String TAG = "CastVoteTask";

    private WeakReference<Context> contextWeakReference;
    private SoundManager soundManager;
    private WeakReference<TextView> capacityViewWeakReference;

    private Factory factory;
    private long userId;
    private int level;

    private long requiredScore;
    private boolean notEnoughScore;

    public UpgradeCapacityTask(Factory factory, Context ctx, TextView capacityView, long userId, int level)
    {
        this.contextWeakReference = new WeakReference<>(ctx);
        soundManager = new SoundManager(ctx);
        this.capacityViewWeakReference = new WeakReference<>(capacityView);

        this.factory = factory;
        this.userId = userId;
        this.level = level;
        requiredScore = 0;
        notEnoughScore = false;
    }

    @Override
    protected Boolean doInBackground(String... strings)
    {
        try
        {
            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL_UP_CAPACITY_TASK", Contents.API_URL + Contents.UP_CAPACITY_URL + "?apipass=" + Contents.API_PASS + "&userId=" + userId + "&factorySpot=" + factory.getFactorySpot());
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.UP_CAPACITY_URL + "?apipass=" + Contents.API_PASS + "&userId=" + userId + "&factorySpot=" + factory.getFactorySpot())
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null)
            {
                rawJson = response.body().string();
            }

            Log.i("BANDOL_UP_CAPACITY_TASK", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);

            if(jsonObj.has("requiredScore"))
            {
                notEnoughScore = true;
                requiredScore = jsonObj.getLong("requiredScore");
            }
            return jsonObj.getBoolean("success");
        }
        catch(IOException | JSONException e)
        {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean success)
    {
        super.onPostExecute(success);

        Context ctx = contextWeakReference.get();
        TextView capacityView = capacityViewWeakReference.get();

        if(success)
        {
            soundManager.playSoundIn();
            factory.setCapacityLevel( level+1 );
            long totalStock = factory.getCurrentStocks().get(0)+factory.getCurrentStocks().get(1)+factory.getCurrentStocks().get(2);
            capacityView.setText(ctx.getString(R.string.factory_stock_display, totalStock, (factory.getCapacityLevel()+1)*1000));
        }
        else if(notEnoughScore)
        {
            soundManager.playSoundOut();
            Toast.makeText(ctx, ctx.getString(R.string.purchase_not_enough_score, requiredScore), Toast.LENGTH_LONG).show();
        }
        else
        {
            soundManager.playSoundOut();
            Toast.makeText(ctx, ctx.getString(R.string.purchase_failure), Toast.LENGTH_LONG).show();
        }
    }
}
