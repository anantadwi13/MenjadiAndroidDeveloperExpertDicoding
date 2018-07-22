package com.anantadwi13.recyclerview;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.ViewHolder> {
    private ArrayList<MovieModel> listMovie;

    public ListMovieAdapter(ArrayList<MovieModel> listMovie) {
        this.listMovie = listMovie;
    }

    public void addMovie(MovieModel movie){
        listMovie.add(movie);
        notifyDataSetChanged();
    }

    public MovieModel getMovie(int position){
        return listMovie.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvJudul.setText(listMovie.get(position).getJudul());
        holder.tvReleaseDate.setText(listMovie.get(position).getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvJudul,tvReleaseDate;

        ViewHolder(View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tvJudul);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
        }
    }
}
