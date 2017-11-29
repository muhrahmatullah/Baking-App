package com.rahmat.codelab.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahmat.codelab.bakingapp.adapter.RecipeStepAdapter;
import com.rahmat.codelab.bakingapp.model.Ingredient;
import com.rahmat.codelab.bakingapp.model.Recipe;
import com.rahmat.codelab.bakingapp.model.Step;
import com.rahmat.codelab.bakingapp.widget.RecipeIntentService;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahmat on 9/18/2017.
 */

public class RecipeDetailFragment extends Fragment {
    public RecipeDetailFragment(){}

    private ArrayList<Recipe> recipe;

    /*@BindView(R.id.detail_text)
    TextView detail_textview;
    @BindView(R.id.step_detail_list)
    RecyclerView recyclerView;*/

    private RecipeStepAdapter recipeStepAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail,container, false);
        //ButterKnife.bind(getActivity(), rootView);
        TextView detail_textview = (TextView) rootView.findViewById(R.id.detail_text);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.step_detail_list);

        recipe = new ArrayList<>();

        if(savedInstanceState != null) {
            recipe = savedInstanceState.getParcelableArrayList(RecipeDetailActivity.SELECTED_RECIPE);
            Log.v("test", "adaji"+recipe.get(0).getName());

        }
        else {
            recipe =getArguments().getParcelableArrayList(RecipeDetailActivity.SELECTED_RECIPE);
        }

        List<Ingredient> ingredient = recipe.get(0).getIngredients();

        ArrayList<String> widgetIngredient= new ArrayList<>();

        for(int i = 0; i < ingredient.size(); i++){
            detail_textview.append("- "+ingredient.get(i).getIngredient()
                    +" ("+ingredient.get(i).getQuantity().toString()
                    +" "+ingredient.get(i).getMeasure()+")\n");

            widgetIngredient.add("- "+ingredient.get(i).getIngredient()+" "
                    +ingredient.get(i).getQuantity().toString()
                    +" "+ingredient.get(i).getMeasure()+"\n");

            Log.v("isi widget", widgetIngredient.get(i));
        }

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(mDividerItemDecoration);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        List<Step> step = recipe.get(0).getSteps();
        recipeStepAdapter = new RecipeStepAdapter(step, (RecipeDetailActivity) getActivity());

        recyclerView.setAdapter(recipeStepAdapter);

        RecipeIntentService.startRecipeService(getContext(),widgetIngredient);

        return rootView;
    }
    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(RecipeDetailActivity.SELECTED_RECIPE, recipe);
    }
}
