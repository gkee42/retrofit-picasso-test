package com.mg.GoldenBooks;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Displays the details of a book.
 */
public class BookDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_details);

        // Make sure the left "back" arrow shows in the action bar
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Listen for ActionBar back button
        if (item.getItemId() == android.R.id.home) {
            finish();
            return false;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
