package l3info.projet.cakemarketingfactory.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.manager.LanguageManager;
import l3info.projet.cakemarketingfactory.activity.manager.SoundManager;
import l3info.projet.cakemarketingfactory.task.AuthenticationTask;
import l3info.projet.cakemarketingfactory.utils.Contents;

public class LoginActivity extends AppCompatActivity {

    Context context;
    SoundManager soundManager;
    Button register, connection;
    EditText loginUsername, loginPassword;
    TextView feedbackTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        soundManager = new SoundManager(context);

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


        /*Setup language*/
        String language = shr.getString("language", "");
        assert language != null;
        if(language.equals("") || language.isEmpty())
        {
            language = Locale.getDefault().getDisplayLanguage().substring(0,2);
            SharedPreferences.Editor ed = shr.edit();
            ed.putString("language",language);
            ed.apply();
        }
        LanguageManager.changeLocale(this, language);
        /*end*/

        connection.setOnClickListener(view -> {
            soundManager.playSoundIn();
            //Authentification Task
            AuthenticationTask task = new AuthenticationTask(loginUsername.getText().toString(), loginPassword.getText().toString(), feedbackTextView, context);
            task.execute();

            //Changer d'activity
            //Intent intentApp = new Intent(LoginActivity.this, LoadingActivity.class);
            //LoginActivity.this.startActivity(intentApp);
        });

        register.setOnClickListener(view -> {
            soundManager.playSoundIn();
            //Changer d'activity
            Intent intentApp = new Intent(LoginActivity.this, RegistrationActivity.class);
            LoginActivity.this.startActivity(intentApp);
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // refresh your views here
        super.onConfigurationChanged(newConfig);
    }
}
