package l3info.projet.cakemarketingfactory.task;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import l3info.projet.cakemarketingfactory.activity.WorldActivity;
import l3info.projet.cakemarketingfactory.modele.World;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EnterWorldTask extends AsyncTask<String, Void, World>{

    private static final String TAG = "EnterWorldTask";

    private final long userId;
    private final WeakReference<Context> ctx;
    private ArrayList<Integer> factorySpots = new ArrayList<>();

    @SuppressWarnings("WeakerAccess")
    public EnterWorldTask(long userId, Context ctx) {
        this.userId = userId;
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    protected World doInBackground(String... params) {
        try {

            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL_ENTER_WORLD_TASK", Contents.API_URL + Contents.ENTER_WORLD_URL + "?userId=" + userId + "&apipass=" + Contents.API_PASS);
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.ENTER_WORLD_URL + "?userId=" + userId + "&apipass=" + Contents.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.i("BANDOL_ENTER_WORLD_TASK", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);

            JSONArray arr = jsonObj.getJSONArray("factorySpots");
            for (int i=0; i < arr.length(); i++) {
                factorySpots.add(arr.getInt(i));
            }
            Log.i("BANDOL_ENTER_WORLD_TASK", factorySpots.toString());
            return new World(factorySpots);
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return new World(factorySpots);
        }
    }

    @Override
    protected void onPostExecute(World world) {
        super.onPostExecute(world);
        Context ctx = this.ctx.get();
        //Redirect to main user activity
        Intent intent;
        intent = new Intent(ctx, WorldActivity.class);
        //Ne permet pas le retour sur l'activité précédente !
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("world", world);
        ctx.startActivity(intent);
    }
}
