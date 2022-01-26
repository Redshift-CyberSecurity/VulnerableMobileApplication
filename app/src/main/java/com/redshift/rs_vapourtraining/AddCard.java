package com.redshift.rs_vapourtraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class AddCard extends AppCompatActivity {
    String Tag = "Redshift_Training_App";
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_card);
        dbHandler = new DBHandler(AddCard.this);
    }

    public void CardAdd (View v){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String userID = prefs.getString("userRS", ""); //vuln
        EditText cardname = findViewById(R.id.add_card_name);
        EditText cardnumber = findViewById(R.id.add_card_number);
        EditText cardexpmonth = findViewById(R.id.exp_month);
        EditText cardexpyear = findViewById(R.id.exp_year);
        EditText cardcvv = findViewById(R.id.add_card_cvv);
        Log.d(Tag, cardcvv.getText().toString());
        Log.d(Tag, cardnumber.getText().toString());
        String expdate = cardexpmonth.getText().toString() + "/" + cardexpyear.getText().toString();
        dbHandler.addCreditCard(userID,cardnumber.getText().toString(),cardcvv.getText().toString(),expdate,cardname.getText().toString());
        Intent intent = new Intent(this, NavigationComponent.class);
        intent.putExtra("username", dbHandler.checkUser(userID));
        startActivity(intent);

    }
}