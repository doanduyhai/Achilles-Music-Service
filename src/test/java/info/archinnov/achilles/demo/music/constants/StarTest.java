package info.archinnov.achilles.demo.music.constants;

import static org.fest.assertions.api.Assertions.assertThat;
import org.junit.Test;

public class StarTest {

    @Test
    public void should_get_correct_ratings() throws Exception
    {
        assertThat(Star.getRating(0L, 0L)).isSameAs(Star.UNDEFINED);
        assertThat(Star.getRating(0L, 1L)).isSameAs(Star.ZERO);
        assertThat(Star.getRating(1L, 9L)).isSameAs(Star.ONE);
        assertThat(Star.getRating(2L, 8L)).isSameAs(Star.ONE_HALF);
        assertThat(Star.getRating(3L, 7L)).isSameAs(Star.TWO);
        assertThat(Star.getRating(4L, 6L)).isSameAs(Star.TWO_HALF);
        assertThat(Star.getRating(5L, 5L)).isSameAs(Star.THREE);
        assertThat(Star.getRating(6L, 4L)).isSameAs(Star.THREE_HALF);
        assertThat(Star.getRating(7L, 3L)).isSameAs(Star.FOUR);
        assertThat(Star.getRating(8L, 2L)).isSameAs(Star.FOUR_HALF);
        assertThat(Star.getRating(9L, 1L)).isSameAs(Star.FIVE);
        assertThat(Star.getRating(10L, 0L)).isSameAs(Star.FIVE);
        assertThat(Star.getRating(1L, 0L)).isSameAs(Star.FIVE);

    }
}
