package com.andalus.abomed7at55.quranplayer.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andalus.abomed7at55.quranplayer.Data.OfflineSura;
import com.andalus.abomed7at55.quranplayer.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfflineListAdapter extends RecyclerView.Adapter<OfflineListAdapter.OfflineItemHolder>{

    private ArrayList<OfflineSura> mData;

    public OfflineListAdapter(ArrayList<OfflineSura> data){
        mData = data;
    }

    @NonNull
    @Override
    public OfflineItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.offline_list_item,parent,false);
        return new OfflineItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineItemHolder holder, int position) {
        holder.tvRewaya.setText(mData.get(position).getRewaya());
        holder.tvOfflineSuraName.setText(mData.get(position).getSuraName());
        holder.tvSheekhName.setText(mData.get(position).getSheekhName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class OfflineItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_offline_rewaya)
        TextView tvRewaya;
        @BindView(R.id.tv_offline_sheekh_name)
        TextView tvSheekhName;
        @BindView(R.id.tv_offline_sura_name)
        TextView tvOfflineSuraName;

        public OfflineItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
