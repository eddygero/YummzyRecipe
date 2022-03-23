package com.gero.yummzyrecipe.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gero.yummzyrecipe.Constants;
import com.gero.yummzyrecipe.R;
import com.gero.yummzyrecipe.models.Category;
import com.gero.yummzyrecipe.ui.RecipeListActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder> {
    private final ArrayList<Category> categories;
    private Context context;

    public CategoryListAdapter(ArrayList<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bindCategory(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.category_name)
        TextView categoryTV;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.category_image)
        ImageView categoryImageView;

        private final Context context;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this,itemView);
            context = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindCategory(Category category)
        {
            categoryTV.setText(category.getName());
            Picasso.get().load(category.getUrl()).into(categoryImageView);

        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();
            Intent intent = new Intent(context, RecipeListActivity.class);
            intent.putExtra("category", Parcels.wrap(categories.get(itemPosition)));
            Constants.CATEGORY =categories.get(itemPosition).getName();
//            intent.putExtra("category",categories.get(itemPosition).getName());
            context.startActivity(intent);
        }
    }
}
