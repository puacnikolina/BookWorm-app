package com.example.bookapp.response;

import com.example.bookapp.model.BookEntity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookSearchResponse {

    @SerializedName("docs")
    @Expose()
    private List<BookEntity> books;

    public List<BookEntity> getBooks(){
        return books;
    }
} 