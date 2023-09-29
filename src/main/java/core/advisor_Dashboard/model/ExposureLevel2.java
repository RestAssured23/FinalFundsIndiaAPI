package core.advisor_Dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter@Setter
public class ExposureLevel2 {
    @Getter@Setter
    public static class Data{
        public String sortBy;
        public String order;
        public ArrayList<Level2> level2;
        public int page;
        public int totalPages;
        public int size;
        public int count;
        public boolean first;
        public boolean last;
        public ArrayList<Row> rows;
        public String name;
        public Object value;
    }
    @Getter@Setter
    public static class Key{
        public String desc;
        public int code;
    }
    @Getter@Setter
    public static class Level2{
        public String name;
        public String value;
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
        public Key key;
    }


}
