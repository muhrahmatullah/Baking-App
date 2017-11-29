package com.rahmat.codelab.bakingapp;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rahmat.codelab.bakingapp.adapter.RecipeMasterListAdapter;
import com.rahmat.codelab.bakingapp.adapter.RecipeStepAdapter;
import com.rahmat.codelab.bakingapp.model.Recipe;
import com.rahmat.codelab.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity implements RecipeStepAdapter.OnListClickListener, RecipeStepDetailFragment.ListOnItemClickListener{

    static final String SELECTED_RECIPE = "selected_recipe";
    static final String SELECTED_STEPS="Selected_Steps";
    static final String SELECTED_INDEX="Selected_Index";
    private static final String STACK_RECIPE_DETAIL="STACK_RECIPE_DETAIL";
    private static final String STACK_RECIPE_STEP_DETAIL="STACK_RECIPE_STEP_DETAIL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {

            Bundle bundle = getIntent().getExtras();

            ArrayList<Recipe> recipe = new ArrayList<>();
            recipe = bundle.getParcelableArrayList(SELECTED_RECIPE);


            final RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(bundle);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(STACK_RECIPE_DETAIL)
                    .commit();

            if (findViewById(R.id.recipe_linear_layout).getTag()!=null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet")) {

                final RecipeStepDetailFragment fragment2 = new RecipeStepDetailFragment();
                fragment2.setArguments(bundle);
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container2, fragment2).addToBackStack(STACK_RECIPE_STEP_DETAIL)
                        .commit();

            }
        } else {

        }

    }

    @Override
    public void onBackPressed() {
        FragmentManager fm = getSupportFragmentManager();
        if (findViewById(R.id.fragment_container2)==null) {
            if (fm.getBackStackEntryCount() > 1) {
                //go back to "Recipe Detail" screen
                fm.popBackStack(STACK_RECIPE_DETAIL, 1);
            } else if (fm.getBackStackEntryCount() > 0) {
                //go back to "Recipe" screen
                finish();
            }
        }
        else {

            //go back to "Recipe" screen
            finish();

        }

        super.onBackPressed();
    }

    @Override
    public void onListItemClick(List<Step> steps, int index) {
        final RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();


        Bundle stepBundle = new Bundle();
        stepBundle.putParcelableArrayList(SELECTED_STEPS,(ArrayList<Step>) steps);
        stepBundle.putInt(SELECTED_INDEX, index);
        fragment.setArguments(stepBundle);

        if (findViewById(R.id.recipe_linear_layout).getTag()!=null && findViewById(R.id.recipe_linear_layout).getTag().equals("tablet")) {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container2, fragment)
                    .addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();

        }
        else {
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(STACK_RECIPE_STEP_DETAIL)
                    .commit();
        }
    }
}
