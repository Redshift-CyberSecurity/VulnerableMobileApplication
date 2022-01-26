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

public class ProductAdaptor extends RecyclerView.Adapter<ProductAdaptor.ViewHolder> {

    // variable for our array list and context
    private ArrayList<ProductModal> productModalArrayList;
    private Context context;

    // Need this clickListener for the Main-list item onClick events
    private OnItemsClickListener listener;

    // constructor
    public ProductAdaptor(ArrayList<ProductModal> productModalArrayList, Context context) {
        this.productModalArrayList = productModalArrayList;
        this.context = context;
    }

    public void setWhenClickListener(OnItemsClickListener listener){
        this.listener = listener;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // on below line we are inflating our layout
        // file for our recycler view items.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // on below line we are setting data
        // to our views of recycler view item.
        ProductModal modal = productModalArrayList.get(position);
        String url = modal.getproductURL();
        Glide.with(context)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(holder.productURLTV);
        holder.productNameTV.setText(modal.getproductName());
        holder.productDescTV.setText(modal.getproductDescription());
        holder.productPriceTV.setText(modal.getproductPrice());
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
        return productModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our text views.
        ImageView productURLTV;
        private TextView productNameTV, productDescTV, productPriceTV;
        public CardView singleItemCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing our text views
            productNameTV = itemView.findViewById(R.id.idproductNameTV);
            productDescTV = itemView.findViewById(R.id.idproductDescTV);
            productURLTV  = (ImageView) itemView.findViewById(R.id.idproductURLTV);
            productPriceTV = itemView.findViewById(R.id.idproductPriceTV);
            singleItemCardView = itemView.findViewById(R.id.singleItemCardView);
        }
    }
    public interface OnItemsClickListener{
        void onItemClick(ProductModal rvProductModel) throws JSONException;
    }
}

