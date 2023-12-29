package core.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class userExistsBO {
@Getter@Setter
    public class Root{
        public int code;
        public String desc;
        public String type;
        public String name;
        public boolean success;
        public ArrayList<Object> errors;
        public Data data;
    }

    @Getter@Setter
    public class Data{
        public String allowedAuthType;
        public boolean changeAllowed;
    }

}
