package com.example.gagan.hrconsole.models;

import android.databinding.Bindable;
import android.support.annotation.NonNull;

import com.android.databinding.library.baseAdapters.BR;
import com.example.gagan.hrconsole.utils.Utils;

/**
 * Created by Gagan on 6/17/2018.
 */

public class Datas extends BasicListModel {
    private final String name;
    private int amount;
    private String displayAmount;

    public Datas(@NonNull String name, @NonNull int amount) {
        this.name = name;
        this.amount = amount;
        this.displayAmount = Utils.getAmountToDisplay(amount);
    }

    public void setAmount(int amount) {
        this.amount = amount;
        displayAmount = Utils.getAmountToDisplay(amount);
        notifyPropertyChanged(BR._all);

    }

    public void setDisplayAmount(String displayAmount) {
        this.displayAmount = displayAmount;
    }

    @Bindable
    public String getName() {
        return Utils.FirstNameCaps(name);

    }

    public int getAmount() {
        return amount;
    }

    @Bindable
    public String getDisplayAmount() {
        return displayAmount;
    }

    public void refresh() {
        setAmount(0);
    }
}
