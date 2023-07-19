package com.sp.studylah.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;

import com.sp.studylah.PageActivities.AssignmentCursorAdapter;
import com.sp.studylah.Database.DatabaseContract;
import com.sp.studylah.Database.DatabaseHelper;
import com.sp.studylah.R;

public class view2 extends AppCompatActivity {
    private ListView listViewAssignments;
    private AssignmentCursorAdapter cursorAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view2);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);

        setContentView(R.layout.activity_view2);
                // Find the ListView by its ID
                listViewAssignments = findViewById(R.id.listViewAssignments);

                // Initialize the database helper
                databaseHelper = new DatabaseHelper(this);

                // Retrieve the assignment data from the database
                SQLiteDatabase db = databaseHelper.getReadableDatabase();
                String[] projection = {
                        DatabaseContract.AssignmentEntry._ID,
                        DatabaseContract.AssignmentEntry.COLUMN_TYPE,
                        DatabaseContract.AssignmentEntry.COLUMN_DATE,
                        DatabaseContract.AssignmentEntry.COLUMN_DESCRIPTION
                };
                Cursor cursor = db.query(
                        DatabaseContract.AssignmentEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null
                );

                // Create a custom CursorAdapter to populate the ListView
                cursorAdapter = new AssignmentCursorAdapter(this, cursor);

                // Set the adapter for the ListView
                listViewAssignments.setAdapter(cursorAdapter);
            }

            @Override
            protected void onDestroy() {
                super.onDestroy();
                // Close the database helper and cursor when the activity is destroyed
                databaseHelper.close();
                if (cursorAdapter != null) {
                    cursorAdapter.swapCursor(null);
                    cursorAdapter = null;
                }
            }
        }
