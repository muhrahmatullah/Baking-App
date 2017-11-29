package com.rahmat.codelab.bakingapp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.rahmat.codelab.bakingapp.model.Recipe;
import com.rahmat.codelab.bakingapp.model.Step;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.thumb;
import static com.rahmat.codelab.bakingapp.RecipeDetailActivity.SELECTED_INDEX;
import static com.rahmat.codelab.bakingapp.RecipeDetailActivity.SELECTED_STEPS;

/**
 * Created by rahmat on 9/19/2017.
 */

public class RecipeStepDetailFragment extends Fragment {

    private SimpleExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    private ArrayList<Step> steps = new ArrayList<>();
    private int selectedIndex;
    private Handler mainHandler;
    private ArrayList<Recipe> recipe;

    public RecipeStepDetailFragment(){

    }

    private ListOnItemClickListener itemClickListener;

    public interface ListOnItemClickListener {
        void onListItemClick(List<Step> allSteps, int Index);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainHandler = new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();

        itemClickListener =(RecipeDetailActivity)getActivity();

        recipe = new ArrayList<>();

        if(savedInstanceState != null) {
            steps = savedInstanceState.getParcelableArrayList(SELECTED_STEPS);
            selectedIndex = savedInstanceState.getInt(SELECTED_INDEX);


        }
        else {
            steps =getArguments().getParcelableArrayList(SELECTED_STEPS);
            if (steps!=null) {
                steps =getArguments().getParcelableArrayList(SELECTED_STEPS);
                selectedIndex=getArguments().getInt(SELECTED_INDEX);
            }
            else {
                recipe =getArguments().getParcelableArrayList(RecipeDetailActivity.SELECTED_RECIPE);
                //casting List to ArrayList
                steps=(ArrayList<Step>)recipe.get(0).getSteps();
                selectedIndex=0;
            }

        }
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        TextView descTextView = (TextView) rootView.findViewById(R.id.recipe_step_textview);
        descTextView.setText(steps.get(selectedIndex).getDescription());


        simpleExoPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.videoplayer);
        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);

        String videoUrl = steps.get(selectedIndex).getVideoURL();


        String imageUrl=steps.get(selectedIndex).getThumbnailURL();
        if (imageUrl!="") {
            Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
            ImageView thumbnail = (ImageView) rootView.findViewById(R.id.thumbnail);
            Picasso.with(getContext())
                    .load(builtUri)
                    .into(thumbnail);
        }

        if (!videoUrl.isEmpty()) {

            initializePlayer(Uri.parse(steps.get(selectedIndex).getVideoURL()));

            if (rootView.findViewWithTag("sw600dp-land-recipe_step_detail")!=null) {
                getActivity().findViewById(R.id.fragment_container2).setLayoutParams(new LinearLayout.LayoutParams(-1,-2));
                simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);

            }
            else if (isInLandscapeMode(getContext())){
                descTextView.setVisibility(View.GONE);
            }
        }
        else {
            player=null;
            simpleExoPlayerView.setVisibility(View.GONE);
        }

        final Button btnNext = (Button) rootView.findViewById(R.id.btn_next);
        final Button btnPrev = (Button) rootView.findViewById(R.id.btn_prev);


        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (steps.get(selectedIndex).getId() > 0) {
                    if (player!=null){
                        player.stop();
                    }
                    itemClickListener.onListItemClick(steps,steps.get(selectedIndex).getId() - 1);
                }
                else {
                    btnPrev.setVisibility(View.GONE);

                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int lastIndex = steps.size()-1;
                if (steps.get(selectedIndex).getId() < steps.get(lastIndex).getId()) {
                    if (player!=null){
                        player.stop();
                    }
                    itemClickListener.onListItemClick(steps,steps.get(selectedIndex).getId() + 1);
                }
                else {
                    btnNext.setVisibility(View.GONE);

                }
            }
        });

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        super.onSaveInstanceState(currentState);
        currentState.putParcelableArrayList(SELECTED_STEPS,steps);
        currentState.putInt(SELECTED_INDEX,selectedIndex);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (player!=null) {
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (player!=null) {
            player.stop();
            player.release();
        }
    }

    private void initializePlayer(Uri mediaUri) {
        if (player == null) {
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveVideoTrackSelection.Factory(bandwidthMeter);
            DefaultTrackSelector trackSelector = new DefaultTrackSelector(mainHandler, videoTrackSelectionFactory);
            LoadControl loadControl = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            simpleExoPlayerView.setPlayer(player);

            String userAgent = Util.getUserAgent(getContext(), "Baking App");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
        }
    }

    private boolean isInLandscapeMode(Context context) {
        Log.v("test", "landscape ji gan");
        return (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE);

    }



}
