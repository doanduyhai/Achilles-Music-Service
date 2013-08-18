package info.archinnov.achilles.demo.music.entity.user;

import info.archinnov.achilles.annotations.Order;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ArtistIndex {

    @EmbeddedId
    private ArtistKey id;

    @Column
    private Artist artist;

    public ArtistIndex() {
    }

    public ArtistIndex(ArtistIndexType type, String value, Artist artist) {
        this.id = new ArtistKey(type, value, artist.getId());
        this.artist = artist;
    }

    public ArtistKey getId() {
        return id;
    }

    public void setId(ArtistKey id) {
        this.id = id;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public static class ArtistKey
    {
        @Order(1)
        private ArtistIndexType type;

        @Order(2)
        private String value;

        @Order(3)
        private Long artistId;

        public ArtistKey() {
        }

        public ArtistKey(ArtistIndexType type, String value, Long artistId) {
            this.type = type;
            this.value = value;
            this.artistId = artistId;
        }

        public ArtistIndexType getType() {
            return type;
        }

        public void setType(ArtistIndexType type) {
            this.type = type;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Long getArtistId() {
            return artistId;
        }

        public void setArtistId(Long artistId) {
            this.artistId = artistId;
        }
    }

    public static enum ArtistIndexType
    {
        NAME, BAND, STYLE

    }
}
