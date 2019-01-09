package com.example.root.sens.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.root.sens.controllers.interfaces.ILoginController;
import com.example.root.sens.R;
import com.example.root.sens.controllers.LoginController;
import com.example.root.sens.fragments.LoginHelpFragment;

public class Login extends AppCompatActivity {
    ILoginController loginController = new LoginController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        EditText sensorField = findViewById(R.id.key_text);
        Button login = findViewById(R.id.login_btn);
        Button help = findViewById(R.id.help_btn);

        sensorField.setText("17-3B.BA"); // Sættes da appen bruges til fremvisning

        login.setOnClickListener((View v) -> {
            String sensorID = String.valueOf(sensorField.getText());

            if (sensorID.length() == 0) {
                Snackbar.make(findViewById(R.id.login_coordinator_layout),
                        "Du skal angive et sensor id for at fortsætte",
                        Snackbar.LENGTH_LONG).show();
                return;
            }

            if (checkForUser(sensorID)) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return;
            } else {
                Snackbar.make(findViewById(R.id.login_coordinator_layout),
                        "Brugeren er ikke fundet!",
                        Snackbar.LENGTH_LONG).show();
            }

            Intent i = new Intent(getApplicationContext(), UserConfigActivity.class);
            Bundle b = new Bundle();
            b.putString("sensorID", sensorID);
            i.putExtras(b);
            startActivity(i);
            getWindow().setEnterTransition(new Slide());
        });

        help.setOnClickListener((View v) -> {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_overlay_layout_login, new LoginHelpFragment())
                    .addToBackStack(null)
                    .commit();
        });
    }

    private boolean checkForUser(String sensorID) {
        return loginController.isUser(sensorID);
    }
}
