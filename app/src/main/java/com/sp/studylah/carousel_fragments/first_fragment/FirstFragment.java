package com.sp.studylah.carousel_fragments.first_fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.sp.studylah.Database.DatabaseContract;
import com.sp.studylah.Database.DatabaseHelper;
import com.sp.studylah.R;
import com.sp.studylah.RecyclerItemClickListener;
import com.sp.studylah.databinding.FragmentFirstBinding;
import com.sp.studylah.Pages.view1;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private ConstraintLayout layout;
    private DatabaseHelper databaseHelper;
    private TextView textViewAssignment;
    private SQLiteDatabase db;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        layout = view.findViewById(R.id.fragment_first);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), view1.class);
                startActivity(intent);
            }
        });
        textViewAssignment = view.findViewById(R.id.todayAssignments);
        /* DB init */
        databaseHelper = new DatabaseHelper(getActivity());
        db = databaseHelper.getReadableDatabase();
    }

    @Override
    public void onResume() {
        /* gets date */
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String formattedDate = dateFormat.format(date); //damnit I need to subtract zzz
        int year = Integer.parseInt(formattedDate.substring(0,4));
        year -= 1900;
        String newDate = String.valueOf(year) + formattedDate.substring(4, formattedDate.length());
        String selector = DatabaseContract.AssignmentEntry.COLUMN_DATE + "=" + newDate;

        /* col initialization */
        String[] columns = {DatabaseContract.AssignmentEntry.COLUMN_TYPE};

        Cursor cursor = db.query(DatabaseContract.AssignmentEntry.TABLE_NAME,
                columns,
                selector,
                null,
                null,
                null,
                null);
        if(cursor == null) {
            textViewAssignment.setText("You have no assignments due today!");
        } else {
            cursor.moveToFirst();
            String displayAssignmentCount = "You have " + cursor.getCount() + " assignments due today!";
            textViewAssignment.setText(displayAssignmentCount);
        }
        /*
        String displayAssignmentCount = "You have " + cursor.getCount() + " assignments due today!";
        textViewAssignment.setText("  DATE: " + formattedDate + '\n' + "  NEW DATE: " + newDate
                + '\n' + "  CURSOR COUNT: " + cursor.getCount()); */
        //textViewAssignment.setText(displayAssignmentCount);
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}