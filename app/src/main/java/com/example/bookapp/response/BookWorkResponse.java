package com.example.bookapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookWorkResponse {

    @SerializedName("title")
    @Expose()
    private String title;

    @SerializedName("key")
    @Expose()
    private String key;

    @SerializedName("description")
    @Expose()
    private String description;

    @SerializedName("subjects")
    @Expose()
    private List<String> subjects;

    @SerializedName("covers")
    @Expose()
    private List<Integer> covers;

    public String getTitle() {
        return title;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public List<Integer> getCovers() {
        return covers;
    }

    public String getCoverUrl() {
        if (covers != null && !covers.isEmpty() && covers.get(0) > 0) {
            return "https://covers.openlibrary.org/b/id/" + covers.get(0) + "-L.jpg";
        }
        return null;
    }

} 