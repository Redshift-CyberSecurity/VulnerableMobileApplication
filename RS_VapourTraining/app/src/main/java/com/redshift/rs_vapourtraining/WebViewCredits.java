package com.redshift.rs_vapourtraining;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.Base64;

public class WebViewCredits extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_web_view_credits);
        String cardName = getIntent().getStringExtra("cardName");
        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.addJavascriptInterface(new WebAppInterface(this), "Android");
        String encodedString = Base64.getEncoder().encodeToString(cardName.getBytes());
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String RS_IP = prefs.getString("RS_IP", ""); //vuln
        myWebView.loadUrl("http://"+RS_IP+":5000/buycredit/cardname/"+encodedString); //192.168.1.193
    }
}