package com.sp.studylah.PageActivities;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.sp.studylah.Database.DatabaseContract;
import com.sp.studylah.Database.DatabaseHelper;
import com.sp.studylah.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AssignmentCursorAdapter extends CursorAdapter {
    private Cursor cursor;

    public AssignmentCursorAdapter(Context context, Cursor cursor)
    {
        super(context, cursor, 0);
        this.cursor = cursor;
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Inflate the layout for each item in the ListView
        return LayoutInflater.from(context).inflate(R.layout.activity_view2a, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Retrieve the assignment data from the cursor
        final long assignmentId = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.AssignmentEntry._ID));
        String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AssignmentEntry.COLUMN_TYPE));
        long dateInMillis = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseContract.AssignmentEntry.COLUMN_DATE));
        String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AssignmentEntry.COLUMN_DESCRIPTION));

        // Set the assignment data to the respective TextViews
        TextView textViewType = view.findViewById(R.id.textViewType);
        TextView textViewDate = view.findViewById(R.id.textViewDate);
        TextView textViewDescription = view.findViewById(R.id.textViewDescription);

        textViewType.setText(type);

        // Format the date correctly
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = sdf.format(new Date(dateInMillis));
        textViewDate.setText(formattedDate);

        textViewDescription.setText(description);
    }
    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor != null) {
            cursor = newCursor;
            notifyDataSetChanged();
        }
        return newCursor;
    }


}

