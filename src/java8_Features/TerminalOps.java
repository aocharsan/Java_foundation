package java8_Features;

import utilsClasses.Employee;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TerminalOps {
	public static void main(String[] args) {

		/* list of terminal operations
		return -- boolean
		allMatch(Predicate<? super T> predicate) 
		anyMatch(Predicate<? super T> predicate)
		noneMatch(Predicate<? super T> predicate)
		
		return -- long
		count()
		
		return -- Optional<T>
		max(Comparator<? super T> comparator)
		min(Comparator<? super T> comparator)
		findAny()
		findFirst()
		reduce(BinaryOperator<T> accumulator)	
		
		forEach(Consumer<? super T> action)	
        collect(Collector<? super T,A,R> collector)
        collect(Supplier<R> supplier, BiConsumer<R,? super T> accumulator, BiConsumer<R,R> combiner)
		*/

		List<Employee> employees = Employee.EmployeeUtils.getEmployees();

		// 1. collect
		List<Integer> list = Arrays.asList(23,54,67,89,12,33,18);
		list.stream().collect(Collectors.toList());
		list.stream().toList();


		// 2. forEach
		//list.stream().forEach(System.out::println);

		// 3. reduce: combines elements to produce a single result.
		Optional<Integer> reduce = list.stream().reduce(Integer::sum);
		System.out.println(reduce.get());
		
		System.out.println("---------------------------------------");
		
		String[] arr= {"Samson","JP Morgan","David Willey","San Francis"};
		Stream<String> streamOfStrings = Arrays.stream(arr);
		//4. Collecting to a specific collection
	    streamOfStrings.collect(Collectors.toCollection(()->new ArrayDeque<>()));
		
	
		//convert Arrays into streams
		Stream<String> arrayToStream= Arrays.stream(arr);
		arrayToStream.sorted((a,b)->b.length()-a.length())
		             .filter(e->e.endsWith("n"))
		             .forEach(e->System.out.println(e));
		
		System.out.println("---------------------------------------");
		
	    Stream<String> immutableStreams=Stream.of("James","Carl","Michel","Reynold");  // internally calls Arrays.stream()
	    immutableStreams.sorted()                    	    
	                    .forEach(System.out::println);
		
	    // for primitive types
	    int [] arr1= {1,34,67,89,13,90,45};
	      IntStream intStream= Arrays.stream(arr1);
	      intStream.filter(n->n%2==0)
	               .sorted()
	               .forEach(System.out::println);
	      
	    System.out.println("---------------------------------------");
	      
	    Stream<int[]> intStream1 = Stream.of(arr1);
	    //intStream1.forEach(e->System.out.println(e[1]));
	    
	    //flatten Stream<int[]> ---> IntStream
	    /*
	      IntStream --> Stream of primitive int values
	      Stream<Integer> --> stream of Integer object
	      basically, Stream<Integer> operates on boxed values which takes significantly more 
	      memory & usually a lot of boxing/unboxing behind the scene.
	      
	     */
	    IntStream flatMapToInt = intStream1.flatMapToInt(Arrays::stream);
	     OptionalDouble avg= flatMapToInt.filter(n->n%2!=0).average();
	     System.out.println(avg.getAsDouble());
	     
	     System.out.println("---------------------------------------");
	    // IntStream.range(1, 10).forEach(System.out::println);
	     IntStream.rangeClosed(2, 100)
	              .filter(n->n%2==0)
	              .forEach(System.out::println);
	   
	    //for checking whether given number is prime or not
	     
	     System.out.println(TerminalOps.isPrime(66));


		// Accumulate names into a List
		List<String> getEmployeeNames =employees.stream()
			             	.map(Employee::getName)
				            .toList();

		System.out.println("Accumulate employee names into a List: "+getEmployeeNames);

        // Accumulate names into a TreeSet
		Set<String> set = employees.stream()
				.map(Employee::getName)
				.collect(Collectors.toCollection(TreeSet::new));
		System.out.println("Accumulate employee names into a TreeSet: "+set);

        // Convert elements to strings and concatenate them, separated by commas
		String joined = employees.stream()
				.map(Employee::getName)
				.collect(Collectors.joining(", "));
		System.out.println("Convert elements to strings and concatenate them, separated by commas: "+joined);

        // Compute sum of salaries of employee
		double total = employees.stream()
				.collect(Collectors.summingDouble(Employee::getSalary));
		System.out.println("Compute sum of salaries of employee :"+total);

        // Group employees by department
		Map<String, List<Employee>> byDept = employees.stream()
				.collect(Collectors.groupingBy(Employee::getDepartment));
		System.out.println("Group employees by department: "+byDept);

		// Compute sum of salaries by department
		Map<String, Double> totalByDept = employees.stream()
				.collect(Collectors.groupingBy(Employee::getDepartment,
						Collectors.summingDouble(Employee::getSalary)));
		System.out.println("Compute sum of salaries by department :"+totalByDept);

        // Partition students into passing and failing
		Map<Boolean, List<Employee>> passingFailing = employees.stream()
				.collect(Collectors.partitioningBy(s->s.getAge()>35));
		System.out.println("Partition students into passing and failing :"+passingFailing);






	}
	
	public static boolean isPrime(int number) {
	    return IntStream.rangeClosed(2, number/2).noneMatch(i -> number%i == 0);
	}

}
