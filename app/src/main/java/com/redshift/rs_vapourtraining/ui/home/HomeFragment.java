package com.redshift.rs_vapourtraining.ui.home;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.redshift.rs_vapourtraining.DBHandler;
import com.redshift.rs_vapourtraining.ProductAdaptor;
import com.redshift.rs_vapourtraining.ProductModal;
import com.redshift.rs_vapourtraining.R;
import com.redshift.rs_vapourtraining.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ArrayList<ProductModal> productModalArrayList;
    private DBHandler dbHandler;
    private ProductAdaptor productRVAdapter;
    private RecyclerView productRV;

    TextView cText;
    TextView gText;
    TextView tText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userID = prefs.getString("userRS", ""); //vuln

        productModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(getActivity());

        productModalArrayList = dbHandler.getOwnedGames(userID);
        if (productModalArrayList != null) {
            productRVAdapter = new ProductAdaptor(productModalArrayList, getActivity());
            productRV = root.findViewById(R.id.idRVProductsOwned);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

            productRV.setLayoutManager(linearLayoutManager);
            productRV.setAdapter(productRVAdapter);
        }
        cText = root.findViewById(R.id.credits_amount);
        gText = root.findViewById(R.id.games_amount); //title2Home
        tText = root.findViewById(R.id.title2Home);
        //bText = root.findViewById(R.id.games_amount);
        cText.setText(String.valueOf(dbHandler.getCreds(userID)));
        tText.setText(String.valueOf(dbHandler.checkUser(userID)));
        gText.setText(String.valueOf(productModalArrayList.size()));
        return root;
    }

}