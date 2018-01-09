package com.example.rummenigged.daggertest;

import android.app.Application;
import android.content.Context;

import com.example.rummenigged.daggertest.component.AppDIComponent;
import com.example.rummenigged.daggertest.module.AppDIModule;
import com.example.rummenigged.daggertest.module.CachedRetrofitCatAPIDIModule;

/**
 * Created by rummenigged on 05/01/18.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppDIModule appDIModule = new AppDIModule() {
            @Override
            public Context provideAppContext() {
                return App.this;
            }
        };
        AppDIComponent.initialize(appDIModule, new CachedRetrofitCatAPIDIModule());
    }

}
