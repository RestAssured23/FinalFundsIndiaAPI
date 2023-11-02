package core.api.testing;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class B2Bbo {
    @Getter@Setter
    public static class Allocation{
        public String category;
        public double allocationPercentage;
        public double allocationAmount;
    }
    @Getter@Setter
    public static class Data{
        public Summary summary;
        public ArrayList<Detail> details;
    }
    @Getter@Setter
    public static class Detail{
        public String dataFor;
        public double totalAmount;
        public ArrayList<Allocation> allocation;
        public int setupCount_CurrentMonth;
        public int expireCount_last30days;
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
        public int totalClients;
        public int activatedClients;
        public int nonActivatedClients;
    }
}
