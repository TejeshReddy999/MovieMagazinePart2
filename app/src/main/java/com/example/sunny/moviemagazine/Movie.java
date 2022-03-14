package com.example.sunny.moviemagazine;

/**
 * Created by sunny on 10-05-2018.
 */

public class Movie {
    private String Rating;
    private String Description;
    private String Title;
    private String Tumbnail;
    private String Release;
    private String BackDrop;

    public Movie(String tumbnail, String backDrop, String title, String rating, String release, String description) {
        Rating = rating;
        Description = description;
        Title = title;
        BackDrop = backDrop;
        Tumbnail = tumbnail;
        Release = release;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTumbnail() {
        return Tumbnail;
    }

    public void setTumbnail(String tumbnail) {
        Tumbnail = tumbnail;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getRelease() {
        return Release;
    }


    public void setRelease(String release) {
        Release = release;
    }

    public String getBackDrop() {
        return BackDrop;
    }

    public void setBackDrop(String backDrop) {
        BackDrop = backDrop;
    }
}
