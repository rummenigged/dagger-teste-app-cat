package com.example.rummenigged.daggertest.module;

import com.example.rummenigged.daggertest.component.AppDIComponent;
import com.example.rummenigged.daggertest.repository.FavoritesRepository;
import com.example.rummenigged.daggertest.repository.SharedPreferencesFavoritesRepository;

/**
 * Created by rummenigged on 08/01/18.
 */

public class SharedPrefFavoritesRepoDIModule implements FavoritesRepoDIModule {
    private AppDIComponent appDIComponent;
    private String userToken;

    public SharedPrefFavoritesRepoDIModule(AppDIComponent appDIComponent, String userToken){
        this.appDIComponent = appDIComponent;
        this.userToken = userToken;
    }

    @Override
    public AppDIComponent getAppDIComponent() {
        return this.appDIComponent;
    }

    @Override
    public FavoritesRepository provideFavoritesRepository() {
        return new SharedPreferencesFavoritesRepository(
                appDIComponent.getAppContext()
                , provideUserToken());
    }

    public String provideUserToken(){
        return this.userToken;
    }
}
