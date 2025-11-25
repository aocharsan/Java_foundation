package exceptionHandling;

import utilsClasses.Amount;

public class CheckedExceptionHandling {
	
	
	
	/* 
	   What is Exception:
	     Except is an abnormal condition which occurs during the execution of a program
	     & disrupts the normal flow of execution. If not handled properly, it will terminate
	     program abruptly.
	        Arises mostly due to:
	             Bad input
	             Unavailability of resources
	             
	  
      java.lang.Throwable
├── java.lang.Error           // Serious system errors
│    ├── OutOfMemoryError
│    ├── StackOverflowError
│    └── VirtualMachineError
└── java.lang.Exception       // Application exceptions
     ├── Checked Exceptions
     │    ├── IOException
     │    │    ├── FileNotFoundException
     │    │    └── EOFException
     │    ├── SQLException
     │    ├── ClassNotFoundException
     │    └── InterruptedException
     └── Unchecked Exceptions (RuntimeException)
          ├── ArithmeticException
          ├── NullPointerException
          ├── ArrayIndexOutOfBoundsException
          ├── IllegalArgumentException
          │    └── NumberFormatException
          └── ClassCastException

        
	   How JVM Handles Exception:
	         Exception is thrown
	             The JVM creates an object of the appropriate exception class. 
	         JVM searches for a matching `catch` block in the current method
	         If not found, JVM goes up the call stack.
	           This continues up the call stack until:
                         A appropriate handler is found, or
                         The main method is reached
	         If still not found, JVM terminates the program and prints a stack trace
	   
	             
	     
	   The method in which exception occurs is responsible for creating exception object.
	   All exceptions occur at runtime only.
	   Exception Stack include:
	       Name of exception
	       description
	       stack-trace (location)

	   What is checked Exception?
	    Exception which are check by compiler for a smooth execution of program at runtime.
	    whether the programmer handling it or not such type of checking is going to be done by compiler.
	      e.g.  IOEx --> FileNotFoundEx
	            ClassNotFoundEx
	            InterruptedEx
	            SQLEx
	            ParseEx
	  
	 
	     declaring given method would throw exception 
	     caller method need to be handled it either using try-catch block or propagate up to call stack using throws
	     as we can't throw exception object anonymously as it is checked type.

	     How JVM handles checked exception:
	     step 1: When exception occurs the JVM creates an exception object.
	     step 2: JVM looks for a matching catch block.
	     step 3: Propagation up to the call stack.  Suppose, occurs at sayNamoh() ---> sayHello ---> main()
	     step 4: looking for default exception handler and print the exception stackTrace & also terminate the
	             execution of a program.


	*/
	
	
	public static Amount addAmount(Amount amount1, Amount amount2) throws Exception {

		if (!amount1.getCurrency().equals(amount2.getCurrency())) {

			throw new Exception("Currencies don't match");
		}

		return new Amount(amount1.getCurrency(), amount1.getAmount() + amount2.getAmount());

	}
	
	
	/*
	 1)  Handle using throws keyword in method declaration 
	 
     public static void main(String[] args) throws Exception {
		addAmount(new Amount("Dollar",234), new Amount("Rupee",345));
	}
	*/
	
	/*
	 2) Handle using surround try-catch block
	public static void main(String[] args) {
	
		try {
			addAmount(new Amount("Dollar", 234), new Amount("Rupee", 345));
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}   */
	
}
