package core.advisor_Dashboard;
import java.util.*;

public class Adv_payload {
    public static HashMap<String, Object> level0() {

        Map<String,Object> payload= new LinkedHashMap<>();
        payload.put("page",1);
        payload.put("size",500);
        payload.put("userRole","string");
            List<String> heads = Arrays.asList("187458");
            List<String> managers = Arrays.asList();
            List<String> advisors = Arrays.asList();
        payload.put("heads",heads);
        payload.put("managers",managers);
        payload.put("advisors",advisors);
        payload.put("duration",1);
        payload.put("type","fi_style");
        //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
        payload.put("aggregateBy","investment_amount");              //investor_count / investment_amount
        payload.put("sortBy","fi_style");
        //scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
        payload.put("order","asc");
       /* Map<String,Object> search= new LinkedHashMap<>();
        search.put("type","amc");         //only applicable for amc, category, scheme_name
        search.put("query","");
        payload.put("search",search);*/
        return (HashMap<String, Object>) payload;
    }

    public static Map<String, Object> level1() {
        Map<String,Object> payload= new LinkedHashMap<>();
        payload.put("page",1);
        payload.put("size",500);
        payload.put("userRole","string");
            List<String> heads = Arrays.asList("187458");
            List<String> managers = Arrays.asList();
            List<String> advisors = Arrays.asList();
        payload.put("heads",heads);
        payload.put("managers",managers);
        payload.put("advisors",advisors);
        payload.put("duration",1);
        payload.put("type","scheme_name");
        //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style

        List<Map<String, Object>> level1 = new ArrayList<>();      // Level 1
        Map<String, Object> data = new HashMap<>();
        data.put("name","credit_quality");
        data.put("value","AA & Below");
        level1.add(data);
        payload.put("level1",level1);

        payload.put("aggregateBy","investment_amount");
        payload.put("sortBy","aum_percentage");
        //scheme_name ,customer_count,customer_percentage,aum , aum_percentage
        payload.put("order","asc");

        Map<String,Object> search= new LinkedHashMap<>();
        search.put("type","scheme_name");                          //[ name, mobile, pan, email, scheme_name, amc, category ]
        search.put("query","Aditya Birla SL Balanced Advantage Fund");
        payload.put("search",search);

        return payload;
    }

    public static Map<String, Object> level2() {
        Map<String,Object> payload= new LinkedHashMap<>();
        payload.put("page",1);
        payload.put("size",500);
        payload.put("userRole","string");

            List<String> heads = Arrays.asList("187458");
            List<String> managers = Arrays.asList();
            List<String> advisors = Arrays.asList();

        payload.put("heads",heads);
        payload.put("managers",managers);
        payload.put("heads",heads);
        payload.put("advisors",advisors);

        payload.put("duration",1);
        payload.put("durationUnit","month");         //[ month, year ]
        payload.put("type","scheme_name");
        //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style

        List<Map<String, Object>> level2 = new ArrayList<>();      // Level 1
        Map<String, Object> data = new HashMap<>();
        data.put("name","scheme_name");
        data.put("value","447");                // scheme code
        level2.add(data);
        payload.put("level2",level2);

        payload.put("aggregateBy","investment_amount");
        payload.put("sortBy","name");         // name and aum
        payload.put("order","asc");

       /* Map<String,Object> search= new LinkedHashMap<>();
        search.put("type","email");                          //[ name, mobile, pan, email, scheme_name, amc, category ]
        search.put("query","sbharadhwaj@gmail.com");
        payload.put("search",search);*/
        return payload;
    }

    public static Map<String, Object> SnapshotPayload() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", 1);
        payload.put("size", 100);
        payload.put("sortBy", "");
        payload.put("sortType", "asc");             // [asc, desc]

        List<String> segment = Arrays.asList("silver");  //"silver", "gold", "platinum", "digital", "n/a"
        List<String> headid = Arrays.asList("2152531");
        List<String> managerid = Collections.emptyList();
        List<String> advisorid = Collections.emptyList();

        Map<String, Object> search = new HashMap<>();
        search.put("type", "name");
        search.put("query", "string");

        payload.put("segments", segment);
        payload.put("heads", headid);
        payload.put("managers", managerid);
        payload.put("advisors", advisorid);
        payload.put("search", search);

        return payload;
    }

}
