package com.naman.apistock.DAO;

import com.naman.apistock.Model.Partner;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PartnerDao {

    @Insert
    void insertPartner(Partner partner);

    @Query("select * from partner")
    List<Partner> fetchAllPartners();

    @Query("select * from partner where type=:type")
    List<Partner> fetchPartnerByType(String type);

    @Query("delete from partner where id=:id")
    void deletePartner(int id);

    @Query("select name from partner where type=:type")
    List<String> fetchPartnerNameList(String type);

    @Update
    void updatePartner(Partner partner);
}
