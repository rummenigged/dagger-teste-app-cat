package com.example.rummenigged.daggertest.module;

import com.example.rummenigged.daggertest.component.AppDIComponent;
import com.example.rummenigged.daggertest.repository.FavoritesRepository;

/**
 * Created by rummenigged on 08/01/18.
 */

public interface FavoritesRepoDIModule {
    AppDIComponent getAppDIComponent();

    FavoritesRepository provideFavoritesRepository();
}
