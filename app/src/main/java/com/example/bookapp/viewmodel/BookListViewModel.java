package com.example.bookapp.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.bookapp.model.BookEntity;
import com.example.bookapp.repository.BookRepository;

import java.util.List;

public class BookListViewModel extends AndroidViewModel {

    private final BookRepository bookRepository;

    public BookListViewModel(@NonNull Application application) {
        super(application);
        bookRepository = BookRepository.getInstance(application);
    }

    public LiveData<List<BookEntity>> getBooks(){
        return bookRepository.getBooks();
    }

    public void searchBooksApi(String query, int pageNumber){
        bookRepository.searchBookApi(query, pageNumber);
    }

    public void searchNextPage(){
        bookRepository.searchNextPage();
    }


    //room -
    public void saveBookToLibrary(BookEntity book) {
        bookRepository.saveBookToLibrary(book);
    }

    public void deleteBook(BookEntity book) {
        bookRepository.deleteBookFromLibrary(book);
    }

    public LiveData<List<BookEntity>> getBooksByStatus(String status) {
        return bookRepository.getBooksByStatus(status);
    }


}
