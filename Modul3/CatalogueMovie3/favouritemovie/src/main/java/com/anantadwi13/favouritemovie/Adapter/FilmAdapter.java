package com.anantadwi13.favouritemovie.Adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.anantadwi13.favouritemovie.DetailActivity;
import com.anantadwi13.favouritemovie.Model.Film;
import com.anantadwi13.favouritemovie.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.ViewHolder> {
    private Cursor cursor;
    private Context context;

    public FilmAdapter(Context context) {
        this.context = context;
    }

    public void swapCursor(Cursor list) {
        if (cursor!=null)
            cursor.close();
        cursor = list;
        notifyDataSetChanged();
    }

    public Cursor getCursor(){
        return cursor;
    }

    public Film getItem(int pos){
        if(!cursor.moveToPosition(pos))
            throw new IllegalStateException("Position invalid");
        return new Film(cursor);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Film item = getItem(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detail = new Intent(context, DetailActivity.class);
                detail.putExtra(DetailActivity.EXTRA_ITEM,item);
                context.startActivity(detail);
            }
        });
        try {
            String[] date = item.getRelease_date().split("-");
            int tahun = Integer.valueOf(date[0]);
            int bulan = Integer.valueOf(date[1]) - 1;
            int tanggal = Integer.valueOf(date[2]);

            Calendar calendar = Calendar.getInstance();
            calendar.set(tahun, bulan, tanggal);

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            holder.movie_releasedate.setText(dateFormat.format(calendar.getTime()));
        }
        catch (Exception e) {
            holder.movie_releasedate.setText("-");
        }

        String overview = item.getOverview();
        if (!TextUtils.isEmpty(overview) && overview.length()>150)
            overview = overview.substring(0,150)+"...";

        Glide.with(context).load(item.getPosterURL())
                .apply(new RequestOptions().placeholder(R.drawable.no_image).error(R.drawable.no_image))
                .into(holder.poster);

        holder.movie_title.setText(item.getTitle());
        holder.movie_overview.setText(overview);
    }

    @Override
    public int getItemCount() {
        if (cursor==null) return 0;
        return cursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView movie_title, movie_overview, movie_releasedate;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview);
            poster = itemView.findViewById(R.id.detail_poster);
            movie_title = itemView.findViewById(R.id.movie_title);
            movie_overview = itemView.findViewById(R.id.movie_overview);
            movie_releasedate = itemView.findViewById(R.id.movie_releasedate);
        }
    }
}
