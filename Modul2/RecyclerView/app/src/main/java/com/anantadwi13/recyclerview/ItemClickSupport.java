package com.anantadwi13.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemClickSupport {
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public ItemClickSupport(RecyclerView rv) {
        recyclerView = rv;
        recyclerView.setTag(R.id.item_click_support,this);
        recyclerView.addOnChildAttachStateChangeListener(attachListener);
    }

    private RecyclerView.OnChildAttachStateChangeListener attachListener = new RecyclerView.OnChildAttachStateChangeListener() {
        @Override
        public void onChildViewAttachedToWindow(View view) {
            if (onItemClickListener!=null)
                view.setOnClickListener(onClickListener);
            if (onItemLongClickListener!=null)
                view.setOnLongClickListener(onLongClickListener);
        }

        @Override
        public void onChildViewDetachedFromWindow(View view) {

        }
    };

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onItemClickListener!=null){
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
                onItemClickListener.onItemClicked(recyclerView,holder.getAdapterPosition(),view);
            }
        }
    };

    private View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
            if (onItemLongClickListener!=null){
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(view);
                onItemLongClickListener.onItemLongClicked(recyclerView,holder.getAdapterPosition(),view);
            }
            return false;
        }
    };

    public static ItemClickSupport addTo(RecyclerView rv){
        ItemClickSupport clickSupport = (ItemClickSupport) rv.getTag(R.id.item_click_support);
        if (clickSupport==null)
            clickSupport = new ItemClickSupport(rv);
        return clickSupport;
    }

    public static ItemClickSupport removeFrom(RecyclerView rv){
        ItemClickSupport clickSupport = (ItemClickSupport) rv.getTag(R.id.item_click_support);
        if (clickSupport!=null)
            clickSupport.detach(rv);
        return clickSupport;
    }

    public void detach(RecyclerView rv){
        rv.removeOnChildAttachStateChangeListener(attachListener);
        rv.setTag(R.id.item_click_support,null);
    }

    public ItemClickSupport setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public ItemClickSupport setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        return this;
    }

    public interface OnItemClickListener{
        void onItemClicked(RecyclerView rv, int position, View v);
    }
    public interface OnItemLongClickListener{
        boolean onItemLongClicked(RecyclerView rv, int position, View v);
    }
}
