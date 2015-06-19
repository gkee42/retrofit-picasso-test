package com.mg.GoldenBooks.interfaces;

import com.mg.GoldenBooks.domain.Book;
import com.mg.GoldenBooks.domain.BookListItem;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface BooksService {

    @GET("/api/v10/items")
    void books(@Query("count") int count,
               @Query("offset") int offset,
               Callback<List<BookListItem>> callback);

    @GET("/api/v10/items/{id}")
    void bookDetails(@Path("id") int id,
                     Callback<Book> callback);

}
