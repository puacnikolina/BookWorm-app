package com.example.bookapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Delete;
import androidx.room.Update;
import androidx.lifecycle.LiveData;
import java.util.List;
import androidx.room.Query;

import com.example.bookapp.model.JournalEntryEntity;

@Dao
public interface JournalEntryDao {
    @Insert
    void insert(JournalEntryEntity entry);

    @Update
    void update(JournalEntryEntity entry);

    @Delete
    void delete(JournalEntryEntity entry);

    @Query("SELECT * FROM journal_entries ORDER BY date_created DESC")
    LiveData<List<JournalEntryEntity>> getAllEntries();
}
