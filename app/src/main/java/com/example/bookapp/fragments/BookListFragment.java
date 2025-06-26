package com.example.bookapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.bookapp.R;
import com.example.bookapp.adapters.BookAdapter;
import com.example.bookapp.adapters.OnBookListener;
import com.example.bookapp.model.BookEntity;
import com.example.bookapp.viewmodel.BookListViewModel;


import java.util.List;

public class BookListFragment extends Fragment implements OnBookListener {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private BookListViewModel bookListViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);
        
        recyclerView = view.findViewById(R.id.recyclerView);
        
        bookListViewModel = new ViewModelProvider(requireActivity()).get(BookListViewModel.class);
        
        ConfigureRecyclerView();
        ObserveAnyChange();
        

        bookListViewModel.searchBooksApi("bestseller popular classic", 1);
        
        return view;
    }

    private void ObserveAnyChange() {
        bookListViewModel.getBooks().observe(getViewLifecycleOwner(), new Observer<List<BookEntity>>() {
            @Override
            public void onChanged(List<BookEntity> bookEntities) {
                if (bookEntities != null) {
                    for (BookEntity book : bookEntities) {
                        Log.v("Tag", "Name: " + book.getTitle());
                        bookAdapter.setmBooks(bookEntities);
                    }
                }
            }
        });
    }

    private void ConfigureRecyclerView() {
        bookAdapter = new BookAdapter(this);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    bookListViewModel.searchNextPage();
                }
            }
        });
    }

    public void searchBooks(String query) {
        bookListViewModel.searchBooksApi(query, 1);
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

} 