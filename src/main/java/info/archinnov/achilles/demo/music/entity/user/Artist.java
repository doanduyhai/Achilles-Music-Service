package info.archinnov.achilles.demo.music.entity.user;

import info.archinnov.achilles.demo.music.constants.MusicStyle;
import info.archinnov.achilles.type.Counter;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import org.apache.commons.lang.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

@Entity
@JsonSerialize(include = Inclusion.NON_NULL)
public class Artist extends User {

    @Column
    private Set<MusicStyle> styles;

    @Column
    private Set<String> bands;

    @Column
    private Integer activeSince;

    @Column
    private String website;

    @Column
    private Counter likeCount;

    public Artist() {
    }

    public Artist(@JsonProperty("id") Long id,
            @JsonProperty("firstname") String firstname,
            @JsonProperty("lastname") String lastname,
            @JsonProperty("description") String description,
            @JsonProperty("styles") Set<MusicStyle> styles,
            @JsonProperty("bands") Set<String> bands,
            @JsonProperty("activeSince") Integer activeSince,
            @JsonProperty("website") String website) {
        super(id, RandomStringUtils.randomAlphabetic(10), RandomStringUtils.randomAlphabetic(10), firstname,
                lastname, description);
        this.styles = styles;
        this.bands = bands;
        this.activeSince = activeSince;
        this.website = website;

    }

    public Set<MusicStyle> getStyles() {
        return styles;
    }

    public void setStyles(Set<MusicStyle> styles) {
        this.styles = styles;
    }

    public Set<String> getBands() {
        return bands;
    }

    public void setBands(Set<String> bands) {
        this.bands = bands;
    }

    public Integer getActiveSince() {
        return activeSince;
    }

    public void setActiveSince(Integer activeSince) {
        this.activeSince = activeSince;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Counter getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Counter likeCount) {
        this.likeCount = likeCount;
    }
}
