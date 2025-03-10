package uz.pdp.simplecrud.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

@Component
public class LogCleanupScheduling {
    private static final Logger logger = LoggerFactory.getLogger(LogCleanupScheduling.class);
    private static final String LOG_FILE_PATH = "C:\\Abbos\\Spring Project\\Test Projects\\SimpleCrud\\logs\\SimpleCRUD.log";

    @Scheduled(cron = "0 0 0 * * *") // har kuni log file ni tozalaydi
    public void clearLogFile() {
        File file = new File(LOG_FILE_PATH);
        if (file.exists()) {
            try {
                Files.write(file.toPath(), new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
                logger.info("✅ Log fayli tozalandi: " + LOG_FILE_PATH);
            } catch (IOException e) {
                logger.error("❌ Log faylni tozalashda xatolik: " + e.getMessage());
            }
        } else {
            logger.warn("⚠ Log fayl topilmadi: " + LOG_FILE_PATH);
        }
    }
}
