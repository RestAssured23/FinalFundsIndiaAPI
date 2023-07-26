package core.advisor_Dashboard;

import java.util.*;

public class adv_payload {

    public static HashMap<String, Object> level0() {

        Map<String,Object> payload= new LinkedHashMap<>();
        payload.put("page",1);
        payload.put("size",500);
        payload.put("userRole","string");
        List<String> advisors = new LinkedList<>();     // Advisors
        advisors.isEmpty();
        payload.put("advisors",advisors);
        List<String> managers = new LinkedList<>();     // Managers
        managers.isEmpty();
        payload.put("managers",managers);
        List<String> heads = new LinkedList<>();        //heads
        heads.add("2152531");
        payload.put("heads",heads);
        payload.put("duration","1y");
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

    public static HashMap<String, Object> level1() {

        Map<String,Object> payload= new LinkedHashMap<>();
        payload.put("page",1);
        payload.put("size",500);
        payload.put("userRole","string");
        List<String> advisors = new LinkedList<>();     // Advisors
        advisors.isEmpty();
        payload.put("advisors",advisors);
        List<String> managers = new LinkedList<>();     // Managers
        managers.isEmpty();
        payload.put("managers",managers);
        List<String> heads = new LinkedList<>();        //heads
        heads.add("2152531");
        payload.put("heads",heads);
        payload.put("duration","1y");
        payload.put("type","scheme_name");
        //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style

        List<Map<String, Object>> level1 = new LinkedList<>();      // Level 1
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

        return (HashMap<String, Object>) payload;
    }

    public static HashMap<String, Object> level2() {
        Map<String,Object> payload= new LinkedHashMap<>();
        payload.put("page",1);
        payload.put("size",500);
        payload.put("userRole","string");
        List<String> advisors = new LinkedList<>();     // Advisors
        advisors.isEmpty();
        payload.put("advisors",advisors);
        List<String> managers = new LinkedList<>();     // Managers
        managers.isEmpty();
        payload.put("managers",managers);
        List<String> heads = new LinkedList<>();        //heads
        heads.add("187458");
        payload.put("heads",heads);
        payload.put("duration","1y");
        payload.put("type","scheme_name");
        //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style

        List<Map<String, Object>> level2 = new LinkedList<>();      // Level 1
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

        return (HashMap<String, Object>) payload;
    }

}
