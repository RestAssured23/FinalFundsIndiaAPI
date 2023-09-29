package core.advisor_Dashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter@Setter
public class ExposureLevel0 {
    @Getter@Setter
    public static class Data{
        public String sortBy;
        public String order;
        public String type;
        public int page;
        public int totalPages;
        public int size;
        public int count;
        public boolean first;
        public boolean last;
        public ArrayList<Row> rows;
        public String name;
        public int value;
    }
    @Getter@Setter
    public static class Key{
        public String code;
        public String desc;
    }
    @Getter@Setter
    public static class MTM{
        public String name;
        public int value;
    }
    @Getter@Setter
    public static class Root{
        public int code;
        public String desc;
        public ArrayList<Object> errors;
        public boolean success;
        public String type;
        public String name;
        public Data data;
    }
    @Getter@Setter
    public static class Row{
        public ArrayList<Data> data;
        @JsonProperty("MTM")
        public MTM mTM;
        public Key key;
    }
}
