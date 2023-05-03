package org.eun.back.util;

import java.io.File;
import java.nio.file.Paths;
import org.eun.back.service.BirtReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Aha
 */
public class Utils {

    private static final Logger log = LoggerFactory.getLogger(BirtReportService.class);

    public static String getOsPath(Boolean isDocker) {
        String userFolder = System.getProperty("user.dir") + File.separatorChar;
        if (!isDocker && "linux".equals(System.getProperty("os.name").toLowerCase())) userFolder =
            Paths.get(System.getProperty("java.class.path")).getParent().toString() + File.separatorChar;
        return userFolder;
    }
}
