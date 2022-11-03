package common.action;

import java.util.Properties;

public class GlobalConfiguration {

    public static int GLOBAL_EXPLICIT_TIMEOUT = 0;
    public static int GLOBAL_EXPLICIT_POLLING_TIME = 0;
    public static String TAKE_SCREENSHOT_FOR_EACH_STEP = null;
    public static String TAKE_SCREENSHOT_ON_FAILURE = null;

    public GlobalConfiguration() {
        Properties prop = ReusableCommonMethods.getPropertiesFileObject(System.getProperty("user.dir") + "\\src\\test\\resources\\global_config\\config.properties");
        GLOBAL_EXPLICIT_TIMEOUT = Integer.parseInt(prop.getProperty("GLOBAL_EXPLICIT_TIMEOUT"));
        GLOBAL_EXPLICIT_POLLING_TIME = Integer.parseInt(prop.getProperty("GLOBAL_EXPLICIT_POLLING_TIME"));
        TAKE_SCREENSHOT_FOR_EACH_STEP = prop.getProperty("TAKE_SCREENSHOT_FOR_EACH_STEP");
        TAKE_SCREENSHOT_ON_FAILURE = prop.getProperty("TAKE_SCREENSHOT_ON_FAILURE");
    }
}
