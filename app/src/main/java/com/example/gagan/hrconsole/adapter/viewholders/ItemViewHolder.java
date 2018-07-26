package com.example.gagan.hrconsole.adapter.viewholders;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.gagan.hrconsole.databinding.HeaderLayiutBinding;
import com.example.gagan.hrconsole.databinding.ItemRowBinding;
import com.example.gagan.hrconsole.models.BasicListModel;
import com.example.gagan.hrconsole.models.Datas;
import com.example.gagan.hrconsole.models.HeadingModel;

/**
 * Created by Gagan on 6/18/2018.
 */

public class ItemViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public ItemViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void setData(BasicListModel data) {
        if (binding instanceof HeaderLayiutBinding)
            ((HeaderLayiutBinding) binding).setItem((HeadingModel) data);
        else if (binding instanceof ItemRowBinding)
            ((ItemRowBinding) binding).setItem((Datas) data);

    }
}
