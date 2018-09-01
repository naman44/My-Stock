package com.naman.apistock.Utils;

import java.util.Date;

import androidx.room.TypeConverter;

public class DateConverter {

    @TypeConverter
    public static Date toDate(Long value){
        return value == null?null:new Date(value);
    }

    @TypeConverter
    public static Long toLong(Date date){
        return date == null?null:date.getTime();
    }
}
