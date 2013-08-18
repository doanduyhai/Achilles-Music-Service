package info.archinnov.achilles.demo.music.service;

import static info.archinnov.achilles.demo.music.constants.MusicStyle.*;
import static org.fest.assertions.api.Assertions.*;
import info.archinnov.achilles.demo.music.entity.user.Artist;
import info.archinnov.achilles.entity.manager.CQLEntityManager;
import info.archinnov.achilles.junit.AchillesCQLResource;
import java.util.List;
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
public class ArtistServiceTest {

    @Rule
    public AchillesCQLResource resource = new AchillesCQLResource("info.archinnov.achilles.demo.music.entity",
            "Artist", "ArtistIndex");

    @InjectMocks
    private ArtistService service;

    private CQLEntityManager em = resource.getEm();

    private Session session = resource.getNativeSession();

    @Before
    public void setUp()
    {
        Whitebox.setInternalState(service, CQLEntityManager.class, em);
    }

    @Test
    public void should_create_artist() throws Exception
    {
        Artist artist = new Artist(null, "John", "LENNON", "John Lennon from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1960, "");

        Artist actual = service.createArtist(artist);

        Long id = actual.getId();

        assertThat(id).isNotNull().isInstanceOf(Long.class);
        assertThat(actual.getLogin()).isNull();
        assertThat(actual.getPassword()).isNull();

        Row row = session.execute("SELECT * FROM Artist where id=" + id).one();

        assertThat(row).isNotNull();
        assertThat(row.getString("login").length()).isEqualTo(10);
        assertThat(row.getString("password").length()).isEqualTo(128);
        assertThat(row.getString("firstname")).isEqualTo("John");
        assertThat(row.getString("lastname")).isEqualTo("LENNON");
        assertThat(row.getString("description")).isEqualTo("John Lennon from the Beattles");
        assertThat(row.getSet("styles", String.class)).containsExactly("POP");
        assertThat(row.getSet("bands", String.class)).containsExactly("The Beattles");
        assertThat(row.getInt("activeSince")).isEqualTo(1960);
        assertThat(row.getString("website")).isEmpty();
    }

    @Test
    public void should_find_artist_by_firstname() throws Exception
    {
        Artist johnLennon = new Artist(null, "John", "LENNON", "John Lennon from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1960, "");

        Artist ladyGaga = new Artist(null, "Lady Gaga", "", "Lady Gaga",
                Sets.newHashSet(R_AND_B),
                null, 2000, "");

        service.createArtist(johnLennon);
        service.createArtist(ladyGaga);

        List<Artist> found = service.findArtistsByName("John");

        assertThat(found).hasSize(1);

        Artist actual = found.get(0);
        assertThat(actual.getFirstname()).isEqualTo("John");
        assertThat(actual.getLastname()).isEqualTo("LENNON");
    }

    @Test
    public void should_find_artist_by_lastname() throws Exception
    {
        Artist johnLennon = new Artist(null, "John", "LENNON", "John Lennon from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1960, "");

        Artist ladyGaga = new Artist(null, "Lady Gaga", "", "Lady Gaga",
                Sets.newHashSet(R_AND_B),
                null, 2000, "");

        service.createArtist(johnLennon);
        service.createArtist(ladyGaga);

        List<Artist> found = service.findArtistsByName("LENN");

        assertThat(found).hasSize(1);

        Artist actual = found.get(0);
        assertThat(actual.getFirstname()).isEqualTo("John");
        assertThat(actual.getLastname()).isEqualTo("LENNON");
    }

    @Test
    public void should_find_artist_by_style() throws Exception
    {
        Artist johnLennon = new Artist(null, "John", "LENNON", "John Lennon from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1960, "");

        Artist ladyGaga = new Artist(null, "Lady Gaga", "", "Lady Gaga",
                Sets.newHashSet(R_AND_B, POP),
                null, 2000, "");

        service.createArtist(johnLennon);
        service.createArtist(ladyGaga);

        List<Artist> foundArtists = service.findArtistByStyle(POP.name());

        assertThat(foundArtists).hasSize(2);
        assertThat(extractProperty("firstname").from(foundArtists)).containsOnly("John", "Lady Gaga");
    }

    @Test
    public void should_find_artist_by_band() throws Exception
    {
        Artist johnLennon = new Artist(null, "John", "LENNON", "John Lennon from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1960, "");

        Artist ringoStarr = new Artist(null, "Ringo", "STARR", "Ringo Starr from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1960, "");

        service.createArtist(johnLennon);
        service.createArtist(ringoStarr);

        List<Artist> foundArtists = service.findArtistByBand("The Beattles");

        assertThat(foundArtists).hasSize(2);
        assertThat(extractProperty("firstname").from(foundArtists)).containsOnly("John", "Ringo");
    }
}
