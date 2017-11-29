package com.rahmat.codelab.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rahmat.codelab.bakingapp.R;
import com.rahmat.codelab.bakingapp.model.Step;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rahmat on 9/19/2017.
 */

public class RecipeStepAdapter extends RecyclerView.Adapter<RecipeStepAdapter.RecipeStepListAdapter> {

    private final List<Step> step;

    private final OnListClickListener mOnListClickListener;

    public interface OnListClickListener{
        void onListItemClick(List<Step> steps,int index);
    }

    public RecipeStepAdapter(List<Step> step, OnListClickListener mOnListClickListener){
        this.step = step;
        this.mOnListClickListener = mOnListClickListener;
    }

    @Override
    public RecipeStepListAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_item, parent, false);

        return new RecipeStepListAdapter(itemView);
    }

    @Override
    public void onBindViewHolder(RecipeStepListAdapter holder, int position) {
        holder.textViewShort.setText(step.get(position).getId()+". "+step.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return step.size();
    }


    public class RecipeStepListAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.textview_short_description)
        TextView textViewShort;
        public RecipeStepListAdapter(View view){
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            mOnListClickListener.onListItemClick(step, index);
        }
    }

}
