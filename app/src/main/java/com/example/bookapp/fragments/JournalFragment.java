package com.example.bookapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.example.bookapp.activities.AddJournalEntryActivity;
import com.example.bookapp.databinding.FragmentJournalBinding;

public class JournalFragment extends Fragment {
    private FragmentJournalBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentJournalBinding.inflate(inflater, container, false);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity());
        binding.viewPager.setAdapter(adapter);

        binding.btnAddJournalEntry.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddJournalEntryActivity.class);
            startActivity(intent);
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {
        public ViewPagerAdapter(FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {

            return new com.example.bookapp.fragments.JournalNotesFragment();
        }

        @Override
        public int getItemCount() {
            return 1;
        }
    }
} 