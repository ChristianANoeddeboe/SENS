package com.example.root.sens.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.sens.Controlles.ILoginController;
import com.example.root.sens.R;

public class Login extends AppCompatActivity {
    ILoginController loginController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        EditText sensorField = findViewById(R.id.key_text);
        Button login = findViewById(R.id.login_btn);
        Button help = findViewById(R.id.help_btn);

        login.setOnClickListener((View v) ->{
                Intent i = new Intent(getApplicationContext(), UserConfigActivity.class);
                startActivity(i);
                getWindow().setEnterTransition(new Slide());
        });

        help.setOnClickListener((View v) ->{
            Toast.makeText(this, "Not implemented yet!", Toast.LENGTH_LONG).show();
        });
    }
}
