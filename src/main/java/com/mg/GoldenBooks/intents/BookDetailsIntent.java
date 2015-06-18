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
    private static final String EXTRA_BOOK = "extraMessage";

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
     * Create an BookDetailsIntent from an Intent. Useful for accessing members
     * with getters.
     *
     * @param intent The Intent supplied to us by Android.
     */
    public BookDetailsIntent(final Intent intent) {
        super(intent);
    }

    /**
     * @return The Book ID stored in this intent.
     */
    public BookListItem getBook() {
        return getBook(this);
    }

    /**
     * @param intent The Intent to retrieve the book ID from.
     * @return The ID of a book.
     */
    public static BookListItem getBook(final Intent intent) {
//        return intent.getIntExtra(EXTRA_BOOK, -1);
        final Parcelable book = intent.getParcelableExtra(EXTRA_BOOK);
        if (book instanceof BookListItem) {
            return (BookListItem) book;
        } else {
            return null;
        }
    }
}
