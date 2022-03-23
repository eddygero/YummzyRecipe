package com.gero.yummzyrecipe.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gero.yummzyrecipe.R;
import com.gero.yummzyrecipe.adapters.CategoryListAdapter;
import com.gero.yummzyrecipe.adapters.LatestMealsAdapter;
import com.gero.yummzyrecipe.models.Category;
import com.gero.yummzyrecipe.models.Meal;
import com.gero.yummzyrecipe.services.EdamamService;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

   @SuppressLint("NonConstantResourceId")
   @BindView(R.id.categories_recyclerView)
    RecyclerView categoryRecyclerView;



   @BindView(R.id.latest_recyclerView)
   RecyclerView latestRecyclerView;

  private LatestMealsAdapter latestMealsAdapter;

    private CategoryListAdapter categoryListAdapter;

    private ArrayList<Meal> latestMeals;


    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmer_view_container)
    ShimmerFrameLayout mShimmerViewContainer;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.shimmer_view_container1)
    ShimmerFrameLayout mShimmerViewContainer1;

    FirebaseUser user;

    ArrayList<Category> categories;

    public HomeActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);


        user = FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);


        //Update header email address with the email from previous activity
        View headerView =navigationView.getHeaderView(0);
        TextView emailTextView =  headerView.findViewById(R.id.textView_email);
        emailTextView.setText(user.getEmail());

        TextView name = headerView.findViewById(R.id.name);
        name.setText(user.getDisplayName());


        getMealCategories();
        getLatestMeals();
    }

    private void getMealCategories() {
        final EdamamService edamamService = new EdamamService();
        EdamamService.getAllCategories(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                categories = edamamService.processCategoryResults(response);

                HomeActivity.this.runOnUiThread(() -> {
                    categoryListAdapter = new CategoryListAdapter(categories,getApplicationContext());
                    categoryRecyclerView.setAdapter(categoryListAdapter);
                    //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
                    categoryRecyclerView.setLayoutManager(layoutManager);
                   categoryRecyclerView.setHasFixedSize(true);

                    mShimmerViewContainer1.stopShimmer();
                    mShimmerViewContainer1.setVisibility(View.GONE);


                });
            }
        });
    }

    public void getLatestMeals()
    {
        final EdamamService edamamService = new EdamamService();
        EdamamService.getLatestMeals(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {

                latestMeals = edamamService.processLatestMealsResults(response);

                HomeActivity.this.runOnUiThread(() -> {
                    latestMealsAdapter = new LatestMealsAdapter(getApplicationContext(),latestMeals);
                    latestRecyclerView.setAdapter(latestMealsAdapter);
                    //LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                    latestRecyclerView.setLayoutManager(layoutManager);
                    latestRecyclerView.setHasFixedSize(true);

                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);

                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        }
        else if (id == R.id.nav_logout)
        {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(HomeActivity.this, WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if(id == R.id.nav_favourite)
        {
            startActivity(new Intent(HomeActivity.this,SavedRecipeListActivity.class));
        }
        else if(id == R.id.nav_exit)
        {
            Toast.makeText(this, "Bye", Toast.LENGTH_SHORT).show();
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
