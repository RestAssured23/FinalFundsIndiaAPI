package core.advisor_Dashboard.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
@Getter@Setter
public class RebalanceResponse {
    @Getter@Setter
    public static class CategoryAllocation{
        public double currentAllocation;
        public double targetAllocation;
        public double proposedAllocation;
        public double currentValue;
        public double proposedValue;
        @JsonProperty("class")
        public String myclass;
    }
    @Getter@Setter
    public static class ChangePercentage{
        public double equity;
        public double debt;
        public double gold;
    }
    @Getter@Setter
    public static class Data{
        public int reviewId;
        public int portfolioValue;
        public int cash;
        public double cashPercentage;
        public int transitCash;
        public double transitCashPercentage;
        public ArrayList<Investment> investments;
        public ArrayList<CategoryAllocation> categoryAllocation;
        public ArrayList<SubCategoryAllocation> subCategoryAllocation;
        public Totals totals;
        public ChangePercentage changePercentage;
    }
    @Getter@Setter
    public static class Investment{
        public String schemeCode;
        public String name;
        public String category;
        public String subCategory;
        public String amcCode;
        public double rating;
        public int amount;
        public int proposedAmount;
        public double currentAllocation;
        public double proposedAllocation;
        public int minimumInvestment;
        public boolean rated;
        public String exitLoad;
        public int lockInAmount;
        public double currentLockInPercent;
        public SystematicPlans systematicPlans;
        public boolean fullyLockedIn;
        public boolean partiallyLockedIn;
        public boolean schemeModified;
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
    public static class Sip{
        public String schemeName;
        public int schemeCode;
        public int amount;
        public int changeAmount;
        public int proposedAmount;
    }
    @Getter@Setter
    public static class SubCategoryAllocation{
        public double currentAllocation;
        public double targetAllocation;
        public double proposedAllocation;
        public double currentValue;
        public double proposedValue;
        @JsonProperty("class")
        public String myclass;
    }
    @Getter@Setter
    public static class SystematicPlans{
        public Sip sip;
    }
    @Getter@Setter
    public static class Totals{
        public double equity;
        public double debt;
        public double gold;
        public double grandTotal;
    }
}
