package IOStreams;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class CopiedDocToDownloadDirectory {
    public static void main(String[] args) throws IOException {

        Path hiddenPath = Paths.get("D:\\generate_credit_bills\\Mission_2.0\\JayHo");
        Path bankStatement;
        // if this directory not exist there should be a backup one, so 99.99% reliable
        if (Files.isDirectory(hiddenPath) && Files.exists(hiddenPath)) {
            bankStatement = hiddenPath.resolve("SBI_BankStatement.pdf");
        } else {
            Path backupPath = Paths.get("D:\\generate_credit_bills\\Mission_1.0\\JayHoAgain");
            bankStatement=backupPath.resolve("SBI_BankStatement.pdf");
        }
        //byte[] readAllBytes = Files.readAllBytes(bankStatement);

        String baseName = "SBIN_37023751389_" + LocalDate.now();
        // after reading moved it to desired directory
        Path desiredDir = Paths.get("C:\\Users\\aocha\\Downloads");

        // ✅ Generate unique execution count by scanning existing files
        AtomicInteger counter = new AtomicInteger(0);
        try (var files = Files.list(desiredDir)) {
            files.filter(path -> path.getFileName().toString().startsWith(baseName))
                    .forEach(path -> counter.incrementAndGet());
        }
        String newFileName;
        if (counter.get() == 0) {
            newFileName = baseName + ".pdf";
        } else {
            newFileName = baseName + "_(" + counter.get() + ").pdf";
        }
        Path targetFile = desiredDir.resolve(newFileName);

        // ✅ Copy the file
        // StandardCopyOption.ATOMIC_MOVE --->used with the Files.move() method — not Files.copy() — to ensure the move operation is atomic.
        // Atomic move = the file is either completely moved or not moved at all.
        Files.copy(bankStatement, targetFile);
        System.out.println("✅ File copied successfully as: " + targetFile.getFileName());

         FileTime now = FileTime.fromMillis(System.currentTimeMillis());
         Files.setLastModifiedTime(targetFile, now);



    }
}
