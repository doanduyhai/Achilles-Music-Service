package info.archinnov.achilles.demo.music.entity.rating;

import info.archinnov.achilles.annotations.Order;
import info.archinnov.achilles.demo.music.constants.Rating;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class RatingByDate {

    @EmbeddedId
    private RatingByDateKey id;

    @Column
    private String comment;

    @Column
    private String userLogin;

    public RatingByDate() {
    }

    public RatingByDate(UUID songId, UUID date, Rating rating, String comment, String userLogin) {
        this.id = new RatingByDateKey(songId, date, rating);
        this.comment = comment;
        this.userLogin = userLogin;
    }

    public RatingByDateKey getId() {
        return id;
    }

    public void setId(RatingByDateKey id) {
        this.id = id;
    }

    public UUID getDate()
    {
        return id == null ? null : id.getDate();
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

    public static class RatingByDateKey
    {
        @Order(1)
        private UUID songId;

        @Order(2)
        private UUID date;

        @Order(3)
        private Rating rating;

        public RatingByDateKey() {
        }

        public RatingByDateKey(UUID songId, UUID date, Rating rating) {
            this.songId = songId;
            this.date = date;
            this.rating = rating;
        }

        public UUID getSongId() {
            return songId;
        }

        public void setSongId(UUID songId) {
            this.songId = songId;
        }

        public UUID getDate() {
            return date;
        }

        public void setDate(UUID date) {
            this.date = date;
        }

        public Rating getRating() {
            return rating;
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }
    }
}
