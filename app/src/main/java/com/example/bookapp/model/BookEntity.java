package com.example.bookapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;
@Entity(tableName = "books")
public class BookEntity implements Parcelable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "book_key")
    @SerializedName("key")
    private String key;

    @ColumnInfo(name = "title", defaultValue = "Unknown Title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "author_name")
    @SerializedName("author_name")
    private List<String> author_name;

    @ColumnInfo(name = "cover_id", defaultValue = "0")
    @SerializedName("cover_i")
    private int cover_i;

    @ColumnInfo(name = "read_status")
    private String read_status;

    @ColumnInfo(name = "user_rating", defaultValue = "0")
    private int user_rating;

    public BookEntity(String key, String title, List<String> author_name, int cover_i) {
        this.key = key;
        this.title = title;
        this.author_name = author_name;
        this.cover_i = cover_i;
        this.read_status = null;
        this.user_rating = 0;
    }

    protected BookEntity(Parcel in) {
        key = in.readString();
        title = in.readString();
        author_name = in.createStringArrayList();
        cover_i = in.readInt();
        read_status = in.readString();
        user_rating = in.readInt();
    }

    public static final Creator<BookEntity> CREATOR = new Creator<BookEntity>() {
        @Override
        public BookEntity createFromParcel(Parcel in) {
            return new BookEntity(in);
        }

        @Override
        public BookEntity[] newArray(int size) {
            return new BookEntity[size];
        }
    };

    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getAuthor_name() {
        return author_name;
    }

    public int getCover_i() {
        return cover_i;
    }

    public String getFirstAuthorName() {
        if (author_name != null && !author_name.isEmpty()) {
            return author_name.get(0);
        }
        return "N/A";
    }

    public String getCoverUrl() {
        if (cover_i > 0) {
            return "https://covers.openlibrary.org/b/id/" + cover_i + "-M.jpg";
        }
        return null;
    }

    public String getRead_status() {
        return read_status;
    }

    public void setRead_status(String read_status) {
        this.read_status = read_status;
    }

    public int getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(int user_rating) {
        this.user_rating = user_rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(title);
        dest.writeStringList(author_name);
        dest.writeInt(cover_i);
        dest.writeString(read_status);
        dest.writeInt(user_rating);
    }

}