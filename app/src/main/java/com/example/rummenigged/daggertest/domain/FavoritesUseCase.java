package com.example.rummenigged.daggertest.domain;

import android.content.Context;

import com.example.rummenigged.daggertest.model.Favorite;
import com.example.rummenigged.daggertest.repository.FavoritesRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rummenigged on 04/01/18.
 */

public class FavoritesUseCase {
    public interface Callback {
        void favoriteUrlsUpdated(List<String> favoriteUrls);
    }

    private FavoritesRepository repo;

    public FavoritesUseCase(FavoritesRepository favoritesRepository) {
        this.repo = favoritesRepository;
    }

    /**
     * @param callback Callback returns a list of favorites once during registration and every time
     *                 the favorites are updated.
     */
    public void getFavorites(final Callback callback) {
        callback.favoriteUrlsUpdated(favoritesToUrls(repo.getFavorites()));

        repo.registerChangeListener(new FavoritesRepository.ChangeListener() {
            @Override
            public void onFavoritesChanged(List<Favorite> favorites) {
                callback.favoriteUrlsUpdated(favoritesToUrls(favorites));
            }
        });
    }

    /**
     * Clear needs to be called when the use case if no more needed.
     */
    public void clear() {
        repo.clearChangeListener();
    }

    private List<String> favoritesToUrls(List<Favorite> favorites) {
        ArrayList<String> urls = new ArrayList<>(favorites.size());
        for (Favorite favorite : favorites) {
            urls.add(favorite.getUrl());
        }
        return urls;
    }

    /**
     * @param url
     * @return True if the url was added successfully.
     */
    public Boolean addFavoriteUrl(String url) {
        if (url == null) {
            return false;
        }
        long timeNow = System.currentTimeMillis();
        Favorite model = new Favorite(timeNow, url);
        List<Favorite> currentList = repo.addFavorite(model);
        return currentList.contains(model);
    }
}
