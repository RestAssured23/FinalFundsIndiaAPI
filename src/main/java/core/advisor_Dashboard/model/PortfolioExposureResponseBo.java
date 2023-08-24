package core.advisor_Dashboard.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class PortfolioExposureResponseBo {
    @Getter@Setter
    public static class Data{
        public String sortBy;
        public String order;
        public ArrayList<Level1> level1;
        public int page;
        public int totalPages;
        public int size;
        public int count;
        public boolean first;
        public boolean last;
        public ArrayList<Row> rows;
        public String name;
        public double value;
    }
    @Getter@Setter
    public static class Key{
        public String desc;
        public String code;
    }
    @Getter@Setter
    public static class Level1{
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
