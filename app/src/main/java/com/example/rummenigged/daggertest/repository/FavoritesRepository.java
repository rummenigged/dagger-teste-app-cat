package com.example.rummenigged.daggertest.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.rummenigged.daggertest.R;
import com.example.rummenigged.daggertest.model.Favorite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rummenigged on 04/01/18.
 */

public interface FavoritesRepository {
    interface ChangeListener {
        void onFavoritesChanged(List<Favorite> favorites);
    }

    /**
     * @return A list of favorites sorted by the time it was added.
     */
    List<Favorite> getFavorites();

    /**
     * @param model
     * @return A list of favorites sorted by the time it was added, with the newly added favorite.
     */
    List<Favorite> addFavorite(Favorite model);

    void registerChangeListener(final ChangeListener listener);

    void clearChangeListener();
}
