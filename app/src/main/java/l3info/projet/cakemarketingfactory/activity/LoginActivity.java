package l3info.projet.cakemarketingfactory.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.task.AuthenticationTask;
import l3info.projet.cakemarketingfactory.utils.Contents;

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

        //checking if username and password in sharedpreferences and using it
        SharedPreferences shr = getSharedPreferences(Contents.SHRD_PREF, Context.MODE_PRIVATE);
        String username = shr.getString("username","");
        String password = shr.getString("password","");
        if (password != null && username != null && !username.isEmpty() && !password.isEmpty()) {
            loginUsername.setText(username);
            loginPassword.setText(password);
            AuthenticationTask task = new AuthenticationTask(loginUsername.getText().toString(), loginPassword.getText().toString(), feedbackTextView, context);
            task.execute();
        }

        connection.setOnClickListener(view -> {
            //Authentification Task
            AuthenticationTask task = new AuthenticationTask(loginUsername.getText().toString(), loginPassword.getText().toString(), feedbackTextView, context);
            task.execute();

            //Changer d'activity
            //Intent intentApp = new Intent(LoginActivity.this, LoadingActivity.class);
            //LoginActivity.this.startActivity(intentApp);
        });

        register.setOnClickListener(view -> {
            //Changer d'activity
            Intent intentApp = new Intent(LoginActivity.this, RegistrationActivity.class);
            LoginActivity.this.startActivity(intentApp);
        });


    }
}
