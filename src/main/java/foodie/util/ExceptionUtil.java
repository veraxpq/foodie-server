package foodie.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {
    private static final Logger logger = LogManager.getLogger(ExceptionUtil.class.getName());

    public static final String getStackTrace(Exception e) {
        StringWriter stringWriter = null;
        PrintWriter printWriter = null;

        try {
            stringWriter = new StringWriter();
            printWriter = new PrintWriter(stringWriter);
            logger.error(printWriter);
            printWriter.flush();
            stringWriter.flush();

            return stringWriter.toString();
        } finally {
            if (stringWriter != null) {
                try {
                    stringWriter.close();
                } catch (IOException ioe) {
                    logger.error(ioe.getStackTrace());
                }
            }
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
