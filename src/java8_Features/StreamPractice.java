package java8_Features;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamPractice {
	public static void main(String[] args) {
		/*
		 represent group of objects as a single entity --> Collection
		 to process objects from collection, an array, I/O channels  --> Stream API
		 
		 Stream API: It provides a powerful & efficient way to process data from source
		 like collection, array, I/O channels etc. in a functional & declarative manner by
		 using Lambda's or method references.
		   
		 Stream: sequence of elements from data source which supports data processing
		 operations provided by Stream API.(stream doesn't modify data source objects just 
		 process it to get desired output)  	 

		 Streams can be obtained in a number of ways. Some examples include:
         From a Collection via the stream() and parallelStream() methods
         From an array via Arrays.stream(Object[])
         From static factory methods on the stream classes, such as Stream.of(Object[]),
          IntStream.range(int, int) or Stream.iterate(Object, UnaryOperator)
         The lines of a file can be obtained from BufferedReader.lines()


		 Stream Interface: contains all intermediate & terminal operations.
		 Collectors : Utility class having all static methods useful for reduction operations.
		 like collecting stream as a collection, summing, grouping as per requirement.
		 
		 what is Lambda function?
		  It is an anonymous function(i.e. having no access modifier, return type or not even a method name)
		  Lambda's syntax consist of three parts: list of parameters, an arrow mark & a body.
		     It enables functional programming so code become more concise & readable.
		      To invoke Lambda, we need Functional Interface.
		       Summation sum=(a,b)->a+b;
		       sum.add();

        *** Using Lambda's, don't need to use any separate implementation class or anonymous inner class.

        Why there is IntStream, LongStream, etc. provided by stream API.
        Stream<Integer> stream = list.stream();
        But here’s a problem — Stream<Integer> deals with wrapper types (Integer), not primitive types (int).
        And every time a primitive int is converted to Integer, boxing and unboxing happens:

        Boxing → converting int → Integer
        Unboxing → converting Integer → int

        This adds unnecessary overhead in both memory and CPU performance when working with large numeric data.
             “Java provides IntStream, LongStream, and DoubleStream as specialized primitive streams to avoid
              the performance overhead of boxing and unboxing in Stream<Integer>, Stream<Long>, etc.
              They are more memory efficient and come with built-in numeric methods like sum() and average().
              For example, using IntStream.range(1, 100) is faster than creating a Stream<Integer> for the same range.”
                   
		 */
		
		//1. creating streams
		//From collection Arrays.asList();
		//Arrays.asList() returns a fixed-size list backed by the original array.
		//It is partially mutable:
        //✅ You can modify elements (set() works).
        //❌ You cannot change the size (add() or remove() will throw UnsupportedOperationException).

		List<String> list = Arrays.asList("David Karl", "Willey Cook", "Shane Watson","Jos Buttler");
		Stream<String> stream = list.stream();

		// creates a fixed-size immutable list with exactly the elements provided.
        // Introduced in Java9, immutable list (i.e. can't support add/remove/set) also not allows null
		// otherwise throws NullPointerException
		List<String> cricNames = List.of("Sachin", "MSD", "Jadeja");

		// From Arrays
		String[] arr = { "Ramesh", "Ganpat", "Ajinkya" };
		Stream<String> stream2 = Arrays.stream(arr);

		// using Stream.of()
		//Stream<String> stream3 = Stream.of("Champak", "Ram");

		// Infinite Streams
		Stream<Integer> stream4 = Stream.generate(() -> 1).limit(100);
	    stream4.forEach(System.out::println);

	    //	Stream.iterate(0, x -> x + 1).limit(20).forEach(System.out::println);
		
		
		//2. count of elements in a stream?
		
		System.out.println(stream.count());
		
		//3. filter even numbers from a list?
		List<Integer> intList=Arrays.asList(1,7,5,56,89,23,44,78,56,12,45,76,23);
		List<Integer> intList1=Arrays.asList(90,68,29);
		List<Integer> intList2=Arrays.asList(101,34,567,7);
		
		List<Integer> evenNums=intList.stream().filter(n->n%2==0).toList();
		//evenNums.add(67);  throws java.lang.UnsupportedOperationException as toList() is unmodificable.
		System.out.println(evenNums);
		
		//4. transform a list of strings to uppercase using streams?
		
		list.stream()
			.map(String::toUpperCase)
			.forEach(System.out::println);
		
		//5. sort a list of numbers using streams?
		intList.stream().sorted(Comparator.reverseOrder())
		                .skip(3)
		                //.limit(5)
		                .forEach(System.out::println);
		
		//6. find the first element of a stream?
	     Optional<String> firstName=list.stream()
		                               .sorted(Comparator.reverseOrder())
		                               .findFirst();
	      System.out.println(firstName.get());
	      
	     //7. check if all/any elements in a stream match a given condition?
	     boolean isAnyStartWithD=list.stream()
	    		           .anyMatch(s->s.startsWith("D"));
	                     // .allMatch(s->s.startsWith("D"));
		  System.out.println(isAnyStartWithD);
		  
		  //8. remove duplicate elements from a list using streams?
		List<Integer> integers = List.of(34, 56, 78, 98, 45, 67, 56, 45, 89, 56);
		List<Integer> uniqueInts=new ArrayList<>();
		for(Integer num:integers){
			if(!uniqueInts.contains(num)){
               uniqueInts.add(num);
			}
		}
        //System.out.println("Unique integers "+uniqueInts);
		integers.stream().distinct().forEach(System.out::println);



		//9. find the sum of a list of integers using streams?
		  // Convert Stream<Integer> into IntStream ---> mapToInt(Integer::intValue) and for reverse used boxed()
		  int totalSum=intList.stream().mapToInt(Integer::intValue).sum();
		  System.out.println(totalSum);
		  
		  //10. find the maximum and minimum values in a stream?
		      OptionalInt maxValue= intList.stream().mapToInt(Integer::intValue).max();
		      System.out.println(maxValue.getAsInt());
		      
		  //11. find the average of numbers in a list using streams?
		   OptionalDouble avgValue=intList.stream().mapToInt(Integer::intValue).average();
		   System.out.println(avgValue.getAsDouble());
		   
		   // 12. concatenate multiple lists into a single stream?
		   // Convert List<List<Integer>> into List<Integer> ----> flatMap(List::stream)
		     Stream.of(intList,intList1,intList2)
		           .flatMap(List::stream)
		           .toList();
		   
		   StreamPractice.concat(intList,intList2,intList1);
		   
		   Stream.concat(intList1.stream(), intList2.stream()).toList();
		   
		 // 13. group a list of numbers into even and odd using streams?  
		   Map<Boolean, List<Integer>> partioningEvenOdd = intList.stream().collect(Collectors.partitioningBy(n->n%2==0));
			//partioningEvenOdd.entrySet().forEach(System.out::println);
		   //use Collectors.groupingBy with a classifier function that returns "EVEN" or "ODD" based on the number's parity.
		   Map<String, List<Integer>> groupingEvenOdd = intList.stream()
				              .collect(Collectors.groupingBy(n->n%2==0 ? "Even":"Odd"));
		   System.out.println(groupingEvenOdd.entrySet());
		   
		 // 14. second highest number in a list using streams?  
		 Optional<Integer> SecondMax=intList.stream()		      
		                                    .sorted(Comparator.reverseOrder())
		                                    .skip(1)
		                                    .findFirst();
		 System.out.println(SecondMax.get());
		 
		 // 15. remove null values from a list using streams? compare Object vs Objects
		     list.stream()
				 .filter(Objects::nonNull)
		         .forEach(System.out::println);
		        
		 //16. concatenate all strings in a list into a single string using streams?
		 
		 
		 
		 //17. Filter a list to find all prime numbers.
	   List<Integer> listOfPrimeNums=intList.stream()
			                                .filter(StreamPractice::isPrime)
			                                .toList();
	                  System.out.println(listOfPrimeNums);
	
	     //18. List down prime number betin 1 to 100             
	     List<Integer> primes = IntStream.rangeClosed(1, 100)
		                                               .filter(StreamPractice::isPrime)
		                                               .boxed()
		                                               .collect(Collectors.toList());
	                System.out.println(primes);
	                
	                
	      //19. convert a list of strings into a map of string-length pairs?
	         Map<String,Integer> mapOfStringNLengths= list.stream().collect(Collectors.toMap(Function.identity(), String::length));
		          System.out.println(mapOfStringNLengths);
		          
		       //   Remove All Whitespaces & collect again/count length.   
		          
		          String name="Hello I am Nirodkar      ";
		                     String nameWithNoSpaces=name.chars()
									                     .filter(ch->!Character.isWhitespace(ch))
									                     .mapToObj(ch->String.valueOf((char)ch))
									                     .collect(Collectors.joining());
		                     System.out.println(nameWithNoSpaces);
		                                                 
		 //20. get distinct characters from a list of strings?
		  list.stream().flatMapToInt(String::chars)
		                   .mapToObj(ch->(char)ch)
		                   .filter(ch->!Character.isWhitespace(ch))
		                   .map(Character::toLowerCase)
		                   .distinct()
		                   .forEach(System.out::print);
		          
		          
		          
		          
		          
		          
		          
		          
		          
		          
	}
	
	@SafeVarargs
	public static <T> List<T> concat(List<T>... lists) {
	    return Stream.of(lists).flatMap(List::stream).collect(Collectors.toList());
	}
	
	public static boolean isPrime(int number) {
		if(number<=1) {return false;}
		return IntStream.rangeClosed(2,(int) Math.sqrt(number))
				                 .allMatch(num->number%num!=0);
	}

}
