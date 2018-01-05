package com.example.rummenigged.daggertest;

import android.app.Application;

import com.example.rummenigged.daggertest.network.CacheCatAPI;
import com.example.rummenigged.daggertest.network.CatAPI;
import com.example.rummenigged.daggertest.network.RetrofitCatAPI;
import com.example.rummenigged.daggertest.repository.FavoritesRepository;
import com.example.rummenigged.daggertest.repository.SharedPreferencesFavoritesRepository;

/**
 * Created by rummenigged on 05/01/18.
 */

public class App extends Application {
    private static CatAPI catAPI;
    private static FavoritesRepository favoritesRepository;

    public static CatAPI getCAtAPIService(){
        return App.catAPI;
    }

    public static FavoritesRepository getFavoritesRepository(){
        return App.favoritesRepository;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CatAPI catAPIService = new RetrofitCatAPI();
        CatAPI catAPICached = new CacheCatAPI(catAPIService);
        App.catAPI = catAPICached;
    }

    public void initializeFavoritesRepository(String userToken){
        if (App.favoritesRepository != null){
            throw new RuntimeException("FavoritesRepository already initialized.");
        }
        App.favoritesRepository = new SharedPreferencesFavoritesRepository(this, userToken);
    }

    public void destroyFavoritesRepository(){
        if (App.favoritesRepository != null){
            App.favoritesRepository.clearChangeListener();
            App.favoritesRepository = null;
        }
    }
}
