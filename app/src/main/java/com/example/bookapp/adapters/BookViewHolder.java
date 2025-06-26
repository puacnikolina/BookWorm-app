package com.example.bookapp.adapters;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.R;

public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView title, author;
    ImageView imageView;
    RatingBar ratingBar;
    ImageButton deleteButton;
    OnBookListener onBookListener;

    public BookViewHolder(@NonNull View itemView, OnBookListener onBookListener) {
        super(itemView);
        this.onBookListener = onBookListener;
        title = itemView.findViewById(R.id.book_title);
        imageView = itemView.findViewById(R.id.book_img);
        author = itemView.findViewById(R.id.book_author);
        ratingBar = itemView.findViewById(R.id.book_rating);
        deleteButton = itemView.findViewById(R.id.delete_button);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onBookListener.onBookClick(getAbsoluteAdapterPosition());
    }
} 