package exceptionHandling;

import utilsClasses.Amount;

public class UncheckedExceptionHandling {

	/* 
	  What is unchecked Exception?
	  All exception which extends RuntimeException class.
	  Compiler never force programmer to catch such exception nor force to
	  declare it to method's declaration using throws keyword.
	     e.g. IndexOutOfBoundEx -- ArrayIndexOutOfBoundEx/StringIndexOutOfBoundEx
	          ClassCastEx
	          ArithmeticEx
	          NullPointerEx
	          NumberFormatEx -- IllegalArgEx

	          optional try catch as we can throw exception object anonymously.
	          It is good practice to handle exception to make application more robust & reliable.

	          How to handle unchecked exceptions:
	           1) optional try-catch block
	           2) preventive check i.e. if(str!=null)
	           3) proper design/ validations --> validate method argument before using, use generics for collection


	
	  */
	
	
	public static Amount addAmount(Amount amount1, Amount amount2)  {

		if (!amount1.getCurrency().equals(amount2.getCurrency())) {

	   //as there is no compilation as it is unchecked type so we can throw exception object anonymously.
			throw new RuntimeException("Currencies don't match"); 
		}

		return new Amount(amount1.getCurrency(), amount1.getAmount() + amount2.getAmount());

	}
	
	
	public static void main(String[] args) {
		
		addAmount(new Amount("Dollar",234), new Amount("Rupee",345));
	}
	
	
}
