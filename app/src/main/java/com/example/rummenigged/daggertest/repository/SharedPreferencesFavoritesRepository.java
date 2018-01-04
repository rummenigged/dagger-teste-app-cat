package com.example.rummenigged.daggertest.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.rummenigged.daggertest.R;
import com.example.rummenigged.daggertest.domain.FavoritesUseCase;
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

public class SharedPreferencesFavoritesRepository implements FavoritesRepository {
    private static String SP_USER_FAVORITES_KEY = "user-favorites-urls-%s";
    private static String TAG = "SharedPrefFavoritesRepo";

    private Context context;
    private String userToken;
    private ChangeListener changeListener;
    private SharedPreferences.OnSharedPreferenceChangeListener sharedPrefListener;

    public SharedPreferencesFavoritesRepository(Context context, String userToken) {
        this.context = context;
        this.userToken = userToken;
    }

    @Override
    public List<Favorite> getFavorites() {
        SharedPreferences pref = getPref();
        String prefKey = getFavoritesKey();
        Set<String> entriesSet = pref.getStringSet(prefKey, new HashSet<String>());

        ArrayList<Favorite> favorites = new ArrayList<>(entriesSet.size());
        for (String entry : entriesSet) {
            String[] decoded = entry.split(";");
            favorites.add(new Favorite(Long.valueOf(decoded[1]), decoded[0]));
        }

        Collections.sort(favorites, new Comparator<Favorite>() {
            @Override
            public int compare(Favorite o1, Favorite o2) {
                return (int) (o2.getTimeAdded() - o1.getTimeAdded());
            }
        });

        return favorites;
    }

    @Override
    public List<Favorite> addFavorite(Favorite model) {
        List<Favorite> oldModels = getFavorites();

        boolean hasUrl = false;
        for (Favorite entry : oldModels) {
            if (entry.getUrl().equals(model.getUrl())) {
                hasUrl = true;
                break;
            }
        }

        if (hasUrl) {
            return oldModels;
        }

        ArrayList<Favorite> newList = new ArrayList<>(oldModels);
        newList.add(model);
        saveFavorites(newList);

        return newList;
    }

    @Override
    public void registerChangeListener(final ChangeListener listener) {
        if (this.changeListener != null) {
            throw new RuntimeException("Listener already registered.");
        }
        this.changeListener = listener;
        this.sharedPrefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                Log.d(TAG, "Key changed: " + key);
                String prefKey = String.format(SP_USER_FAVORITES_KEY, userToken);
                if (key.equals(prefKey)) {
                    changeListener.onFavoritesChanged(getFavorites());
                }
            }
        };
        getPref().registerOnSharedPreferenceChangeListener(this.sharedPrefListener);
    }

    @Override
    public void clearChangeListener() {
        this.changeListener = null;
        if (this.sharedPrefListener != null) {
            getPref().unregisterOnSharedPreferenceChangeListener(this.sharedPrefListener);
            this.sharedPrefListener = null;
        }
    }

    private SharedPreferences getPref() {
        String prefName = context.getString(R.string.pref_key_user_data);
        return context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    private String getFavoritesKey() {
        return String.format(SP_USER_FAVORITES_KEY, userToken);
    }

    private void saveFavorites(ArrayList<Favorite> newList) {
        HashSet<String> newEntries = new HashSet<>(newList.size());

        for (Favorite entry : newList) {
            newEntries.add(entry.getUrl() + ";" + entry.getTimeAdded());
        }

        getPref().edit().putStringSet(getFavoritesKey(), newEntries).apply();
    }
}
