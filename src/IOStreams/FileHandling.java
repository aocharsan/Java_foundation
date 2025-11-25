package IOStreams;

import java.io.*;

public class FileHandling {
    public static void main(String[] args) throws IOException {
        /*
          A Bytestream is a stream of data that handles I/O in bytes (8 bits at a time) rather than characters.
            Works at the lowest level — raw binary data.
            Can handle any type of file: text, images, audio, video, or even executable files.
            Classes for bytestreams are part of the java.io package.
              Key Point:
               Byte Stream = 8-bit streams → use InputStream / OutputStream e.g. Binary files like images, audio, video, PDFs
               Character Stream = 16-bit streams → use Reader / Writer e.g. text files
               Bytestream is useful for non-text files where characters may not make sense (like images or PDFs).

            InputStream (abstract class) → for reading bytes
             FileInputStream → read bytes from a file
             BufferedInputStream → read bytes efficiently with buffer
             DataInputStream → read Java primitive data types in binary

            OutputStream (abstract class) → for writing bytes
             FileOutputStream → write bytes to a file
             BufferedOutputStream → write bytes efficiently with buffer
             DataOutputStream → write Java primitive types in binary

      Rule: Use bytestream for any file, and character stream only for text files.
            When working with images, audio, PDF, or binary files, you must use Bytestreams.

       Why not always use Character Stream?
        Character streams convert bytes into characters using an encoding (UTF-8, UTF-16, etc.).
        Works perfectly for text files, but corrupts binary files (like images or audio) because the bytes may not represent valid characters.
          Example:
            An image file has byte values like 137, 80, 78, 71 (PNG header).
            Character stream may interpret these bytes as UTF-16 characters → data gets corrupted.
            Byte stream reads raw bytes, preserving original content.

       Why we convert native file format to ByteStream?
         use ByteStream to:
          Read/write exact bytes without any conversion.
          Preserve file integrity for binary data.
          Enable low-level manipulation like copying, compressing, or encrypting files.

       Bonus: How to read data from S3 bucket using latest AWS SDK v2 Maven dependency is non-blocking and modern — better than v1 (com.amazonaws.services.s3).
          Step 1: need to configure S3Client based on our region.
          Step 2: then build GetObjectRequest object by providing bucketName & key info.
                  after that use S3Client instance method getObject(requested) which returns InputStream directly.
                  After that need to read image as a ByteStream using S3ImageReader.readImageAsStream(bucket,key),
                  if required to be read byte stream into a BufferedImage using
                        BufferedImage image = ImageIO.read(imageStream);

             public class S3ImageReader {

                public static InputStream readImageAsStream(String bucketName, String key) {
                   S3Client s3 = S3Util.createS3Client();

                   GetObjectRequest request = GetObjectRequest.builder()
                                                              .bucket(bucketName)
                                                              .key(key)
                                                              .build();

                   // Returns an InputStream (byte stream)
                   return s3.getObject(request);
                    }
                 }

          ⚙️ Important Notes
               You must have correct AWS credentials configured:
                  Either via environment variables
                  Or using ~/.aws/credentials file
                  Or via AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY in your environment.

               InputStream from S3 should always be closed after usage (use try-with-resources).
               This approach works for any binary file, not just images.

           AWS SDK and ImageIO (standard Java) are available.
             use thumbnailator (a popular lightweight image-processing library).

             Why there is a nio file handling added in Java 8?(Initially introduced in Java1.4, major improvisation in Java8 )
               Enhancement: java.nio.file in Java 7 and 8
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





         */

        // reading & writing on text file using PrintWriter & PrintReader class
        FileWriter fileWriter=new FileWriter("expenses.txt");
        fileWriter.write("Hello, I am going to maintain my expenses soon!");
        fileWriter.close();
        FileReader fileReader=new FileReader("expenses.txt");
        BufferedReader br=new BufferedReader(fileReader);
        String data= br.readLine();
        System.out.println(data);


        // Copying jpg file using ByteStream

        String path="C:\\Users\\aocha\\Downloads\\pexels-pixabay-460775.jpg";
        FileInputStream fis=new FileInputStream(path);
        FileOutputStream fos=new FileOutputStream("C:\\Users\\aocha\\Downloads\\newIMG.jpg");
        byte[] buffer = new byte[1024]; // buffer for efficiency
        int readIMGAsBinary;
        while ((readIMGAsBinary=fis.read(buffer))!=-1){
             fos.write(buffer,0,readIMGAsBinary);    // write exact bytes
        }
        fis.close();
        fos.close();

        System.out.println("Image copied through ByteStream successfully!");

    }
}
