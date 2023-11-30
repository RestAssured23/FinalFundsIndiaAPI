package core.advisor_Dashboard.BankCollection;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class ekoResponseBO {
    @Getter@Setter
    public static class BankAddress{
        public String addressLine1;
        public String city;
    }
    @Getter@Setter
    public static class Data{
        public String investorId;
        public String accountHolderName;
        public GatewayBankIds gatewayBankIds;
        public String bankId;
        public String bankName;
        public String type;
        public String ifsc;
        public String accountNo;
        public String accountType;
        public String bankAccountType;
        public boolean activated;
        public String userBankId;
        public ArrayList<String> mandateType;
        public String status;
        public ArrayList<String> actions;
        public String verificationId;
        public int nameMatchScore;
        public BankAddress bankAddress;
        public String provider;
        public boolean success;
        public boolean chequeUploadRequired;
    }
    @Getter@Setter
    public static class GatewayBankIds{
        public String tpsl;
        public String upi;
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
