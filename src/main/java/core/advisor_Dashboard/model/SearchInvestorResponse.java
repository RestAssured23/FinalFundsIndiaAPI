package core.advisor_Dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter@Setter
public class SearchInvestorResponse {
    @Getter@Setter
    public static class Data{
        public ArrayList<UserIdList> userIdList;
        public ArrayList<ReviewIdList> reviewIdList;
    }
    @Getter@Setter
    public static class Investor{
        public String investorName;
        public int investorId;
        public String pan;
        public ArrayList<Portfolio> portfolios;
    }
    @Getter@Setter
    public static class Portfolio{
        public String portfolioName;
        public int portfolioId;
    }
    @Getter@Setter
    public static class ReviewIdList{
        public int reviewId;
        public ArrayList<Investor> investors;
        public boolean isPortfolioChanged;
        public String date;
        public int userId;
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
    public static class UserIdList{
        public int id;
        public int userId;
        public String name;
        public String email;
        public String mobile;
        public String pan;
        public int plId;
        public int advisorId;
        public String userEmail;
    }
}
