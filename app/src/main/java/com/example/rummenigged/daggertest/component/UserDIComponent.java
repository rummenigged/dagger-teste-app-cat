package com.example.rummenigged.daggertest.component;

import com.example.rummenigged.daggertest.module.FavoritesRepoDIModule;
import com.example.rummenigged.daggertest.network.CatAPI;
import com.example.rummenigged.daggertest.repository.FavoritesRepository;

/**
 * Created by rummenigged on 08/01/18.
 */

public class UserDIComponent {
    private static UserDIComponent instance;
    private FavoritesRepoDIModule module;
    private FavoritesRepository favoritesRepository;

    public static UserDIComponent get(){
        return instance;
    }

    public static void initialize(FavoritesRepoDIModule module){
        if (UserDIComponent.get() != null){
            throw new RuntimeException("UserDIComponent already iitialized.");
        }
        UserDIComponent.instance = new UserDIComponent(module);
    }

    public UserDIComponent(FavoritesRepoDIModule module){
        this.module = module;
    }

    public CatAPI getCatAPIService(){
        return this.module.getAppDIComponent().getCatAPI();
    }

    public FavoritesRepository getFavoritesRepository(){
        if (favoritesRepository == null){
            favoritesRepository = module.provideFavoritesRepository();
        }
        return favoritesRepository;
    }

    public void close(){
        if (favoritesRepository != null){
            favoritesRepository.clearChangeListener();
        }
    }
}
