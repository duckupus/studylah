package com.sp.studylah.PageActivities;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sp.studylah.Database.DatabaseContract;
import com.sp.studylah.R;

public class AssignmentCursorAdapter extends CursorAdapter {

    public AssignmentCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate the layout for each item in the ListView
        return LayoutInflater.from(context).inflate(R.layout.activity_view2_1, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Retrieve the assignment data from the cursor
        String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AssignmentEntry.COLUMN_TYPE));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AssignmentEntry.COLUMN_DATE));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AssignmentEntry.COLUMN_DESCRIPTION));

        // Set the assignment data to the respective TextViews
        TextView textViewType = view.findViewById(R.id.textViewType);
        TextView textViewDate = view.findViewById(R.id.textViewDate);
        TextView textViewDescription = view.findViewById(R.id.textViewDescription);

        textViewType.setText(type);
        textViewDate.setText(date);
        textViewDescription.setText(description);
    }
}

