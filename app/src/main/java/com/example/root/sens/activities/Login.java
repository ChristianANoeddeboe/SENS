package com.example.root.sens.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.sens.controllers.interfaces.ILoginController;
import com.example.root.sens.R;
import com.example.root.sens.controllers.LoginController;
import com.example.root.sens.notification.NotificationsManager;

public class Login extends AppCompatActivity {
    private final static String TAG = Login.class.getSimpleName();
    ILoginController loginController = new LoginController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        UserDAO userDAO = new UserDAO();
//        User user = new User("Kenneth", "Klodshans", Calendar.getInstance().getTime());
//        user.setSensors(new RealmList<>(new Sensor("1234")));
//        userDAO.createUser(user);
//        userDAO.saveUser(user);

        setContentView(R.layout.activity_login);

        NotificationsManager noti = new NotificationsManager("42", this);


        EditText sensorField = findViewById(R.id.key_text);
        Button login = findViewById(R.id.login_btn);
        Button help = findViewById(R.id.help_btn);
        sensorField.setText("17-3B.BA");
        login.setOnClickListener((View v) -> {
            String sensorID = String.valueOf(sensorField.getText());

            if (sensorID.length() == 0) {
                Toast.makeText(this, "Du skal angive et sensor id for at fortsætte", Toast.LENGTH_SHORT).show();
                return;
            }

            if (checkForUser(sensorID)) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                Bundle b = new Bundle();
                b.putString("snackbar", "Text to show in the snackbar");
                i.putExtras(b);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                return;
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
