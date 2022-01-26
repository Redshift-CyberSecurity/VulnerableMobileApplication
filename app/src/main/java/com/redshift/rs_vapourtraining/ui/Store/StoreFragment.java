package com.redshift.rs_vapourtraining.ui.Store;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.redshift.rs_vapourtraining.DBHandler;
import com.redshift.rs_vapourtraining.ProductAdaptor;
import com.redshift.rs_vapourtraining.ProductModal;
import com.redshift.rs_vapourtraining.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class StoreFragment extends Fragment {

    private ArrayList<ProductModal> productModalArrayList;
    private DBHandler dbHandler;
    private ProductAdaptor productRVAdapter;
    private RecyclerView productRV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_store, container, false);

        productModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(getActivity());

        productModalArrayList = dbHandler.readProducts();
        productRVAdapter = new ProductAdaptor(productModalArrayList, getActivity());

        productRV = root.findViewById(R.id.idRVProducts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        productRV.setLayoutManager(linearLayoutManager);
        productRV.setAdapter(productRVAdapter);

        productRVAdapter.setWhenClickListener(new ProductAdaptor.OnItemsClickListener() {
            @Override
            public void onItemClick(ProductModal rvProductModel) throws JSONException {
                Toast.makeText(getContext(), "Buying: "+String.valueOf(rvProductModel.getproductName()) , Toast.LENGTH_SHORT).show();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String userID = prefs.getString("userRS", ""); //vuln
                String message;
                int userCredits = dbHandler.getCreds(userID);
                Toast.makeText(getContext(), "Credits: " +  String.valueOf(userCredits), Toast.LENGTH_SHORT).show();
                int productCreds = rvProductModel.getProductCreditsworth();
                int userBalance = userCredits - productCreds;
                if(userBalance > 0){
                    JSONObject json = new JSONObject();
                    json.put("name", "student");
                    JSONArray array = new JSONArray();
                    JSONObject item = new JSONObject();
                    item.put("name", rvProductModel.getproductName());
                    item.put("ID", rvProductModel.getId());
                    item.put("price", rvProductModel.getproductPrice());
                    item.put("userID", userID);
                    array.put(item);
                    json.put("course", array);
                    message = json.toString();
                    writeToFile(message, getActivity());
                    //TextView actionCreds = (TextView)root.findViewById(R.id.ActionCreds);
                    //actionCreds.setText("Credits: "+String.valueOf(userBalance));
                    dbHandler.buyGame(userID, rvProductModel.getId());
                    dbHandler.updateCredits(userID,userBalance);
                    Toast.makeText(getContext(), "Bought: " +  rvProductModel.getproductName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Insuffcient credits!" , Toast.LENGTH_SHORT).show();
                }
            }
        });
        return root;
    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("gamesbought.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
