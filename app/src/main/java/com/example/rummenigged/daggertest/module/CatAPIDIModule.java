package com.example.rummenigged.daggertest.module;

import com.example.rummenigged.daggertest.network.CatAPI;

/**
 * Created by rummenigged on 08/01/18.
 */

public interface CatAPIDIModule {
    CatAPI provideCatAPI();
}
