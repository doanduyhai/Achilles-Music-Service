package info.archinnov.achilles.demo.music.entity.user;

import static org.fest.assertions.api.Assertions.assertThat;
import info.archinnov.achilles.demo.music.entity.user.User;
import org.apache.commons.lang.math.RandomUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class UserTest {

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void should_serialize_and_deserialize() throws Exception
    {
        // Given
        Long id = RandomUtils.nextLong();
        String firstname = "fn", lastname = "ln", login = "login", password = "password";
        User user = new User();
        user.setId(id);
        user.setLogin(login);
        user.setPassword(password);
        user.setFirstname(firstname);
        user.setLastname(lastname);

        //When
        String serialized = mapper.writeValueAsString(user);

        // Then (test Inclusion.NON_NULL)
        assertThat(serialized.contains("description")).isFalse();

        //When
        User deserialized = mapper.readValue(serialized, User.class);

        // Then (test custom constructor with password SHA-512 hashing)
        assertThat(deserialized.getPassword()).isNotEqualTo(password);
        assertThat(deserialized.getPassword().length()).isEqualTo(128);
    }
}
