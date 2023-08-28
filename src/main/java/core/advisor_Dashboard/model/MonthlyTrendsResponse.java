package core.advisor_Dashboard.model;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter@Setter
public class MonthlyTrendsResponse {
    @Getter@Setter
    public static class Data{
        public int page;
        public int totalPages;
        public int size;
        public int count;
        public boolean first;
        public boolean last;
        public ArrayList<Row> rows;
        public ArrayList<Summary> summary;
    }
    @Getter@Setter
    public static class Lfy{
        public String name;
        public int value;
    }
    @Getter@Setter
    public static class Month{
        public String name;
        public int value;
        public int id;
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
        public ArrayList<Month> months;
        public String segment;
        public String name;
        public int ytd;
        public String advisorName;
        public String zohoCrmId;
        public String userId;
        public String uuid;
        public String email;
        public Lfy lfy;
    }
    @Getter@Setter
    public static class Summary{
        public String name;
        public Lfy lfy;
        public double ytd;
        public ArrayList<Month> months;
    }


}
