package com.example.owner.myreposapplication.di.module;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    @Singleton
    public Executor provideExecutor() {
        return r -> new Thread(r).start();
    }
}
