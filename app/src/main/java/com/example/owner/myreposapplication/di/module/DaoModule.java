package com.example.owner.myreposapplication.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.example.owner.myreposapplication.dao.RepoDao;
import com.example.owner.myreposapplication.dao.RepoDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DaoModule {
    @Provides
    @Singleton
    public RepoDao provideRepoDao(Application app) {
        RepoDatabase db = Room.databaseBuilder(app,
                RepoDatabase.class, "repo-db").build();
        return db.repoDao();
    }
}
