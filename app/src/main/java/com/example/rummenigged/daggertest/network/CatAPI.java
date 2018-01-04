package com.example.rummenigged.daggertest.network;

import com.example.rummenigged.daggertest.model.CatImages;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rummenigged on 04/01/18.
 */

public interface CatAPI {
    interface Callback {
        void response(CatImages response);
    }

    void getCatsWithHats(Callback callback);
}

