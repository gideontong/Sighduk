package com.gideontong.sighduk;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class HomeAdapter implements ListAdapter {
    public static final String TAG = "HomeAdapter";

    ArrayList<EntryData> arrayList;
    Context context;

    public HomeAdapter(Context context, ArrayList<EntryData> arrayList) {
        this.arrayList=arrayList;
        this.context=context;

        Log.d(TAG, "Setting home adapter with an ArrayList");
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
        Log.d(TAG, "Starting entryList arrayList tagging");

        EntryData entryData = arrayList.get(position);

        Log.d(TAG, "entryData memory address is " + entryData);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.item_show, null);

            // ListView listView = (ListView) convertView.findViewById(R.id.list_show);

            // Log.d(TAG, "ListView memory address is " + listView);
            /* convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            }); */
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