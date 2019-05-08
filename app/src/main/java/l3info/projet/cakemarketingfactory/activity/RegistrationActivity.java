package l3info.projet.cakemarketingfactory.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import l3info.projet.cakemarketingfactory.R;
import l3info.projet.cakemarketingfactory.task.RegistrationTask;

public class RegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Button registerCancel = findViewById(R.id.registrationCancel);
        registerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Changer d'activity
                Intent intentApp = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intentApp);
            }
        });

        Button registerValidate = findViewById(R.id.registrationValidate);
        registerValidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText emailField = (EditText) findViewById(R.id.registrationEmail);
                EditText usernameField = (EditText) findViewById(R.id.registrationUsername);
                EditText passwordField = (EditText) findViewById(R.id.registrationPassword);
                TextView feedbackTextView = (TextView) findViewById(R.id.registrationFeedbackMessage);

                String email = emailField.getText().toString();
                String username = usernameField.getText().toString();
                String password = passwordField.getText().toString();

                RegistrationTask task = new RegistrationTask(email,username,password,feedbackTextView, RegistrationActivity.this);
                task.execute();
            }
        });
    }
}
