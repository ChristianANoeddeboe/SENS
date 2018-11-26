package com.example.root.sens.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.sens.controllers.ILoginController;
import com.example.root.sens.R;
import com.example.root.sens.controllers.LoginController;

public class Login extends AppCompatActivity {
    ILoginController loginController = new LoginController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        EditText sensorField = findViewById(R.id.key_text);
        Button login = findViewById(R.id.login_btn);
        Button help = findViewById(R.id.help_btn);

        login.setOnClickListener((View v) -> {
            String sensorID = String.valueOf(sensorField.getText());

            if (sensorID.length() == 0) {
                Toast.makeText(this, "Du skal angive et sensor id for at fortsætte", Toast.LENGTH_SHORT).show();
                return;
            }

            if (checkForUser(sensorID)) {
                Toast.makeText(this, "This is an user!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
            }

            Intent i = new Intent(getApplicationContext(), UserConfigActivity.class);
            Bundle b = new Bundle();
            b.putString("sensorID", sensorID);
            i.putExtras(b);
            startActivity(i);
            getWindow().setEnterTransition(new Slide());
        });

        help.setOnClickListener((View v) -> {
            Toast.makeText(this, "Not implemented yet!", Toast.LENGTH_LONG).show();
        });
    }

    private boolean checkForUser(String sensorID) {
        return loginController.isUser(sensorID);
    }
}
