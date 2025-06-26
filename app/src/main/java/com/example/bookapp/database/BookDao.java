package com.example.bookapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bookapp.model.BookEntity;

import java.util.List;

@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookEntity book);

    @Update
    void update(BookEntity book);

    @Delete
    void delete(BookEntity book);

    @Query("SELECT * FROM books")
    LiveData<List<BookEntity>> getAllBooks();

    @Query("SELECT * FROM books WHERE `book_key` = :key")
    BookEntity getBookByKey(String key);

    @Query("SELECT * FROM books WHERE title LIKE '%' || :searchQuery || '%'")
    List<BookEntity> searchBooks(String searchQuery);

    @Query("SELECT * FROM books WHERE read_status = :status")
    LiveData<List<BookEntity>> getBooksByStatus(String status);



}