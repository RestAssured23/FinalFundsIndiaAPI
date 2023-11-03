package core.basepath.TESTING;

public class BasePlatformTest {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java BasePlatform <profile>");
            System.exit(1);
        }

        String profile = args[0];
        String path = setUp(profile);

        if (path.isEmpty()) {
            System.out.println("Failed to load configuration for the specified profile.");
            System.exit(1);
        }

        // Add your code logic here that uses the 'path' variable
        System.out.println("Using properties file: " + path);
    }

    public static String setUp(String profile) {
        String os = System.getProperty("os.name").toLowerCase();
        String basepath = System.getProperty("user.dir") + "/data/automation-testing/wifs/config/properties/";

        if (os.contains("win")) {
            basepath = basepath.replace("/", "\\");
        } else if (!os.contains("mac") && !os.contains("linux")) {
            System.out.println("OS Not Detected");
            basepath = "";
        }

        return basepath + profile + ".properties";
    }
}
