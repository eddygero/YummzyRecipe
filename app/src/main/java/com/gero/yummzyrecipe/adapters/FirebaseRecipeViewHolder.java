package com.gero.yummzyrecipe.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.gero.yummzyrecipe.R;
import com.gero.yummzyrecipe.models.Meal;
import com.gero.yummzyrecipe.util.ItemTouchHelperViewHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class FirebaseRecipeViewHolder  extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {

    View mView;
    Context mContext;
    FirebaseUser user;
    public ImageView image;

    public FirebaseRecipeViewHolder(View itemView) {
        super(itemView);
        mView = itemView;

        mContext = itemView.getContext();
//        itemView.setOnClickListener(this);
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void bindRestaurant(Meal recipe) {
        TextView name = mView.findViewById(R.id.recipe_name);
        image = mView.findViewById(R.id.recipe_image);

        Picasso.get().load(recipe.getImage()).into(image);
        name.setText(recipe.getName());
    }

    @Override
    public void onItemSelected() {
        itemView.animate()
                .alpha(0.7f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
    }

    @Override
    public void onItemClear() {
        itemView.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f);
    }

//    @Override
//    public void onClick(View view) {
//        final ArrayList<Meal> recipes = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RECIPES).child(user.getUid());
//
//        ref.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    recipes.add(snapshot.getValue(Meal.class));
//                }
//
//                int itemPosition = getLayoutPosition();
//
//                Intent intent = new Intent(mContext, RecipeDetailActivity.class);
//                intent.putExtra("position", itemPosition + "");
//                intent.putExtra("recipes", Parcels.wrap(recipes));
//
//                mContext.startActivity(intent);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }
}