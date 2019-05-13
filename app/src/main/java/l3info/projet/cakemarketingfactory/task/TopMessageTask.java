package l3info.projet.cakemarketingfactory.task;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.DesignerActivity;
import l3info.projet.cakemarketingfactory.model.TopPlayer;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@SuppressLint("StaticFieldLeak")
public class TopMessageTask extends AsyncTask<String, Void, ArrayList<TopPlayer>> {
//    private static final String TAG = "TopMessagesTask";

    private final WeakReference<Context> ctx;
    private int userId;
    private RelativeLayout buttonArea;

    public TopMessageTask(int userId, RelativeLayout buttonArea, Context ctx) {
        this.ctx = new WeakReference<>(ctx);
        this.userId = userId;
        this.buttonArea = buttonArea;
    }

    @Override
    protected ArrayList<TopPlayer> doInBackground(String... params) {
        ArrayList<TopPlayer> topPlayers = new ArrayList<>();
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.LAST_WEEK_TOP_DESIGN + "?apipass=" + Contents.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }

            JSONObject jsonObj = new JSONObject(rawJson);

            JSONArray jsonArray = jsonObj.getJSONArray("lastWeekTop");

            for(int i=0; i<jsonArray.length(); i++)
            {
                JSONObject mail = jsonArray.getJSONObject(i);
                int userId = mail.getInt("userId");
                int rank = mail.getInt("rank");
                topPlayers.add(new TopPlayer(userId, rank));
            }

            return topPlayers;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
            topPlayers.add(new TopPlayer(0,0));
            return topPlayers;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<TopPlayer> topPlayers) {
        super.onPostExecute(topPlayers);
        Context ctx = this.ctx.get();
        for(TopPlayer topPlayer : topPlayers)
        {
            if(userId == topPlayer.getUserId())
            {
                Button designer = new Button(this.ctx.get());
                designer.setText(ctx.getResources().getString(R.string.designer));
                designer.setTextColor(ctx.getResources().getColor(R.color.buttonGreenText));
                designer.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                designer.setPadding(20,10, 20, 20);
                designer.setBackground(ctx.getResources().getDrawable(R.drawable.green_button_selector));
                designer.setVisibility(View.VISIBLE);
                buttonArea.addView(designer);

                designer.setOnClickListener(v -> {
                    Intent intent = new Intent(ctx, DesignerActivity.class);
                    ctx.startActivity(intent);
                });
                return;
            }
        }
        //topPlayers;
    }
}
