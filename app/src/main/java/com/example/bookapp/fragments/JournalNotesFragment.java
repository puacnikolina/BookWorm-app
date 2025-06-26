package com.example.bookapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookapp.R;
import com.example.bookapp.adapters.JournalEntryAdapter;
import com.example.bookapp.model.JournalEntryEntity;
import com.example.bookapp.viewmodel.JournalEntryViewModel;

public class JournalNotesFragment extends Fragment implements JournalEntryAdapter.OnJournalEntryActionListener {
    private RecyclerView recyclerView;
    private JournalEntryAdapter adapter;
    private JournalEntryViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_journal_notes, container, false);
        

        viewModel = new ViewModelProvider(this).get(JournalEntryViewModel.class);
        
        recyclerView = view.findViewById(R.id.journal_notes_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new JournalEntryAdapter(this);
        recyclerView.setAdapter(adapter);

        viewModel.getAllEntries().observe(getViewLifecycleOwner(), entries -> {
            adapter.setEntries(entries);
        });

        return view;
    }

    @Override
    public void onDeleteEntry(JournalEntryEntity entry) {
        viewModel.deleteEntry(entry);
    }
} 