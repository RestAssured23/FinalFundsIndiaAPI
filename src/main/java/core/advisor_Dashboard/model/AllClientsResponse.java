package core.advisor_Dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class AllClientsResponse {
    @Getter@Setter
    public static class Client{
        public String previousReviewOn;
        public String advisorName;
        public String userName;
        public int advisorUserId;
        public int userId;
        public ArrayList<Investor> investors;
        public String nextReviewOn;
        public String review4;
        public String review3;
        public String review2;
        public String review1;
        public double aum;
        public int sip;
        public String email;
        public String status;
    }
    @Getter@Setter
    public static class Data{
        public int count;
        public int size;
        public String next;
        public int page;
        public boolean first;
        public boolean last;
        public int totalPages;
        public ArrayList<Client> clients;
    }
    @Getter@Setter
    public static class Investor{
        public int id;
        public String name;
        public String pan;
        public String email;
        public Object mobile;
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

}
