package com.example.bookapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.bookapp.model.JournalEntryEntity;
import com.example.bookapp.repository.JournalEntryRepository;
import java.util.List;

public class JournalEntryViewModel extends AndroidViewModel {
    private final JournalEntryRepository repository;
    public JournalEntryViewModel(@NonNull Application application) {
        super(application);
        repository = JournalEntryRepository.getInstance(application);
    }
    public LiveData<List<JournalEntryEntity>> getAllEntries() {
        return repository.getAllEntries();
    }

    public void deleteEntry(JournalEntryEntity entry) {
        repository.delete(entry);
    }
} 