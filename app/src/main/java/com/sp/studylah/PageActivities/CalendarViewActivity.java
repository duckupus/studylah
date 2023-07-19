package com.sp.studylah.PageActivities;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.sp.studylah.Database.DatabaseContract;
import com.sp.studylah.Database.DatabaseHelper;
import com.sp.studylah.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CalendarViewActivity extends Activity{
        private CalendarView calendarView;
        private TextView textViewAssignment;
        private Button buttonBack;

        private DatabaseHelper databaseHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_calendar_view);

            // Find views by their IDs
            calendarView = findViewById(R.id.calendarView);
            textViewAssignment = findViewById(R.id.textViewAssignment);
            buttonBack = findViewById(R.id.buttonBack);

            // Initialize the database helper
            databaseHelper = new DatabaseHelper(this);

            // Set a click listener for the calendar dates
            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                    showAssignment(year, month, dayOfMonth);
                }
            });

            // Set a click listener for the back button
            buttonBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }

        private void showAssignment(int year, int month, int dayOfMonth) {
            // Convert the selected date to a string
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date = new Date(year - 1900, month, dayOfMonth);
            String selectedDate = sdf.format(date);

            // Retrieve the assignment from the database
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            String[] projection = {
                    DatabaseContract.AssignmentEntry.COLUMN_TYPE,
                    DatabaseContract.AssignmentEntry.COLUMN_DESCRIPTION
            };
            String selection = DatabaseContract.AssignmentEntry.COLUMN_DATE + " = ?";
            String[] selectionArgs = {selectedDate};
            Cursor cursor = db.query(
                    DatabaseContract.AssignmentEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            // Check if the assignment exists for the selected date
            if (cursor.moveToFirst()) {
                String type = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AssignmentEntry.COLUMN_TYPE));
                String description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.AssignmentEntry.COLUMN_DESCRIPTION));

                String assignmentText = "Type: " + type + "\nDate: " + selectedDate + "\nDescription: " + description;
                textViewAssignment.setText(assignmentText);
                textViewAssignment.setVisibility(View.VISIBLE);
            } else {
                textViewAssignment.setText("There are no Exams/Assignments on this date.");
                textViewAssignment.setVisibility(View.VISIBLE);
            }

            cursor.close();
        }

        @Override
        protected void onDestroy() {
            super.onDestroy();
            // Close the database helper when the activity is destroyed
            databaseHelper.close();
        }
    }
