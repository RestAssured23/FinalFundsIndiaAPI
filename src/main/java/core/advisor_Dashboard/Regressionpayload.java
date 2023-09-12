package core.advisor_Dashboard;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Regressionpayload  extends AD_AccessPropertyFile {

    public static Map<String, Object> SearchInvestor() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("query",mailid);
        payload.put("type","email");
        return payload;
    }
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
    public static Map<String, Object> AllClients_month() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", 1);
        payload.put("size", 20);
        payload.put("due", "month");
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
    public static Map<String, Object> AllClients_Week() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", 1);
        payload.put("size", 20);
        payload.put("due", "week");
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
    public static Map<String, Object> AllClients_overdue() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", 1);
        payload.put("size", 20);
        payload.put("due", "overdue");
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
    public static Map<String, Object> AllClients_mailsearch() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", 1);
        payload.put("size", 20);

        List<String> segment = Arrays.asList("silver","platinum","gold","digital");
        //"silver", "gold", "platinum", "digital", "n/a"
        List<String> status = Arrays.asList("not_started","in_progress","completed","overdue","not_reviewed");
        // "not_started","in_progress","completed","overdue","not_reviewed"
        List<String> headid = Arrays.asList("187458","2152531");
        List<String> managerid = Arrays.asList();
        List<String> advisorid = Arrays.asList();

        payload.put("segments", segment);
        payload.put("status", status);
        payload.put("heads", headid);
        payload.put("managers", managerid);
        payload.put("advisors", advisorid);
        payload.put("sortBy", "user_name");
        payload.put("sortType", "asc");                      // [asc, desc]

        Map<String,Object>searchdata=new HashMap<>();
        searchdata.put("query",mailid);
        searchdata.put("type","email");
        payload.put("search",searchdata);
        return payload;
    }
    public static Map<String, Object> AllClients_mobilesearch() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", 1);
        payload.put("size", 20);

        List<String> segment = Arrays.asList("silver","platinum","gold","digital");
        //"silver", "gold", "platinum", "digital", "n/a"
        List<String> status = Arrays.asList("not_started","in_progress","completed","overdue","not_reviewed");
        // "not_started","in_progress","completed","overdue","not_reviewed"
        List<String> headid = Arrays.asList("187458","2152531");
        List<String> managerid = Arrays.asList();
        List<String> advisorid = Arrays.asList();

        payload.put("segments", segment);
        payload.put("status", status);
        payload.put("heads", headid);
        payload.put("managers", managerid);
        payload.put("advisors", advisorid);
        payload.put("sortBy", "user_name");
        payload.put("sortType", "asc");                      // [asc, desc]

        Map<String,Object>searchdata=new HashMap<>();
        searchdata.put("query",searchphno);
        searchdata.put("type","mobile");
        payload.put("search",searchdata);
        return payload;
    }
    public static Map<String, Object> AllClients_panSearch() {
        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", 1);
        payload.put("size", 20);

        List<String> segment = Arrays.asList("silver","platinum","gold","digital");
        //"silver", "gold", "platinum", "digital", "n/a"
        List<String> status = Arrays.asList("not_started","in_progress","completed","overdue","not_reviewed");
        // "not_started","in_progress","completed","overdue","not_reviewed"
        List<String> headid = Arrays.asList("187458","2152531");
        List<String> managerid = Arrays.asList();
        List<String> advisorid = Arrays.asList();

        payload.put("segments", segment);
        payload.put("status", status);
        payload.put("heads", headid);
        payload.put("managers", managerid);
        payload.put("advisors", advisorid);
        payload.put("sortBy", "user_name");
        payload.put("sortType", "asc");                      // [asc, desc]

        Map<String,Object>searchdata=new HashMap<>();
        searchdata.put("query",searchpan);
        searchdata.put("type","pan");
        payload.put("search",searchdata);
        return payload;
    }
    public static Map<String, Object> AllClients_DateFilter() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime fromdate = LocalDateTime.now().minusDays(30);
        LocalDateTime todate = LocalDateTime.now();
        String startdate = fromdate.format(formatter);
        String endate = todate.format(formatter);

        Map<String, Object> payload = new LinkedHashMap<>();
        payload.put("page", 1);
        payload.put("size", 20);

        List<String> segment = Arrays.asList("silver","platinum","gold","digital");
        //"silver", "gold", "platinum", "digital", "n/a"
        List<String> status = Arrays.asList("not_started","in_progress","completed","overdue","not_reviewed");
        // "not_started","in_progress","completed","overdue","not_reviewed"
        List<String> headid = Arrays.asList("187458","2152531");
        List<String> managerid = Arrays.asList();
        List<String> advisorid = Arrays.asList();

        payload.put("segments", segment);
        payload.put("status", status);
        payload.put("heads", headid);
        payload.put("managers", managerid);
        payload.put("advisors", advisorid);
        payload.put("sortBy", "user_name");
        payload.put("sortType", "asc");                      // [asc, desc]

        payload.put("fromDate",startdate);
        payload.put("toDate",endate);
        return payload;
    }
}