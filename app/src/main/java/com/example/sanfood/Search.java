package com.example.sanfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import android.view.View;

public class Search extends AppCompatActivity {

    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> items;
    private TextView noResultsMessage;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.list_view);
        noResultsMessage = findViewById(R.id.noResultsMessage); // Initialize noResultsMessage

        items = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
                String selectedItem = (String) parent.getItemAtPosition(position);

                Intent intent = new Intent(Search.this, RecipeDetails.class);
                intent.putExtra("selectedItem", selectedItem);
                startActivity(intent);
        });

        setupSearchView();
        makeJsonObjectRequest("defaultQuery");
    }

    private void filter(String query) {
        adapter.getFilter().filter(query);
    }
    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                makeJsonObjectRequest(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void makeJsonObjectRequest(String query) {
        searchView.setQueryHint("Enter your search query here");
        String url = "https://www.themealdb.com/api/json/v1/1/search.php?s=" + query;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("meals")) {
                        JSONArray mealsArray = response.getJSONArray("meals");
                        items.clear();

                        for (int i = 0; i < mealsArray.length(); i++) {
                            JSONObject mealObject = mealsArray.getJSONObject(i);
                            String strMeal = mealObject.getString("strMeal");
                            items.add(strMeal);
                        }

                        adapter.notifyDataSetChanged();
                    } else {
                        searchView.setQuery("", false);
                        items.clear();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}