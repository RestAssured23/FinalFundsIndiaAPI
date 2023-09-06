package core.advisor_Dashboard;
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
        List<String> segment = Arrays.asList("silver", "platinum", "gold", "digital");
        //"silver", "gold", "platinum", "digital", "n/a"
        List<String> status = Arrays.asList("not_started", "in_progress", "completed", "overdue", "not_reviewed");
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
}