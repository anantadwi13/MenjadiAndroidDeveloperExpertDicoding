package com.anantadwi13.cataloguemovie;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CatalogueAdapter extends BaseAdapter {
    private ArrayList<MovieItem> movieItems = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private Context context;

    public CatalogueAdapter(Context context) {
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setMovieItems(ArrayList<MovieItem> movieItems) {
        this.movieItems = movieItems;
        notifyDataSetChanged();
    }

    public void addItem(MovieItem item){
        movieItems.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        movieItems.clear();
    }

    @Override
    public int getCount() {
        if(movieItems==null) return 0;
        return movieItems.size();
    }

    @Override
    public MovieItem getItem(int position) {
        return movieItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(view==null){
            viewHolder = new ViewHolder();
            view = mLayoutInflater.inflate(R.layout.movie_item,null);
            viewHolder.poster = view.findViewById(R.id.poster);
            viewHolder.title = view.findViewById(R.id.movie_title);
            viewHolder.overview = view.findViewById(R.id.overview);
            viewHolder.release_date = view.findViewById(R.id.release_date);
            view.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) view.getTag();

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

        return view;
    }

    public static class ViewHolder{
        ImageView poster;
        TextView title, overview, release_date;
    }
}
