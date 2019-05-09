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

import l3info.projet.cakemarketingfactory.activity.MessagesActivity;
import l3info.projet.cakemarketingfactory.model.MessageItem;
import l3info.projet.cakemarketingfactory.model.Messages;
import l3info.projet.cakemarketingfactory.utils.Contents;
import l3info.projet.cakemarketingfactory.utils.DateUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EnterMessagesTask extends AsyncTask<String, Void, ArrayList<MessageItem>> {
    private static final String TAG = "EnterMessagesTask";

    private final WeakReference<Context> ctx;

    public EnterMessagesTask(Context ctx) {
        this.ctx = new WeakReference<>(ctx);
    }

    @Override
    protected ArrayList<MessageItem> doInBackground(String... params) {
        try {

            OkHttpClient client = new OkHttpClient();
            Log.i("BANDOL_MESSAGES_TASK", Contents.API_URL + Contents.ENTER_MESSAGES_URL + "&apipass=" + Contents.API_PASS);
            Request request = new Request.Builder()
                    .url(Contents.API_URL + Contents.ENTER_MESSAGES_URL + "?apipass=" + Contents.API_PASS)
                    .build();

            Response response = client.newCall(request).execute();
            String rawJson = null;
            if (response.body() != null) {
                rawJson = response.body().string();
            }
            Log.i("BANDOL_MESSAGES_TASK", rawJson);
            JSONObject jsonObj = new JSONObject(rawJson);

            JSONArray jsonArray = jsonObj.getJSONArray("mails");

            ArrayList<MessageItem> messageItems = new ArrayList<>();

            for(int i=0; i<jsonArray.length(); i++)
            {
                JSONObject mail = jsonArray.getJSONObject(i);
                int mailId = mail.getInt("mailId");
                String sendDate = mail.getString("sendDate");
                String title = mail.getString("title");
                String message = mail.getString("message");
                String mailType = mail.getString("mailType");
                String adminName = mail.getString("adminName");
                messageItems.add(new MessageItem(mailId, sendDate, title, message, mailType, adminName));
            }

            return messageItems;
        } catch (IOException | JSONException e) {
            Log.e(TAG, "Error while authenticating ... : " + e.getMessage(), e);
            ArrayList<MessageItem> messageItems = new ArrayList<>();
            messageItems.add(new MessageItem(-1, DateUtil.getCurrent("JJ/MM/YYYY"), "Error", "Communication error...","adminMessage", "System"));
            return messageItems;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<MessageItem> messageItems) {
        super.onPostExecute(messageItems);
        Context ctx = this.ctx.get();


        //Redirect to main user activity
        Intent intent;
        intent = new Intent(ctx, MessagesActivity.class);
        intent.putExtra("messageItems", messageItems);
        ctx.startActivity(intent);
    }
}
