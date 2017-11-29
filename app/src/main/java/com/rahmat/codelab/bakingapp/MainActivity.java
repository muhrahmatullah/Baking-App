package com.rahmat.codelab.bakingapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rahmat.codelab.bakingapp.IdlingResource.SimpleIdlingResource;
import com.rahmat.codelab.bakingapp.adapter.RecipeMasterListAdapter;
import com.rahmat.codelab.bakingapp.model.Recipe;

import java.util.ArrayList;

import butterknife.ButterKnife;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements RecipeMasterListAdapter.ListItemClickListener{


    @Nullable
    private SimpleIdlingResource mIdlingResource;

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIdlingResource();
    }


    @Override
    public void onListItemClick(Recipe selectedIndex) {
        Bundle bundle = new Bundle();
        ArrayList<Recipe> selectedRecipe = new ArrayList<>();
        selectedRecipe.add(selectedIndex);
        String SELECTED_RECIPE = "selected_recipe";
        bundle.putParcelableArrayList(SELECTED_RECIPE,selectedRecipe);

        final Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLongListItemClick(String id) {
        Toast.makeText(this, "Ini idnya klo diklik panjang"+id, Toast.LENGTH_SHORT).show();
    }
}
