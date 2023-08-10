package core.advisor_Dashboard;
import java.util.*;

public class Adv_payload {

    public static Map<String, Object> AllClients() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", 1);
        payload.put("size", 20);
            List<String> segment = Arrays.asList("silver","platinum","gold","digital");
                                            //"silver", "gold", "platinum", "digital", "n/a"
            List<String> status = Arrays.asList("not_started","in_progress","completed","overdue","not_reviewed");
                                            // "not_started","in_progress","completed","overdue","not_reviewed"
            List<String> headid = Arrays.asList("187458");
            List<String> managerid = Arrays.asList();
            List<String> advisorid = Arrays.asList();

        payload.put("segments", segment);
        payload.put("status", status);
        payload.put("heads", headid);
        payload.put("managers", managerid);
        payload.put("advisors", advisorid);
        payload.put("sortBy", "user_name");
        payload.put("sortType", "asc");                      // [asc, desc]
        return payload;
    }
    public static Map<String, Object> AllReviews() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", 1);
        payload.put("size", 20);
            List<String> segment = Arrays.asList("silver","platinum","gold","digital");
                                                 //"silver", "gold", "platinum", "digital", "n/a"
            List<String> status = Arrays.asList("all","draft","generated","completed");
            List<String> headid = Arrays.asList("187458");
            List<String> managerid = Arrays.asList();
            List<String> advisorid = Arrays.asList();
        payload.put("segments", segment);
        payload.put("status", status);
        payload.put("heads", headid);
        payload.put("managers", managerid);
        payload.put("advisors", advisorid);
        payload.put("types", "all");            //[ all, recent, updated ]
        payload.put("sortBy", "advisor_name");
        payload.put("sortType", "asc");                      // [asc, desc]
        return payload;
    }

    public static Map<String, Object> quality_review() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("reviewId", "23015");
        payload.put("critical", "yes");         // [yes , no]
        payload.put("date", "2023-06-26T12:18:34.268Z");
        payload.put("comments", "testing");

            List<Map<String, Object>> parameter = new ArrayList<>();
                Map<String, Object> data = new HashMap<>();
                data.put("id","1");
                data.put("description","Testing");
                data.put("value","yes");
            List<String> tag = Arrays.asList("Tags testing");
                data.put("tags",tag);
            parameter.add(data);
        payload.put("parameters",parameter);
        return payload;
    }

    public static  Map<String, Object> level0() {

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
        payload.put("financialYear","2023-2024");
        payload.put("type","fi_rating");
                    //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
        payload.put("aggregateBy","investment_amount");
                    //investor_count / investment_amount
        payload.put("sortBy","fi_rating");
                         //scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
        payload.put("order","asc");
       /* Map<String,Object> search= new LinkedHashMap<>();
        search.put("type","category");         //only applicable for amc, category, scheme_name
        search.put("query","Equity");
        payload.put("search",search);*/
        return  payload;
    }

    public static  Map<String, Object> level0_paltinum_manager() {

        Map<String,Object> payload= new LinkedHashMap<>();
        payload.put("page",1);
        payload.put("size",500);
        payload.put("userRole","Manager");
        List<String> heads = Arrays.asList("187458");
        List<String> managers = Arrays.asList();
        List<String> advisors = Arrays.asList();
        payload.put("heads",heads);
        payload.put("managers",managers);
        payload.put("advisors",advisors);
        payload.put("financialYear","2023-2024");
        payload.put("type","category");
        //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
        payload.put("aggregateBy","investment_amount");
        //investor_count / investment_amount
        payload.put("sortBy","category");
        //scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
        payload.put("order","asc");
        Map<String,Object> search= new LinkedHashMap<>();
        search.put("type","category");         //only applicable for amc, category, scheme_name
        search.put("query","Equity");
        payload.put("search",search);
        return  payload;
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
        payload.put("financialYear","2023-2024");
        payload.put("type","amc");
            //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style

        List<Map<String, Object>> level1 = new ArrayList<>();      // Level 1
        Map<String, Object> data = new HashMap<>();
        data.put("name","amc");
        data.put("value","400047");
        level1.add(data);
        payload.put("level1",level1);

        payload.put("aggregateBy","investment_amount");
        payload.put("sortBy","aum");
                //scheme_name ,customer_count,customer_percentage,aum , aum_percentage
        payload.put("order","asc");

    /*    Map<String,Object> search= new LinkedHashMap<>();
        search.put("type","scheme_name");                          //[ name, mobile, pan, email, scheme_name, amc, category ]
        search.put("query","Aditya Birla SL Balanced Advantage Fund");
        payload.put("search",search);*/

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

        payload.put("financialYear","2023-2024");
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
        search.put("query","ridhimasingh910@gmail.com");
        payload.put("search",search);*/
        return payload;
    }

    public static Map<String, Object> SnapshotPayload() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", 1);
        payload.put("size", 320);
        payload.put("sortBy", "outflow");
        payload.put("sortType", "desc");                      // [asc, desc]

        List<String> segment = Arrays.asList("silver", "gold", "platinum", "digital");     //"silver", "gold", "platinum", "digital"
        List<String> headid = Arrays.asList("187458");
        List<String> managerid = Arrays.asList("359296");
        List<String> advisorid = Arrays.asList("1063336");

   /*     Map<String, Object> search = new HashMap<>();
        search.put("type", "name");
        search.put("query", "bal");
        payload.put("search", search);*/

        payload.put("segments", segment);
        payload.put("heads", headid);
        payload.put("managers", managerid);
        payload.put("advisors", advisorid);

        return payload;
    }

}
