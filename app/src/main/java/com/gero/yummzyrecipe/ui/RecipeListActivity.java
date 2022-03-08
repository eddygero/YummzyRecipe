package com.gero.yummzyrecipe.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.gero.yummzyrecipe.R;
import com.gero.yummzyrecipe.R;
import com.gero.yummzyrecipe.adapters.RecipeListAdapter;
import com.gero.yummzyrecipe.adapters.RecipeListAdapter;

import com.gero.yummzyrecipe.models.Recipe;

import com.gero.yummzyrecipe.services.EdamamService;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RecipeListActivity extends AppCompatActivity {

    private static final String TAG = "RecipeListActivity";
    @BindView(R.id.recipeListRecycler)
    RecyclerView recyclerView;

    RecipeListAdapter recipeListAdapter;

    ArrayList<Recipe> recipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);

        //get category from home activity
        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        getRecipes(category);

    }

    private void getRecipes(String category)
    {

        final EdamamService edamamService = new EdamamService();
        EdamamService.findRecipes(category, new Callback() {
            @Override
            public void onFailure(@NonNull Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                recipes = edamamService.processResults(response);

                RecipeListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recipeListAdapter = new RecipeListAdapter(getApplicationContext(),recipes);
                        recyclerView.setAdapter(recipeListAdapter);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setHasFixedSize(true);

                    }
                });

            }
        });
    }
}