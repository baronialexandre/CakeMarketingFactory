package l3info.projet.cakemarketingfactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Changer d'activity
        Intent intentApp = new Intent(LoadingActivity.this, WorldActivity.class);
        LoadingActivity.this.startActivity(intentApp);
    }
}
