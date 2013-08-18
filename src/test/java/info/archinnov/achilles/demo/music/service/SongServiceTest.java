package info.archinnov.achilles.demo.music.service;

import static info.archinnov.achilles.demo.music.constants.MusicStyle.*;
import static org.fest.assertions.api.Assertions.assertThat;
import info.archinnov.achilles.demo.music.constants.MusicStyle;
import info.archinnov.achilles.demo.music.entity.song.Album;
import info.archinnov.achilles.demo.music.entity.song.Song;
import info.archinnov.achilles.demo.music.entity.user.Artist;
import info.archinnov.achilles.entity.manager.CQLEntityManager;
import info.archinnov.achilles.junit.AchillesCQLResource;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.apache.cassandra.utils.UUIDGen;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class SongServiceTest {

    @Rule
    public AchillesCQLResource resource = new AchillesCQLResource("info.archinnov.achilles.demo.music.entity",
            "Album", "Song", "SongIndex");

    @InjectMocks
    private SongService service;

    private CQLEntityManager em = resource.getEm();

    private Session session = resource.getNativeSession();

    @Before
    public void setUp()
    {
        Whitebox.setInternalState(service, CQLEntityManager.class, em);
    }

    @Test
    public void should_create_album_and_songs() throws Exception
    {

        UUID songId1 = UUIDGen.getTimeUUID();
        UUID songId2 = UUIDGen.getTimeUUID();
        Artist johnLennon = new Artist(null, "John", "LENNON", "John Lennon from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1960, "");
        Artist paulMcCartney = new Artist(null, "Paul", "MCCARTNEY", "Paul McCartney from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1957, "");

        Song yesterday = new Song(songId1, "Yesterday", johnLennon, 1965, MusicStyle.POP, 125,
                Sets.newHashSet("Help!"));
        Song nightBefore = new Song(songId2, "The Night Before", paulMcCartney, 1965, MusicStyle.POP, 155,
                Sets.newHashSet("Help!"));

        Album album = new Album(null, "Help!", 1965, Arrays.asList(yesterday, nightBefore));

        UUID albumId = service.createAlbum(album);

        Row foundYesterday = session.execute("SELECT * FROM Song WHERE id=" + songId1).one();

        assertThat(foundYesterday).isNotNull();
        assertThat(foundYesterday.getString("title")).isEqualTo(yesterday.getTitle());
        assertThat(foundYesterday.getString("author")).contains("John").contains("LENNON");

        Row foundNightBefore = session.execute("SELECT * FROM Song WHERE id=" + songId2).one();

        assertThat(foundNightBefore).isNotNull();
        assertThat(foundNightBefore.getString("title")).isEqualTo(nightBefore.getTitle());
        assertThat(foundNightBefore.getString("author")).contains("Paul").contains("MCCARTNEY");

        Row foundAlbum = session.execute("SELECT * FROM Album where id=" + albumId).one();

        assertThat(foundAlbum).isNotNull();
        assertThat(foundAlbum.getString("title")).isEqualTo(album.getTitle());
        assertThat(foundAlbum.getInt("trackCount")).isEqualTo(2);
        assertThat(foundAlbum.getInt("totalDuration")).isEqualTo(125 + 155);
        List<UUID> foundSongs = foundAlbum.getList("songs", UUID.class);
        assertThat(foundSongs).hasSize(2);
        assertThat(foundSongs.get(0)).isEqualTo(songId1);
        assertThat(foundSongs.get(1)).isEqualTo(songId2);
    }

    @Test
    public void should_create_song() throws Exception
    {
        Artist johnLennon = new Artist(null, "John", "LENNON", "John Lennon from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1960, "");
        Song yesterday = new Song(null, "Yesterday", johnLennon, 1965, MusicStyle.POP, 125,
                Sets.newHashSet("Help!"));

        UUID songId = service.createSong(yesterday);

        Row foundYesterday = session.execute("SELECT * FROM Song WHERE id=" + songId).one();

        assertThat(foundYesterday).isNotNull();
        assertThat(foundYesterday.getString("title")).isEqualTo(yesterday.getTitle());
        assertThat(foundYesterday.getInt("releaseYear")).isEqualTo(yesterday.getReleaseYear());
        assertThat(foundYesterday.getString("style")).isEqualTo("POP");
        assertThat(foundYesterday.getInt("duration")).isEqualTo(125);
        assertThat(foundYesterday.getSet("albums", String.class)).containsOnly("Help!");
        assertThat(foundYesterday.getString("author")).contains("John").contains("LENNON");
    }

    @Test
    public void should_find_songs_by_title() throws Exception
    {
        Artist johnLennon = new Artist(null, "John", "LENNON", "John Lennon from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1960, "");
        Artist paulMcCartney = new Artist(null, "Paul", "MCCARTNEY", "Paul McCartney from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1957, "");
        Song song1 = new Song(null, "You've Got to Hide Your Love Away", johnLennon, 1965, MusicStyle.POP, 125,
                Sets.newHashSet("Help!"));
        Song song2 = new Song(null, "You're Going to Lose That Girl", paulMcCartney, 1965, MusicStyle.POP, 155,
                Sets.newHashSet("Help!"));

        service.createSong(song1);
        service.createSong(song2);

        List<Song> foundSongs = service.findSongsByTitle("You");

        assertThat(foundSongs).hasSize(2);
        assertThat(foundSongs.get(0).getTitle()).isEqualTo(song2.getTitle());
        assertThat(foundSongs.get(1).getTitle()).isEqualTo(song1.getTitle());
    }

    @Test
    public void should_find_songs_by_artist() throws Exception
    {
        Artist johnLennon = new Artist(null, "John", "LENNON", "John Lennon from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1960, "");
        Artist johnnyHallyday = new Artist(null, "Johnny", "HALLYDAY", "",
                Sets.newHashSet(POP, ROCK),
                Sets.newHashSet(""), 1960, "");

        Song song1 = new Song(null, "Yesterday", johnLennon, 1965, POP, 155,
                Sets.newHashSet("Help!"));
        Song song2 = new Song(null, "Allumer le feu", johnnyHallyday, 1998, ROCK, 155,
                Sets.newHashSet("Ce que je sais"));

        service.createSong(song1);
        service.createSong(song2);

        List<Song> foundSongs = service.findSongsByArtist("John");

        assertThat(foundSongs).hasSize(2);
        assertThat(foundSongs.get(0).getTitle()).isEqualTo(song1.getTitle());
        assertThat(foundSongs.get(1).getTitle()).isEqualTo(song2.getTitle());
    }

    @Test
    public void should_find_songs_by_music_style() throws Exception
    {
        Artist johnLennon = new Artist(null, "John", "LENNON", "John Lennon from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1960, "");
        Artist johnnyHallyday = new Artist(null, "Johnny", "HALLYDAY", "",
                Sets.newHashSet(POP, ROCK),
                Sets.newHashSet(""), 1960, "");

        Song song1 = new Song(null, "Yesterday", johnLennon, 1965, POP, 155,
                Sets.newHashSet("Help!"));
        Song song2 = new Song(null, "Allumer le feu", johnnyHallyday, 1998, ROCK, 155,
                Sets.newHashSet("Ce que je sais"));

        service.createSong(song1);
        service.createSong(song2);

        List<Song> foundSongs = service.findSongsByStyle("ROCK");

        assertThat(foundSongs).hasSize(1);
        assertThat(foundSongs.get(0).getTitle()).isEqualTo(song2.getTitle());
    }
}
