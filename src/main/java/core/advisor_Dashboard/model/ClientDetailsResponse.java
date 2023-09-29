package core.advisor_Dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class ClientDetailsResponse {
    @Getter@Setter
    public static class Data{
        public ArrayList<Summary> summary;
        public String username;
    }
    @Getter@Setter
    public static class Lfy{
        public String name;
        public Object value;
    }
    @Getter@Setter
    public static class Month{
        public String name;
        public Object value;
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
    public static class Summary{
        public Object ytd;
        public Lfy lfy;
        public String name;
        public ArrayList<Month> months;
    }
}
