package com.example.root.sens.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
        sensorField.getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        sensorField.setText(R.string.StaticSensorID); // Sættes da appen bruges til fremvisning

        Button login = findViewById(R.id.login_btn);
        Button help = findViewById(R.id.help_btn);


        login.setOnClickListener((View v) -> {
            String sensorID = String.valueOf(sensorField.getText());
            if (sensorID.length() == 0) {
                Snackbar.make(findViewById(R.id.login_coordinator_layout),
                        R.string.LoginGiveSensorID,
                        Snackbar.LENGTH_LONG).show();
                return;
            }

            /*  Checks whether there is an existing user with the
                given sensor ID.
                Flags are added because it should not be possible
                to enter back into this "flow". (Back stack management)
             */
            if (loginController.login(sensorID)) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return;
            }

            Intent i = new Intent(getApplicationContext(), UserConfigActivity.class);
            Bundle b = new Bundle();
            b.putString("sensorID", sensorID);
            i.putExtras(b);
            startActivity(i);
            getWindow().setEnterTransition(new Slide());
        });

        help.setOnClickListener((View v) ->
                getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_overlay_layout_login, new LoginHelpFragment())
                .addToBackStack(null)
                .commit());
    }
}
