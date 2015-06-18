package com.mg.GoldenBooks.fragments;

import com.mg.GoldenBooks.R;
import com.mg.GoldenBooks.adapters.BooksAdapter;
import com.mg.GoldenBooks.domain.BookListItem;
import com.mg.GoldenBooks.intents.BookDetailsIntent;
import com.mg.GoldenBooks.interfaces.BooksService;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Main list of books.
 */
public class BooksFragment extends Fragment {

    private ListView mListView;

    private TextView mEmptyView;

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);
        mListView = (ListView) view.findViewById(R.id.fragment_home_list_view);
        mEmptyView = (TextView) view
                .findViewById(R.id.fragment_history_empty_text);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();

        // Set up the adapter - we don't have data for now, so use null.
        final BooksAdapter booksAdapter = new BooksAdapter(
                activity, null);
        mListView.setAdapter(booksAdapter);

        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> adapterView,
                            View view,
                            int position,
                            long id) {
                        BookDetailsIntent intent
                                = new BookDetailsIntent(
                                activity,
                                ((BooksAdapter) mListView
                                        .getAdapter())
                                        .getItem(position));
                        startActivity(intent);
                    }
                });

        // Fetch data using retrofit
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.base_url))
                .build();

        BooksService booksService = restAdapter.create(BooksService.class);
        // Get books asynchronously by passing a callback.
        booksService.books(new Callback<List<BookListItem>>() {
            @Override
            public void success(List<BookListItem> bookListItems, Response response) {
                // turn the list into an array
                BookListItem[] bookArray = new BookListItem[bookListItems.size()];
                bookListItems.toArray(bookArray);
                // populate list
                setAdapterData(bookArray);
            }

            @Override
            public void failure(RetrofitError e) {
                Log.w("MG", e.getMessage());
                showErrorMessage(getString(R.string.empty_list));
            }
        });
    }

    private void setAdapterData(BookListItem[] books) {
        final BooksAdapter booksAdapter
                = (BooksAdapter) mListView.getAdapter();
        booksAdapter.changeBooksList(books);
    }

    private void showErrorMessage(String msg) {
        // Error downloading data, set error message
        mEmptyView.setText(msg);
        mEmptyView.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }
}
