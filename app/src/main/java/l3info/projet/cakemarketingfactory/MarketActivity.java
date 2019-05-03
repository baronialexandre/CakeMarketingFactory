package l3info.projet.cakemarketingfactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MarketActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        ImageView marketBack = findViewById(R.id.marketBack);
        marketBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(MarketActivity.this, WorldActivity.class);
                MarketActivity.this.startActivity(intentApp);
            }
        });
    }
}
