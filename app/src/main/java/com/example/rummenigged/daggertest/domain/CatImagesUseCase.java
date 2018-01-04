package com.example.rummenigged.daggertest.domain;

import android.util.Log;

import com.example.rummenigged.daggertest.model.CatImage;
import com.example.rummenigged.daggertest.model.CatImages;
import com.example.rummenigged.daggertest.network.CatAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

/**
 * Created by rummenigged on 04/01/18.
 */

public class CatImagesUseCase {
    public interface Callback {
        void imagesUrls(List<String> urls);
    }

    private static String TAG = "CatImagesUseCase";

    public void getImagesUrls(final Callback callback) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://thecatapi.com/api/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        CatAPI retrofitTheCatApi = retrofit.create(CatAPI.class);
        retrofitTheCatApi.listCatsWithHat().enqueue(new retrofit2.Callback<CatImages>() {
            @Override
            public void onResponse(Call<CatImages> call, Response<CatImages> response) {
                ArrayList<String> imageUrls = new ArrayList<>();
                if (response.body().catImages != null) {
                    for (CatImage img : response.body().catImages) {
                        Log.d(TAG, "Found: " + img.url);
                        imageUrls.add(img.url);
                    }
                }
                callback.imagesUrls(imageUrls);
            }

            @Override
            public void onFailure(Call<CatImages> call, Throwable t) {
                Log.e(TAG, "Error fetching cat images", t);
            }
        });
    }
}
