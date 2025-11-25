package java8_Features;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;


public class IntermediateOps {

	public static void main(String[] args) {

		/* Intermediate ops transforms a stream into another stream
		   they are lazily loaded i.e. don't execute until terminal operation not
		   invoked.
		   
		   Intermediate vs Terminal ops:
		   1) I ops returns stream as a result, while T ops return non-stream values like primitive,
		   Object or collection or may not return anything.
		   
		   2) As I ops returns stream as a result , so they can be chained together to form a pipeline
		   of operations. T ops can't be chained.
		   
		   3) I ops are lazily loaded i.e. (Just store inside memory) not executed until T operation called
		   at the end of pipeline.
		   	   
		   filter(Predicate<? super T> predicate)
		   map(Function<? super T, ? extends R> mapper)
		   flatMap(Function<? super T, ? extends Stream<? extends R>> mapper)
		   distinct()
		   sorted(Comparator<? super T> comparator)
		   limit(long maxSize)
		   skip(long n)
		   takeWhile(Predicate<? super T> predicate)
		   dropWhile(Predicate<? super T> predicate) ............. so on

           Difference betin map() vs flatMap()
            In Java Streams, both map() and flatMap() are intermediate operations used to transform data,
            but they differ in how they handle nested structures.”

            map() → does one-to-one transformation. i.e. each input element becomes exactly one output element.
             e.g. 1 student--> 1 ID
            flatMap() → does one-to-many transformation. i.e. each input element may produce multiple outputs,
            and flatMap() flattens them into a single stream.
             e.g. 1 student --> may have List of subjects and can be flattened as a single list using flatMap()

		   Intermediate operations are further divided into stateless and stateful operations.
		   Stateless operations, such as filter and map, retain no state from previously seen element
		   when processing a new element --  each element can be processed independently of operations
		   on other elements.
		   Stateful operations, such as distinct and sorted, may incorporate state from previously seen
		   elements when processing new elements.

           Bonus: By default, the common ForkJoinPool (used by parallelStream(), CompletableFuture, etc.)
                  creates as many worker threads as the number of available CPU cores.
           🧠 What is ForkJoinPool?
              ForkJoinPool is a special thread pool introduced in Java 7 (in java.util.concurrent package).
              It is designed to execute tasks in parallel by splitting (forking) a big task into smaller
               subtasks and then combining (joining) their results.
              It uses the “Divide and Conquer” strategy for parallel processing — ideal for CPU-bound operations.

              🧩 Core Components
                ForkJoinPool → Manages worker threads (similar to ExecutorService).
                RecursiveTask<V> → Used when the task returns a result.
                RecursiveAction → Used when the task does not return a result.
		
		 */

		List<String> list = Arrays.asList("Ramadhir","ramadhiR", "Sardar", "Faizal","Ranveer","Ajinkya","Pushkar");
		list.stream().filter(x -> x.toLowerCase().endsWith("r"))
		             //.peek(x->System.out.println(x))		             
		             //.map(x->x.toUpperCase())
		             .map(String::toUpperCase)
		             .distinct()
		            // .sorted()    //sorted according to natural order.
		             .sorted((a,b)->a.length()-b.length())   //If the elements of this stream are not Comparable, a java.lang.ClassCastException may be thrown when the terminal operation is executed.
		             .limit(3)
		             .skip(1)
		             .forEach(System.out::println);


		int coresAvailable = Runtime.getRuntime().availableProcessors();
		System.out.println("Available CPU cores for my machine "+coresAvailable);

		int poolSize=ForkJoinPool.getCommonPoolParallelism();
		System.out.println("Total pool size of ForkJoinPool "+poolSize);


	}

}
