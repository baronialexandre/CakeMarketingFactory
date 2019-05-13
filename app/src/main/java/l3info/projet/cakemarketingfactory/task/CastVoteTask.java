package l3info.projet.cakemarketingfactory.task;

import android.app.Dialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.utils.Contents;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CastVoteTask extends AsyncTask<String, Void, Boolean>
{
    private static final String TAG = "CastVoteTask";

    private WeakReference<Dialog> dialogWeakReference;

    private int productId;
    private long userId;

    public CastVoteTask(int productId, long userId, Dialog dialog)
    {
        this.productId = productId;
        this.userId = userId;
        this.dialogWeakReference = new WeakReference<>(dialog);
    }

    @Override
    protected Boolean doInBackground(String... params)
    {
        try
        {
            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL_CAST_VOTE_TASK", Contents.API_URL + Contents.CAST_VOTE_URL + "?apipass=" + Contents.API_PASS + "&userId=" + userId + "&productId=" + productId);
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.CAST_VOTE_URL + "?apipass=" + Contents.API_PASS + "&userId=" + userId + "&productId=" + productId)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null)
            {
                rawJson = response.body().string();
            }
            Log.i("BANDOL_CAST_VOTE_TASK", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);
            return jsonObj.getBoolean("voteCheck");
        }
        catch(IOException | JSONException e)
        {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean voteCheck)
    {
        super.onPostExecute(voteCheck);
        final Dialog dialog = dialogWeakReference.get();
        TextView marketVoteCastAlert = dialog.findViewById(R.id.marketVoteCastAlert);
        if(!voteCheck)
            marketVoteCastAlert.setText(R.string.vote_already_cast);
        else
            marketVoteCastAlert.setText(R.string.vote_cast);
    }
}
