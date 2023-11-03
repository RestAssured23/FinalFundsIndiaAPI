package core.basepath.TESTING;

import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class AccessPropertyFileTest {
    public static double switch_unitpro, switch_amtpro, inv_amount;
    public static String holdingid_pro, folio_pro, units_pro, amount_pro,
            targetscheme_pro, switch_target, amcschemeSearch, expectedscheme, alertStartdate, alertEnddate;
    public static String dbusr, dbpwd, dburl, databasename, encrypt, dbdrivername;
    public static int sipDate;
    static Properties properties = new Properties();

    @Test
    public String getBasePath(String profile) {
        try {
            FileInputStream fis = new FileInputStream(profile + ".properties");
            properties.load(fis);
            return properties.getProperty("baseURI");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public String getChannelID(String profile) {
        try {
            FileInputStream fis = new FileInputStream(profile + ".properties");
            properties.load(fis);
            return properties.getProperty("channelID");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public String getAccessToken(String profile) {
        holdingid_pro = properties.getProperty("holdingid");
        folio_pro = properties.getProperty("folio");
        // ... Other property assignments ...

        try {
            FileInputStream fis = new FileInputStream(profile + ".properties");
            properties.load(fis);
            if (properties.getProperty("accesstoken").isEmpty()) {
                // ... Your existing logic for access token ...
            } else {
                return properties.getProperty("accesstoken");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return profile;
    }

}
