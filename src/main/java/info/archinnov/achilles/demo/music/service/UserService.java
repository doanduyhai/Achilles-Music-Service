package info.archinnov.achilles.demo.music.service;

import info.archinnov.achilles.demo.music.entity.user.User;
import info.archinnov.achilles.entity.manager.CQLEntityManager;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private CQLEntityManager em;

    public User createUser(User user)
    {
        Long userId = RandomUtils.nextLong();
        user.setId(userId);

        em.persist(user);

        user.setPassword(null);
        return user;
    }

}
