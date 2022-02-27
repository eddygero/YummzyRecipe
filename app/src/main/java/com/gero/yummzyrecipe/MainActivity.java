package com.gero.yummzyrecipe;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


@BindView(R.id.findRecipesButton) Button mFindRecipesButton;
    @BindView(R.id.foodTypeEditText) EditText mFoodTypeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFindRecipesButton.setOnClickListener((View.OnClickListener) this);
    }

}

