package com.sp.studylah.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.sp.studylah.PageActivities.AssignmentCursorAdapter;
import com.sp.studylah.Database.DatabaseContract;
import com.sp.studylah.Database.DatabaseHelper;
import com.sp.studylah.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class view2 extends AppCompatActivity {
    private ListView listViewAssignments;
    private AssignmentCursorAdapter cursorAdapter;
    private DatabaseHelper databaseHelper;

    void shareToWhatsApp(String type, String date, String description ) {
        String message = "I have [workType] [description] at [date]";
        message = message.replace("[date]", date);
        message = message.replace("[workType]", type);
        message = message.replace("[description]", description);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        sendIntent.setPackage("com.whatsapp"); // Specify WhatsApp package name

        try {
            startActivity(sendIntent);
        } catch (ActivityNotFoundException ex) {
            // Handle case where WhatsApp is not installed
            Toast.makeText(this, "WhatsApp is not installed.", Toast.LENGTH_SHORT).show();
        }
    }
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
                listViewAssignments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Cursor pos = (Cursor) parent.getItemAtPosition(position);
                        String description = pos.getString(pos.getColumnIndexOrThrow(DatabaseContract.AssignmentEntry.COLUMN_DESCRIPTION));
                        String date = pos.getString(pos.getColumnIndexOrThrow(DatabaseContract.AssignmentEntry.COLUMN_DATE));
                        Date dateDate = new Date(Long.parseLong(date));
                        // convert date to locale format
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        date = dateFormat.format(dateDate);

                        String type = pos.getString(pos.getColumnIndexOrThrow(DatabaseContract.AssignmentEntry.COLUMN_TYPE));
                        shareToWhatsApp(type, date, description);
                    }
                });

                // Find the clear button by ID
                Button buttonClear = findViewById(R.id.buttonClear);

                // Set the OnClickListener for the clear button
                buttonClear.setOnClickListener (new View.OnClickListener() {
                @Override
                    public void onClick(View v) {
                        clearAssignments();
                    }
                });
            }

            // Method to clear the assignments
    private void clearAssignments() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int deletedRows = db.delete(
                DatabaseContract.AssignmentEntry.TABLE_NAME,null,null
        );
        if (deletedRows > 0) {
            // Reload the ListView with updated data
            Cursor updatedCursor = db.query(
                    DatabaseContract.AssignmentEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            cursorAdapter.swapCursor(updatedCursor);
            Toast.makeText(this, "Assignments Cleared, Please refresh the page", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No Assignments to clear", Toast.LENGTH_SHORT).show();
        }
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
