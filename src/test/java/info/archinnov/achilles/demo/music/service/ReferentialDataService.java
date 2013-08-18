package info.archinnov.achilles.demo.music.service;

import info.archinnov.achilles.demo.music.entity.referential.ReferentialData;
import info.archinnov.achilles.demo.music.entity.referential.ReferentialData.DataType;
import info.archinnov.achilles.entity.manager.CQLEntityManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;

@Service
public class ReferentialDataService {

    @Autowired
    private CQLEntityManager em;

    private static final Function<ReferentialData, String> extractData = new Function<ReferentialData, String>() {

        @Override
        public String apply(ReferentialData index)
        {
            return index.getData();
        }
    };

    public List<String> findAvailableBands() {

        return findData(DataType.BAND);
    }

    public List<String> findAvailableMusicStyles() {

        return findData(DataType.MUSIC_STYLES);
    }

    private List<String> findData(DataType type) {

        List<ReferentialData> foundBands = em.sliceQuery(ReferentialData.class)
                .partitionKey(type)
                .getFirst(10);

        return FluentIterable.from(foundBands).transform(extractData).toImmutableList();
    }
}
