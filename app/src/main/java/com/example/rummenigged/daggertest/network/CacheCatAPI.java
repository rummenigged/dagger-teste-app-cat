package com.example.rummenigged.daggertest.network;

import com.example.rummenigged.daggertest.model.CatImages;

/**
 * Created by rummenigged on 05/01/18.
 */

public class CacheCatAPI implements CatAPI {

    private static String TAG = CacheCatAPI.class.getSimpleName();

    private CatAPI service;
    private CatImages lastResponse;

    public CacheCatAPI(CatAPI service){
        this.service = service;
    }

    @Override
    public void getCatsWithHats(final Callback callback) {
        if (lastResponse != null){
            callback.response(lastResponse);
        }else{
            service.getCatsWithHats(new Callback() {
                @Override
                public void response(CatImages response) {
                    lastResponse = response;
                    callback.response(response);
                }
            });
        }
    }
}
