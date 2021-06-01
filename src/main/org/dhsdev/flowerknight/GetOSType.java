package org.dhsdev.flowerknight;

import java.util.Locale;

/**
 * Get the OS of the host system
 * @author Brandon Qi
 */
public class GetOSType {

    private GetOSType() {
        throw new IllegalStateException("GetOSType Class");
    }

    /**
     * Actual function to get OS
     * @return The OS of the system
     */
    public static OSType getOSType() {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) return OSType.WINDOWS;  // Windows
        if (os.contains("mac")) return OSType.MACOS;    // Mac OS
        if (os.contains("linux")) return OSType.LINUX;  // Linux

        // If the function has not returned yet, the OS is none of the
        // above, so we can safely say that the OS is OSType.OTHER
        return OSType.OTHER;
    }
}
