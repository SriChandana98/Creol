package com.example.vineetha.creol;

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

import com.example.vineetha.creol.Card;
import com.example.vineetha.creol.R;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;

/**
 * Created by ADMIN-PC on 3/8/2018.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {

    Context ctx;
    ArrayList<Card> cardList;

    public CardAdapter(Context ctx, ArrayList<Card> cardList) {
        this.ctx = ctx;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.list_layout, null);
        CardViewHolder holder = new CardViewHolder(view,ctx,cardList);
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

    class CardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewName , textViewDesc;
        List<Card> cardList;
        Context ctx;

        public CardViewHolder(View itemView,Context ctx,ArrayList<Card> cardList) {
            super(itemView);
            this.cardList=cardList;
            this.ctx=ctx;
            itemView.setOnClickListener(this);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
        }

        @Override
        public void onClick(View view) {
            int position=getAdapterPosition();
            Card card=this.cardList.get(position);
            Intent intent=new Intent(this.ctx,Display.class);
            intent.putExtra("title",card.getPname());
            intent.putExtra("desc",card.getPdescription());
            this.ctx.startActivity(intent);
        }
    }
}
