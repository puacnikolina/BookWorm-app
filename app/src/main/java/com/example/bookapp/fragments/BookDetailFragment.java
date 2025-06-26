package com.example.bookapp.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.bookapp.R;
import com.example.bookapp.model.BookEntity;
import com.example.bookapp.repository.BookRepository;
import com.example.bookapp.request.Service;
import com.example.bookapp.response.BookWorkResponse;
import com.example.bookapp.response.BookRatingsResponse;
import com.example.bookapp.viewmodel.BookListViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookDetailFragment extends Fragment {

    private ImageView imageViewDetails;
    private TextView titleDetails, descDetails, authorDetails, ratingDetails, subjectsDetails;
    private BookEntity bookEntity;
    private Button btnWantToRead, btnReading, btnRead;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        
        imageViewDetails = view.findViewById(R.id.imageView_details);
        titleDetails = view.findViewById(R.id.textView_title_details);
        descDetails = view.findViewById(R.id.textView_desc);
        authorDetails = view.findViewById(R.id.textView_author);
        ratingDetails = view.findViewById(R.id.textView_rating);
        subjectsDetails = view.findViewById(R.id.textView_subjects);

        btnWantToRead = view.findViewById(R.id.button_want_to_read);
        btnReading = view.findViewById(R.id.button_reading);
        btnRead = view.findViewById(R.id.button_read);

        setupButtonClickListeners();


        if (getArguments() != null) {
            bookEntity = getArguments().getParcelable("book");
        }

        if (bookEntity != null) {
            displayBookDetails();
            fetchDetailedBookInfo();
        }
        
        return view;
    }


    private void setupButtonClickListeners() {
        BookRepository repo = BookRepository.getInstance(requireActivity().getApplication());
        btnWantToRead.setOnClickListener(v -> {
            if (bookEntity != null) {
                bookEntity.setRead_status("WANT_TO_READ");
                repo.saveBookToLibrary(bookEntity);
                Toast.makeText(requireContext(), "Book added to Want to Read", Toast.LENGTH_SHORT).show();
            }
        });
        btnReading.setOnClickListener(v -> {
            if (bookEntity != null) {
                bookEntity.setRead_status("READING");
                repo.saveBookToLibrary(bookEntity);
                Toast.makeText(requireContext(), "Book added to Currently Reading", Toast.LENGTH_SHORT).show();
            }
        });
        btnRead.setOnClickListener(v -> {
            if (bookEntity != null) {
                showRatingDialog();
            }
        });
    }

    private void showRatingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.rating_dialog, null);
        
        RatingBar ratingBar = dialogView.findViewById(R.id.rating_bar);
        TextView ratingText = dialogView.findViewById(R.id.rating_text);
        

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (rating > 0) {
                ratingText.setText("Rating: " + (int)rating + " stars");
            } else {
                ratingText.setText("Select your rating");
            }
        });
        
        builder.setView(dialogView)
               .setTitle("Rate this book")
               .setPositiveButton("OK", (dialog, which) -> {
                   int rating = (int) ratingBar.getRating();
                   if (rating > 0) {
                       bookEntity.setRead_status("READ");
                       bookEntity.setUser_rating(rating);
                       BookRepository.getInstance(requireActivity().getApplication())
                                   .saveBookToLibrary(bookEntity);
                       Toast.makeText(requireContext(), "Book added to Read with rating: " + rating + " stars", Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(requireContext(), "Please select a rating", Toast.LENGTH_SHORT).show();
                   }
               })
               .setNegativeButton("Cancel", null)
               .show();
    }

    private void displayBookDetails() {
        if (bookEntity != null) {
            titleDetails.setText(bookEntity.getTitle());
            authorDetails.setText("By " + bookEntity.getFirstAuthorName());

            Glide.with(this)
                    .load(bookEntity.getCoverUrl())
                    .into(imageViewDetails);
        }
    }

    private void fetchDetailedBookInfo() {
        if (bookEntity != null && bookEntity.getKey() != null) {

            String originalKey = bookEntity.getKey();
            final String workKey;
            if (originalKey.startsWith("/works/")) {
                workKey = originalKey.substring(7);
            } else {
                workKey = originalKey;
            }

            fetchBookDetails(workKey);

            fetchBookRatings(workKey);
        }
    }


    private void fetchBookDetails(String workKey) {
        Call<BookWorkResponse> workCall = Service.getBookApi().getBookDetails(workKey);
        workCall.enqueue(new Callback<BookWorkResponse>() {
            @Override
            public void onResponse(Call<BookWorkResponse> call, Response<BookWorkResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BookWorkResponse workResponse = response.body();
                    Log.d("BookDetail", "Book details fetched successfully");
                    updateWithBookDetails(workResponse);
                } else {
                    Log.e("BookDetail", "Error fetching book details: " + response.code());
                    showNoBookDetails();
                }
            }

            @Override
            public void onFailure(Call<BookWorkResponse> call, Throwable t) {
                Log.e("BookDetail", "Network error fetching book details", t);
                showNoBookDetails();
            }
        });
    }


    private void fetchBookRatings(String workKey) {
        Call<BookRatingsResponse> ratingsCall = Service.getBookApi().getBookRatings(workKey);
        ratingsCall.enqueue(new Callback<BookRatingsResponse>() {
            @Override
            public void onResponse(Call<BookRatingsResponse> call, Response<BookRatingsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BookRatingsResponse ratingsResponse = response.body();
                    Log.d("BookDetail", "Ratings fetched successfully");
                    updateWithRatings(ratingsResponse);
                } else {
                    Log.e("BookDetail", "Error fetching ratings: " + response.code());
                    ratingDetails.setText("Rating: No available info");
                }
            }

            @Override
            public void onFailure(Call<BookRatingsResponse> call, Throwable t) {
                Log.e("BookDetail", "Network error fetching ratings", t);
                ratingDetails.setText("Rating: No available info");
            }
        });
    }


    private void updateWithBookDetails(BookWorkResponse workResponse) {
        if (workResponse != null) {

            if (workResponse.getTitle() != null && !workResponse.getTitle().isEmpty()) {
                titleDetails.setText(workResponse.getTitle());
            } else {
                titleDetails.setText("No available info");
            }


            if (workResponse.getDescription() != null && !workResponse.getDescription().isEmpty()) {
                String description = formatDescription(workResponse.getDescription());
                descDetails.setText(description);
            } else {
                descDetails.setText("No available info");
            }


            if (workResponse.getSubjects() != null && !workResponse.getSubjects().isEmpty()) {
                StringBuilder subjects = new StringBuilder("Genres: ");
                for (int i = 0; i < Math.min(5, workResponse.getSubjects().size()); i++) {
                    if (i > 0) subjects.append(", ");
                    subjects.append(workResponse.getSubjects().get(i));
                }
                subjectsDetails.setText(subjects.toString());
            } else {
                subjectsDetails.setText("Genres: No available info");
            }


            if (workResponse.getCoverUrl() != null) {
                Glide.with(this)
                        .load(workResponse.getCoverUrl())
                        .into(imageViewDetails);
            }
        }
    }


    private String formatDescription(String description) {
        if (description == null || description.isEmpty()) {
            return "No available info";
        }


        description = description.replaceAll("\\[([^\\]]+)\\]\\([^)]+\\)", "$1");

        description = description.replaceAll("https?://\\S+", "");

        description = description.replaceAll("\\s+", " ").trim();
        
        return description;
    }


    private void updateWithRatings(BookRatingsResponse ratingsResponse) {
        if (ratingsResponse != null) {
            ratingDetails.setText("Rating: " + ratingsResponse.getFormattedRating());
        } else {
            ratingDetails.setText("Rating: No available info");
        }
    }


    private void showNoBookDetails() {
        descDetails.setText("No available info");
        subjectsDetails.setText("Genres: No available info");
    }


} 