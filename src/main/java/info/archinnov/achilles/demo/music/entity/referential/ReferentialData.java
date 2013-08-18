package info.archinnov.achilles.demo.music.entity.referential;

import info.archinnov.achilles.annotations.Order;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class ReferentialData {

    @EmbeddedId
    private ReferentialIndex id;

    public ReferentialData() {
    }

    public ReferentialData(DataType type, String data) {
        this.id = new ReferentialIndex(type, data);
    }

    public ReferentialIndex getId() {
        return id;
    }

    public void setId(ReferentialIndex id) {
        this.id = id;
    }

    public String getData()
    {
        return id == null ? null : id.getData();
    }

    public static class ReferentialIndex
    {
        @Order(1)
        private DataType type;

        @Order(2)
        private String data;

        public ReferentialIndex() {
        }

        public ReferentialIndex(DataType type, String data) {
            this.type = type;
            this.data = data;
        }

        public DataType getType() {
            return type;
        }

        public void setType(DataType type) {
            this.type = type;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

    }

    public static enum DataType
    {
        BAND, MUSIC_STYLES;
    }
}
