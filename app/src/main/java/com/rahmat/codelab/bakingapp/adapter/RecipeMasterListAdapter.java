package com.rahmat.codelab.bakingapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rahmat.codelab.bakingapp.R;
import com.rahmat.codelab.bakingapp.RecipeMasterListFragment;
import com.rahmat.codelab.bakingapp.model.Recipe;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.media.CamcorderProfile.get;

/**
 * Created by rahmat on 9/17/2017.
 */

public class RecipeMasterListAdapter extends RecyclerView.Adapter<RecipeMasterListAdapter.RecipeListAdapter>{

    private final List<Recipe> recipeList;
    private final ListItemClickListener mOnClickListener;
    private Context context;

    public RecipeMasterListAdapter(ListItemClickListener mOnClickListener, List<Recipe> recipeList){
        this.mOnClickListener = mOnClickListener;
        this.recipeList = recipeList;
    }

    @Override
    public RecipeListAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeListAdapter(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeListAdapter holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getName());
        String serv = "Serving = "+String.valueOf(recipe.getServings());
        holder.recipeServing.setText(serv);
        String imageUrl=recipeList.get(position).getImage();

        if (!imageUrl.equals("")) {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            Picasso.with(context).load(builtUri).into(holder.recipeImage);
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public interface ListItemClickListener {
        void onListItemClick(Recipe clickedItemIndex);
        void onLongListItemClick(String id);
    }

    class RecipeListAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        @BindView(R.id.recipe_name)
        TextView recipeName;
        @BindView(R.id.recipe_serving)
        TextView recipeServing;
        @BindView(R.id.recipeImage)
        ImageView recipeImage;

        RecipeListAdapter(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(recipeList.get(adapterPosition));
        }

        @Override
        public boolean onLongClick(View view) {
            int adapterPosition = getAdapterPosition();
            String id = String.valueOf(recipeList.get(adapterPosition).getServings());
            mOnClickListener.onLongListItemClick(id);
            return true;
        }
    }
}
