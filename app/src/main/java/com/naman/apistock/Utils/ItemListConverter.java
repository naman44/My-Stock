package com.naman.apistock.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.naman.apistock.Model.ItemList;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import androidx.room.TypeConverter;

public class ItemListConverter {

    static Gson gson = new Gson();

    @TypeConverter
    public static List<ItemList> stringToItemList(String data){
        if(data == null){
            return Collections.emptyList();
        }

        Type typeList = new TypeToken<List<ItemList>>() {}.getType();
        return gson.fromJson(data, typeList);
    }

    @TypeConverter
    public static String itemListtoString(List<ItemList> list){
        return gson.toJson(list);
    }
}
