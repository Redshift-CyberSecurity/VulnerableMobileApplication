package com.redshift.rs_vapourtraining;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.register_layout);
        dbHandler = new DBHandler(Register.this);
    }

    public void Register (View v){
        //Get Username
        EditText txtname = findViewById(R.id.registerUsername);
        String name = txtname.getText().toString();
        //Get UserPassword
        EditText txtpsswrd = findViewById(R.id.registerPassword);
        String psswrd = txtpsswrd.getText().toString();

        if (name.isEmpty() && psswrd.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter all the data..", Toast.LENGTH_SHORT).show();
            return;
        }else{
            String autheruser = dbHandler.checkUser(name, psswrd); // Vuln (Enum)
            if (autheruser == null) {
                dbHandler.addNewUser(name, psswrd, 10);
                Toast.makeText(getApplicationContext(), "Welcome " + name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Login.class);
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(), "Username Already exists", Toast.LENGTH_SHORT).show();
            }
        }
    }
}