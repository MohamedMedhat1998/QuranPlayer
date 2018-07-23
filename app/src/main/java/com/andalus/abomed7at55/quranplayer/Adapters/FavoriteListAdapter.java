package com.andalus.abomed7at55.quranplayer.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andalus.abomed7at55.quranplayer.Data.FavoriteSura;
import com.andalus.abomed7at55.quranplayer.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteListAdapter extends RecyclerView.Adapter<FavoriteListAdapter.FavoriteIemViewHolder>{

    private ArrayList<FavoriteSura> mData;

    public FavoriteListAdapter(ArrayList<FavoriteSura> data){
        mData = data;
    }

    @NonNull
    @Override
    public FavoriteIemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favorite_list_item,parent,false);
        return new FavoriteIemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteIemViewHolder holder, int position) {
        holder.tvName.setText(mData.get(position).getSuraName());
        holder.tvRewaya.setText(mData.get(position).getRewaya());
        holder.tvSheekh.setText(mData.get(position).getSheekhName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class FavoriteIemViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_favorite_sura_name)
        TextView tvName;
        @BindView(R.id.tv_favorite_sura_rewaya)
        TextView tvRewaya;
        @BindView(R.id.tv_favorite_sura_sheekh)
        TextView tvSheekh;

        public FavoriteIemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
