package com.mg.GoldenBooks.interfaces;

import com.mg.GoldenBooks.domain.Book;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by mg on 8/7/13.
 */
public interface BookDetailsService {

    @GET("/api/v10/items/{id}")
    void  bookDetails(
            @Path("id") int id,
            Callback<Book> callback
            );

}
