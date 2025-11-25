package IOStreams;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
// ctrl + alt + O ---> to remove unused packages

public class NewFileHandling {
    /*
       Why nio file handling added to replace io file handling?
        Blocking I/O — Threads had to wait until data was read/written completely.
        Inefficient for large files or multiple connections — not suitable for scalable servers.
        Limited file metadata access — File class could not easily read permissions, ownership, timestamps, etc.
        No proper support for symbolic links, file locking, or watching directory changes.

         🚀 Enhancement: java.nio.file in Java 7 and 8
         📁 Key Features of NIO File Handling (in Java 7/8):
           Path and Paths API
             Replaces old File class.
             Provides better handling of file paths across OS platforms.
              Path path = Paths.get("C:/data/test.txt");

           Files Utility Class
            Provides static methods for almost all file operations:
             Files.copy(src, dest);
             Files.move(src, dest);
             Files.delete(path);
             Files.exists(path);

            Better Metadata Access
             Retrieve attributes like size, creation time, permissions:
             BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);

            Directory Traversal and Filtering
              Walk through directories recursively using streams (added in Java 8):

                try (Stream<Path> paths = Files.walk(Paths.get("C:/data"))) {
                  paths.filter(Files::isRegularFile)
                       .forEach(System.out::println);
                     }

            Watch Service API (Directory Monitoring)
              Monitor file changes (create, delete, modify) in real time:

               WatchService watchService = FileSystems.getDefault().newWatchService();
               Path path = Paths.get("C:/data");
               path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            Stream API Integration (Java 8)
              NIO methods return streams, allowing functional-style operations:
               long count = Files.lines(Paths.get("data.txt"))
                                 .filter(line -> line.contains("Java"))
                                 .count();

            NIO file handling was added in Java 8 to modernize and simplify file operations. It provides a more powerful,
             flexible, and platform-independent API through classes like Path, Paths, and Files. It integrates with the
             Stream API for functional-style programming, supports directory traversal, metadata access, symbolic links,
             and efficient I/O operations — all addressing the limitations of the older java.io.File API.




     */


