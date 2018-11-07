package com.example.owner.myreposapplication.application;

import android.app.Application;

import com.example.owner.myreposapplication.di.AppComponent;
import com.example.owner.myreposapplication.di.DaggerAppComponent;
import com.example.owner.myreposapplication.di.module.ApiModule;
import com.example.owner.myreposapplication.di.module.AppModule;
import com.example.owner.myreposapplication.di.module.DaoModule;
import com.example.owner.myreposapplication.di.module.NetModule;
import com.example.owner.myreposapplication.di.module.RepositoryModule;


public class MyApplication extends Application {

    private static AppComponent appComponent;

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public void onCreate(){
        super.onCreate();

        appComponent = DaggerAppComponent.builder().
                appModule(new AppModule(this)).
                apiModule(new ApiModule()).
                netModule(new NetModule("https://api.github.com")).
                daoModule(new DaoModule()).
                repositoryModule(new RepositoryModule()).
                build();

    }
}
