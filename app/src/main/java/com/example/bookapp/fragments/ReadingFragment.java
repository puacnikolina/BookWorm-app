package com.example.bookapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.R;
import com.example.bookapp.adapters.BookAdapter;
import com.example.bookapp.adapters.OnBookListener;
import com.example.bookapp.model.BookEntity;
import com.example.bookapp.viewmodel.LibraryViewModel;
import com.example.bookapp.fragments.BookDetailFragment;

public class ReadingFragment extends Fragment implements OnBookListener, BookAdapter.OnBookDeleteListener {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private LibraryViewModel libraryViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reading, container, false);

        recyclerView = view.findViewById(R.id.reading_recycler_view);

        libraryViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(LibraryViewModel.class);

        setupRecyclerView();
        observeBooks();

        return view;
    }

    private void setupRecyclerView() {
        bookAdapter = new BookAdapter(this);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void observeBooks() {
        libraryViewModel.getBooksByStatus("READING").observe(getViewLifecycleOwner(), books -> {
            if (books != null) {
                bookAdapter.setmBooks(books);
            }
        });
    }

    @Override
    public void onBookClick(int position) {
        BookEntity selectedBook = bookAdapter.getSelectedBook(position);
        BookDetailFragment fragment = new BookDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("book", selectedBook);
        fragment.setArguments(args);
        

        View rightContainer = requireActivity().findViewById(R.id.right_fragment_container);
        if (rightContainer != null) {

            requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.right_fragment_container, fragment)
                .addToBackStack(null)
                .commit();
        } else {

            requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
        }
    }

    @Override
    public void onBookDelete(int position) {
        BookEntity bookToDelete = bookAdapter.getSelectedBook(position);
        if (bookToDelete != null) {
            new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                    .setTitle("Delete Book")
                    .setMessage("Are you sure you want to delete '" + bookToDelete.getTitle() + "' from your library?")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        libraryViewModel.deleteBook(bookToDelete);
                        bookAdapter.deleteBook(position);
                        Toast.makeText(requireContext(), "Book deleted from library", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
    }
}
