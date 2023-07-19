package com.sp.studylah.Pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sp.studylah.Database.DatabaseContract;
import com.sp.studylah.Database.DatabaseHelper;
import com.sp.studylah.R;

public class view1 extends AppCompatActivity {

    private CalendarView calendarView;
    private EditText editTextDescription;
    private Button buttonSave;
    private RadioGroup radioGroup;
    private RadioButton radioButtonExam;
    private RadioButton radioButtonAssignment;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view1);
        overridePendingTransition(R.anim.zoom_in, R.anim.zoom_out);

    setContentView(R.layout.activity_view1);
    //find view by their IDs
    radioGroup = findViewById(R.id.radioGroup);
    radioButtonExam = findViewById(R.id.radioButtonExam);
    radioButtonAssignment = findViewById(R.id.radioButtonAssignment);
    calendarView = findViewById(R.id.calendarView);
    editTextDescription = findViewById(R.id.editTextDescription);
    buttonSave = findViewById(R.id.buttonSave);

    //initialize the database helper
    databaseHelper = new DatabaseHelper(this);

    //set a click listener for the save button
        buttonSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            saveData();
        }
    });
}


    private void saveData () {
        //retrieve the selected type (exam or assignment)
        String type = radioButtonExam.isChecked() ? "Exam " : "Assignment";

        //retrieve the selected date
        long dateInMillis = calendarView.getDate(); // Date in Milliseconds

        //retrieve the description
        String description = editTextDescription.getText().toString();

        //saving the details to Database
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.AssignmentEntry.COLUMN_TYPE, type);
        values.put(DatabaseContract.AssignmentEntry.COLUMN_DATE, dateInMillis);
        values.put(DatabaseContract.AssignmentEntry.COLUMN_DESCRIPTION, description);
        long newRowId = db.insert(DatabaseContract.AssignmentEntry.TABLE_NAME, null, values);

        //check if data was saved
        if (newRowId != 1) {
            Toast.makeText(this, "Date saved successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "An error has occured", Toast.LENGTH_SHORT).show();
        }
        //set the background color of the Save Button programmatically
        int buttonColor = getResources().getColor(R.color.button_color);
        buttonSave.setBackgroundColor(buttonColor);
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        //close the database helper when the activity is destroyed
        databaseHelper.close();
    }
}