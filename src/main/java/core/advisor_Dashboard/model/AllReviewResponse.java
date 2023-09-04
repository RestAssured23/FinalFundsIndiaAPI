package core.advisor_Dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

@Getter@Setter
public class AllReviewResponse {
    @Getter@Setter
    public static class Root {
        public int code;
        public String desc;
        public ArrayList<Object> errors;
        public boolean success;
        public String type;
        public String name;
        public Data data;
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
        public ArrayList<Review> reviews;
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
    public static class Review{
        public Date reviewDate;
        public String advisorName;
        public String userName;
        public int advisorUserId;
        public ArrayList<Investor> investors;
        public int userId;
        public int reviewId;
        public String email;
        public String status;
        public String qaStatus;
    }
}
