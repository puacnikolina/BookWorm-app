package com.example.bookapp.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import com.example.bookapp.database.BookDatabase;
import com.example.bookapp.database.JournalEntryDao;
import com.example.bookapp.model.JournalEntryEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class JournalEntryRepository {
    private static JournalEntryRepository instance;
    private final JournalEntryDao journalEntryDao;
    private final Executor executor = Executors.newSingleThreadExecutor();

    public static JournalEntryRepository getInstance(Application application) {
        if (instance == null) {
            instance = new JournalEntryRepository(application);
        }
        return instance;
    }

    private JournalEntryRepository(Application application) {
        BookDatabase db = BookDatabase.getInstance(application);
        journalEntryDao = db.journalEntryDao();
    }

    public void insert(JournalEntryEntity entry) {
        executor.execute(() -> journalEntryDao.insert(entry));
    }

    public void update(JournalEntryEntity entry) {
        executor.execute(() -> journalEntryDao.update(entry));
    }

    public void delete(JournalEntryEntity entry) {
        executor.execute(() -> journalEntryDao.delete(entry));
    }

    public LiveData<List<JournalEntryEntity>> getAllEntries() {
        return journalEntryDao.getAllEntries();
    }
} 