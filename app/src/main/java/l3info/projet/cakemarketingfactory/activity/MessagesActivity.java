package l3info.projet.cakemarketingfactory.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.adapter.MessageAdapter;
import l3info.projet.cakemarketingfactory.activity.manager.SoundManager;
import l3info.projet.cakemarketingfactory.model.MessageItem;
import l3info.projet.cakemarketingfactory.task.TopMessageTask;
import l3info.projet.cakemarketingfactory.utils.Contents;

@SuppressWarnings("FieldCanBeLocal")
public class MessagesActivity extends AppCompatActivity {

    private ArrayList<MessageItem> messageItems;

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    Context ctx;
    SoundManager soundManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        ctx = this;
        soundManager = new SoundManager(this);

        ImageView ivBack = findViewById(R.id.messagesBack);
        ivBack.setOnClickListener(view -> {
            soundManager.playSoundOut();
            //Changer d'activity
            MessagesActivity.this.finish(); //dépile la stack d'activity
        });

        initMessageList();

    }


    public void initMessageList() {
        messageItems = (ArrayList<MessageItem>) getIntent().getSerializableExtra("messageItems");
        buildRecyclerview();
    }

    public void buildRecyclerview() {
        recyclerView = findViewById(R.id.messages);
        layoutManager = new LinearLayoutManager(this);
        adapter = new MessageAdapter(messageItems);

        //cela évite du calcul innutile
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(position -> {
            soundManager.playSoundIn();
            itemRead(position); //lu
            messageClicked(position); //open large
        });
    }

    public void itemRead(int position) {
        messageItems.get(position).setImageResource(R.drawable.message_letter_open);
        adapter.notifyItemChanged(position);
    }

    public void messageClicked(int position) {
        MessageItem myMessage = messageItems.get(position);
        String title = myMessage.getTitle();
        String text = myMessage.getMessage();
        String date = myMessage.getSendDate();

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_message);

        ImageView ivPopupMessageClose = dialog.findViewById(R.id.popupMessageClose);
        ivPopupMessageClose.setOnClickListener(v -> {
            soundManager.playSoundOut();
            dialog.dismiss();
        });

        TextView tvPopupMessageTitle = dialog.findViewById(R.id.popupMessageTitle);
        tvPopupMessageTitle.setText(title);
        TextView tvPopupMessageText = dialog.findViewById(R.id.popupMessageText);
        tvPopupMessageText.setText(text);
        TextView tvPopupMessageDate = dialog.findViewById(R.id.popupMessageDate);

        tvPopupMessageDate.setText(date);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur

        if(myMessage.getMailType().equals("resultMessage"))
        {
            SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
            int userId = (int) shr.getLong("userId",0L);

            RelativeLayout buttonArea = dialog.findViewById(R.id.popupMessageButtonArea);
            TopMessageTask topMessageTask = new TopMessageTask(userId, buttonArea, ctx);
            topMessageTask.execute();
        }
        dialog.setCancelable(false);
        dialog.show();
    }

}
