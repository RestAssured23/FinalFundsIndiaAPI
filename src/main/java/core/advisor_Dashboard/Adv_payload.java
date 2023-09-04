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
            List<String> managerid = Arrays.asList("1871006");
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
            List<String> managers = Arrays.asList("1871006");           //Manager -> 1871006
            List<String> advisors = Arrays.asList();
        payload.put("heads",heads);
        payload.put("managers",managers);
        payload.put("advisors",advisors);
        payload.put("financialYear","2023-2024");
        payload.put("type","amc");
                    //type possibility==> scheme_name, amc, category, fi_rating, credit_quality, asset_allocation, fi_style
        payload.put("aggregateBy","investment_amount");
                    //investor_count / investment_amount
        payload.put("sortBy","amc");
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
            List<String> advisors = Arrays.asList("86808");
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
        search.put("query","SANDHYAKDN@yahoo.com");
        payload.put("search",search);*/
        return payload;
    }

    public static Map<String, Object> SnapshotPayload() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", 1);
        payload.put("size", 1500);
        payload.put("sortBy", "inflow");
        payload.put("sortType", "asc");                      // [asc, desc]

        List<String> segment = Arrays.asList("silver", "gold", "platinum", "digital");     //"silver", "gold", "platinum", "digital"
        List<String> headid = Arrays.asList("187458","2152531");
        List<String> managerid = Arrays.asList("359296");
        List<String> advisorid = Arrays.asList("98300");

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
    public static Map<String, Object> Monthly_Trends() {
        Map<String,Object> payload= new LinkedHashMap<>();
        payload.put("page",1);
        payload.put("size",50);
        payload.put("financialYear","2023-2024");
        payload.put("sortBy","months");         //ytd ,months,lfy
        payload.put("sortType","asc");          //asc , desc

/*        List<String> segment = Arrays.asList("silver");     //"silver", "gold", "platinum", "digital"
        payload.put("segments",segment);*/
        List<String> heads = Arrays.asList("187458");
        payload.put("heads",heads);
        List<String> managers = Arrays.asList("86808");
        payload.put("managers",managers);
        List<String> advisors = Arrays.asList("86808");
        payload.put("advisors",advisors);

        Map<String,Object> trendsdata=new LinkedHashMap<>();
                 //INFLOW
                          /*  Map<String,Object> inflowdata=new LinkedHashMap<>();
                            inflowdata.put("enabled",true);
                            inflowdata.put("sip",true);
                            inflowdata.put("lumpsum",false);
                            inflowdata.put("transferIn",false);
                            trendsdata.put("inflow",inflowdata);*/
                //OUTFLOW
                           Map<String,Object> outflowdata=new LinkedHashMap<>();
                            outflowdata.put("enabled",true);
                            outflowdata.put("redemption",true);
                            outflowdata.put("swp",true);
                             outflowdata.put("transferOut",true);
                            trendsdata.put("outflow",outflowdata);
                //NETFLOW
                       /*   Map<String,Object> netflowdata=new LinkedHashMap<>();
                            netflowdata.put("enabled",true);
                            trendsdata.put("netflow",netflowdata);*/
                //AUM
                          /*  Map<String,Object> aumdata=new LinkedHashMap<>();
                            aumdata.put("enabled",true);
                            trendsdata.put("netflow",aumdata);*/
                //SEARCH
                          /* Map<String, Object> search = new HashMap<>();
                            search.put("type", "name");             // email , pan , name , mobile
                            search.put("query", "Bhaumik");
                            payload.put("search", search);*/


        payload.put("trendsBy",trendsdata);
        return payload;
    }
    public static Map<String, Object> Filters() {
        Map<String, Object> payload = new LinkedHashMap<>();
      //  payload.put("id", "");
        payload.put("name", "YTD");
        payload.put("source", "MonthlyTrends");

        List<Map<String, Object>> parameter = new ArrayList<>();
            Map<String,Object>filterdata=new LinkedHashMap<>();
            filterdata.put("month","ytd");                 // mtd, lfy, ytd, current_month, previous_month, other_month
            filterdata.put("monthName","June");
            filterdata.put("field","swp");                //sip, lumpsum, inflow, outflow, transfer_in, transfer_out, redemption, swp
            filterdata.put("type","or");                  //  or, and
            Map<String,Object>condi_data=new LinkedHashMap<>();
                condi_data.put("type","is_less_than");
                                    /*    is_less_than, is_greater_than, is_equal_to, is_less_than_or_equal_to,
                                          is_greater_than_or_equal_to,contains, in_range_between   */
                condi_data.put("value1","2000");
          //      condi_data.put("value2","");
          //      condi_data.put("enums","string");
            filterdata.put("condition",condi_data);
        parameter.add(filterdata);
    payload.put("filters",parameter);
        return payload;
    }
}
