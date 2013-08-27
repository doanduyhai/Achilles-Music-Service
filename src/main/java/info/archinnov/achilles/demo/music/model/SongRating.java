package info.archinnov.achilles.demo.music.model;

import info.archinnov.achilles.demo.music.constants.Rating;
import java.util.Date;

public class SongRating {

    private Date date;

    private Rating rating;

    private String comment;

    private String userLogin;

    public SongRating() {
    }

    public SongRating(Rating rating, String comment, String userLogin) {
        this.rating = rating;
        this.comment = comment;
        this.userLogin = userLogin;
    }

    public SongRating(Date date, Rating rating, String comment, String userLogin) {
        this.date = date;
        this.rating = rating;
        this.comment = comment;
        this.userLogin = userLogin;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Rating getGrade() {
        return rating;
    }

    public void setGrade(Rating rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

}
