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

public class LoginActivity extends AppCompatActivity {

    Context context;


    Button register, connection;
    EditText loginUsername, loginPassword;
    TextView feedbackTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        register = findViewById(R.id.loginRegister);
        connection = findViewById(R.id.loginConnection);
        loginUsername = findViewById(R.id.loginUsername);
        loginPassword = findViewById(R.id.loginPassword);
        feedbackTextView = findViewById(R.id.loginFeedbackMessage);

        connection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Authentification Task
                AuthenticationTask task = new AuthenticationTask(loginUsername.getText().toString(), loginPassword.getText().toString(), feedbackTextView, context);
                task.execute();


                //Changer d'activity
                //Intent intentApp = new Intent(LoginActivity.this, LoadingActivity.class);
                //LoginActivity.this.startActivity(intentApp);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(LoginActivity.this, RegistrationActivity.class);
                LoginActivity.this.startActivity(intentApp);
            }
        });


    }
}
