package common.action;

import java.util.Properties;

public class GlobalConfiguration {

    public static int GLOBAL_EXPLICIT_TIMEOUT = 0;
    public static int GLOBAL_EXPLICIT_POLLING_TIME = 0;

    public GlobalConfiguration() {
        Properties prop = ReusableCommonMethods.getPropertiesFileObject(System.getProperty("user.dir") + "\\src\\test\\resources\\global_config\\config.properties");
        GLOBAL_EXPLICIT_TIMEOUT = Integer.parseInt(prop.getProperty("GLOBAL_EXPLICIT_TIMEOUT"));
        GLOBAL_EXPLICIT_POLLING_TIME = Integer.parseInt(prop.getProperty("GLOBAL_EXPLICIT_POLLING_TIME"));
    }
}
