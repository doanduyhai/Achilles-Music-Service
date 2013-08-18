package info.archinnov.achilles.demo.music.service;

import static info.archinnov.achilles.demo.music.constants.MusicStyle.*;
import static org.fest.assertions.api.Assertions.assertThat;
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
import com.google.common.collect.Sets;

@RunWith(MockitoJUnitRunner.class)
public class ReferentialDataServiceTest {

    @Rule
    public AchillesCQLResource resource = new AchillesCQLResource("info.archinnov.achilles.demo.music.entity",
            "Artist", "ArtistIndex", "ReferentialData");

    @InjectMocks
    private ReferentialDataService service;

    private ArtistService artistService = new ArtistService();

    private CQLEntityManager em = resource.getEm();

    @Before
    public void setUp()
    {
        Whitebox.setInternalState(service, CQLEntityManager.class, em);
        Whitebox.setInternalState(artistService, CQLEntityManager.class, em);
    }

    @Test
    public void should_find_available_bands_and_music_styles() throws Exception
    {
        Artist johnLennon = new Artist(null, "John", "LENNON", "John Lennon from the Beattles",
                Sets.newHashSet(POP),
                Sets.newHashSet("The Beattles"), 1960, "");

        Artist fergie = new Artist(null, "Stacy Ann", "FERGUSON", "Fergie Starr from the Black Eyes Peas",
                Sets.newHashSet(R_AND_B, HIP_HOP),
                Sets.newHashSet("The Black Eyes Peas"), 1984, "");

        artistService.createArtist(johnLennon);
        artistService.createArtist(fergie);

        List<String> foundBands = service.findAvailableBands();
        assertThat(foundBands).containsOnly("The Beattles", "The Black Eyes Peas");

        List<String> foundStyles = service.findAvailableMusicStyles();
        assertThat(foundStyles).containsOnly("POP", "R_AND_B", "HIP_HOP");

    }

}
