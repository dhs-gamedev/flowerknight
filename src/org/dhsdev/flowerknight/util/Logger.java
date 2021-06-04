package org.dhsdev.flowerknight.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is for showing the user important processes, and for giving
 * the programmer debugging info
 * @author jakeroggenbuck
 */
public class Logger {

    private Logger() {
        throw new IllegalStateException("Logger class");
    }

    /**
     * @param message Log Message
     * @param severity Severity of the problem
     * @author jakeroggenbuck
     */
    public static synchronized void log(String message, Severity severity) {
        final var logFile = new File("data/logs.txt");

        try {
            logFile.createNewFile();
        } catch (IOException e) {
            System.err.println("Log file could not be written to.");
            System.exit(-1);
        }

        String errorMessage;

        // Add the time header to the error message unless we specify raw logging
        if (severity == Severity.RAW) {
            errorMessage = message + "\n";
        } else {
            final var timeFormat = "MM-dd-yy HH:mm:ss";
            final var sdf = new SimpleDateFormat(timeFormat);
            var date = new Date();
            String formattedDate = sdf.format(date);
            String header = "[ " + formattedDate + " ] [ " + severity.name() + " ] ";
            errorMessage = header + message + "\n";
        }

        // Debug warning are ones we don't want to give the use
        // Debug should only be written to the file
        if (severity != Severity.DEBUG) {
            System.out.print(errorMessage);
        }

        // Try to write the errorMessage to the file
        try {
            var writer = new FileWriter(logFile, true);
            writer.write(errorMessage);
            // This looks repetitive, but we want to write the log each time
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (severity == Severity.ERROR) {
            System.exit(-1);
        }

    }
}