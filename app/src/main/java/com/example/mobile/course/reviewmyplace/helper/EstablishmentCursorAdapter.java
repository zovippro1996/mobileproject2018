package com.example.mobile.course.reviewmyplace.helper;

import android.content.Context;
import android.database.Cursor;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.mobile.course.reviewmyplace.R;

import java.util.Locale;

public class EstablishmentCursorAdapter extends ResourceCursorAdapter {

    public EstablishmentCursorAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
    }

    /**
     * Bind all data to a given view (e.g. setting the text on a TextView)
     * @param view View
     * @param context Context
     * @param cursor Cursor
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Populate fields from inflated template with corresponding properties from cursor
        // Name of establishment (all caps)
        TextView textView = view.findViewById(R.id.list_record_establishment_name);
        textView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ESTABLISHMENT_NAME)).toUpperCase(Locale.ROOT));

        // User ID
//        textView = view.findViewById(R.id.list_record_user);
//        textView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_ID)));

        // Type of establishment
        textView = view.findViewById(R.id.list_record_type);
        String cursorType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ESTABLISHMENT_TYPE));
        if (cursorType.equalsIgnoreCase("COFFEE_SHOP")) {
            cursorType = "coffee shop";
        }
        textView.setText(cursorType.toLowerCase(Locale.ROOT));

        // Food served in establishment
        textView = view.findViewById(R.id.list_record_food);
        textView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FOOD)));

        // Location of the establishment
//        textView = view.findViewById(R.id.list_record_location);
//        textView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_LOCATION_DESC)));


        // Set background color for even/odd row records
        if (cursor.getPosition() % 2 == 0) {
            // Set ligh cyan color
            view.findViewById(R.id.list_record).setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorLightCyan));
        } else {
            // Set light orange color
            view.findViewById(R.id.list_record).setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.colorLightOrange));
        }
    }
}
