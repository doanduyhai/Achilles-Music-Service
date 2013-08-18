package info.archinnov.achilles.demo.music.constants;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;

public class RatingTest {

    @Test
    public void should_get_correct_ratings() throws Exception
    {
        assertThat(Rating.getRating(0L, 0L)).isSameAs(Rating.UNDEFINED);
        assertThat(Rating.getRating(0L, 1L)).isSameAs(Rating.ZERO);
        assertThat(Rating.getRating(1L, 9L)).isSameAs(Rating.ONE);
        assertThat(Rating.getRating(2L, 8L)).isSameAs(Rating.ONE_HALF);
        assertThat(Rating.getRating(3L, 7L)).isSameAs(Rating.TWO);
        assertThat(Rating.getRating(4L, 6L)).isSameAs(Rating.TWO_HALF);
        assertThat(Rating.getRating(5L, 5L)).isSameAs(Rating.THREE);
        assertThat(Rating.getRating(6L, 4L)).isSameAs(Rating.THREE_HALF);
        assertThat(Rating.getRating(7L, 3L)).isSameAs(Rating.FOUR);
        assertThat(Rating.getRating(8L, 2L)).isSameAs(Rating.FOUR_HALF);
        assertThat(Rating.getRating(9L, 1L)).isSameAs(Rating.FIVE);
        assertThat(Rating.getRating(10L, 0L)).isSameAs(Rating.FIVE);
        assertThat(Rating.getRating(1L, 0L)).isSameAs(Rating.FIVE);

    }
}
