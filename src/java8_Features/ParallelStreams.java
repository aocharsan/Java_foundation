package java8_Features;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import utilsClasses.Student;

public class ParallelStreams {
	public static void main(String[] args) {
		  System.out.println(Runtime.getRuntime().availableProcessors());
		  System.out.println(Runtime.version());
		  
		  // How to test a stream pipeline parallelism?
		  ArrayList<Integer> listOfIntegers=new ArrayList<>();
		  listOfIntegers.addAll(List.of(12, 23, 33, 35, 18, 67, 98, 34, 57, 98));
		  
		  /* parallel streams---> Splits the stream into multiple substreams and processes them in parallel 
		                                   using the Fork&Join framework. order of execution is not under control.
		                                   so, it is advicible to use it only when order of execution of threads doesn't matter
		                                   & state of one element does not affects other result.
		  */
		  
		  
		  // check performance of parallel streams
		 Long start=System.currentTimeMillis();
		List<Student> students=Student.StudentUtils.getAllStudents();
		students.stream()
		            .parallel()
		            .filter(stu->{
		              System.out.println("Filtering  "+ stu.getFirstName()+" Processed by thread: "+ Thread.currentThread().getName() );
		              return stu.getAge()<30; })
		            .map(stu->{
		              System.out.println("Mapping  "+ stu.getFirstName()+" Processed by thread: "+ Thread.currentThread().getName());
		              return stu.getFirstName();})
		            .forEach(stu->{
		            	System.out.println("Processed by thread: "+ Thread.currentThread().getName());
		            	System.out.println(stu);});
		 Long end=System.currentTimeMillis();
		 System.out.println("Time taken by parallel stream in millis: "+(end-start));
		  
		  /* 
		 Filter:  Filtering  Roshan Processed by thread: ForkJoinPool.commonPool-worker-3
		 map: Mapping  Roshan Processed by thread: ForkJoinPool.commonPool-worker-3
         forEach: Processed by thread: ForkJoinPool.commonPool-worker-3

		   */
		  
		  
	       Stream<Integer> streamOfInts=listOfIntegers.stream()
		                       .parallel()  // Here stream pipeline converts to parallel stream
		                       .map(num->num*2)
		                       .sequential()  // again converts to sequential stream
		                       .filter(num->num>10);
		  
		    System.out.println(streamOfInts.isParallel());
		    
		    
		    
		  
		  
	}

}
