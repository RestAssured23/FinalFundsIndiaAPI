package core.advisor_Dashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Data
public class PortfolioMonitorClientsBO {
@Getter@Setter
    public static class Amc{
        public String id;
        public String desc;
    }
    @Getter@Setter
    public static class AssetAllocation{
        public int abc;
        public Deviation deviation;
        public Exposure exposure;
    }
    @Getter@Setter
    public static class CashAllocation{
        public double overnightLiquidExposure;
    }
    @Getter@Setter
    public static class Category{
        public double blend;
        public double quality;
        public int value;
        public double midSmall;
        public double global;
        public double others;
    }
    @Getter@Setter
    public static class Client{
        public String crmId;
        public String netflowGrowth;
        public String aum;
        public String returns;
        public int yearsWithFundsIndia;
        public Details details;
        public double riskScore;
        public String uuid;
        public String investingSince;
        public String email;
        public String netflowYtd;
    }
    @Getter@Setter
    public static class Cost{
        public double expenseRatio;
    }
    @Getter@Setter
    public static class Data{
        public int page;
        public int totalPages;
        public int size;
        public int count;
        public boolean first;
        public boolean last;
        public ArrayList<Client> clients;
    }
    @Getter@Setter
    public static class DebtMonitor{
        public double exposure;
        public double ytm;
        public double aaa;
        public int ust;
        public double overnight;
        public Durations durations;
        public int creditRisk;
        public int dynamicFunds;
        public int conservativeFunds;
        public int others;
        public Ratings ratings;
    }
    @Getter@Setter
    public static class Details{
        public AssetAllocation assetAllocation;
        public CashAllocation cashAllocation;
        public Quality quality;
        public Liquidity liquidity;
        public Cost cost;
        public EquityMonitor equityMonitor;
        public DebtMonitor debtMonitor;
        public Sip sip;
        public Diversification diversification;
    }
    @Getter@Setter
    public static class Deviation{
        public int equity;
    }
    @Getter@Setter
    public static class Diversification{
        public Highest highest;
        public SecondHighest secondHighest;
        public int debtFunds;
        public int totalFunds;
    }
    @Getter@Setter
    public static class Durations{
        @JsonProperty("short")
        public double myshort;
        @JsonProperty("long")
        public int mylong;
        public double low;
        public int medium;
    }
    @Getter@Setter
    public static class EquityMonitor{
        public double equity;
        public double largeCap;
        public double smallCap;
        public int sector;
        public Ratings ratings;
        public Category category;
    }
    @Getter@Setter
    public static class Exposure{
        public int target;
        public int others;
        public double debt;
        public double equity;
    }
    @Getter@Setter
    public static class Fund{
        public String id;
        public String desc;
    }
    @Getter@Setter
    public static class Highest{
        public Amc amc;
        public Fund fund;
    }
    @Getter@Setter
    public static class Liquidity{
        public String underLockIn;
        public double elssExposure;
    }
    @Getter@Setter
    public static class Quality{
        public double rating5;
        public double rating4;
        public double ratingb3;
        public double rating3;
        public double rating2;
        public double rating1;
        public double rating0;
        public double select;
    }
    @Getter@Setter
    public static class Ratings{
        public double rating5;
        public double rating4;
        public double ratingb3;
        public double rating3;
        public double rating2;
        public double rating1;
        public double rating0;
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
    public static class SecondHighest{
        public Fund fund;
    }
    @Getter@Setter
    public static class Sip{
        public int total;
        public int equity;
        public int debt;
        public int others;
        public Ratings ratings;
    }


/*    @Getter@Setter
    public static class Amc{
        public String id;
        public String desc;
    }
    @Getter@Setter
    public static class AssetAllocation{
        public int abc;
        public Deviation deviation;
        public Exposure exposure;
    }
    @Getter@Setter
    public static class CashAllocation{
        public int overnightLiquidExposure;
    }
    @Getter@Setter
    public static class Category{
        public int blend;
        public int quality;
        public int value;
        public int midSmall;
        public int global;
        public int others;
    }
    @Getter@Setter
    public static class Client{
        public Object crmId;
        public int netflowGrowth;
        public String name;
        public int aum;
        public int returns;
        public int yearsWithFundsIndia;
        public Details details;
        public double riskScore;
        public int uuid;
        public String investingSince;
        public String email;
        public int netflowYtd;
    }
    @Getter@Setter
    public static class Cost{
        public double expenseRatio;
    }
    @Getter@Setter
    public static class Data{
        public int page;
        public int totalPages;
        public int size;
        public int count;
        public boolean first;
        public boolean last;
        public ArrayList<Client> clients;
    }
    @Getter@Setter
    public static class DebtMonitor{
        public int exposure;
        public int ytm;
        public int aaa;
        public int ust;
        public int overnight;
        public Durations durations;
        public int creditRisk;
        public int dynamicFunds;
        public int conservativeFunds;
        public int others;
        public Ratings ratings;
    }
    @Getter@Setter
    public static class Details{
        public AssetAllocation assetAllocation;
        public CashAllocation cashAllocation;
        public Quality quality;
        public Liquidity liquidity;
        public Cost cost;
        public EquityMonitor equityMonitor;
        public DebtMonitor debtMonitor;
        public Sip sip;
        public Diversification diversification;
    }
    @Getter@Setter
    public static class Deviation{
        public int equity;
    }
    @Getter@Setter
    public static class Diversification{
        public Highest highest;
        public SecondHighest secondHighest;
        public int debtFunds;
        public int totalFunds;
    }
    @Getter@Setter
    public static class Durations{
        @JsonProperty("short")
        public int myshort;
        @JsonProperty("long")
        public int mylong;
        public int low;
        public int medium;
    }
    @Getter@Setter
    public static class EquityMonitor{
        public int equity;
        public int largeCap;
        public int smallCap;
        public int sector;
        public Ratings ratings;
        public Category category;
    }
    @Getter@Setter
    public static class Exposure{
        public int target;
        public int others;
        public int debt;
        public int equity;
    }
    @Getter@Setter
    public static class Fund{
        public int id;
        public String desc;
    }
    @Getter@Setter
    public static class Highest{
        public Amc amc;
        public Fund fund;
    }
    @Getter@Setter
    public static class Liquidity{
        public String underLockIn;
        public int elssExposure;
    }
    @Getter@Setter
    public static class Quality{
        public int rating5;
        public int rating4;
        public int ratingb3;
        public int rating3;
        public int rating2;
        public int rating1;
        public int rating0;
        public int select;
    }
    @Getter@Setter
    public static class Ratings{
        public int rating5;
        public int rating4;
        public int ratingb3;
        public int rating3;
        public int rating2;
        public int rating1;
        public int rating0;
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
    public static class SecondHighest{
        public Fund fund;
    }
    @Getter@Setter
    public static class Sip{
        public int total;
        public int equity;
        public int debt;
        public int others;
        public Ratings ratings;
    }*/

}
