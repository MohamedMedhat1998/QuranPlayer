package com.andalus.abomed7at55.quranplayer.Data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface FavoriteSuraDao {
    @Query("SELECT * FROM favoritesura")
    LiveData<List<FavoriteSura>> getAll();

    @Query("SELECT * FROM favoritesura WHERE uniqueId LIKE :id")
    FavoriteSura getById(String id);

    @Insert
    void insertAll(FavoriteSura... favoriteSuras);

    @Query("DELETE FROM favoritesura WHERE uniqueId LIKE :id")
    void deleteById(int id);

    @Delete
    void deleteFromFavorite(FavoriteSura favoriteSura);
}
