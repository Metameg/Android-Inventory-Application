package com.alexmetzger.inventoryapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    private EditText mUsername;
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView (R.layout.activity_login);

        mUsername = findViewById(R.id.UsernameEdit);
        mPassword = findViewById(R.id.PasswordEdit);
    }

    public void onLoginClicked(View view) {
        LoginDatabase db = LoginDatabase.getInstance(getApplicationContext());

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        Boolean verified = db.verification(username, password);

        if (verified) {
            Intent intent = new Intent(this, ItemsActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Invalid Username/Password",
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void onNewUserClicked(View view) {

        LoginDatabase db = LoginDatabase.getInstance(getApplicationContext());
        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        if (username.equals("") || password.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Enter valid Username and Password",
                    Toast.LENGTH_SHORT).show();
        }

        Login login = new Login(username, password);
        Log.d("username", " " + login.getUsername());
        Log.d("pwd", " " + login.getPassword());
        db.addLogin(login);

        Toast.makeText(this, "New User Added!", Toast.LENGTH_SHORT).show();



    }
}