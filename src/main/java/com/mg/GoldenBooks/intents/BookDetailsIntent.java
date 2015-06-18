package com.mg.GoldenBooks.intents;

import com.mg.GoldenBooks.BookDetailsActivity;
import com.mg.GoldenBooks.domain.BookListItem;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

public class BookDetailsIntent extends Intent {

    /**
     * A quick message for the user, ie. Hello World.
     */
    private static final String EXTRA_BOOK_ID = "extraMessage";

    /**
     * Create an Intent containing a message.
     *
     * @param bookId ID of the book to be displayed.
     */
    public BookDetailsIntent(final int bookId) {
        putExtra(EXTRA_BOOK_ID, bookId);
    }

    public BookDetailsIntent(final Context context, final BookListItem bookId) {
        super(context, BookDetailsActivity.class);
        putExtra(EXTRA_BOOK_ID, bookId);
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
//        return intent.getIntExtra(EXTRA_BOOK_ID, -1);
        final Parcelable book = intent.getParcelableExtra(EXTRA_BOOK_ID);
        if (book instanceof BookListItem) {
            return (BookListItem) book;
        } else {
            return null;
        }
    }
}
