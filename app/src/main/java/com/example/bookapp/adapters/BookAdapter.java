package com.example.bookapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookapp.R;
import com.example.bookapp.model.BookEntity;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BookEntity> mBooks;
    private OnBookListener onBookListener;

    public BookAdapter(OnBookListener onBookListener) {
        this.onBookListener = onBookListener;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_item,
                parent, false);
        return new BookViewHolder(view, onBookListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BookViewHolder) holder).title.setText(mBooks.get(position).getTitle());
        ((BookViewHolder) holder).author.setText(mBooks.get(position).getFirstAuthorName());


        BookEntity book = mBooks.get(position);
        if (book.getRead_status() != null && book.getRead_status().equals("READ") && book.getUser_rating() > 0) {
            ((BookViewHolder) holder).ratingBar.setRating(book.getUser_rating());
            ((BookViewHolder) holder).ratingBar.setVisibility(View.VISIBLE);
        } else {
            ((BookViewHolder) holder).ratingBar.setVisibility(View.GONE);
        }


        if (book.getRead_status() != null) {
            ((BookViewHolder) holder).deleteButton.setVisibility(View.VISIBLE);
            ((BookViewHolder) holder).deleteButton.setOnClickListener(v -> {
                if (onBookListener instanceof OnBookDeleteListener) {
                    ((OnBookDeleteListener) onBookListener).onBookDelete(position);
                }
            });
        } else {
            ((BookViewHolder) holder).deleteButton.setVisibility(View.GONE);
        }

        Glide.with(holder.itemView.getContext())
                .load(mBooks.get(position).getCoverUrl())
                .into(((BookViewHolder) holder).imageView);

    }

    @Override
    public int getItemCount() {
        if (mBooks != null) {
            return mBooks.size();
        }
        return 0;
    }

    public void setmBooks(List<BookEntity> mBooks) {
        this.mBooks = mBooks;
        notifyDataSetChanged();
    }

    public BookEntity getSelectedBook(int position) {
        if (mBooks != null) {
            if (mBooks.size() > 0) {
                return mBooks.get(position);
            }
        }
        return null;
    }

    public void deleteBook(int position) {
        if (mBooks != null && position >= 0 && position < mBooks.size()) {
            mBooks.remove(position);
            notifyItemRemoved(position);
        }
    }

    public interface OnBookDeleteListener {
        void onBookDelete(int position);
    }
} 