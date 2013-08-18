package info.archinnov.achilles.demo.music.entity.song;

import info.archinnov.achilles.annotations.Order;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class SongIndex {

    @EmbeddedId
    private SongKey id;

    @Column
    private Song song;

    public SongIndex() {
    }

    public SongIndex(SongIndexType type, String value, Song song) {
        this.id = new SongKey(type, value, song.getId());
        this.song = song;
    }

    public SongKey getId() {
        return id;
    }

    public void setId(SongKey id) {
        this.id = id;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public static class SongKey
    {
        @Order(1)
        private SongIndexType type;

        @Order(2)
        private String value;

        @Order(3)
        private UUID songId;

        public SongKey() {
        }

        public SongKey(SongIndexType type, String value, UUID songId) {
            this.type = type;
            this.value = value;
            this.songId = songId;
        }

        public SongIndexType getType() {
            return type;
        }

        public void setType(SongIndexType type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public UUID getSongId() {
            return songId;
        }

        public void setSongId(UUID songId) {
            this.songId = songId;
        }
    }

    public static enum SongIndexType
    {
        TITLE, ARTIST, MUSIC_STYLE;
    }
}
