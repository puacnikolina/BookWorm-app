package com.example.bookapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import com.example.bookapp.R;
import com.example.bookapp.fragments.BookListFragment;

public class HomeFragment extends Fragment {
    private BookListFragment bookListFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        Toolbar toolbar = view.findViewById(R.id.toolbar);



        final SearchView searchView = view.findViewById(R.id.search_bar);
        bookListFragment = new BookListFragment();
        getChildFragmentManager().beginTransaction()
                .replace(R.id.home_fragment_container, bookListFragment)
                .commit();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (bookListFragment != null) {
                    bookListFragment.searchBooks(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }
} 