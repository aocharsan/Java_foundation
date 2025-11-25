package java8_Features;


import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import utilsClasses.Employee;


public class EmployeeStreamOperations {
	
	public static void main(String[] args) {
		List<Employee> employees = Employee.EmployeeUtils.getEmployees();
      
		// calculate avg salary in an organization

		OptionalDouble avgSalary = employees.stream().mapToDouble(Employee::getSalary).average(); // an empty optional
																									// if this stream is
																									// empty.

		Double avgSal = employees.stream().collect(Collectors.averagingDouble(Employee::getSalary)); // If no elements
																										// are present,
																										// the result is
																										// 0.
		System.out.println(String.format("%.2f", avgSalary.getAsDouble()));
		System.out.println(String.format("%.2f", avgSal));

		System.out.println("------------------------------------------------------");
		// calculate count of male & female employees
		Map<String, Long> maleFemaleRatio = employees.stream()
				                                     .collect(Collectors.groupingBy(Employee::getGender, 
				                                    		                            Collectors.counting()));

		maleFemaleRatio.entrySet().forEach(System.out::println);

		System.out.println("------------------------------------------------------");
		
		//sort employee first byName & then location
		
		employees.stream()
		         .sorted(Comparator.comparing(Employee::getName).thenComparing(Employee::getDepartment));
		        // .forEach(System.out::println);
		
		 employees.stream()
                  .sorted((e1,e2)->{
                     int sortByname = e1.getName().compareToIgnoreCase(e2.getName());
                      // Sorting by department name if name is same.
                     return sortByname == 0 ? e1.getDepartment().compareToIgnoreCase(e2.getDepartment()) : sortByname;
                       }).forEach(System.out::println);
		 
		 System.out.println("------------------------------------------------------");
		 //find name of all departments in the organization?
		 employees.stream()
		          .map(Employee::getDepartment)
		          .distinct()
		          .forEach(System.out::println);
		 
		 System.out.println("------------------------------------------------------");
		  // find average age of male and female employees?
         Map<String,Double>	avgAgeByGender=employees.stream()
		                                            .collect(Collectors.groupingBy(Employee::getGender,
		        		                                       Collectors.averagingDouble(Employee::getAge)));
         avgAgeByGender.entrySet().forEach(employee-> System.out.println(employee.getKey()+"  "+ String.format("%.2f", employee.getValue())));

         System.out.println("------------------------------------------------------");
         
         //Get the details of highest paid employee in the organization?
        Optional<Employee> highPaidEmployee= employees.stream()
                                                      .max(Comparator.comparing(Employee::getSalary));
        System.out.println(highPaidEmployee.get());
        
        
        Optional<Employee> highPaidPerson=employees.stream()
                                                   .collect(Collectors.maxBy(Comparator
                                                		   .comparing(Employee::getSalary)));
        System.out.println(highPaidPerson.get());
        
        System.out.println("------------------------------------------------------");
 
        //find the names of all employees who have joined after 2015?       
         employees.stream()
                  .filter(e->e.getYearOfJoining()>2015)
                  .map(Employee::getName)
                  .forEach(System.out::println);
         System.out.println("------------------------------------------------------");
         
         //Count the number of employees in each department?
        Map<String,Long> countPerDept=employees.stream()
                                            .collect(Collectors.groupingBy(Employee::getDepartment,
                		                                                  Collectors.counting()));
       countPerDept.entrySet().forEach(System.out::println);
       System.out.println("------------------------------------------------------");
     
      //details of youngest male employee in the product development department?
      Optional<Employee> yougestEmployee=  employees.stream()
                                                   .filter(e->e.getGender().equalsIgnoreCase("Male") &&
            		                                        e.getDepartment().equalsIgnoreCase("Product Development")
            		                                  )
                                                   .sorted(Comparator.comparing(Employee::getAge))
                                                   .findFirst();
            System.out.println(yougestEmployee);
            
       System.out.println("------------------------------------------------------");
          //find employee with most working experience in the organization?
            employees.stream()
                     .sorted(Comparator.comparing(Employee::getYearOfJoining))               
                     .findFirst();
            
        System.out.println("-------------------------------------------------------");


        // List down the names of all employees in each department?
     Map<String,List<String>>  namesAllEmployeesPerDept=employees.stream()
                                                                .collect(Collectors.groupingBy(Employee::getDepartment,
                		                                                       Collectors.mapping(Employee::getName, 
                		 		                                                   Collectors.toList())));
     namesAllEmployeesPerDept.entrySet().forEach(System.out::println);
    
     System.out.println("------------------------------------------------");
    
     //find an avg salary of each department
     Map<String,Double> avgSalPerDept=employees.stream()
                                            .collect(Collectors.groupingBy(Employee::getDepartment,
            		                                Collectors.averagingDouble(Employee::getSalary)));
     avgSalPerDept.entrySet().forEach(System.out::println);

      System.out.println("------------------------------------------------");
		//Convert a list of Employee objects to a map with id as key and Employee as value.
       // also If your list may contain Person objects with duplicate ids, you need to handle key collisions:
         Map<Integer,Employee> convertToMap=employees.stream().collect(Collectors.toMap(
        		 Employee::getId,
        		 v->v,
        		 (existing, replacement) -> existing  ));// keep existing in case of duplicate keys      		 
          convertToMap.forEach((id,employee)->System.out.println(id+"  "+employee.getName()));
          
          System.out.println("------------------------------------------------");
          //Find the highest salary from a list of Employees for Each department.
             Map<String,Optional<Employee>>   highestSalPerDept=employees.stream()
                                              .collect(Collectors.groupingBy(Employee::getDepartment,
                        		              Collectors.maxBy(Comparator.comparing(Employee::getSalary))));
             highestSalPerDept.entrySet().stream().forEachOrdered(str->System.out.println(str.getKey()+"----->> "+str.getValue().get().getName()+"----->> "+str.getValue().get().getSalary()));
             
          /*
           Why is it not safe to modify a collection during stream processing?
           
            Suppose, if we try to modify source collection while processing it will throw ConcurrentModificationException.
            as collection like Arraylist, HashSet and many more are fail-fast iterator type.
            If the collection is changed outside of the iterator during traversal, an exception is thrown to prevent data inconsistency or corruption.
            to avoid use concurrent collection objects like CopyOnWriteArrayList
           * */
             System.out.println("------------------------------------------------");
             
           // Used Collectors.filtering method Java9 feature
             
           //Group employees by dept but includes those with salary > 20k
             
           Long startTime= System.currentTimeMillis();
        Map<String,List<String>> highEarnersFromEachDept= employees.stream()
                                                                            .collect(Collectors.groupingBy(Employee::getDepartment,
                        		                                               Collectors.filtering(
                        		                                            		   emp->emp.getSalary()>20000,
                        		                                            		   Collectors.mapping(Employee::getName, Collectors.toList()))));
         Long endTime=System.currentTimeMillis();
         System.out.println("Time taken using stream(): "+(endTime-startTime)+" in millis");
        highEarnersFromEachDept.entrySet().stream().forEachOrdered(str->System.out.println(str.getKey()+"----->> "+str.getValue()));
             
        System.out.println("------------------------------------------------");
             
          // Concatenate employees dept as a single String
        String allDept=employees.stream()
        		                             .map(Employee::getDepartment)
        		                             .distinct()
        		                             .collect(Collectors.joining("--"));
        System.out.println(allDept);
        
        System.out.println("------------------------------------------------");
       // Generates statistical summery about salaries from an organisation
      DoubleSummaryStatistics getSummery = employees.parallelStream().collect(Collectors.summarizingDouble(Employee::getSalary));
      System.out.println("Average Salary in an organisation :"+String.format("%.2f",getSummery.getAverage()));
      System.out.println("Maximum Salary in an organisation :"+getSummery.getMax());
      System.out.println("Minimum Salary in an organisation :"+getSummery.getMin());
        
        
          
          
          
          
          
          
          
          
          
          
          
          
	}

}
