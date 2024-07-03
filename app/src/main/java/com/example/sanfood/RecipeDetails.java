package com.example.sanfood;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecipeDetails extends AppCompatActivity {

    private ImageView logoImageView;
    private TextView recipeNameTextView;
    private ImageView recipeImageView;
    private TextView instructionsTextView;

    String selectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        recipeNameTextView = findViewById(R.id.recipeNameTextView);
        recipeImageView = findViewById(R.id.recipeImageView);
        instructionsTextView = findViewById(R.id.instructionsTextView);

        selectedItem = getIntent().getStringExtra("selectedItem");

        if (selectedItem != null) {
            // Do something with the selected item, e.g., display it in a TextView
            TextView recipeName = findViewById(R.id.recipeNameTextView);
            recipeName.setText(selectedItem);
        }
        else {
            // Handle the case where selectedItem is null
            Log.e("RecipeDetails", "No selectedItem found in the intent");
            Toast.makeText(this, "No selectedItem found in the intent", Toast.LENGTH_SHORT).show();
            finish(); // Finish the activity if no selectedItem is found
        }
        fetchRecipeDetails();
    }

    private void fetchRecipeDetails() {
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + selectedItem;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray meals = response.getJSONArray("meals");

                    if (meals.length() > 0) {
                        JSONObject meal = meals.getJSONObject(0);

                        String recipeName = meal.getString("strMeal");
                        recipeNameTextView.setText(recipeName);

                        String imageUrl = meal.getString("strMealThumb");
                        Picasso.get().load(imageUrl).into(recipeImageView);

                        String instructions = meal.getString("strInstructions");
                        instructionsTextView.setText(instructions);
                    } else {
                        // Handle the case where no meals are found
                        Log.e("RecipeDetails", "No meals found for " + selectedItem);
                        Toast.makeText(RecipeDetails.this, "No meals found for " + selectedItem, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("RecipeDetails", "Error parsing JSON", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RecipeDetails", "Error fetching recipe details", error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}