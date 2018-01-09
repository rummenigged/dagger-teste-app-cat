package com.example.rummenigged.daggertest.component;

import android.content.Context;

import com.example.rummenigged.daggertest.module.AppDIModule;
import com.example.rummenigged.daggertest.module.CatAPIDIModule;
import com.example.rummenigged.daggertest.network.CatAPI;

/**
 * Created by rummenigged on 08/01/18.
 */

public class AppDIComponent {
    private static AppDIComponent instance;
    private AppDIModule appDIModule;
    private CatAPIDIModule catAPIDIModule;
    private Context appContext;
    private CatAPI catAPI;

    public static AppDIComponent get(){
        return AppDIComponent.instance;
    }

    public static void initialize(AppDIModule appDIModule, CatAPIDIModule catAPIDIModule){
        if (AppDIComponent.get() != null){
            throw new RuntimeException("AppDiComponent already initialized.");
        }else{
            AppDIComponent.instance = new AppDIComponent(appDIModule, catAPIDIModule);
        }
    }

    public AppDIComponent(AppDIModule appDIModule, CatAPIDIModule catAPIDIModule){
        this.appDIModule = appDIModule;
        this.catAPIDIModule = catAPIDIModule;
    }

    public Context getAppContext(){
        if (appContext == null){
            appContext = appDIModule.provideAppContext();
        }

        return appContext;
    }

    public CatAPI getCatAPI(){
        if (catAPI == null){
            catAPI = catAPIDIModule.provideCatAPI();
        }

        return catAPI;
    }

}