    public static void main(String[] args) throws IOException {
        Path path = Paths.get("D:\\generate_credit_bills");
        Path filePath = path.resolve("user_bill_"+ LocalDate.now() +".txt");


        if (!(Files.exists(path) && Files.isDirectory(path))) {
            Files.createDirectories(path);
            System.out.println("Directory created successfully inside: " + path);
        } else {
            System.out.println("No worries anymore, Directory already exists.");
        }
        if(Files.notExists(filePath)){
            Files.createFile(filePath);
            System.out.println("File created successfully inside with given name "+filePath);
        }else {
            System.out.println("File already exists in given path");
            //1️⃣ File Existence & Basic Checks
            System.out.println(Files.isRegularFile(filePath));
            /*
              Bonus: Files.isExecutable() → checks OS-level execute permission, not the content type.
                   On Windows, almost any file you have access to will return true.
                   To reliably check if a file is truly executable, check the extension or POSIX execute bit on Unix systems.

             */
            System.out.println(Files.isExecutable(filePath));
            System.out.println(Files.isHidden(filePath));
            System.out.println(Files.isReadable(filePath));
            System.out.println(Files.isWritable(filePath));
            System.out.println(Files.isSymbolicLink(filePath));


            //2️⃣ Reading File Content
            // reading line by line
            List<String> readDataFromFile = Files.readAllLines(filePath, StandardCharsets.UTF_8);
            readDataFromFile.forEach(System.out::println);

            System.out.println("-----------------------------------------------------");
            // reading file as a String
            String readAsString=Files.readString(filePath);
            System.out.println(readAsString);

            // Read file using Stream API as Stream<String>
            try(Stream<String> stream=Files.lines(filePath)){
                  stream.filter(word->word.startsWith("c"))
                        .collect(Collectors.toCollection(LinkedHashSet::new));
            }

            System.out.println("reading file as a byte.............");
            // reading file as a byte.
             byte[] readAsBytes=Files.readAllBytes(filePath);
            for (byte byteRead : readAsBytes) {
                System.out.print(byteRead+" ");
            }

          //3️⃣ Writing to a File
            // write as a binary inside file using ---> Files.write(path, byte[], StandardOpenOption...)
            // writing string content, but it will override original content
            // for append after original content, use Enum ---> StandardOpenOption.APPEND and explore as per need.
            String content="""
                    Please, follow instructions given by our customer executive to pay all your due EMIs on time.
                     If you have any doubt don't hesitate to call us any moment.
                       Thank you for using Axis Bank Credit Services, India
                    """;
            Path fileWritePath = Files.writeString(filePath,
                                                    content,
                                                    StandardOpenOption.CREATE,
                                                    StandardOpenOption.TRUNCATE_EXISTING
                                                  );
            System.out.println("""
                    filepath to be written on:
                    """+fileWritePath);
            System.out.println("files modified successfully !");

         //4️⃣ File Copy, Move, Delete
            Path copiedPath=Paths.get("D:\\text_copy_"+LocalDate.now()+".txt");

            // deleting file from given path if not there throws exception.
            if(Files.exists(copiedPath)){
                Files.delete(copiedPath);
                System.out.println("file deleted successfully...........");
            }


            // copying file to another location also same way you can move it to desired path location.
            if(Files.notExists(copiedPath)){
                Files.copy(filePath,copiedPath);
                System.out.println("files copied again because file not be there inside copied path location");
            }


            //5️⃣ File Metadata & Attributes
             // getting basic metadata like file size, creation time, last modified time, etc.
            System.out.println("All about files metadata.............");
            BasicFileAttributes fileAttributes = Files.readAttributes(filePath, BasicFileAttributes.class);
            System.out.println(fileAttributes.size());
            System.out.println(fileAttributes.creationTime());
            System.out.println(fileAttributes.lastModifiedTime());
            System.out.println(fileAttributes.lastAccessTime());
            // get file owner ---> return UserPrincipal
            System.out.println(Files.getOwner(filePath));
            System.out.println(Files.isSymbolicLink(filePath));
            // doubt: Why it has created new file on startup application on next day?

            //6️⃣ Directory / File Tree Operations
            // listing all files from directory
            try( Stream<Path> paths=Files.list(path)) {
                 long totalFilesAvailable=paths.count();
                System.out.println(totalFilesAvailable);

            }

            // transversing directory recursively ---> useful for searching or filtering
             try(Stream<Path> allFiles=Files.walk(path,FileVisitOption.FOLLOW_LINKS)) {
                 allFiles
                         .filter(Files::isRegularFile)
                         .forEach(System.out::println);
             }

             /*delete a directory recursively, it means:
               Delete the directory itself and everything inside it — including all files and subdirectories, and their contents too.
               deleting a directory along with all its subdirectories and files. In Java NIO, I can achieve this using
               Files.walk(path).sorted(Comparator.reverseOrder()).forEach(Files::delete)
               to ensure files are deleted before their parent folders.

              */
//                  Files.walk(path)
//                          .sorted(Comparator.reverseOrder())
//                          .forEach(path1 -> {
//                              try {
//                                  Files.deleteIfExists(path1);
//                              } catch (IOException e) {
//                                  throw new RuntimeException(e);
//                              }
//                          });
            // Let's explore real time scenario, delete a ".txt" files from provided directory which is
            // more than 30 days older
            System.out.println("deleting file older than 2 minute having .txt extension.......");

            String fileExtension = ".txt";
            long minuteOld = 2;
               if(Files.exists(path) && Files.isDirectory(path)){

                          try (Stream<Path> fileToWalk=Files.walk(path)){
                              fileToWalk
                                      .filter(Files::isRegularFile)
                                      .filter(p->p.toString().endsWith(".txt"))
                                      .forEach(path1 -> {
                                          // check files last modified date
                                          try {
                                              //Represents the value of a file's time stamp attribute. For example, it may represent the time that the file was last modified, accessed, or created.
                                              //Instances of this class are immutable.
                                              FileTime lastModifiedTime = Files.getLastModifiedTime(path1);
                                              long diffInMinutes =
                                                      TimeUnit.MINUTES.convert(
                                                              System.currentTimeMillis() - lastModifiedTime.toMillis(),
                                                              TimeUnit.MILLISECONDS);
                                              if(diffInMinutes>minuteOld){
                                                  Files.deleteIfExists(path1);
                                                  System.out.println("Deleted old files: "+path1);
                                              }

                                          } catch (IOException _) {
                                              System.out.println("Unable to delete files as all files modified within last 30 days");
                                          }
                                      });
                          }
               }

               // 7️⃣ Advanced / Special Operations
               /* We can also define POSIX-style permissions: rw-r-----
                  It’s optional, but if you use it, you can specify things like:
                  Who can read/write/execute the file (on POSIX systems)
                  Custom attributes like creationTime, etc. (though mostly permissions are used)

                  Bonus: | Point          | Description                                                                |
                         | -------------- | -------------------------------------------------------------------------- |
                         | 🪟 Windows     | Doesn’t fully support POSIX permissions → this parameter is often ignored. |
                         | 🐧 Linux/macOS | Works perfectly. You can use `PosixFilePermissions` safely.                |
                         | `rw-r-----`    | Equivalent to `chmod 640` (Owner: read/write, Group: read, Others: none).  |

                         | String        | Meaning               | chmod equivalent |
                         | ------------- | --------------------- | ---------------- |
                         | `"rw-------"` | Owner read/write only | `600`            |
                         | `"rw-r--r--"` | Everyone can read     | `644`            |
                         | `"rwxr-xr-x"` | Executable by all     | `755`            |



                */
                  //
                  FileAttribute<?> attrs = PosixFilePermissions.asFileAttribute(
                                    PosixFilePermissions.fromString("rw-r-----")
                                  );  // On Windows, this parameter is often ignored because NTFS handles permissions differently.
                  //don’t specify a directory,
                  //Java automatically creates the file in the system’s default temporary directory.
                  // find that default directory & check whether temp exists there or not with given name
                  String tempDir = System.getProperty("java.io.tmpdir");
                  System.out.println(tempDir);

                 Path defaultTempDir=Paths.get(tempDir);
                 Path tempFile = defaultTempDir.resolve("HelloJava.tmp");
            try (var listOfTempFiles=Files.list(defaultTempDir)) {
                listOfTempFiles.filter(file->file.getFileName().toString().startsWith("HelloJava_"))
                               .forEach(System.out::println);

                                   // Create temp file if it doesn't exist
                                   if (Files.notExists(tempFile)) {
                                       try {
                                           Files.createFile(tempFile);
                                           System.out.println("✅ Temp file created: " + tempFile);
                                           String writeOnTempFile = """
                                                            This temp file has been created to store your most recently used cache
                                                           to improve your application experience to next level.
                                                           Need not to worry!
                                                       """;

                                           Files.writeString(tempFile, writeOnTempFile,StandardOpenOption.CREATE);
                                           System.out.println("✅ Content written successfully inside temp file!");
                                       } catch (IOException e) {
                                           System.out.println("There is a problem occur while creating a tmp file inside default directory.......");
                                       }

                                   } else {
                                       System.out.println("⚠️ Temp file already exists, append another info " + tempFile);
                                       String anotherInfo= """
                                               We already have a temp file inside so just append it after previous data. 
                                               """;  // we can write it dynamically by calling data from DB or from any third party API call.
                                       Files.writeString(tempFile,anotherInfo,StandardOpenOption.APPEND);

                                   }



            }
        }
        /*
          Efficient ways to read images, videos, pdf, etc. files as a binary
          files (images, PDFs, videos, etc.) → Must be read as bytes, because text readers will corrupt the data.
          In NIO, the core methods are:
           Files.readAllBytes(Path) → Reads entire file into a byte[]
           Files.newInputStream(Path) → Gets an InputStream for reading
           Files.newByteChannel(Path) → For advanced NIO operations

         Binary files can be handled using bytes, and NIO provides multiple ways to write them to disk:
          Files.copy(source, target, CopyOption...) → copies the file directly
          Files.write(target, byte[]) → writes bytes read from the source
          Files.newInputStream() / Files.newOutputStream() → for large files in chunks
         */


        Path imagePath = path.resolve("squirrel_image.jpg");
        try {
            // Read all bytes
            byte[] imageBytes = Files.readAllBytes(imagePath);
            System.out.println("Image size in bytes: " + imageBytes.length);

            // Optionally, you can save it somewhere else
            Path desiredDir = Paths.get("D:\\");
            Path copyPath = desiredDir.resolve("squirrel_" + LocalDate.now());
            Files.write(copyPath, imageBytes);
            System.out.println("Image copied successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
