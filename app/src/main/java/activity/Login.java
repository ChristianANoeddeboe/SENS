package activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.root.sens.MainActivity;
import com.example.root.sens.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login = findViewById(R.id.login_btn);
        Button help = findViewById(R.id.help_btn);

        login.setOnClickListener((View v) ->{
//            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            Intent i = new Intent(getApplicationContext(), Settings.class);
            startActivity(i);
        });

        help.setOnClickListener((View v) ->{
            Toast.makeText(this, "Not implemented yet!", Toast.LENGTH_LONG).show();
        });
    }
}
