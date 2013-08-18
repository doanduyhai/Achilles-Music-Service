package info.archinnov.achilles.demo.music.entity.song;

import info.archinnov.achilles.type.Counter;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
public class Album {

    @Id
    private UUID id;

    @Column
    private String title;

    @Column
    private Integer releaseYear;

    @Column
    private Integer trackCount;

    @Column
    private Integer totalDuration;

    @JoinColumn
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Song> songs;

    @Column
    private Counter buyCount;

    public Album() {
    }

    public Album(UUID id, String title, Integer releaseYear, List<Song> songs) {
        this.id = id;
        this.title = title;
        this.releaseYear = releaseYear;
        this.trackCount = songs.size();
        this.totalDuration = inferTotalDuration(songs);
        this.songs = songs;
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

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getTrackCount() {
        return trackCount;
    }

    public void setTrackCount(Integer trackCount) {
        this.trackCount = trackCount;
    }

    public Integer getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(Integer totalDuration) {
        this.totalDuration = totalDuration;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public Counter getBuyCount() {
        return buyCount;
    }

    public void setBuyCount(Counter buyCount) {
        this.buyCount = buyCount;
    }

    private Integer inferTotalDuration(List<Song> songs)
    {
        int totalDuration = 0;
        for (Song song : songs) {
            totalDuration += song.getDuration();
        }

        return totalDuration;
    }
}
