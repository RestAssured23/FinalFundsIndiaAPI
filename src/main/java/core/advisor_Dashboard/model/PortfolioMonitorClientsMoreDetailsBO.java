package core.advisor_Dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class PortfolioMonitorClientsMoreDetailsBO {
    @Getter@Setter
    public static class Datum{
        public double noOfFunds;
        public double amount;
        public String heading;
        public double percentage;
        public ArrayList<Heading> headings;
        public String schemeCode;
        public String amcCode;
        public String schemeName;
        public String amcName;
        public String category;
        public String aaa;
        public String rating;
        public String ytm;
        public String expenseRatio;
    }
    @Getter@Setter
    public static class Heading{
        public String heading;
        public double noOfFunds;
        public int amount;
        public double percentage;
        public ArrayList<Datum> data;
    }
    @Getter@Setter
    public static class Root{
        public int code;
        public String desc;
        public ArrayList<Object> errors;
        public boolean success;
        public String type;
        public String name;
        public ArrayList<Datum> data;
    }

}
