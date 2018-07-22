package com.anantadwi13.asynctaskloader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherAdapter extends BaseAdapter {
    private ArrayList<WeatherItems> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    public WeatherAdapter(Context context) {
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(ArrayList<WeatherItems> items) {
        mData = items;
        notifyDataSetChanged();
    }

    public void addItem(WeatherItems item){
        mData.add(item);
        notifyDataSetChanged();
    }

    public void clearData(){
        mData.clear();
    }

    @Override
    public int getCount() {
        if(mData == null) return 0;
        return mData.size();
    }

    @Override
    public WeatherItems getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if(view==null){
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.weather_item,null);
            holder.tvNamaKota = view.findViewById(R.id.tvKota);
            holder.tvTemp = view.findViewById(R.id.tvTemp);
            holder.tvDesc = view.findViewById(R.id.tvDesc);
            view.setTag(holder);
        }
        else holder = (ViewHolder) view.getTag();

        holder.tvNamaKota.setText(mData.get(i).getNama());
        holder.tvDesc.setText(mData.get(i).getDescription());
        holder.tvTemp.setText(mData.get(i).getTemperature());

        return view;
    }

    private class ViewHolder{
        TextView tvNamaKota, tvTemp, tvDesc;
    }
}
