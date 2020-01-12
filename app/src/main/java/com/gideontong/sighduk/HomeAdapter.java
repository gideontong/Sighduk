package com.gideontong.sighduk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.gideontong.sighduk.db.SavedContract;
import com.gideontong.sighduk.db.SavedDbHelper;
import com.gideontong.sighduk.db.ShowContract;
import com.gideontong.sighduk.db.ShowDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class HomeAdapter extends BaseAdapter implements ListAdapter {
    public static final String TAG = "HomeAdapter";

    ArrayList<EntryData> arrayList;
    private ShowDbHelper mHelper;
    private SavedDbHelper kHelper;
    Context context;

    public HomeAdapter(Context context, ArrayList<EntryData> arrayList) {
        this.arrayList=arrayList;
        this.context=context;

        mHelper = new ShowDbHelper(context);
        kHelper = new SavedDbHelper(context);

        Log.d(TAG, "Setting home adapter with an ArrayList");
    }

    public void deleteShow(View view) {
        View parent = (View) view.getParent();
        Log.d(TAG, "The parent is " + parent.getParent().getParent().getParent().getParent());
        TextView showTextView = (TextView) parent.findViewById(R.id.show_title);
        String show = String.valueOf(showTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();

        /*
        Cursor cursor = db.query(ShowContract.ShowEntry.TABLE,
                new String[]{
                        ShowContract.ShowEntry._ID,
                        ShowContract.ShowEntry.COL_SHOW_TITLE,
                        ShowContract.ShowEntry.COL_SHOW_IMAGE_URL
                },
                ShowContract.ShowEntry.COL_SHOW_TITLE + " LIKE '" + show + "'",
                null, null, null, null);
        int uriIndex = cursor.getColumnIndex(ShowContract.ShowEntry.COL_SHOW_IMAGE_URL);
        Log.d(TAG, "Cursor pos is " + cursor + " with index at " + uriIndex);
        // String uri = cursor.getString(uriIndex);
        */
        String uri = "wtf";

        Log.d(TAG, "Deleting show with name " + show + " and uri " + uri);

        db.delete(ShowContract.ShowEntry.TABLE,
                ShowContract.ShowEntry.COL_SHOW_TITLE + " = ?",
                new String[]{show});
        db.close();

        SQLiteDatabase saved = kHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SavedContract.SavedEntry.COL_SHOW_TITLE, show);
        values.put(SavedContract.SavedEntry.COL_SHOW_IMAGE_URL, uri);

        saved.insertWithOnConflict(SavedContract.SavedEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        saved.close();

        // updateUI();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Log.d(TAG, "Starting entryList arrayList tagging");

        EntryData entryData = arrayList.get(position);

        // Log.d(TAG, "entryData memory address is " + entryData);
        if(convertView == null) {
            Log.d(TAG, "The context should be " + context);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_show, null);

            final Button delete = (Button) convertView.findViewById(R.id.show_delete);
            Log.d(TAG, "I found a button at " + delete);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "A button was pressed at " + v);
                    deleteShow(v);
                }
            });
            TextView title = convertView.findViewById(R.id.show_title);
            Log.d(TAG, "title memory address is " + title);
            ImageView imag = convertView.findViewById(R.id.show_image);
            // Log.d(TAG, "title is " + convert);
            title.setText(entryData.SubjectName);
            Picasso.with(context)
                    .load(entryData.Image)
                    .into(imag);
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
        // return arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}