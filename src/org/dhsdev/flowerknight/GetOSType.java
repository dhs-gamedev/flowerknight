package org.dhsdev.flowerknight;

import java.util.Locale;

/**
 * Get the OS of the host system
 * @author Brandon Qi
 */
public class GetOSType {

    /**
     * Actual function to get OS
     * @return The OS of the system
     */
    public static OSType getOSType() {
        String OS = System.getProperty("os.name").toLowerCase();

        if (OS.contains("win")) return OSType.WINDOWS;  // Windows
        if (OS.contains("mac")) return OSType.MACOS;    // Mac OS
        if (OS.contains("linux")) return OSType.LINUX;  // Linux

        // If the function has not returned yet, the OS is none of the
        // above, so we can safely say that the OS is OSType.OTHER
        return OSType.OTHER;
    }
}
