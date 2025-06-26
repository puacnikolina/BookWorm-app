package com.example.bookapp.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "journal_entries")
public class JournalEntryEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "date_created")
    private long date_created;

    @ColumnInfo(name = "book_name")
    private String bookName;

    @ColumnInfo(name = "book_author")
    private String bookAuthor;

    @ColumnInfo(name = "book_cover_url")
    private String bookCoverUrl;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    public JournalEntryEntity(long date_created, String bookName, String bookAuthor, String bookCoverUrl, String title, String description) {
        this.date_created = date_created;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.bookCoverUrl = bookCoverUrl;
        this.title = title;
        this.description = description;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public long getDate_created() { return date_created; }
    public void setDate_created(long date_created) { this.date_created = date_created; }

    public String getBookName() { return bookName; }
    public void setBookName(String bookName) { this.bookName = bookName; }

    public String getBookAuthor() { return bookAuthor; }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }

    public String getBookCoverUrl() { return bookCoverUrl; }
    public void setBookCoverUrl(String bookCoverUrl) { this.bookCoverUrl = bookCoverUrl; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
} 