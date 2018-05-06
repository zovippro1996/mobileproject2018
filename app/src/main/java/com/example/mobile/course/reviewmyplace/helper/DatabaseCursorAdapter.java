package com.example.mobile.course.reviewmyplace.helper;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.mobile.course.reviewmyplace.R;

public class DatabaseCursorAdapter extends ResourceCursorAdapter {

    public DatabaseCursorAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Name of establishment
        TextView textView = (TextView) view.findViewById(R.id.list_record_establishment_name);

        // TO DO
        // Bind each column (from database) with the corresponding view list_row_record
        // Add onItemClickListener for each view -> open the corresponding Establishment detail activity
    }
}
