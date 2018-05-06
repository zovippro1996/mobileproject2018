package com.example.mobile.course.reviewmyplace;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.example.mobile.course.reviewmyplace.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    private DatabaseHelper mDatabaseHelper;
    private ArrayList<String> mFilterTypes;       // type of establishment to filter
    private Menu mOptionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Initialize database helper
        mDatabaseHelper = new DatabaseHelper(this);

        // Initialize filter types
        mFilterTypes = new ArrayList<>();

        // Get the intent, verify the action (i.e. SEARCH) and get the query
        handleSearchIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleSearchIntent(intent);
    }

    private void handleSearchIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
//            String query = intent.getStringExtra(SearchManager.QUERY);

            // Do nothing
        }
    }

    private void setupSearchOnTyping(Menu menu) {
        // Get all records
        Cursor records = mDatabaseHelper.getAllEstablishmentRecords();

        // Array of cursor columns and array of template object to bind to
        String[] columns = new String[] {DatabaseHelper.COL_ESTABLISHMENT_NAME,
                                DatabaseHelper.COL_USER_ID, DatabaseHelper.COL_ESTABLISHMENT_TYPE,
                                DatabaseHelper.COL_FOOD, DatabaseHelper.COL_LOCATION_DESC};
        int[] templateObjects = new int[] {R.id.list_record_establishment_name,
                                R.id.list_record_user, R.id.list_record_type,
                                R.id.list_record_food, R.id.list_record_location};

        // Create a new list adapter bound to the cursor
        // Display all Establishment at first
        final SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,   // context
                R.layout.list_row_record,   // specify the row template to use
                records,    // the cursor to bind to
                columns,    // array of cursor columns to bind to
                templateObjects,     // parallel array of which template objects to bind to those columns
                SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );

        // Bound to new adapter
        ListView listView = findViewById(android.R.id.list);
        listView.setAdapter(adapter);

        // Set listener for item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Last argument (i.e. long l) is actually the row id from the database
                Log.i("id", String.format(Locale.getDefault(), "%d", l));
            }
        });

        // Set filter query provider (i.e. function returning the required cursor) for the adapter
        adapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence charSequence) {
                return mDatabaseHelper.getFilteredEstablishmentRecords(charSequence.toString(), mFilterTypes);
            }
        });

        SearchView searchView = (SearchView) menu.findItem(R.id.dashboard_menu_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                // collapse the view?

                // Filter the input keywords
                adapter.getFilter().filter(s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                // Filter the input keywords
                adapter.getFilter().filter(s);

                // Notify changes of data set
                adapter.notifyDataSetChanged();

                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Get reference to menu for later use
        mOptionsMenu = menu;

        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.dashboard_menu_search).getActionView();
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(false);    // Do not iconify the widget; expand it by default

        // Setup search-on-typing for SearchView
        setupSearchOnTyping(menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.dashboard_menu_search) {
            return super.onOptionsItemSelected(item);
        }

        // Retrieve Adapter bound to ListView
        ListView listView = findViewById(android.R.id.list);
        SimpleCursorAdapter adapter = (SimpleCursorAdapter) listView.getAdapter();

        // Retrieve the current query in SearchView
        SearchView searchView = (SearchView) mOptionsMenu.findItem(R.id.dashboard_menu_search).getActionView();
        String query = searchView.getQuery().toString();

        // Handle item selection
        switch (item.getItemId()) {
            case R.id.dashboard_menu_restaurant:
                // Set the state of the checkbox item
                if (item.isChecked()) {
                    item.setChecked(false);

                    // Remove "Restaurant" from list of filter types
                    mFilterTypes.remove("RESTAURANT");
                } else {
                    item.setChecked(true);

                    // Add "Restaurant" to list of filter types
                    mFilterTypes.add("RESTAURANT");
                }

                break;
            case R.id.dashboard_menu_coffee_shop:
                // Set the state of the checkbox item
                if (item.isChecked()) {
                    item.setChecked(false);

                    // Remove "Coffee Shop" from list of filter types
                    mFilterTypes.remove("COFFEE_SHOP");
                } else {
                    item.setChecked(true);

                    // Add "Coffee Shop" to list of filter types
                    mFilterTypes.add("COFFEE_SHOP");
                }

                break;
            case R.id.dashboard_menu_bar:
                // Set the state of the checkbox item
                if (item.isChecked()) {
                    item.setChecked(false);

                    // Remove "Bar"  from list of filter types
                    mFilterTypes.remove("BAR");
                } else {
                    item.setChecked(true);

                    // Add "Bar" to list of filter types
                    mFilterTypes.add("BAR");
                }

                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        // Re-filter the results
        adapter.getFilter().filter(query);

        // Notify change
        adapter.notifyDataSetChanged();

        return true;
    }
}
