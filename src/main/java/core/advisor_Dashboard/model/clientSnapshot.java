package core.advisor_Dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class clientSnapshot {
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
        public String outflow;
        public String netflow;
        public String baseAum;
        public String netflowGrowthPercentage;
        public String mtm;
        public String currentAum;
        public String name;
        public String mtmPercentage;
        public String aumGrowthPercentage;
        public String inflow;
        public String userId;
        public String advisorName;
        public boolean moveIn;
        public boolean moveOut;
        public boolean referral;
    }
    @Getter@Setter
    public static class Summary{
        public String outflow;
        public String netflow;
        public String baseAum;
        public String netflowGrowthPercentage;
        public String mtm;
        public String currentAum;
        public String name;
        public String mtmPercentage;
        public String aumGrowthPercentage;
        public String inflow;
      //  public String userId;
    }
}
