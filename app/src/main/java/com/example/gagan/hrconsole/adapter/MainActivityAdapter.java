package com.example.gagan.hrconsole.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.adapters.ViewBindingAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.gagan.hrconsole.R;
import com.example.gagan.hrconsole.adapter.viewholders.ItemViewHolder;
import com.example.gagan.hrconsole.models.BasicListModel;
import com.example.gagan.hrconsole.models.Datas;
import com.example.gagan.hrconsole.models.HeadingModel;

import java.util.List;

/**
 * Created by Gagan on 6/18/2018.
 */

public class MainActivityAdapter extends RecyclerView.Adapter<ItemViewHolder> {
    private final List<BasicListModel> mData;

    public MainActivityAdapter(@NonNull List<BasicListModel> datas) {
        this.mData = datas;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), viewType, parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public int getItemViewType(int position) {
        BasicListModel item = mData.get(position);
        if (item instanceof Datas)
            return R.layout.item_row;
        if (item instanceof HeadingModel)
            return R.layout.header_layiut;
        return R.layout.divider;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.setData(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
