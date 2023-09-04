package core.advisor_Dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter@Setter
public class PortfolioListResponse {
    @Getter@Setter
    public static class Data{
        public String name;
        public int id;
        public String type;
        public boolean failOnExitingPortfolio;
        public String investorName;
        public String investorId;
    }
    @Getter@Setter
    public static class Root{
        public int code;
        public String desc;
        public ArrayList<Object> errors;
        public boolean success;
        public String type;
        public String name;
        public ArrayList<Data> data;
    }

}
