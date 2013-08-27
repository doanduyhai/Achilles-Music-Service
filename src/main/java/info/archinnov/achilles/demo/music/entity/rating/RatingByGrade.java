package info.archinnov.achilles.demo.music.entity.rating;

import info.archinnov.achilles.annotations.Order;
import info.archinnov.achilles.demo.music.constants.Rating;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class RatingByGrade {

    @EmbeddedId
    private RatingByGradeKey id;

    @Column
    private String comment;

    @Column
    private String userLogin;

    public RatingByGrade() {
    }

    public RatingByGrade(UUID songId, Rating rating, UUID date, String comment, String userLogin) {
        this.id = new RatingByGradeKey(songId, rating, date);
        this.comment = comment;
        this.userLogin = userLogin;
    }

    public RatingByGradeKey getId() {
        return id;
    }

    public void setId(RatingByGradeKey id) {
        this.id = id;
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

    public static class RatingByGradeKey
    {
        @Order(1)
        private UUID songId;

        @Order(2)
        private Rating rating;

        @Order(3)
        private UUID date;

        public RatingByGradeKey() {
        }

        public RatingByGradeKey(UUID songId, Rating rating, UUID date) {
            this.songId = songId;
            this.rating = rating;
            this.date = date;
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
