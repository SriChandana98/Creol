package com.wise.vineetha.creol;

/**
 * Created by vineetha on 08-03-2018.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

/**
 * Created by ADMIN-PC on 3/8/2018.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    private Context mCtx;
    private List<Card> cardList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public CardAdapter(Context mCtx, List<Card> cardList) {
        this.mCtx = mCtx;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.list_layout, null);
        CardViewHolder holder = new CardViewHolder(view, mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {

        Card card = cardList.get(position);

        holder.textViewName.setText(card.getPname());
        holder.textViewDesc.setText(card.getPdescription());

    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName , textViewDesc;

        public CardViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    if(listener != null) {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                            //Intent intent = new Intent(mCtx, projectScreen.class);
                            //intent.putExtra("project-name", (Serializable) cardList.get());
                            //intent.putExtra("project-description", (Serializable) cardList.get());
                        }
                    }
                }
            });
        }
    }
}
