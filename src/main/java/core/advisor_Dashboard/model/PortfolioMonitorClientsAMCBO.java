package core.advisor_Dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class PortfolioMonitorClientsAMCBO {
    @Getter@Setter
    public static class Category{
        public String name;
        public double noOfFunds;
        public int amount;
        public double percentage;
        public ArrayList<Heading> headings;
    }
    @Getter@Setter
    public static class Datum{
        public String amcName;
        public String amcCode;
        public ArrayList<Category> categories;
        public String schemeCode;
        public String schemeName;
        public String category;
        public String subCategory;
        public double noOfFunds;
        public int amount;
        public double percentage;
        public String aaa;
        public String rating;
        public String ytm;
        public String expenseRatio;
    }
    @Getter@Setter
    public static class Heading{
        public String heading;
        public double percentage;
        public int amount;
        public double noOfFunds;
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
