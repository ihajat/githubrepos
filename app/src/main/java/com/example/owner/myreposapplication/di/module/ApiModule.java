package com.example.owner.myreposapplication.di.module;


import com.example.owner.myreposapplication.api.RepoApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class ApiModule {
    @Provides
    @Singleton
    public RepoApi providesCatalogApi(Retrofit retrofit) {
        return retrofit.create(RepoApi.class);
    }
}
