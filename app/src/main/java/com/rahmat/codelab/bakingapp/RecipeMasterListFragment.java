package com.rahmat.codelab.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rahmat.codelab.bakingapp.IdlingResource.SimpleIdlingResource;
import com.rahmat.codelab.bakingapp.adapter.RecipeMasterListAdapter;
import com.rahmat.codelab.bakingapp.model.Recipe;
import com.rahmat.codelab.bakingapp.rest.RecipeClient;
import com.rahmat.codelab.bakingapp.rest.RetrofitBuilder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by rahmat on 9/17/2017.
 */

public class RecipeMasterListFragment extends Fragment{

    //@BindView(R.id.master_recipe_recyclerview)
    //RecyclerView recyclerView;

    private RecyclerView recyclerView;
    private RecipeMasterListAdapter recipeMasterListAdapter;

    public RecipeMasterListFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_master_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.master_recipe_recyclerview);


        if (rootView.getTag()!=null && rootView.getTag().equals("tablet")){
            GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),3);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        else {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }

        @SuppressWarnings("VisibleForTests")
        final SimpleIdlingResource idlingResource = (SimpleIdlingResource)((MainActivity)getActivity()).getIdlingResource();

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }


        RecipeClient recipeClient = RetrofitBuilder.Retrieve();
        Call<ArrayList<Recipe>> recipe = recipeClient.getRecipe();

        recipe.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                ArrayList<Recipe> recipes = response.body();
                Bundle recipesBundle = new Bundle();
                recipesBundle.putParcelableArrayList("Baking_Recipes", recipes);

                recipeMasterListAdapter = new RecipeMasterListAdapter((MainActivity)getActivity(), recipes);
                recyclerView.setAdapter(recipeMasterListAdapter);

                if (idlingResource != null) {
                    idlingResource.setIdleState(true);
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return rootView;
    }

}
