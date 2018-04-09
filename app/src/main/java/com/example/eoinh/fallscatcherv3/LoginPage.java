package com.example.eoinh.fallscatcherv3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {
    private LoginManager loginManager;
    private DatabaseHandler databaseHandler;
    private EditText nameText, passwordText;
    private Button loginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        nameText = findViewById(R.id.nameInput);
        passwordText = findViewById(R.id.passwordInput);
        loginButton = (Button)findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!nameText.getText().toString().equals("") && !passwordText.getText().toString().equals("")){
                    String name = nameText.getText().toString();
                    String password = passwordText.getText().toString();
                    if(loginManager.checkLogin(name, password)) {
                        User user = loginManager.getSingleUser(passwordText.getText().toString(), nameText.getText().toString());
                        databaseHandler.setUser(user);

                        finish();
                    }
                    else
                        Toast.makeText(view.getContext(),"Invalid name and password combination ", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(view.getContext(),"Please enter both password and name fields", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
