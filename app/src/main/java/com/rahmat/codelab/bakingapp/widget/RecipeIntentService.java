package com.rahmat.codelab.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by rahmat on 9/19/2017.
 */

public class RecipeIntentService extends IntentService {

    public RecipeIntentService(){super("RecipeIntentService");}

    public static final String FROM_ACTIVITY_INGREDIENTS_LIST ="FROM_ACTIVITY_INGREDIENTS_LIST";


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            ArrayList<String> fromActivityIngredientsList = intent.getExtras().getStringArrayList(FROM_ACTIVITY_INGREDIENTS_LIST);
            actionUpdateWidgets(fromActivityIngredientsList);

        }
    }

    public static void startRecipeService(Context context, ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent(context, RecipeIntentService.class);
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivityIngredientsList);
        Log.v("testji", "terpanggilkah");
        context.startService(intent);
    }

    private void actionUpdateWidgets(ArrayList<String> fromActivityIngredientsList) {
        Intent intent = new Intent("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE2");
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST,fromActivityIngredientsList);
        sendBroadcast(intent);
    }
}
