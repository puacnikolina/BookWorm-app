package com.example.bookapp.activities;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.example.bookapp.R;
import com.example.bookapp.fragments.HomeFragment;
import com.example.bookapp.fragments.LibraryFragment;
import com.example.bookapp.fragments.JournalFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    
    private View leftFragmentContainer;
    private View rightFragmentContainer;
    private boolean isTablet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        leftFragmentContainer = findViewById(R.id.left_fragment_container);
        rightFragmentContainer = findViewById(R.id.right_fragment_container);
        isTablet = (leftFragmentContainer != null && rightFragmentContainer != null);


        if (savedInstanceState == null) {
            if (isTablet) {
                getSupportFragmentManager().beginTransaction()
                    .replace(R.id.left_fragment_container, new HomeFragment())
                    .commit();
            } else {

                View fragmentContainer = findViewById(R.id.fragment_container);
                if (fragmentContainer != null) {
                    getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
                }
            }
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selected = null;
            if (item.getItemId() == R.id.home) {
                selected = new HomeFragment();
            } else if (item.getItemId() == R.id.library) {
                selected = new LibraryFragment();
            } else if (item.getItemId() == R.id.journal) {
                selected = new JournalFragment();
            }
            if (selected != null) {
                if (isTablet) {
                    getSupportFragmentManager().beginTransaction()
                        .replace(R.id.left_fragment_container, selected)
                        .commit();
                } else {

                    View fragmentContainer = findViewById(R.id.fragment_container);
                    if (fragmentContainer != null) {
                        getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, selected)
                            .commit();
                    }
                }
                return true;
            }
            return false;
        });
    }
} 