package com.example.owner.myreposapplication.dao;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.owner.myreposapplication.dto.RepoDto;


@Database(entities = {RepoDto.class}, version = 1, exportSchema = false)
public abstract class RepoDatabase extends RoomDatabase {
    public abstract RepoDao repoDao();
}

