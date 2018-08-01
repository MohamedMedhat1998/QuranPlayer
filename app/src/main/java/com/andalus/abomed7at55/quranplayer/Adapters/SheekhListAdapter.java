package com.andalus.abomed7at55.quranplayer.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andalus.abomed7at55.quranplayer.Interfaces.OnSheekhItemClickListener;
import com.andalus.abomed7at55.quranplayer.Objects.Sheekh;
import com.andalus.abomed7at55.quranplayer.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SheekhListAdapter extends RecyclerView.Adapter<SheekhListAdapter.SheekhItemHolder>{

    private ArrayList<Sheekh> mData;
    private OnSheekhItemClickListener mSheekhItemClickListener;


    public SheekhListAdapter(ArrayList<Sheekh> data , OnSheekhItemClickListener sheekhItemClickListener){
        mData = data;
        mSheekhItemClickListener = sheekhItemClickListener;
    }

    @NonNull
    @Override
    public SheekhItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.sheekh_list_item,parent,false);
        return new SheekhItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SheekhItemHolder holder, int position) {
        holder.tvSheekhName.setText(mData.get(position).getName());
        holder.tvRewaya.setText(mData.get(position).getRewaya());
        holder.tvSuraCount.setText(mData.get(position).getCount()+"");

        holder.tvSheekhName.setContentDescription(mData.get(position).getName());
        holder.tvRewaya.setContentDescription(mData.get(position).getRewaya());
        holder.tvSuraCount.setContentDescription(mData.get(position).getCount()+"");
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class SheekhItemHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_sheekh_name)
        TextView tvSheekhName;
        @BindView(R.id.tv_rewaya)
        TextView tvRewaya;
        @BindView(R.id.tv_sura_count)
        TextView tvSuraCount;

        SheekhItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSheekhItemClickListener.onSheekhItemClicked(mData.get(getAdapterPosition()).getSurasIds(),
                            mData.get(getAdapterPosition()).getServer(),
                            (int) mData.get(getAdapterPosition()).getId(),
                            mData.get(getAdapterPosition()).getName(),
                            mData.get(getAdapterPosition()).getRewaya(),
                            tvSheekhName,
                            tvRewaya);
                }
            });
        }
    }

}
