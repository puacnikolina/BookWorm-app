package com.example.bookapp.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.bookapp.R;
import com.example.bookapp.activities.AddJournalEntryActivity;
import com.example.bookapp.model.JournalEntryEntity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JournalEntryAdapter extends RecyclerView.Adapter<JournalEntryAdapter.EntryViewHolder> {
    private List<JournalEntryEntity> entries;
    private OnJournalEntryActionListener listener;

    public interface OnJournalEntryActionListener {
        void onDeleteEntry(JournalEntryEntity entry);
    }

    public JournalEntryAdapter(OnJournalEntryActionListener listener) {
        this.listener = listener;
    }

    public void setEntries(List<JournalEntryEntity> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.journal_entry_item, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        JournalEntryEntity entry = entries.get(position);
        holder.bind(entry);
        

        holder.btnEdit.setOnClickListener(v -> {
            // Open edit activity
            Intent intent = new Intent(v.getContext(), AddJournalEntryActivity.class);
            intent.putExtra("entry_id", entry.getId());
            intent.putExtra("book_name", entry.getBookName());
            intent.putExtra("book_author", entry.getBookAuthor());
            intent.putExtra("book_cover_url", entry.getBookCoverUrl());
            intent.putExtra("note_title", entry.getTitle());
            intent.putExtra("note_description", entry.getDescription());
            v.getContext().startActivity(intent);
        });
        
        holder.btnDelete.setOnClickListener(v -> {

            new AlertDialog.Builder(v.getContext())
                .setTitle("Delete Note")
                .setMessage("Are you sure you want to delete this note?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (listener != null) {
                        listener.onDeleteEntry(entry);
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
        });
    }

    @Override
    public int getItemCount() {
        return entries != null ? entries.size() : 0;
    }

    static class EntryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivBookCover;
        private final TextView tvBookName;
        private final TextView tvBookAuthor;
        private final TextView tvNoteTitle;
        private final TextView tvNoteDescription;
        private final TextView tvDate;
        private final ImageButton btnEdit;
        private final ImageButton btnDelete;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBookCover = itemView.findViewById(R.id.iv_book_cover);
            tvBookName = itemView.findViewById(R.id.tv_book_name);
            tvBookAuthor = itemView.findViewById(R.id.tv_book_author);
            tvNoteTitle = itemView.findViewById(R.id.tv_note_title);
            tvNoteDescription = itemView.findViewById(R.id.tv_note_description);
            tvDate = itemView.findViewById(R.id.tv_date);
            btnEdit = itemView.findViewById(R.id.btn_edit_note);
            btnDelete = itemView.findViewById(R.id.btn_delete_note);
        }

        public void bind(JournalEntryEntity entry) {
            tvBookName.setText(entry.getBookName());
            tvBookAuthor.setText(entry.getBookAuthor());
            tvNoteTitle.setText(entry.getTitle());
            tvNoteDescription.setText(entry.getDescription());
            String dateStr = new SimpleDateFormat("yyyy-MM-dd").format(new Date(entry.getDate_created()));
            tvDate.setText(dateStr);
            if (entry.getBookCoverUrl() != null && !entry.getBookCoverUrl().isEmpty()) {
                Glide.with(ivBookCover.getContext())
                    .load(entry.getBookCoverUrl())
                    .into(ivBookCover);
            } else {
                ivBookCover.setImageResource(R.drawable.ic_launcher_background);
            }
        }
    }
} 