package com.example.bookapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.bookapp.R;
import com.example.bookapp.fragments.BookSelectionDialogFragment;
import com.example.bookapp.model.BookEntity;
import com.example.bookapp.model.JournalEntryEntity;
import com.example.bookapp.repository.JournalEntryRepository;

public class AddJournalEntryActivity extends AppCompatActivity implements BookSelectionDialogFragment.OnBookSelectedListener {
    private BookEntity selectedBook;
    private int editingEntryId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal_entry);

        ImageView ivBookCover = findViewById(R.id.iv_book_cover);


        editingEntryId = getIntent().getIntExtra("entry_id", -1);
        if (editingEntryId != -1) {

            EditText etBookName = findViewById(R.id.et_book_name);
            EditText etBookAuthor = findViewById(R.id.et_book_author);
            EditText etBookCoverUrl = findViewById(R.id.et_book_cover_url);
            EditText etTitle = findViewById(R.id.et_note_title);
            EditText etDescription = findViewById(R.id.et_note_description);
            
            String bookName = getIntent().getStringExtra("book_name");
            String bookAuthor = getIntent().getStringExtra("book_author");
            String bookCoverUrl = getIntent().getStringExtra("book_cover_url");
            String noteTitle = getIntent().getStringExtra("note_title");
            String noteDescription = getIntent().getStringExtra("note_description");
            
            etBookName.setText(bookName);
            etBookAuthor.setText(bookAuthor);
            etBookCoverUrl.setText(bookCoverUrl);
            etTitle.setText(noteTitle);
            etDescription.setText(noteDescription);
            

            if (bookCoverUrl != null && !bookCoverUrl.isEmpty()) {
                ivBookCover.setVisibility(View.VISIBLE);
                Glide.with(this)
                    .load(bookCoverUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .into(ivBookCover);
            }
            

            androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
            toolbar.setTitle("Edit Journal Entry");
        }

        Button btnChooseBook = findViewById(R.id.btn_choose_book);
        btnChooseBook.setOnClickListener(v -> {
            new BookSelectionDialogFragment().show(getSupportFragmentManager(), "book_select");
        });

        Button btnSave = findViewById(R.id.btn_save_entry);
        btnSave.setOnClickListener(v -> {
            EditText etBookName = findViewById(R.id.et_book_name);
            EditText etBookAuthor = findViewById(R.id.et_book_author);
            EditText etBookCoverUrl = findViewById(R.id.et_book_cover_url);
            EditText etTitle = findViewById(R.id.et_note_title);
            EditText etDescription = findViewById(R.id.et_note_description);
            String bookName = etBookName.getText().toString().trim();
            String bookAuthor = etBookAuthor.getText().toString().trim();
            String bookCoverUrl = etBookCoverUrl.getText().toString().trim();
            String title = etTitle.getText().toString().trim();
            String description = etDescription.getText().toString().trim();
            if (bookName.isEmpty() || title.isEmpty() || description.isEmpty()) {
                // Optionally show error to user
                return;
            }
            
            JournalEntryEntity entry;
            if (editingEntryId != -1) {

                entry = new JournalEntryEntity(
                    System.currentTimeMillis(),
                    bookName,
                    bookAuthor,
                    bookCoverUrl,
                    title,
                    description
                );
                entry.setId(editingEntryId);
                JournalEntryRepository.getInstance(getApplication()).update(entry);
            } else {

                long dateCreated = new java.util.Date().getTime();
                entry = new JournalEntryEntity(
                    dateCreated,
                    bookName,
                    bookAuthor,
                    bookCoverUrl,
                    title,
                    description
                );
                JournalEntryRepository.getInstance(getApplication()).insert(entry);
            }
            finish();
        });
    }

    @Override
    public void onBookSelected(BookEntity book) {
        selectedBook = book;
        EditText etBookName = findViewById(R.id.et_book_name);
        EditText etBookAuthor = findViewById(R.id.et_book_author);
        EditText etBookCoverUrl = findViewById(R.id.et_book_cover_url);
        ImageView ivBookCover = findViewById(R.id.iv_book_cover);
        
        etBookName.setText(book.getTitle());
        etBookAuthor.setText(book.getFirstAuthorName());
        etBookCoverUrl.setText(book.getCoverUrl());
        

        if (book.getCoverUrl() != null && !book.getCoverUrl().isEmpty()) {
            ivBookCover.setVisibility(View.VISIBLE);
            Glide.with(this)
                .load(book.getCoverUrl())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(ivBookCover);
        } else {
            ivBookCover.setVisibility(View.GONE);
        }
    }
} 