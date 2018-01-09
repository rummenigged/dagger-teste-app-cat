package com.example.rummenigged.daggertest.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.rummenigged.daggertest.App;
import com.example.rummenigged.daggertest.R;
import com.example.rummenigged.daggertest.component.UserDIComponent;
import com.example.rummenigged.daggertest.domain.FavoritesUseCase;
import com.example.rummenigged.daggertest.repository.SharedPreferencesFavoritesRepository;
import com.example.rummenigged.daggertest.view.adapter.ImagesAdapter;

import java.util.List;

/**
 * Created by rummenigged on 04/01/18.
 */

public class FavoritesActivity extends AppCompatActivity {
    private static String TAG = "ImagesRvAdapter";

    static public void launch(Context context) {
        Intent intent = new Intent(context, FavoritesActivity.class);

        context.startActivity(intent);
    }

    private RecyclerView recyclerView;
    private ImagesAdapter rvAdapter;

    private FavoritesUseCase favoritesUseCase;
    private SharedPreferencesFavoritesRepository sharedPreferencesFavoritesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab =findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListActivity.launch(FavoritesActivity.this);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.favorites_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        rvAdapter = new ImagesAdapter(null);
        recyclerView.setAdapter(rvAdapter);

        favoritesUseCase = new FavoritesUseCase(UserDIComponent.get().getFavoritesRepository());
//        sharedPreferencesFavoritesRepository = new SharedPreferencesFavoritesRepository(this, userToken);
//        favoritesUseCase = new FavoritesUseCase(sharedPreferencesFavoritesRepository);
    }

    @Override
    protected void onResume() {
        super.onResume();
        favoritesUseCase.getFavorites(new FavoritesUseCase.Callback() {
            @Override
            public void favoriteUrlsUpdated(List<String> favoriteUrls) {
                Log.d(TAG, "Updated favorites: " + favoriteUrls.toString());
                rvAdapter.updateImageUrls(favoriteUrls);
            }
        });
    }

    @Override
    protected void onPause() {
        favoritesUseCase.clear();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        favoritesUseCase = null;
        super.onDestroy();
    }
}
