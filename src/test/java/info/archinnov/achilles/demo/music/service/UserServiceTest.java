package info.archinnov.achilles.demo.music.service;

import static org.fest.assertions.api.Assertions.assertThat;
import info.archinnov.achilles.demo.music.entity.user.User;
import info.archinnov.achilles.entity.manager.CQLEntityManager;
import info.archinnov.achilles.junit.AchillesCQLResource;
import info.archinnov.achilles.junit.AchillesTestResource.Steps;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Rule
    public AchillesCQLResource resource = new AchillesCQLResource("info.archinnov.achilles.demo.music.entity",
            Steps.AFTER_TEST, "User");

    @InjectMocks
    private UserService service;

    private CQLEntityManager em = resource.getEm();

    private Session session = resource.getNativeSession();

    @Before
    public void setUp()
    {
        Whitebox.setInternalState(service, CQLEntityManager.class, em);
    }

    @Test
    public void should_create_user() throws Exception
    {
        User user = new User(null, "login", "password", "fn", "ln", "description");

        User actual = service.createUser(user);

        Long id = actual.getId();

        assertThat(id).isNotNull().isInstanceOf(Long.class);
        assertThat(actual.getPassword()).isNull();

        Row row = session.execute("SELECT * FROM User where id=" + id).one();

        assertThat(row).isNotNull();
        assertThat(row.getString("login")).isEqualTo("login");
        assertThat(row.getString("password").length()).isEqualTo(128);
        assertThat(row.getString("firstname")).isEqualTo("fn");
        assertThat(row.getString("lastname")).isEqualTo("ln");
        assertThat(row.getString("description")).isEqualTo("description");
    }
}
