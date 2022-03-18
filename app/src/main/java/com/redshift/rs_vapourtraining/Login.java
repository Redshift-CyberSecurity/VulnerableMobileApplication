package com.redshift.rs_vapourtraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Window;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Button;

public class Login extends AppCompatActivity {
    String Tag = "Redshift_Training_App";
    private String adminUser = "admin";
    private String adminPass = "admin";
    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.login_layout);
        dbHandler = new DBHandler(Login.this);
        if (!prefs.getBoolean("firstTime2", false)) {
            // <---- run your one time code here
            dbHandler.addNewUser(adminUser,adminPass, 10000);
            dbHandler.addNewProduct("game1","very cool game", "https://news.xbox.com/en-us/wp-content/uploads/w=940,h=9999/sites/2/2021/07/lost_01.jpg", "$6", 6);
            dbHandler.addNewProduct("game2","very not cool game", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRLLxFu415NRfnLvfV1ar34IJTe9Z2lH7y9Cg&usqp=CAU", "$6", 6);
            dbHandler.addNewProduct("game3","very pretty cool game", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT-On8ytqpfFwEU84Fktv_T5rCmSQmQv-EhcQ&usqp=CAU", "$6", 6);
            dbHandler.addNewProduct("game4","very meh game", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQajvEnyslYEGMUBTKckfVGZRnK6PJSumbAPQ&usqp=CAU", "$6", 6);
            dbHandler.addCreditCard("1","0000000000000000","234","12/26","Admin");
            // mark first time has ran.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime2", true);
            editor.putString("cookie","djkwapjdpoajdopj3o24242ojpfejspfjo"); //Doesn't do anything YET
            editor.commit();
        }
    }


    public void Login(View v) {
        //Get Username
        EditText txtname = findViewById(R.id.editTextTextPersonName);
        String name = txtname.getText().toString();
        //Get UserPassword
        EditText txtpsswrd = findViewById(R.id.editTextTextPassword);
        String psswrd = txtpsswrd.getText().toString();
        //Log For debuging progress
        Log.d(Tag, name);
        Log.d(Tag, psswrd);
        if (name.isEmpty() && psswrd.isEmpty()) {
            Toast.makeText(Login.this, "Please enter all the data..", Toast.LENGTH_SHORT).show();
            return;
        }else{
            String autheruser = dbHandler.checkUser(name, psswrd); // Vuln
            if (autheruser != null){
                Toast.makeText(getApplicationContext(),"Welcome "+ autheruser,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, NavigationComponent.class);
                intent.putExtra("username",autheruser);
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("userRS",dbHandler.getUserID(autheruser)); //vuln
                editor.putString("userbuyCredits","0");
                editor.commit();
                startActivity(intent);
            }else{
                Toast.makeText(Login.this, "User or password not valid ", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Register(View v) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
        //Open register window
    }

}