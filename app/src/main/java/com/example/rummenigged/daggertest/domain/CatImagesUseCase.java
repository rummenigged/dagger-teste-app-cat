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

    private CatAPI catAPI;

    public CatImagesUseCase(CatAPI catAPI) {
        this.catAPI = catAPI;
    }

    public void getImagesUrls(final Callback callback) {
        catAPI.getCatsWithHats(new CatAPI.Callback() {
            @Override
            public void response(CatImages response) {
                ArrayList<String> imageUrls = new ArrayList<>();
                if (response != null) {
                    for (CatImage img : response.catImages) {
                        imageUrls.add(img.url);
                    }
                }
                callback.imagesUrls(imageUrls);
            }
        });
    }
}
