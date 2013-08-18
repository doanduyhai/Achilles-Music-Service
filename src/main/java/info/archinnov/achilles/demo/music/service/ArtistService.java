package info.archinnov.achilles.demo.music.service;

import static info.archinnov.achilles.demo.music.entity.user.ArtistIndex.ArtistIndexType.*;
import info.archinnov.achilles.demo.music.constants.MusicStyle;
import info.archinnov.achilles.demo.music.entity.referential.ReferentialData;
import info.archinnov.achilles.demo.music.entity.referential.ReferentialData.DataType;
import info.archinnov.achilles.demo.music.entity.user.Artist;
import info.archinnov.achilles.demo.music.entity.user.ArtistIndex;
import info.archinnov.achilles.entity.manager.CQLEntityManager;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

@Service
public class ArtistService {

    @Autowired
    private CQLEntityManager em;

    private static final Function<ArtistIndex, Artist> extractArtist = new Function<ArtistIndex, Artist>() {

        @Override
        public Artist apply(ArtistIndex index)
        {
            return index.getArtist();
        }
    };

    public Artist createArtist(Artist artist)
    {
        Long userId = RandomUtils.nextLong();
        artist.setId(userId);

        em.persist(artist);

        indexArtistName(artist);
        indexMusicStyles(artist);
        indexBands(artist);

        artist.setLogin(null);
        artist.setPassword(null);

        return artist;
    }

    public List<Artist> findArtistsByName(String name)
    {
        List<ArtistIndex> foundArtists = em.sliceQuery(ArtistIndex.class)
                .partitionKey(NAME)
                .fromClusterings(name)
                .toClusterings(name + "z")
                .get(10);

        return FluentIterable.from(foundArtists).transform(extractArtist).toImmutableList();
    }

    public List<Artist> findArtistByStyle(String style)
    {
        List<ArtistIndex> foundArtists = em.sliceQuery(ArtistIndex.class)
                .partitionKey(STYLE)
                .getFirst(10, style);

        return FluentIterable.from(foundArtists).transform(extractArtist).toImmutableList();
    }

    public List<Artist> findArtistByBand(String band)
    {
        List<ArtistIndex> foundArtists = em.sliceQuery(ArtistIndex.class)
                .partitionKey(BAND)
                .getFirst(10, band);

        return FluentIterable.from(foundArtists).transform(extractArtist).toImmutableList();
    }

    private void indexArtistName(Artist artist) {
        em.persist(new ArtistIndex(NAME, artist.getFirstname(), artist));
        em.persist(new ArtistIndex(NAME, artist.getLastname(), artist));
    }

    private void indexBands(Artist artist) {
        Set<String> bands = artist.getBands();
        if (bands != null)
        {
            for (String band : bands)
            {
                em.persist(new ArtistIndex(BAND, band, artist));
                em.persist(new ReferentialData(DataType.BAND, band));
            }
        }
    }

    private void indexMusicStyles(Artist artist) {
        Set<MusicStyle> styles = artist.getStyles();
        if (styles != null)
        {
            for (MusicStyle style : styles)
            {
                em.persist(new ArtistIndex(STYLE, style.name(), artist));
                em.persist(new ReferentialData(DataType.MUSIC_STYLES, style.name()));
            }
        }
    }
}
