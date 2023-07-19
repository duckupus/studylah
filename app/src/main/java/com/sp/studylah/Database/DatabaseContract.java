package com.sp.studylah.Database;
import android.provider.BaseColumns;

public final class DatabaseContract {

    private DatabaseContract(){}
        public static class AssignmentEntry implements BaseColumns{
            public static final String TABLE_NAME = "assignments";
            public static final String COLUMN_TYPE = "type";
            public static final String COLUMN_DATE = "date";
            public static final String COLUMN_DESCRIPTION = "description";
        }
    }

