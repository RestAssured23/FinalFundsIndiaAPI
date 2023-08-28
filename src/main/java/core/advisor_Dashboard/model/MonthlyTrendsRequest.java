package core.advisor_Dashboard.model;

import lombok.Data;

import java.util.List;
@Data
public class MonthlyTrendsRequest {
    @Data
    public class Aum{
        public boolean enabled;
    }

    @Data
    public class Inflow{
        public boolean enabled;
        public boolean sip;
        public boolean lumpsum;
        public boolean transferIn;
    }

    @Data
    public class Netflow{
        public boolean enabled;
    }

    @Data
    public class Outflow{
        public boolean enabled;
        public boolean redemption;
        public boolean transferOut;
        public boolean swp;
    }

    public int page;
    public int size;
    public List<String> segments;
    public TrendsBy trendsBy;
    public List<String> heads;
    public List<String> managers;
    public List<String> advisors;
    public String sortBy;
    public String sortType;
    public Search search;
    public String financialYear;


  @Data
    public class TrendsBy{
        public Inflow inflow;
        public Outflow outflow;
        public Netflow netflow;
        public Aum aum;
    }
    @Data
    public class Search {
        public String type;
        public String query;

    }
}
