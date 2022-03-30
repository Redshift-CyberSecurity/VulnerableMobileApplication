package com.redshift.rs_vapourtraining.ui.BuyCredits;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.redshift.rs_vapourtraining.AddCard;
import com.redshift.rs_vapourtraining.CardAdaptor;
import com.redshift.rs_vapourtraining.CardModal;
import com.redshift.rs_vapourtraining.DBHandler;
import com.redshift.rs_vapourtraining.NavigationComponent;
import com.redshift.rs_vapourtraining.ProductAdaptor;
import com.redshift.rs_vapourtraining.ProductModal;
import com.redshift.rs_vapourtraining.R;
import com.redshift.rs_vapourtraining.WebViewCredits;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class BuyCreditsFragment extends Fragment{

//    TextView cText = getActivity().findViewById(R.id.text_cards_credits);
    Button buycredit;

    private ArrayList<CardModal> cardModalArrayList;
    private DBHandler dbHandler;
    private CardAdaptor cardRVAdapter;
    private RecyclerView cardRV;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_credits, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userID = prefs.getString("userRS", ""); //vuln


        cardModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(getActivity());

        cardModalArrayList = dbHandler.getCard(userID);
        if (cardModalArrayList != null) {
            cardRVAdapter = new CardAdaptor(cardModalArrayList, getActivity());
            cardRV = root.findViewById(R.id.idRVCardsOwned);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

            cardRV.setLayoutManager(linearLayoutManager);
            cardRV.setAdapter(cardRVAdapter);

            cardRVAdapter.setWhenClickListener(new CardAdaptor.OnItemsClickListener() {
                @Override
                public void onItemClick(CardModal rvCardModel) throws JSONException {
                    Intent intent = new Intent(getContext(), WebViewCredits.class);
                    intent.putExtra("cardName", rvCardModel.getNameoncard());
                    startActivity(intent);
                }
            });
        }else {
            //cText.setText("Please add some cards");
        }
        return root;
    }
}
