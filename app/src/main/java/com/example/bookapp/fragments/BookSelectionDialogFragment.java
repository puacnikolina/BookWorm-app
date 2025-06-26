package com.example.bookapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bookapp.R;
import com.example.bookapp.adapters.BookAdapter;
import com.example.bookapp.adapters.OnBookListener;
import com.example.bookapp.model.BookEntity;
import com.example.bookapp.viewmodel.LibraryViewModel;
import java.util.List;

public class BookSelectionDialogFragment extends DialogFragment implements OnBookListener {
    private BookAdapter bookAdapter;
    private OnBookSelectedListener listener;

    public interface OnBookSelectedListener {
        void onBookSelected(BookEntity book);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnBookSelectedListener) {
            listener = (OnBookSelectedListener) context;
        } else if (getParentFragment() instanceof OnBookSelectedListener) {
            listener = (OnBookSelectedListener) getParentFragment();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = new RecyclerView(requireContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bookAdapter = new BookAdapter(this);
        recyclerView.setAdapter(bookAdapter);

        LibraryViewModel viewModel = new ViewModelProvider(this).get(LibraryViewModel.class);
        viewModel.getBooksByStatus("READ").observe(this, books -> {
            bookAdapter.setmBooks(books);
        });

        return new AlertDialog.Builder(requireContext())
                .setTitle("Select a Book")
                .setView(recyclerView)
                .setNegativeButton("Cancel", null)
                .create();
    }

    @Override
    public void onBookClick(int position) {
        BookEntity selectedBook = bookAdapter.getSelectedBook(position);
        if (listener != null) {
            listener.onBookSelected(selectedBook);
        }
        dismiss();
    }
} 