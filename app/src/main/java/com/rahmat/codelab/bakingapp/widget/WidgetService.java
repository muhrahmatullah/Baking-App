package com.rahmat.codelab.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rahmat.codelab.bakingapp.R;

import java.util.List;

import static com.rahmat.codelab.bakingapp.widget.RecipeWidget.ingredients;

/**
 * Created by rahmat on 9/19/2017.
 */

public class WidgetService extends RemoteViewsService {

    private List<String> remoteViewingredient;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory(this.getApplicationContext());
    }

    class RemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext = null;

        public RemoteViewsFactory(Context context) {
            mContext = context;

        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            remoteViewingredient = ingredients;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {

            return remoteViewingredient.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

            views.setTextViewText(R.id.widget_grid_view_item, remoteViewingredient.get(position));

            Intent fillInIntent = new Intent();
            views.setOnClickFillInIntent(R.id.widget_grid_view_item, fillInIntent);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }




    }
}
