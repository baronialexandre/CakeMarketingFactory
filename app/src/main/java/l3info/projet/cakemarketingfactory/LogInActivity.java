package l3info.projet.cakemarketingfactory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import l3info.projet.cakemarketingfactory.task.AuthenticationTask;

public class LogInActivity extends AppCompatActivity {

    Context context;

    Button bRegister, bConnection;
    EditText etLogin, etPassword;
    TextView feedbackTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        bRegister = findViewById(R.id.bMainRegister);
        bConnection = findViewById(R.id.bMailConnection);
        etLogin = findViewById(R.id.etMainLogin);
        etPassword = findViewById(R.id.etMainPassword);
        feedbackTextView = findViewById(R.id.login_feedback_message);
        bConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Authentification Task
                AuthenticationTask task = new AuthenticationTask(etLogin.getText().toString(), etPassword.getText().toString(), feedbackTextView, context);
                task.execute();


                //Changer d'activity
                Intent intentApp = new Intent(LogInActivity.this, LoadingActivity.class);
                LogInActivity.this.startActivity(intentApp);
            }
        });

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(LogInActivity.this, RegisterActivity.class);
                LogInActivity.this.startActivity(intentApp);
            }
        });

    }
}
