package com.anantadwi13.kamus.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anantadwi13.kamus.DetailActivity;
import com.anantadwi13.kamus.Model.Word;
import com.anantadwi13.kamus.R;

import java.util.ArrayList;

public class KamusAdapter extends RecyclerView.Adapter<KamusAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Word> list=new ArrayList<>();

    public KamusAdapter(Context context) {
        this.context = context;
    }

    public void setList(ArrayList<Word> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public ArrayList<Word> getList() {
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_word,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = position;
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lihatArti = new Intent(context, DetailActivity.class);
                lihatArti.putExtra(DetailActivity.EXTRA,getList().get(pos));
                context.startActivity(lihatArti);
            }
        });
        holder.tvKata.setText(getList().get(position).getKata());
    }

    @Override
    public int getItemCount() {
        return getList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvKata;
        CardView card;
        public ViewHolder(View itemView) {
            super(itemView);
            tvKata = itemView.findViewById(R.id.kata);
            card = itemView.findViewById(R.id.card);
        }
    }
}
