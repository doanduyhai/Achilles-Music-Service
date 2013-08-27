package info.archinnov.achilles.demo.music.service;

import static info.archinnov.achilles.demo.music.entity.song.SongIndex.SongIndexType.*;
import static info.archinnov.achilles.type.OrderingMode.*;
import info.archinnov.achilles.demo.music.constants.Rating;
import info.archinnov.achilles.demo.music.entity.rating.RatingByDate;
import info.archinnov.achilles.demo.music.entity.rating.RatingByGrade;
import info.archinnov.achilles.demo.music.entity.song.Album;
import info.archinnov.achilles.demo.music.entity.song.Song;
import info.archinnov.achilles.demo.music.entity.song.SongIndex;
import info.archinnov.achilles.demo.music.model.SongRating;
import info.archinnov.achilles.entity.manager.CQLEntityManager;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import org.apache.cassandra.utils.UUIDGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

@Service
public class SongService {

    @Autowired
    private CQLEntityManager em;

    private static final Function<SongIndex, Song> extractSong = new Function<SongIndex, Song>() {

        @Override
        public Song apply(SongIndex index)
        {
            return index.getSong();
        }
    };

    private static final Function<RatingByDate, SongRating> convertToRatingByDate = new Function<RatingByDate, SongRating>() {

        @Override
        public SongRating apply(RatingByDate rating)
        {
            Date date = new Date(UUIDGen.unixTimestamp(rating.getId().getDate()));
            return new SongRating(date, rating.getId().getRating(), rating.getComment(),
                    rating.getUserLogin());
        }
    };

    public UUID createAlbum(Album album) {
        UUID albumId = UUIDGen.getTimeUUID();

        album.setId(albumId);
        em.persist(album);

        return albumId;
    }

    public UUID createSong(Song song)
    {
        UUID songId = UUIDGen.getTimeUUID();

        song.setId(songId);
        em.persist(song);

        em.persist(new SongIndex(TITLE, song.getTitle(), song));
        em.persist(new SongIndex(ARTIST, song.getAuthor().getFirstname(), song));
        em.persist(new SongIndex(ARTIST, song.getAuthor().getLastname(), song));
        em.persist(new SongIndex(MUSIC_STYLE, song.getStyle().name(), song));

        return songId;
    }

    public List<Song> findSongsByTitle(String title)
    {
        List<SongIndex> foundSongs = em.sliceQuery(SongIndex.class)
                .partitionKey(TITLE)
                .fromClusterings(title)
                .toClusterings(title + "z")
                .get(10);

        return FluentIterable.from(foundSongs).transform(extractSong).toImmutableList();
    }

    public List<Song> findSongsByArtist(String artistName)
    {
        List<SongIndex> foundSongs = em.sliceQuery(SongIndex.class)
                .partitionKey(ARTIST)
                .fromClusterings(artistName)
                .toClusterings(artistName + "z")
                .get(10);

        return FluentIterable.from(foundSongs).transform(extractSong).toImmutableList();
    }

    public List<Song> findSongsByStyle(String style) {
        List<SongIndex> foundSongs = em.sliceQuery(SongIndex.class)
                .partitionKey(MUSIC_STYLE)
                .getFirst(10, style);

        return FluentIterable.from(foundSongs).transform(extractSong).toImmutableList();
    }

    public void rateSong(UUID songId, SongRating songRating) {
        Song song = em.find(Song.class, songId);
        if (song != null)
        {
            song.getRatingCount().incr();
            UUID date = UUIDGen.getTimeUUID();
            Rating rating = songRating.getGrade();
            String comment = songRating.getComment();
            String userLogin = songRating.getUserLogin();

            em.persist(new RatingByDate(songId, date, rating, comment, userLogin));
            em.persist(new RatingByGrade(songId, rating, date, comment, userLogin));
        }

    }

    public List<SongRating> getRatingByDate(UUID songId, boolean oldestFirst) {
        List<RatingByDate> ratingsByDate = em.sliceQuery(RatingByDate.class)
                .partitionKey(songId)
                .ordering(oldestFirst ? DESCENDING : ASCENDING)
                .get(10);

        return FluentIterable.from(ratingsByDate).transform(convertToRatingByDate).toImmutableList();
    }

    public List<SongRating> getRatingByRate(UUID songId, boolean descending) {
        List<RatingByGrade> ratingsByDate = em.sliceQuery(RatingByGrade.class)
                .partitionKey(songId)
                .ordering(descending ? DESCENDING : ASCENDING)
                .get(10);
        return null;
    }
}
