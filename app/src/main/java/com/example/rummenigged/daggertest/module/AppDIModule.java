package com.example.rummenigged.daggertest.module;

import android.content.Context;

import com.example.rummenigged.daggertest.exception.EmptyModuleException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rummenigged on 08/01/18.
 */
@Module
public class AppDIModule {
    @Provides
    @Singleton
    public Context provideAppContext(){
        throw new EmptyModuleException();
    }
}
