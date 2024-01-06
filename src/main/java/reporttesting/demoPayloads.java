package reporttesting;

import java.util.HashMap;
import java.util.Map;

public class demoPayloads {

    public static Map<String,String> getHeaders(String apiVersion, String channelId){
        Map<String, String> payload = new HashMap<>();
        payload.put("x-api-version", apiVersion);
        payload.put("channel-id", channelId);
        return payload;
    }

    public static Map<String,String> getLoginPayload(String emailId, String password,String grantType, String refreshToken){
        Map<String, String> payload = new HashMap<>();
        payload.put("emailId", emailId);
        payload.put("password", password);
        payload.put("grantType", grantType);
        payload.put("refreshToken", refreshToken);
        return payload;
    }

}
