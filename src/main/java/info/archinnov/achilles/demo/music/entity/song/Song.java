package info.archinnov.achilles.demo.music.entity.song;

import info.archinnov.achilles.demo.music.constants.MusicStyle;
import info.archinnov.achilles.demo.music.entity.user.Artist;
import info.archinnov.achilles.type.Counter;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
public class Song {

    @Id
    private UUID id;

    @Column
    private String title;

    @Column
    private Artist author;

    @Column
    private Integer releaseYear;

    @Column
    private MusicStyle style;

    @Column
    private Integer duration;

    @Column
    private Set<String> albums;

    @Column
    private Counter ratingCount;

    public Song() {
    }

    public Song(UUID id,
            String title,
            Artist author,
            Integer releaseYear,
            MusicStyle style,
            Integer duration,
            Set<String> albums) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.releaseYear = releaseYear;
        this.style = style;
        this.duration = duration;
        this.albums = albums;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Artist getAuthor() {
        return author;
    }

    public void setAuthor(Artist author) {
        this.author = author;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public MusicStyle getStyle() {
        return style;
    }

    public void setStyle(MusicStyle style) {
        this.style = style;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Set<String> getAlbums() {
        return albums;
    }

    public void setAlbums(Set<String> albums) {
        this.albums = albums;
    }

    public Counter getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Counter ratingCount) {
        this.ratingCount = ratingCount;
    }
}
