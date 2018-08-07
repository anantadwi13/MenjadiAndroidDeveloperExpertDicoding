package com.anantadwi13.cataloguemovie.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anantadwi13.cataloguemovie.Model.Film;
import com.anantadwi13.cataloguemovie.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CatalogueAdapter extends RecyclerView.Adapter<CatalogueAdapter.ViewHolder> {
    private ArrayList<Film> list = new ArrayList<>();
    private Context context;

    public CatalogueAdapter(Context context) {
        this.context = context;
    }

    public void setList(ArrayList<Film> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public Film getItem(int pos){
        return list.get(pos);
    }

    public void removeItem(int pos){
        list.remove(pos);
        notifyDataSetChanged();
    }

    public ArrayList<Film> getList() {
        return list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_film,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Film item = getItem(position);

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
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView poster;
        TextView movie_title, movie_overview, movie_releasedate;
        public ViewHolder(View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.detail_poster);
            movie_title = itemView.findViewById(R.id.movie_title);
            movie_overview = itemView.findViewById(R.id.movie_overview);
            movie_releasedate = itemView.findViewById(R.id.movie_releasedate);
        }
    }
}
