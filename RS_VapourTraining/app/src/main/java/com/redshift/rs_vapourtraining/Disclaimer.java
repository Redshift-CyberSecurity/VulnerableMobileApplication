package com.redshift.rs_vapourtraining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Disclaimer extends AppCompatActivity {

    boolean isConnected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_disclaimer);

        EditText editIP = (EditText) findViewById(R.id.ipAddress);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String value = prefs.getString("RS_IP",null);
        if(value != null)
        {
            editIP.setText(value);
        }
    }

    public void launchLogin(View v){
        EditText editIP = (EditText) findViewById(R.id.ipAddress);
        String ipAddress = editIP.getText().toString();

        if(isValidIPAddress(ipAddress)){
            Intent intent = new Intent(this, Login.class);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String value = prefs.getString("RS_IP",null);
            if(value != null)
            {
                editIP.setText(value);
            }
            if (ipAddress.isEmpty()){
                ipAddress = value;
            }
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("RS_IP",ipAddress);
            editor.commit();
            startActivity(intent);
        }else{
            Toast.makeText(Disclaimer.this, "Please enter valid IP!", Toast.LENGTH_SHORT).show();
        }
    }

    public void connectionTest(View v){
        EditText editIP = (EditText) findViewById(R.id.ipAddress);
        String ipAddress = editIP.getText().toString();
        if(!getServer(ipAddress)){
            Toast.makeText(Disclaimer.this, "Wait a second.", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isValidIPAddress(String ip)
    {
        String zeroTo255
                = "(\\d{1,2}|(0|1)\\"
                + "d{2}|2[0-4]\\d|25[0-5])";
        String regex
                = zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255 + "\\."
                + zeroTo255;

        Pattern p = Pattern.compile(regex);
        if (ip == null) {
            return false;
        }
        Matcher m = p.matcher(ip);
        return m.matches();
    }

    public boolean getServer(String ipAddress){
        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(this);
        String url = "http://"+ipAddress+":5000/";
        StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(Disclaimer.this, "Connected to server.", Toast.LENGTH_SHORT).show();
                isConnected = true;
            }
        }, new Response.ErrorListener() { //Create an error listener to handle errors appropriately.
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Disclaimer.this, "Server error.", Toast.LENGTH_SHORT).show();
            }
        });
        ExampleRequestQueue.add(ExampleStringRequest);
        if(isConnected){
            isConnected = false;
            return  true;
        }
        return false;
    }

}