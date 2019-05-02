package l3info.projet.cakemarketingfactory;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Context context;

    Button bSignUp, bSignIn;
    EditText etLogin, etPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        bSignUp = findViewById(R.id.bMainSignUp);
        bSignIn = findViewById(R.id.bMailSignIn);
        etLogin = findViewById(R.id.etMainLogin);
        etPassword = findViewById(R.id.etMainPassword);


        bSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Changer d'activity
                Intent intentApp = new Intent(MainActivity.this, LoadingActivity.class);
                MainActivity.this.startActivity(intentApp);

            }
        });

        bSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
