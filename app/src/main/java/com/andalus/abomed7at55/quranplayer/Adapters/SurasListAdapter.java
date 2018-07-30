package com.andalus.abomed7at55.quranplayer.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andalus.abomed7at55.quranplayer.Interfaces.OnSuraClickListener;
import com.andalus.abomed7at55.quranplayer.Objects.Sura;
import com.andalus.abomed7at55.quranplayer.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SurasListAdapter extends RecyclerView.Adapter<SurasListAdapter.SuraItemHolder> {

    private ArrayList<Sura> mData;
    private OnSuraClickListener mOnSuraClickListener;

    public SurasListAdapter(ArrayList<Sura> data,OnSuraClickListener onSuraClickListener){
        mData = data;
        mOnSuraClickListener = onSuraClickListener;
    }

    @NonNull
    @Override
    public SuraItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.suras_list_item,parent,false);
        return new SuraItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SuraItemHolder holder, int position) {
        holder.tvSuraName.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class SuraItemHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_sura_name)
        TextView tvSuraName;

        SuraItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnSuraClickListener.onSuraClick(mData.get(getAdapterPosition()));
                }
            });
        }
    }

}
