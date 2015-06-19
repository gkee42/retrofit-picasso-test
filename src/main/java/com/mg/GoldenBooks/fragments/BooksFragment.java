package com.mg.GoldenBooks.fragments;

import com.mg.GoldenBooks.R;
import com.mg.GoldenBooks.adapters.BooksAdapter;
import com.mg.GoldenBooks.domain.BookListItem;
import com.mg.GoldenBooks.intents.BookDetailsIntent;
import com.mg.GoldenBooks.BookApplication;
import com.mg.GoldenBooks.interfaces.BooksService;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

/**
 * Main list of books.
 */
public class BooksFragment extends Fragment {

    @InjectView(R.id.fragment_home_list_view)
    ListView mListView;

    @InjectView(R.id.fragment_history_empty_text)
    TextView mEmptyView;

    @InjectView(R.id.progress)
    ProgressBar mProgress;

    private int mOffset = 0;
    private static final int COUNT = 10;

    private boolean isLoading = false;
    private boolean hasMore = true;

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_books, container, false);

        ButterKnife.inject(this, view);

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
                                = new BookDetailsIntent(activity,
                                ((BooksAdapter) mListView
                                        .getAdapter())
                                        .getItem(position));
                        startActivity(intent);
                    }
                });

        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) { // download when user stops scrolling...
                    if (mListView.getLastVisiblePosition() >= mListView.getCount() - 1 - 2/*threshold*/) {
                        loadMore();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                // Do nothing
            }
        });

        loadMore();
    }

    private void loadMore() {
        // TODO Inject bookservice with dagger?

        if(isLoading || !hasMore) {
            return;
        }

        isLoading=true;

        // Fetch data using retrofit
        // Get books asynchronously by passing a callback.
        BooksService booksService = ((BookApplication) getActivity().getApplication()).getBooksService();

        booksService.books(COUNT, mOffset, new Callback<List<BookListItem>>() {
            @Override
            public void success(List<BookListItem> bookListItems, Response response) {
                mProgress.setVisibility(View.GONE);
                // populate list
                setAdapterData(bookListItems);

                mOffset += COUNT;
                isLoading = false;
                hasMore =  bookListItems.size() == COUNT;
            }

            @Override
            public void failure(RetrofitError e) {
                Timber.w("MG", e.getMessage());
                mProgress.setVisibility(View.GONE);
                showErrorMessage(getString(R.string.empty_list));
                isLoading = false;
            }
        });

    }

    private void setAdapterData(List<BookListItem> books) {
        final BooksAdapter booksAdapter
                = (BooksAdapter) mListView.getAdapter();
        booksAdapter.addBooksToList(books);
    }

    private void showErrorMessage(String msg) {
        // Error downloading data, set error message
        mEmptyView.setText(msg);
        mEmptyView.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }
}
