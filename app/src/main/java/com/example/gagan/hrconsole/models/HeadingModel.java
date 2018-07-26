package com.example.gagan.hrconsole.models;

import android.databinding.Bindable;
import android.support.annotation.NonNull;

/**
 * Created by Gagan on 6/17/2018.
 */

public class HeadingModel extends BasicListModel {
    private final String header;

    @Bindable
    public String getHeader() {
        return header;
    }

    public HeadingModel(@NonNull String header) {
        this.header = header;
    }
}
