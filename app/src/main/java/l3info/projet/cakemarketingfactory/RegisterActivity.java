package l3info.projet.cakemarketingfactory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button registerCancel = findViewById(R.id.registerCancel);
        registerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(RegisterActivity.this, LogInActivity.class);
                RegisterActivity.this.startActivity(intentApp);
            }
        });

        Button registerValidate = findViewById(R.id.registerValidate);
        registerValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(RegisterActivity.this, LogInActivity.class);
                RegisterActivity.this.startActivity(intentApp);
            }
        });
    }
}
