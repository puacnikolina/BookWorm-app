package com.example.bookapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.bookapp.database.BookDao;
import com.example.bookapp.database.BookDatabase;
import com.example.bookapp.model.BookEntity;
import com.example.bookapp.request.BookApiClient;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BookRepository {

    private static BookRepository instance;
    private static BookApiClient bookApiClient;
    private String mQuery;
    private int mPage;

    private final BookDao bookDao;
    private LiveData<List<BookEntity>> allBooksFromDb;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public static BookRepository getInstance(Application application) {
        if (instance == null) {
            instance = new BookRepository(application);
        }
        return instance;
    }

    public BookRepository(Application application) {
        bookApiClient = BookApiClient.getInstance();
        BookDatabase db = BookDatabase.getInstance(application);
        bookDao = db.bookDao();
        allBooksFromDb = bookDao.getAllBooks();
    }

    public void saveBookToLibrary(BookEntity book) {
        Executors.newSingleThreadExecutor().execute(() -> bookDao.insert(book));
    }

    public void deleteBookFromLibrary(BookEntity book) {
        Executors.newSingleThreadExecutor().execute(() -> bookDao.delete(book));
    }

    public LiveData<List<BookEntity>> getBooksByStatus(String status) {
        return bookDao.getBooksByStatus(status);
    }

    public LiveData<List<BookEntity>> getBooks(){
        return bookApiClient.getBooks();
    }

    public void searchBookApi(String query, int pageNumber){
        mQuery = query;
        mPage = pageNumber;
        bookApiClient.searchBooksApi(query,pageNumber);
    }

    public void searchNextPage(){
        searchBookApi(mQuery,mPage+1);
    }

} 