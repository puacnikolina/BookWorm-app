package com.example.bookapp.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.bookapp.model.BookEntity;
import com.example.bookapp.repository.BookRepository;

import java.util.List;

public class LibraryViewModel extends AndroidViewModel {

    private BookRepository repository;


    public LibraryViewModel(Application application) {
        super(application);
        repository = BookRepository.getInstance(application);
    }

    public LiveData<List<BookEntity>> getBooksByStatus(String status) {
        return repository.getBooksByStatus(status);
    }

    public void insertBook(BookEntity book) {
        repository.saveBookToLibrary(book);
    }

    public void deleteBook(BookEntity book) {
        repository.deleteBookFromLibrary(book);
    }
}
