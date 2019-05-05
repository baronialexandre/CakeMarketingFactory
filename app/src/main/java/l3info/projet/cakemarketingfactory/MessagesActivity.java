package l3info.projet.cakemarketingfactory;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Objects;

public class MessagesActivity  extends AppCompatActivity {

    private ArrayList<MessageItem> messageItems;

    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        ImageView ivBack = findViewById(R.id.messagesBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(MessagesActivity.this, WorldActivity.class);
                MessagesActivity.this.startActivity(intentApp);
            }
        });

        initMessageList();

    }


    public void initMessageList()
    {
        createMessageList();
        buildRecyclerview();
    }

    String randomTextGenerator(int sizeMax)
    {
        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder string = new StringBuilder();
        double size = Math.random()*sizeMax;
        for(int i=0; i < size; i++)
        {
            if(Math.random()<0.3 && string.length() > 1)
                string.append(" ");
            else
                string.append(alpha.charAt((int)(Math.random()*alpha.length())));
        }
        return string.toString();
    }

    public void createMessageList() {
        messageItems= new ArrayList<>();

        double size = Math.random()*100;
        for(int i=0; i < size; i++)
        {
            messageItems.add(new MessageItem(R.drawable.message_letter, randomTextGenerator(10), randomTextGenerator(1000), "03/05/2000"));
        }
    }
    public void buildRecyclerview(){
        recyclerView = findViewById(R.id.messages);
        layoutManager = new LinearLayoutManager(this);
        adapter = new MessageAdapter(messageItems);

        //cela évite du calcul innutile
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new MessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                itemRead(position); //lu
                itemOpen(position); //open large
            }
        });
    }

    public void itemRead(int position) {
        messageItems.get(position).setImageResource(R.drawable.message_letter_open);
        adapter.notifyItemChanged(position);
    }

    public void itemOpen(int position) {
        String title = messageItems.get(position).getTitle();
        String text = messageItems.get(position).getMessage();
        String date = messageItems.get(position).getDate();

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.popup_message);

        ImageView ivPopupMessageClose = dialog.findViewById(R.id.popupMessageClose);
        ivPopupMessageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView tvPopupMessageTitle = dialog.findViewById(R.id.popupMessageTitle);
        tvPopupMessageTitle.setText(title);
        TextView tvPopupMessageText = dialog.findViewById(R.id.popupMessageText);
        tvPopupMessageText.setText(text);
        TextView tvPopupMessageDate = dialog.findViewById(R.id.popupMessageDate);

        tvPopupMessageDate.setText(date);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent); //contours couleur
        dialog.setCancelable(false);
        dialog.show();

    }

}
