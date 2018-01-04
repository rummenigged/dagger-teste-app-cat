package com.example.rummenigged.daggertest.network;

import android.util.Log;

import com.example.rummenigged.daggertest.model.CatImages;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

import static android.content.ContentValues.TAG;

/**
 * Created by rummenigged on 04/01/18.
 */

public class RetrofitCatAPI implements CatAPI{
    interface RetrofitCatService {
        @GET("images/get?format=xml&results_per_page=20&category=hats")
        Call<CatImages> listCatsWithHat();
    }

    @Override
    public void getCatsWithHats(final Callback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thecatapi.com/api/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        RetrofitCatService retrofitCatService = retrofit.create(RetrofitCatService.class);
        retrofitCatService.listCatsWithHat().enqueue(new retrofit2.Callback<CatImages>() {
            @Override
            public void onResponse(Call<CatImages> call, Response<CatImages> response) {
                callback.response(response.body());
            }

            @Override
            public void onFailure(Call<CatImages> call, Throwable t) {
                Log.e(TAG, "Error fetching cat images", t);
                callback.response(null);
            }
        });
    }
}
