package core.advisor_Dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class ShortTermBO {
    @Getter@Setter
    public static class Root{
        public String requirementId;
        public ArrayList<OneToThreeYear> oneToThreeYears;
        public ArrayList<ThreeToFiveYear> threeToFiveYears;
        public Rationals rationals;
        public String comments;
    }
    @Getter@Setter
    public static class OneToThreeYear{
        public String schemeCode;
        public String schemeName;
        public int ratings;
        public String category;
        public String subCategory;
        public int sip;
        public int lumpsum;
        public String rationale;
    }
    @Getter@Setter
    public static class Rationals{
        public ArrayList<OneToThreeYear> oneToThreeYears;
        public ArrayList<ThreeToFiveYear> threeToFiveYears;
    }

    @Getter@Setter
    public static class ThreeToFiveYear{
        public String schemeCode;
        public String schemeName;
        public int ratings;
        public String category;
        public String subCategory;
        public int sip;
        public int lumpsum;
        public String rationale;
    }
}
