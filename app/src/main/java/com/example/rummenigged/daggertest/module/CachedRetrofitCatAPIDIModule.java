package com.example.rummenigged.daggertest.module;

import com.example.rummenigged.daggertest.network.CacheCatAPI;
import com.example.rummenigged.daggertest.network.CatAPI;
import com.example.rummenigged.daggertest.network.RetrofitCatAPI;

/**
 * Created by rummenigged on 08/01/18.
 */

public class CachedRetrofitCatAPIDIModule extends CatAPIDIModule {

    @Override
    public CatAPI provideCatAPI() {
        return provideCatAPICached(provideCatAPIRetrofit());
    }

    public CatAPI provideCatAPIRetrofit(){
        return new RetrofitCatAPI();
    }

    public CatAPI provideCatAPICached(CatAPI catAPI){
        return new CacheCatAPI(catAPI);
    }

}
