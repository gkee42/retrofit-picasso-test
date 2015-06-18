package com.mg.GoldenBooks;


import com.actionbarsherlock.app.SherlockFragmentActivity;

import android.os.Bundle;

/**
 * Home, Sweet Home.
 */
public class BooksActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
    }
}
