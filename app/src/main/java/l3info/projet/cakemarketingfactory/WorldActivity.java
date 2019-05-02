package l3info.projet.cakemarketingfactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class WorldActivity  extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);

        ImageView ivWorldFactory1 = findViewById(R.id.ivWorldFactory1);
        ivWorldFactory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(WorldActivity.this, FactoryActivity.class);
                WorldActivity.this.startActivity(intentApp);
            }
        });

        ImageView ivMarket = findViewById(R.id.ivWorldMarket);
        ivMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(WorldActivity.this, MarketActivity.class);
                WorldActivity.this.startActivity(intentApp);
            }
        });

        ImageView ivMessages = findViewById(R.id.ivWorldLetter);
        ivMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(WorldActivity.this, MessagesActivity.class);
                WorldActivity.this.startActivity(intentApp);
            }
        });


    }
}
