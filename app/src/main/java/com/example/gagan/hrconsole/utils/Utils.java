package com.example.gagan.hrconsole.utils;

import android.support.annotation.NonNull;

/**
 * Created by Gagan on 6/17/2018.
 */

public class Utils {
    public static String getAmountToDisplay(int amount) {
        return amount + "";
    }

    @NonNull
    public static String FirstNameCaps(String name) {
        return name.replace(name.charAt(0), Character.toUpperCase(name.charAt(0)));
    }
}
