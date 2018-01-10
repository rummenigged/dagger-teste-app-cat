package com.example.rummenigged.daggertest.component;

import android.content.Context;

import com.example.rummenigged.daggertest.module.AppDIModule;
import com.example.rummenigged.daggertest.module.CatAPIDIModule;
import com.example.rummenigged.daggertest.network.CatAPI;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rummenigged on 08/01/18.
 */

@Singleton
@Component(modules = { AppDIModule.class, CatAPIDIModule.class})
public abstract class AppDIComponent {
    private static AppDIComponent instance;

    public static AppDIComponent get(){
        return AppDIComponent.instance;
    }

    public static void initialize(AppDIModule appDIModule, CatAPIDIModule catAPIDIModule){
        if (AppDIComponent.get() != null){
            throw new RuntimeException("AppDiComponent already initialized.");
        }else{
            AppDIComponent.instance = DaggerAppDIComponent
                                        .builder()
                                        .appDIModule(appDIModule)
                                        .catAPIDIModule(catAPIDIModule)
                                        .build();
        }
    }

    abstract public Context getAppContext();

    abstract public CatAPI getCatAPI();
}
