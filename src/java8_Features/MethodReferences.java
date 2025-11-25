package java8_Features;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import utilsClasses.Employee;
import utilsClasses.HighSalariedEmployees;

public class MethodReferences {
	
	public void getMessage(){
          System.out.println("Dear friend, you need to study Hard !");
    }   
    
    public static void getAlarm() {
		System.out.println("Need to study anyhow no matter how painful it is !");
		
	}
	
	public static void main(String[] args) {
		
		
		   List<Employee> employees=Employee.EmployeeUtils.getEmployees();
		
		/*
		 What is method reference?
		 It is a special type of lambda expression used to create simple expression by
		 referring to an existing methods by name using :: operator.
		 Functional Interface abstract method can be mapped to specific existing method
		 using method reference operator.
		 
		 Types of method references:
		 1) Referring to static method -->
		        className::methodName
		        
		  2) Referring to an Instance Method of a Particular Object -->   
		         object::instanceMethod  ===> new MyClass()::getMessage
		            Use when we need to call a method on a specific object.
		         
		   3) Referring to an Instance Method of an Arbitrary Object of a Particular Type    
		            ClassName::instanceMethod
		               Use when we're calling the method on each object as it comes in,
		            like from a stream or list.
		    
		    4)  Referring to a Constructor   
		          ClassName::new      
		 */
		
		SayEverything say=MethodReferences::getAlarm;
		say.sayNamah();
		
		//MethodReferences myclass;
	     SayEverything sayAgain=new MethodReferences()::getMessage;
	     sayAgain.sayNamah();
	     
	     
	    // String::compareToIgnoreCase --> refers to the instance method toUpperCase of any object of type String.
	     String[] cities= {"Jalandar","Bhantinda","Chandigarh","Amritsar","Shree Nagar","Jalandar"};
	     List<String> upperCities= Arrays.stream(cities).map(String::toUpperCase).collect(Collectors.toList());
	     
	    
	     Set<String> uniqueCities=upperCities.stream()
				                             .sorted(Comparator.reverseOrder())
				                             .collect(Collectors.toCollection(LinkedHashSet::new));
	     for(String city:uniqueCities) {
	    	 System.out.println(city);
	     }
	     
	     // To use constructor references with multiple parameters
	     
	    List<HighSalariedEmployees> highSalEmps=employees.stream()
	                                                      .filter(e->e.getSalary()>30000)
				                                          .map(e-> new HighSalariedEmployees(
	                                                                            		               e.getId(),
																		                               e.getName(),
																		                               e.getDepartment())
														  ).toList();
	     highSalEmps.forEach(System.out::println);
	                   
	                   
	     
	    
	
	}

}

@FunctionalInterface
interface SayEverything{
	void sayNamah();
}
