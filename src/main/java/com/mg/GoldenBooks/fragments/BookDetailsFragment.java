package com.mg.GoldenBooks.fragments;

import com.actionbarsherlock.app.SherlockFragment;
import com.mg.GoldenBooks.R;
import com.mg.GoldenBooks.domain.Book;
import com.mg.GoldenBooks.domain.BookListItem;
import com.mg.GoldenBooks.intents.BookDetailsIntent;
import com.mg.GoldenBooks.interfaces.BookDetailsService;
import com.squareup.picasso.Picasso;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Display the details of a single book.
 */
public class BookDetailsFragment extends SherlockFragment {

    private ImageView mBookPic;

    private TextView mBookTitle;

    private TextView mBookAuthor;

    private TextView mBookPrice;

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        mBookPic = (ImageView) view
                .findViewById(R.id.fragment_book_details_image);

        mBookTitle = (TextView) view
                .findViewById(R.id.fragment_book_details_title);

        mBookAuthor = (TextView) view
                .findViewById(R.id.fragment_book_details_author);

        mBookPrice = (TextView) view
                .findViewById(R.id.fragment_book_details_price);

        // We already have the title, might as well display it at once
        BookListItem book = BookDetailsIntent
                .getBook(getActivity().getIntent());
        mBookTitle.setText(book.getTitle());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final Activity activity = getActivity();
        final Intent activityIntent = activity.getIntent();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setServer(getString(R.string.base_url))
                .build();

        BookDetailsService booksService = restAdapter
                .create(BookDetailsService.class);

        booksService.bookDetails(
                BookDetailsIntent.getBook(activityIntent).getId(), new Callback<Book>() {
            @Override
            public void success(Book book, Response response) {
                displayBookData(book);
            }

            @Override
            public void failure(RetrofitError e) {
                Log.w("MG", e.getMessage().toString());
                showErrorMessage(e.getMessage().toString());
            }
        });
    }

    private void showErrorMessage(String msg) {
        mBookTitle.setText(R.string.empty_list);
        mBookAuthor.setText(msg);
    }

    private void displayBookData(Book book) {

        if (book == null) {
            Log.w("MG", "Can't display book, it's null");
            return;
        }

        Picasso.with(getActivity())
                .load(book.getImage())
                .error(R.drawable.no_image)
                .into(mBookPic);

        mBookTitle.setText(book.getTitle());

        mBookAuthor.setText(book.getAuthor());

        // Make sure the currency displays as per user's locale
        NumberFormat format = NumberFormat
                .getCurrencyInstance();
        mBookPrice.setText(format.format(book.getPrice()));
    }
}
