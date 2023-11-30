package core.advisor_Dashboard.BankCollection;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;

public class qrResponseBO {
    @Getter@Setter
    public static class Data{
        public String id;
        public String shortUrl;
        public String status;
        public String traceId;
        public String upiBillId;
        public String upiLink;
        public Date expiresOn;
        public String qrCode;
        public int failureCount;
        public boolean serviceAvailable;
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
