package com.rahmat.codelab.bakingapp.rest;

import com.rahmat.codelab.bakingapp.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by rahmat on 9/18/2017.
 */

public interface RecipeClient {
    @GET("baking.json")
    Call<ArrayList<Recipe>> getRecipe();
}
