package com.gero.yummzyrecipe.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.gero.yummzyrecipe.models.Meal;
import com.gero.yummzyrecipe.ui.RecipeDetailFragment;

import java.util.ArrayList;

public class RecipePagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<Meal>recipes;

    public RecipePagerAdapter(@NonNull FragmentManager fm,ArrayList<Meal>recipes) {
        super(fm);
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return RecipeDetailFragment.newInstances(recipes.get(position));
    }

    @Override
    public int getCount() {
        return recipes.size();
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return recipes.get(position).getName();
    }
}
