package com.anantadwi13.cataloguemovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CatalogueRecyclerAdapter extends RecyclerView.Adapter<CatalogueRecyclerAdapter.MyViewHolder> {
    private ArrayList<MovieItem> list;
    private Context context;

    public CatalogueRecyclerAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public CatalogueRecyclerAdapter(Context context,ArrayList<MovieItem> movies) {
        this.list = movies;
        this.context = context;
    }

    public void setList(ArrayList<MovieItem> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addMovie(MovieItem movie){
        list.add(movie);
        notifyDataSetChanged();
    }

    public MovieItem getItem(int pos){
        return list.get(pos);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position) {
        MovieItem item = getItem(position);
        try {
            String[] date = item.getRelease_date().split("-");
            int tahun = Integer.valueOf(date[0]);
            int bulan = Integer.valueOf(date[1]) - 1;
            int tanggal = Integer.valueOf(date[2]);

            Calendar calendar = Calendar.getInstance();
            calendar.set(tahun, bulan, tanggal);

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            viewHolder.release_date.setText(dateFormat.format(calendar.getTime()));
        }
        catch (Exception e) {
            viewHolder.release_date.setText("-");
        }

        String overview = item.getOverview();
        if (!TextUtils.isEmpty(overview) && overview.length()>35)
            overview = overview.substring(0,35)+"...";

        Glide.with(context).load(item.getPosterURL())
                .apply(new RequestOptions().placeholder(R.drawable.no_image).error(R.drawable.no_image))
                .into(viewHolder.poster);

        viewHolder.title.setText(item.getTitle());
        viewHolder.overview.setText(overview);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView title, overview, release_date;
        public MyViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            title = itemView.findViewById(R.id.movie_title);
            overview = itemView.findViewById(R.id.overview);
            release_date = itemView.findViewById(R.id.release_date);
        }
    }
}
