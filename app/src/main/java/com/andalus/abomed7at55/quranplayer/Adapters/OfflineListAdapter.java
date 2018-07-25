package com.andalus.abomed7at55.quranplayer.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andalus.abomed7at55.quranplayer.Data.OfflineSura;
import com.andalus.abomed7at55.quranplayer.Interfaces.OnOfflineSuraClickListener;
import com.andalus.abomed7at55.quranplayer.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OfflineListAdapter extends RecyclerView.Adapter<OfflineListAdapter.OfflineItemHolder>{

    private ArrayList<OfflineSura> mData;
    private OnOfflineSuraClickListener mOnOfflineSuraClickListener;

    public OfflineListAdapter(ArrayList<OfflineSura> data,OnOfflineSuraClickListener onOfflineSuraClickListener){
        mData = data;
        mOnOfflineSuraClickListener = onOfflineSuraClickListener;
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
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    OfflineSura temp = mData.get(getAdapterPosition());
                    mOnOfflineSuraClickListener.onOfflineSuraClicked(Integer.parseInt(temp.getSuraId()),
                            temp.getSuraName(),
                            Integer.parseInt(temp.getSheekhId()),
                            temp.getSheekhName(),
                            temp.getStreamingPath(),
                            temp.getRewaya(),
                            temp.getOfflineFileName());
                }
            });
        }
    }
}
