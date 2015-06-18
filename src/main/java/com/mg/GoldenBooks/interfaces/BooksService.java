package com.mg.GoldenBooks.interfaces;

import com.mg.GoldenBooks.domain.BookListItem;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface BooksService {

    @GET("/api/v10/items")
    void books(Callback<List<BookListItem>> callback);

}
