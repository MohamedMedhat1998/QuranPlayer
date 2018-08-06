package com.andalus.abomed7at55.quranplayer.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;
@Dao
public interface OfflineSuraDao {
    @Query("SELECT * FROM offlinesura")
    List<OfflineSura> getAll();

    @Query("SELECT * FROM offlinesura WHERE uniqueId LIKE :id")
    OfflineSura getById(String id);

    @Insert
    void insertAll(OfflineSura... offlineSuras);

    @Query("DELETE FROM OfflineSura WHERE uniqueId LIKE :id")
    void deleteById(int id);
}
