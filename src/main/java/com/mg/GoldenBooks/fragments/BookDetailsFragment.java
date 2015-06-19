package com.mg.GoldenBooks.fragments;

import com.mg.GoldenBooks.R;
import com.mg.GoldenBooks.domain.Book;
import com.mg.GoldenBooks.domain.BookListItem;
import com.mg.GoldenBooks.intents.BookDetailsIntent;
import com.mg.GoldenBooks.BookApplication;
import com.mg.GoldenBooks.interfaces.BooksService;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;


/**
 * Display the details of a single book.
 */
public class BookDetailsFragment extends Fragment {


    @InjectView (R.id.fragment_book_details_image)
    ImageView mBookPic;

    @InjectView (R.id.fragment_book_details_title)
    TextView mBookTitle;

    @InjectView (R.id.fragment_book_details_author)
    TextView mBookAuthor;

    @InjectView (R.id.fragment_book_details_price)
    TextView mBookPrice;

    private BookListItem mBook;

    @Override
    public View onCreateView(final LayoutInflater inflater,
            final ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);

        ButterKnife.inject(this, view);

        // We already have the title, might as well display it at once
        mBook = BookDetailsIntent.getBook(getActivity().getIntent());
        mBookTitle.setText(mBook.getTitle());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO Inject bookservice with dagger?
        BooksService booksService = ((BookApplication) getActivity().getApplication()).getBooksService();

        booksService.bookDetails(mBook.getId(), new Callback<Book>() {
            @Override
            public void success(Book book, Response response) {
                displayBookData(book);
            }

            @Override
            public void failure(RetrofitError e) {
                Timber.w("MG", e.getMessage());
                showErrorMessage(e.getMessage());
            }
        });
    }

    private void showErrorMessage(String msg) {
        mBookTitle.setText(R.string.empty_list);
        mBookAuthor.setText(msg);
    }

    private void displayBookData(Book book) {

        if (book == null) {
            Timber.w("MG", "Can't display book, it's null");
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
