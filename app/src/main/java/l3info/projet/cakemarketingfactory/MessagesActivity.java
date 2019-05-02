package l3info.projet.cakemarketingfactory;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MessagesActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

//        RecyclerView rvMessages = findViewById(R.id.rvMessages);
//        rvMessages.setLayoutManager(new LinearLayoutManager(this));
//        rvMessages.setAdapter(new MyAdapter()); //creer l'adapter, check projet du 05/08/2016 s'il est pas deprecated ;)
    }
}
