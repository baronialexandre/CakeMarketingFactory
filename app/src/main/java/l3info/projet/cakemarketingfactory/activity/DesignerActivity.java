package l3info.projet.cakemarketingfactory.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import l3info.projet.cakemarketingfactory.R;

public class DesignerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer);

        ImageView back = findViewById(R.id.designerBack);
        back.setOnClickListener(v -> {
            finish();
        });

    }
}
