package com.gero.yummzyrecipe.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.gero.yummzyrecipe.R;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);
    }

}
