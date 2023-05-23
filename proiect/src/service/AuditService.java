package service;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditService {
    private static final String CSV_FILE_PATH = "audit.csv";

    public static void logAction(String actionName) {
        try {
            FileWriter fileWriter = new FileWriter(CSV_FILE_PATH, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            LocalDateTime timestamp = LocalDateTime.now();
            String formattedTimestamp = timestamp.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

            printWriter.println(actionName + "," + formattedTimestamp);

            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
