package com.example.bookapp.utils;

import com.example.bookapp.response.BookSearchResponse;
import com.example.bookapp.response.BookWorkResponse;
import com.example.bookapp.response.BookRatingsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookApi {

    @GET("/search.json")
    Call<BookSearchResponse> searchBook(
            @Query("q") String query,
            @Query("page") int page
    );

    @GET("/works/{workKey}.json")
    Call<BookWorkResponse> getBookDetails(
            @Path("workKey") String workKey
    );

    @GET("/works/{workKey}/ratings.json")
    Call<BookRatingsResponse> getBookRatings(
            @Path("workKey") String workKey
    );
} 