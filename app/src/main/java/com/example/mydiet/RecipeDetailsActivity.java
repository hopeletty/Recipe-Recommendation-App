package com.example.mydiet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mydiet.Adapters.IngredientsAdapter;
import com.example.mydiet.Adapters.InstructionsAdapter;
import com.example.mydiet.Adapters.SimilarRecipeAdapter;
import com.example.mydiet.Listeners.InstructionsListener;
import com.example.mydiet.Listeners.RecipeClickListener;
import com.example.mydiet.Listeners.RecipeDetailsListener;
import com.example.mydiet.Listeners.SimilarRecipesListener;
import com.example.mydiet.Models.InstructionsResponse;
import com.example.mydiet.Models.RecipeDetailsResponse;
import com.example.mydiet.Models.SimilarRecipeResponse;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecipeDetailsActivity extends AppCompatActivity {
    int id;
    TextView meal_name, meal_source, meal_summary;
    RecyclerView recycler_meal_ingredients, recycler_meal_similar, recycler_meal_instructions;
    ImageView meal_image;
    RequestManager manager;
    ProgressDialog dialog;
    IngredientsAdapter ingredientsAdapter;
    SimilarRecipeAdapter similarRecipeAdapter;
    InstructionsAdapter instructionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        findViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        manager = new RequestManager(this);
        manager.getRecipeDetails(recipeDetailsListener, id);
        manager.getSimilarRecipes(similarRecipesListener, id);
        manager.getInstructions(instructionsListener, id);
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Details...");
        dialog.show();
    }

    @SuppressLint("CutPasteId")
    private void findViews() {
        meal_image = findViewById(R.id.meal_image);
        meal_name = findViewById(R.id.meal_name);
        meal_source = findViewById(R.id.meal_source);
        meal_summary = findViewById(R.id.meal_summary);
        recycler_meal_ingredients = findViewById(R.id.recycler_meal_ingredients);
        recycler_meal_similar = findViewById(R.id.recycler_meal_similar);
        recycler_meal_instructions = findViewById(R.id.recycler_meal_instructions);
    }
    private final RecipeDetailsListener recipeDetailsListener = new RecipeDetailsListener() {
        @Override
        public void didFetch(RecipeDetailsResponse response, String message) {
            dialog.dismiss();
            meal_name.setText(response.title);
            meal_source.setText(response.sourceName);
            meal_summary.setText(response.summary);
            Picasso.get().load(response.image).into(meal_image);

            recycler_meal_ingredients.setHasFixedSize(true);
            recycler_meal_ingredients.setLayoutManager(new LinearLayoutManager(RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            ingredientsAdapter = new IngredientsAdapter(RecipeDetailsActivity.this, response.extendedIngredients);
            recycler_meal_ingredients.setAdapter(ingredientsAdapter);

        }

        @Override
        public void didError(String message) {
            Toast.makeText(RecipeDetailsActivity.this, message, Toast.LENGTH_SHORT).show();

        }
    };
    private final SimilarRecipesListener similarRecipesListener = new SimilarRecipesListener() {
        @Override
        public void didFetch(List<SimilarRecipeResponse> response, String message) {
            recycler_meal_similar.setHasFixedSize(true);
            recycler_meal_similar.setLayoutManager(new LinearLayoutManager
                    (RecipeDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
            similarRecipeAdapter = new SimilarRecipeAdapter(RecipeDetailsActivity.this, response, recipeClickListener);
            recycler_meal_similar.setAdapter(similarRecipeAdapter);

        }

        @Override
        public void didError(String message) {

        }
    };
    private final RecipeClickListener recipeClickListener = new RecipeClickListener() {
        @Override
        public void onRecipeClicked(String id) {
            startActivity(new Intent(RecipeDetailsActivity.this, RecipeDetailsActivity.class)
            .putExtra("id", id));
        }
    };

    private final InstructionsListener instructionsListener = new InstructionsListener() {
        @Override
        public void didFetch(List<InstructionsResponse> response, String message) {
            recycler_meal_instructions.setHasFixedSize(true);
            recycler_meal_instructions.setLayoutManager(new LinearLayoutManager
                    (RecipeDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
            instructionsAdapter = new InstructionsAdapter(RecipeDetailsActivity.this, response);
            recycler_meal_instructions.setAdapter(instructionsAdapter);


        }

        @Override
        public void didError(String message) {

        }
    };
}