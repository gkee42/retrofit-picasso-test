package com.mg.GoldenBooks.intents;

import com.mg.GoldenBooks.BookDetailsActivity;
import com.mg.GoldenBooks.domain.BookListItem;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

public class BookDetailsIntent extends Intent {

    /**
     * The partial book object to be displayed.
     */
    private static final String EXTRA_BOOK = "extraBook";

    /**
     * Create an Intent containing a book object.
     *
     * @param bookListItem the book to be displayed.
     */
    public BookDetailsIntent(final Context context, final BookListItem bookListItem) {
        super(context, BookDetailsActivity.class);
        putExtra(EXTRA_BOOK, bookListItem);
    }

    /**
     * @param intent The Intent to retrieve the book from.
     * @return The BookListItem containing some book details.
     */
    public static BookListItem getBook(final Intent intent) {
        final Parcelable book = intent.getParcelableExtra(EXTRA_BOOK);
        if (book instanceof BookListItem) {
            return (BookListItem) book;
        } else {
            return null;
        }
    }
}
