package com.example.rummenigged.daggertest.view;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.rummenigged.daggertest.R;
import com.example.rummenigged.daggertest.domain.CatImagesUseCase;
import com.example.rummenigged.daggertest.domain.FavoritesUseCase;
import com.example.rummenigged.daggertest.network.CatAPI;
import com.example.rummenigged.daggertest.network.RetrofitCatAPI;
import com.example.rummenigged.daggertest.repository.SharedPreferencesFavoritesRepository;
import com.example.rummenigged.daggertest.view.adapter.ImagesAdapter;
import com.plattysoft.leonids.ParticleSystem;

import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static String TAG = "List";
    private static String ARG_USER_TOKEN = "list-user-token";

    static public void launch(Context context, String userToken) {
        Intent intent = new Intent(context, ListActivity.class);
        intent.putExtra(ARG_USER_TOKEN, userToken);
        context.startActivity(intent);
    }

    private String userToken;
    private RecyclerView recyclerView;

    private SharedPreferencesFavoritesRepository sharedPreferencesFavoritesRepository;
    private FavoritesUseCase favoriteUseCase;
    private CatAPI catAPI;
    private CatImagesUseCase catImagesUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String extraUserToken = getIntent().getStringExtra(ARG_USER_TOKEN);
        if (extraUserToken != null) {
            userToken = extraUserToken;
        }
        Log.d(TAG, "UserToken: " + userToken);

        recyclerView = findViewById(R.id.list_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ImagesAdapter adapter = new ImagesAdapter(new ImagesAdapter.ImageOnClick() {
            @Override
            public void imageClicked(ImageView view, String url) {
                addUrlToUserFavoritesList(view, url);
            }
        });
        recyclerView.setAdapter(adapter);


        sharedPreferencesFavoritesRepository = new SharedPreferencesFavoritesRepository(this, userToken);
        favoriteUseCase = new FavoritesUseCase(sharedPreferencesFavoritesRepository);

        catAPI = new RetrofitCatAPI();
        catImagesUseCase = new CatImagesUseCase(catAPI);

        catImagesUseCase.getImagesUrls(new CatImagesUseCase.Callback() {
            @Override
            public void imagesUrls(List<String> urls) {
                adapter.updateImageUrls(urls);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FavoritesActivity.launch(this, userToken, true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addUrlToUserFavoritesList(ImageView view, String url) {
        boolean imageAdded = favoriteUseCase.addFavoriteUrl(url);

        int msgId;
        if (imageAdded) {
            msgId = R.string.list_user_favorite_url_added_success;
            new ParticleSystem(ListActivity.this, 500, R.mipmap.ic_launcher_round, 2000)
                    .setAcceleration(0.0005f, 90)
                    .setSpeedRange(0.2f, 0.5f)
                    .setRotationSpeedRange(90, 180)
                    .setInitialRotationRange(0, 180)
                    .setFadeOut(500)
                    .setScaleRange(0.1f, 1.0f)
                    .oneShot(view, 100);
        } else {
            msgId = R.string.list_user_favorite_url_already_in;
        }
        Snackbar.make(recyclerView, msgId, Snackbar.LENGTH_SHORT).show();
    }

}
