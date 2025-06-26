package com.example.bookapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.bookapp.utils.AppExecutors;
import com.example.bookapp.model.BookEntity;
import com.example.bookapp.response.BookSearchResponse;
import com.example.bookapp.utils.BookApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class BookApiClient {

    private MutableLiveData<List<BookEntity>> mBooks;
    private static BookApiClient instance;
    private RetrieveBooksRunnable retrieveBooksRunnable;

    public static BookApiClient getInstance() {
        if (instance == null) {
            instance = new BookApiClient();
        }
        return instance;
    }

    private BookApiClient() {
        mBooks = new MutableLiveData<>();
    }

    public LiveData<List<BookEntity>> getBooks() {
        return mBooks;
    }

    public void searchBooksApi(String query, int page_count) {
        if (retrieveBooksRunnable != null) {
            retrieveBooksRunnable = null;
        }

        retrieveBooksRunnable = new RetrieveBooksRunnable(page_count, query);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveBooksRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 3000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveBooksRunnable implements Runnable {

        private String query;
        private int page_number;
        private boolean cancel_req;

        public RetrieveBooksRunnable(int page_number, String query) {
            this.page_number = page_number;
            this.query = query;
            this.cancel_req = false;
        }

        @Override
        public void run() {
            try {
                Response response = getBooks(query, page_number).execute();
                if (cancel_req) {
                    return;
                }
                if (response.isSuccessful()) {
                    BookSearchResponse searchResponse = (BookSearchResponse) response.body();
                    if (searchResponse != null) {
                        List<BookEntity> list = new ArrayList<>(searchResponse.getBooks());
                        if (page_number == 1) {
                            mBooks.postValue(list);
                        } else {
                            List<BookEntity> currentBooks = mBooks.getValue();
                            if (currentBooks != null) {
                                currentBooks.addAll(list);
                                mBooks.postValue(currentBooks);
                            }
                        }
                    }
                } else {
                    String error = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                    Log.v("Tag", "Error: " + error);
                    mBooks.postValue(null);
                }
            } catch (IOException e) {
                Log.e("Tag", "IOException", e);
                mBooks.postValue(null);
            }
        }

        private Call<BookSearchResponse> getBooks(String query, int page_number) {
            BookApi bookApi = Service.getBookApi();
            return bookApi.searchBook(query, page_number);
        }

        private void cancelRequest() {
            Log.v("Tag", "Canceling search request");
            cancel_req = true;
        }
    }
} 