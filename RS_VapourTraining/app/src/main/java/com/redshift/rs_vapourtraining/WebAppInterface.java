package com.redshift.rs_vapourtraining;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class WebAppInterface {
    Context mContext;
    private DBHandler dbHandler;

   /* public void updateCreds()
    {
        int requestedcreds = Integer.parseInt(response);
        int mycreds = dbHandler.getCreds(userID);
        int totalcreds = requestedcreds+mycreds;
        dbHandler.updateCredits(userID, mycreds);
    }*/

    /** Instantiate the interface and set the context */
    WebAppInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void showToast(String toast) {
        Toast.makeText(mContext, "Bought: "+toast, Toast.LENGTH_SHORT).show();
        //getCredits(Integer.parseInt(toast));
    }


    @JavascriptInterface
    public void getCredits(int numCredits){
        RequestQueue ExampleRequestQueue = Volley.newRequestQueue(mContext);
        dbHandler = new DBHandler(mContext);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String userID = prefs.getString("userRS", ""); //vuln
        String RS_IP = prefs.getString("RS_IP", ""); //vuln
        String url = "http://"+RS_IP+":5000/requestcredits/"+String.valueOf(numCredits);
        String userName = dbHandler.checkUser(userID);
        SharedPreferences.Editor editor = prefs.edit();
        StringRequest ExampleStringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                 //hehehehe
                editor.putString("userbuyCredits",response);
                editor.commit();
                Intent intent = new Intent(mContext, NavigationComponent.class);
                intent.putExtra("username",userName);
                mContext.startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //This code is executed if there is an error.
                Intent intent = new Intent(mContext, NavigationComponent.class);
                intent.putExtra("username",dbHandler.checkUser(userID));
                Toast.makeText(mContext, "An error has occured. ", Toast.LENGTH_SHORT).show();
                mContext.startActivity(intent);
            }
        });

        ExampleRequestQueue.add(ExampleStringRequest);
    }

    @JavascriptInterface
    public void getBack(){
        dbHandler = new DBHandler(mContext);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String userID = prefs.getString("userRS", ""); //vuln
        Intent intent = new Intent(mContext, NavigationComponent.class);
        intent.putExtra("username",dbHandler.checkUser(userID));
        mContext.startActivity(intent);
    }

}
