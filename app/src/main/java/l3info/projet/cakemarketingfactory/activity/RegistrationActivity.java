package l3info.projet.cakemarketingfactory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.activity.manager.SoundManager;
import l3info.projet.cakemarketingfactory.task.RegistrationTask;

public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        SoundManager soundManager = new SoundManager(this);

        Button registerCancel = findViewById(R.id.registrationCancel);
        registerCancel.setOnClickListener(view -> {
            soundManager.playSoundOut();
            //Changer d'activity
            Intent intentApp = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intentApp);
        });

        Button registerValidate = findViewById(R.id.registrationValidate);
        registerValidate.setOnClickListener(view -> {
            soundManager.playSoundIn();
            EditText emailField = findViewById(R.id.registrationEmail);
            EditText usernameField = findViewById(R.id.registrationUsername);
            EditText passwordField = findViewById(R.id.registrationPassword);
            TextView feedbackTextView = findViewById(R.id.registrationFeedbackMessage);

            String email = emailField.getText().toString();
            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();

            RegistrationTask task = new RegistrationTask(email,username,password,feedbackTextView, RegistrationActivity.this);
            task.execute();
        });
    }
}
