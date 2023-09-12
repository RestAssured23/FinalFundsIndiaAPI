package core.advisor_Dashboard.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class AUMSummaryResponse {
    @Getter@Setter
    public static class Current{
        public long baseAum;
        public long currentAum;
        public int currentUser;
        public int netflow;
    }
    @Getter@Setter
    public static class Data{
        public String aumAsOn;
        public double aumGrowth;
        public String baseAumAsOn;
        public ArrayList<Current> current;
        public ArrayList<MoveIn> moveIn;
        public ArrayList<MoveOut> moveOut;
        public double netflowGrowth;
        public Redemption redemption;
        public ArrayList<Referral> referral;
        public double sipGrowth;
        public Target target;
    }
    @Getter@Setter
    public static class MoveIn{
        public long baseAum;
        public long currentAum;
        public int currentUser;
        public int netflow;
    }
    @Getter@Setter
    public static class MoveOut{
        public long baseAum;
        public long currentAum;
        public int currentUser;
        public int netflow;
    }
    @Getter@Setter
    public static class Redemption{
        public int mtd;
        public int today;
        public int ytd;
    }
    @Getter@Setter
    public static class Referral{
        public int baseAum;
        public int currentAum;
        public int currentUser;
        public int netflow;
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
    public static class Target{
        public int aum;
        public int sip;
    }
}
