package com.example.bookapp.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookRatingsResponse {

    @SerializedName("summary")
    @Expose()
    private RatingSummary summary;

    public static class RatingSummary {
        @SerializedName("average")
        @Expose()
        private double average;

        @SerializedName("count")
        @Expose()
        private int count;

        public double getAverage() {
            return average;
        }

        public int getCount() {
            return count;
        }
    }

    public String getFormattedRating() {
        if (summary != null && summary.getCount() > 0) {
            return String.format("%.1f/5", summary.getAverage());
        }
        return "No rating";
    }
} 