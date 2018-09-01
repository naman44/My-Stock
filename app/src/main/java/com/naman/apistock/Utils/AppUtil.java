package com.naman.apistock.Utils;

import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppUtil {

    private static String dateFormat = "yyyy/MM/dd";

    public static int getSpinIndex(Spinner spin, String selection){
        for(int i=0; i<spin.getCount(); i++){
            if(spin.getItemAtPosition(i).toString().equalsIgnoreCase(selection)){
                return i;
            }
        }
        return -1;
    }

    public static String formatDate(Date date){
        SimpleDateFormat form = new SimpleDateFormat(dateFormat);
        return form.format(date);
    }
}
