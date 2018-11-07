package com.example.owner.myreposapplication.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.owner.myreposapplication.dto.RepoDto;

import java.util.List;

@Dao
public interface RepoDao {

    @Query("SELECT * FROM RepoDto LIMIT :limit OFFSET :offset")
    LiveData<List<RepoDto>> get(int offset, int limit);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(RepoDto... repoDtos);

    @Delete
    void delete(RepoDto RepoDto);

    @Query("DELETE FROM RepoDto")
    void deleteAll();
}
