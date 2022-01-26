package com.redshift.rs_vapourtraining.ui.AddCards;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.unusedapprestrictions.IUnusedAppRestrictionsBackportService;
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

import java.util.ArrayList;

public class AddCardsFragment extends Fragment  implements View.OnClickListener {

    //TextView cText = getActivity().findViewById(R.id.text_cards);
    private ArrayList<CardModal> cardModalArrayList;
    private DBHandler dbHandler;
    private CardAdaptor cardRVAdapter;
    private RecyclerView cardRV;
    Button addCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cards, container, false);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userID = prefs.getString("userRS", ""); //vuln

        cardModalArrayList = new ArrayList<>();
        dbHandler = new DBHandler(getActivity());

        cardModalArrayList = dbHandler.getCard(userID);
        if (cardModalArrayList != null)
        {
            cardRVAdapter = new CardAdaptor(cardModalArrayList, getActivity());
            cardRV = root.findViewById(R.id.idRVCardsOwned);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

            cardRV.setLayoutManager(linearLayoutManager);
            cardRV.setAdapter(cardRVAdapter);
        }else {
            //cText.setText("Please add some cards");
        }
        addCard = (Button) root.findViewById(R.id.add_card);
        addCard.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_card:
                Intent intent = new Intent(getContext(), AddCard.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
