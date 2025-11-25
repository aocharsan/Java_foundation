package exceptionHandling;

import java.io.BufferedReader;
import java.io.FileReader;

public class TryWithResources   {

	public static void main(String[] args)  {
		/*
		  try-with-resources?
		    from java 1.7 version, when the try block ends, resources will be closed automatically.
		    There is no need to create separate finally block. It internally uses AutoClosable close()
		    method to close resources automatically(need not to be provide implementation);
		    
		     try-with-resources is available to any class that implements AutoClosable interface. 
	
		  */
		try(BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\aocha\\OneDrive\\Desktop\\All@GitHub.txt"))){
			
			String line;
			while((line=br.readLine())!=null) {
				System.out.println(line);
			}
			
		}catch(Exception ex) {
			ex.getMessage();
		}

		/*
		throw → throws one exception object
        throws → declares one or more exceptions in method signature

        finally block:
        Executes always, whether exception occurs or not.
        Useful for resource cleanup (like closing files, DB connections).

        Chained exceptions:
        Wrap one exception inside another using constructor:
        new IOException("Failed", causeException);

        Rules of Exception Inheritance:
        1️⃣ Catch order matters
          Subclass exception must come before superclass exception in catch blocks
          Otherwise → compiler error

         try {
           int[] arr = new int[5];
           System.out.println(arr[10]);
           } catch (Exception e) {
           System.out.println("Exception caught");
          }
          // catch (ArrayIndexOutOfBoundsException e) { ... } ❌ Compiler error


         ✅ Correct order:

         try {
          int[] arr = new int[5];
          System.out.println(arr[10]);
           } catch (ArrayIndexOutOfBoundsException e) {
          System.out.println("Array out of bounds");
           } catch (Exception e) {
          System.out.println("General exception");
        }

         2️⃣ Method overriding rules for exceptions -->
          Overriding method can only throw the same or narrower checked exceptions than the parent method
          Unchecked exceptions can be thrown freely

          class Parent {
              void m() throws IOException {}  // Checked exception
            }

          class Child extends Parent {
            void m() throws FileNotFoundException {} // ✅ Narrower, allowed
           // void m() throws Exception {} ❌ Broader, compile error
            }


         For unchecked exceptions (RuntimeException), any subclass can throw them

         3️⃣ Multiple inheritance (catching multiple exceptions)
          Java doesn’t support multiple inheritance for classes, but you can catch multiple exceptions using
          | (Java 7+)
          Rules:
          Exceptions must not have parent-child relationship
          Otherwise → compiler error

           try {
           // some code
           } catch (IOException | SQLException e) { // ✅ OK
            e.printStackTrace();
           }
        // catch (Exception | IOException e) ❌ Compiler error

         Both Error and Exception are subclasses of Throwable. The key difference lies in their nature,
         recoverability, and how we handle them.
         Basically, error is nothing but system level problem occurs in JVM like outOfMemoryError when memory
         runs out, StackOverFlowError due to infinite recursion, etc. Errors are unchecked exception & as a developer
         we need not to handled it.






		 */

		
		
		
	}


	
	
}
