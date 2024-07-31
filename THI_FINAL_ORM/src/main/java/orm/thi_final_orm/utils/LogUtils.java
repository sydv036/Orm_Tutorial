package orm.thi_final_orm.utils;

import org.apache.log4j.Logger;

public class LogUtils {
    private static final Logger loggerInfo = Logger.getLogger("InfoLog");
    private static final Logger loggerError = Logger.getLogger("ErrorLog");

    /**
     * @param message
     * @author SyDV3
     * @birthday 2003_01_04
     */
    public static void info(String message) {
        loggerInfo.info(message);
    }

    /**
     * @param message
     * @author SyDV3
     * @birthday 2003_01_04
     */
    public static void error(String message) {
        loggerError.error(message);
    }
}
