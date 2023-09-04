package core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter@Setter
public class AuthorizationResponse {
    @Getter@Setter
    public static class Data{
        public ArrayList<Object> sips;
        public ArrayList<Object> portfolioSips;
        public ArrayList<Object> swps;
        public ArrayList<Object> stps;
        public ArrayList<Object> redemptions;
        public ArrayList<Switch> switches;
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
    public static class Switch{
        public String id;
        public String folio;
        public String goalId;
        public String goalName;
        public String fromSchemeCode;
        public String fromSchemeName;
        public String toSchemeCode;
        public String toSchemeName;
        public String toAmcCode;
        public String fromAmcCode;
        public double amount;
        public String monthlyAmountFormatted;
        public double units;
        public String unitsFormatted;
        public String toOption;
        public String fromOption;
        public String fromDividendOption;
        public String switchType;
        public String switchMode;
        public ArrayList<String> actions;
        public String holdingProfileId;
        public String holdingProfileName;
        public String toDividendOption;
    }

}
