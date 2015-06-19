package com.mg.GoldenBooks;

import android.app.Application;

import com.mg.GoldenBooks.interfaces.BooksService;

import retrofit.RestAdapter;
import timber.log.Timber;

public class BookApplication extends Application {

    public static BooksService sBooksService;

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public static BooksService getBooksService() {
        if(sBooksService == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint("http://assignment.gae.golgek.mobi/")
                    .build();

            sBooksService = restAdapter.create(BooksService.class);
        }
        return sBooksService;
    }
}
