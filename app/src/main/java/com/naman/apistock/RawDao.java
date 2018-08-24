package com.naman.apistock;

import com.naman.apistock.Model.RawMaterial;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface RawDao {

    @Insert
    long insertRawData(RawMaterial raw);

    @Query("select * from rawMaterial")
    List<RawMaterial> fetchAllRawData();

    @Query("delete from rawmaterial where rawId=:id")
    int deleteRaw(int id);

    @Update
    int updateRawData(RawMaterial raw);
}
