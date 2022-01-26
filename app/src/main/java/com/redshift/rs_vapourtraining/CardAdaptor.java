package com.redshift.rs_vapourtraining;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.json.JSONException;

import java.util.ArrayList;

public class CardAdaptor extends RecyclerView.Adapter<CardAdaptor.ViewHolder> {

    // variable for our array list and context
    private ArrayList<CardModal> cardModalArrayList;
    private Context context;

    // Need this clickListener for the Main-list item onClick events
    private CardAdaptor.OnItemsClickListener listener;

    // constructor
    public CardAdaptor(ArrayList<CardModal> cardModalArrayList, Context context) {
        this.cardModalArrayList = cardModalArrayList;
        this.context = context;
    }

    public void setWhenClickListener(CardAdaptor.OnItemsClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public CardAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_rc_item, parent, false);
        return new CardAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdaptor.ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        CardModal modal = cardModalArrayList.get(position);
        holder.cardName.setText(modal.getNameoncard());
        holder.cardNumber.setText(modal.getCardNummber());
        holder.expiryDate.setText(modal.getExpiryDate());
        holder.cardCVV.setText(modal.getCvv());
        holder.singleItemCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    try {
                        listener.onItemClick(modal);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        // returning the size of our array list
        return cardModalArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        private TextView cardName, cardNumber, expiryDate, cardCVV;
        public CardView singleItemCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            cardName = itemView.findViewById(R.id.card_name);
            cardNumber = itemView.findViewById(R.id.card_nummber);
            expiryDate = itemView.findViewById(R.id.Expire_date);
            cardCVV = itemView.findViewById(R.id.card_cvv);
            singleItemCardView = itemView.findViewById(R.id.singleItemCardView);
        }
    }

    public interface OnItemsClickListener{
        void onItemClick(CardModal rvCardModel) throws JSONException;
    }
}
