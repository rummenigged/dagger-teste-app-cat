package com.example.rummenigged.daggertest.network;

import com.example.rummenigged.daggertest.model.CatImages;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rummenigged on 04/01/18.
 */

public interface CatAPI {
    @GET("images/get?format=xml&results_per_page=20&category=hats")
    Call<CatImages> listCatsWithHat();
}

