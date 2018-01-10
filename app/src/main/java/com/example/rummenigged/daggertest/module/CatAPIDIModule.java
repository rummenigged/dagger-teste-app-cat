package com.example.rummenigged.daggertest.module;

import com.example.rummenigged.daggertest.exception.EmptyModuleException;
import com.example.rummenigged.daggertest.network.CatAPI;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rummenigged on 08/01/18.
 */

@Module
public class CatAPIDIModule {
    @Provides
    @Singleton
    public CatAPI provideCatAPI(){
        throw new EmptyModuleException();
    }
}
