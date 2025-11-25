package collection;

import java.util.*;

public class ListDemo {

	public static void main(String[] args) {
		/*
		  Collection provides a ready-mate DS for storing & manipulating a group of
		  objects. Basically, there is Interfaces & Classes representing particular DS
		  & as per need we can use it. 
		  Iterable -- Top interface includes all iterating methods
		  Collection -- root Interface
		  List -- an ordered collection that can contain duplicates elements(e.g. ArrayList, LinkedList)
		  Set -- a collection that contain unique elements. 
		  Queue -- A collection designed for holding elements prior to processing(e.g.
		      PriorityQueue, LinkedList when use as a queue) 
		  Deque -- A double-ended queue that allows insertion & removal from both ends.(e.g. ArrayDeque)		  
		  Map -- An interface that represents a collection of key-value pair.
		 */

		List<Integer> intList = new ArrayList<>(100); // intial capacity 100 to avoid overhead
        List<String> name1=List.of("Jagan","Ram","Pavan");  //immutable list from Java 9
        // name1.add("Baban");  throws unsupportedOpsExcept
        // name1.set(1, "Krishna"); throws unsupportedOpsExcept
      
    
		String[] names = { "Sanket", "Param", "Shravan", "Rohit" };
		List<String> nameList = Arrays.asList(names); // Returns a fixed-size list backed by the specified array. 
 		// support set operation                                 // can't add or remove but support set()
		nameList.set(1, "Jagan");

//		System.out.println("can't add or remove in Arrays.asList()");
//		nameList.remove("Sanket"); // throws unsupportedOpsExcept
		System.out.println(nameList);

		List.of(1, 34, 5, 67, 89); // Returns an unmodifiable list

		/*
		  ArrayList: Implementing class of List interface used to store dynamic
		  collections of object. Unlike array, it can grow & shrink as element added
		  or removed. This dynamic resizing achieved by creating new array of 1.5 times
		  of current capacity. when the current array becomes full & it copies the
		  elements from old array to the new array.
		  
		  Time Complexity:
		  Access by index (get) -- O(1) 
		  adding element -- worst case O(n) when resizing occurs.
		  removing element -- O(n) bcz it may involve shifting of elements. iterating -- O(n)
		  
		  When we create an arraylist, it has initial capacity (default 10).
		  capacity:refers to size of internal array that can hold elements before needing to resize.
		  
		  adding element : check capacity --> resize if necessary --> add the element
		  removing element : check bounds (check if index is within valid range) -->
		  remove the element (shifting of elements) --> reduce size by 1
		  
		   Fail-fast vs Fail-safe Iterator:
		    A fail-fast iterator throws a ConcurrentModificationException if the collection is 
		    structurally modified (e.g., adding or removing elements) after the iterator is created,
		    except through the iterator’s own methods.
		           e.g. ArrayList, HashSet, HashMap
		           
		    A fail-safe iterator does not throw exceptions if the collection is modified during
		    iteration. Instead, it works on a clone or snapshot of the collection.
		       e.g. ConcurrentHashMap, CopyOnWriteArrayList, etc
		       
		    How Java know to throw ConcurrentModExcept?
		       It uses a transient variable modCount which keeps track of how many times
		    a collection objects modified internally. Supposed when we try to modify collection
		    objects by adding/removing while iterating then modCount change & it will not 
		    match with expectedModCount hence iterator will throw ConcurrentModificationExcpt.
		    
		    Solution: Use Iterator's own methods to safely modify collection in single-threaded
		    environment. or we can use Concurrent collection classes like CopyOnWriteArrayList, so on
		    in multithreaded env. (but we can't use iterators own method to modify)

		        DTO(Data Transfer Object)                         JPA Entity/Model
		    1) Used to transfer data between layers              Represents a table in the database.
		      (Controller → Service → Client).

		    2) Reducing exposure of internal DB structure,       Managing persistence and ORM mapping.
		       improving performance and security.                e.g. ClaimEntity
               e.g. ClaimRequestDTO, ClaimResponseDTO

            Why You Should Not Return Entity Directly
              Security: Entity might have sensitive fields like password, internalId, etc.
              Loose Coupling: Keeps persistence and presentation layers independent.
              Custom Response Shape: DTO can have computed or formatted fields that don’t exist in the Entity.

           ModelMapper Library advanced processing:
           service layer conversion from Entity to DTO --->
            modelMapper.map(source ref, destination class);

           Skip fields you don’t want mapped:
             modelMapper.typeMap(Employee.class, EmployeeDTO.class)
                        .addMappings(mapper -> mapper.skip(EmployeeDTO::setDepartment));

           Map nested objects automatically.
           Convert lists:
            List<EmployeeDTO> dtoList = employees.stream()
                                                 .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                                                 .collect(Collectors.toList());


		 */
		// adding elements
		intList.add(23); // internally converts primitive type to it's corresponding Wrapper class --> (Auto-boxing)
		intList.add(79);
		intList.add(80);

		// size of list
		System.out.println(intList.size());

		// removing elements by index or value
		// intList.remove(0);
		intList.remove(Integer.valueOf(80));

		// retrieving elements
		int num1 = intList.get(0); // auto-unboxing
		System.out.println(num1);

		List<String> cities = new ArrayList<>();
		cities.add("Pune");
		cities.add("Mumbai");
		cities.add("Sangrampur");
		cities.add("New York");
		cities.add("Gurugram");
		// Iterating using iterate() method of Iterable
		// forEach() internally uses iterator()
		System.out.println("Transverse on list from forward");

	   Iterator<String> itr=cities.iterator();
	   while(itr.hasNext()) {
		   String name=itr.next();
		   if(name.equals("Gurugram")) {
			    itr.remove(); // does not throw ConcurrentModificationExcept as we use iterators remove method
			    System.out.println("Gurugram removed successfully "+name);
		   }
	   }

		//from Java 8+ preferred removeIf(Predicate<T>) from Collection interface
	    boolean isRemoved=cities.removeIf(name->name.equals("Gurugram"));
		System.out.println("Whether provided city removed "+isRemoved);
	   
	   // ListIterator extends Iterator --> used for both forward & backword iterating.
	   System.out.println("Transverse on list from backword");
	   ListIterator<String> ltr=nameList.listIterator(nameList.size());	
	   while(ltr.hasPrevious()) {
		   System.out.println(ltr.previous()); 
	   }
	   
	   
	   
		System.out.println(intList);
		for (int i : intList) {
			System.out.println(i);
		}

		// check elements contains in a list
		boolean isAvailable = intList.contains(23);
		System.out.println(isAvailable);

		// adding element at particular index
		intList.add(2, 67);

		// replace element from a particular index
		intList.set(2, 97);
		System.out.println(intList);

		// convert list to array
		Integer[] intArr = intList.toArray(new Integer[0]);
		for (int i : intArr) {
			System.out.println(i);
		}

		System.out.println("-----------------------------------------------");
		/*
		   LinkedList: Implementation of List Interface. Similar to arraylist but backed
		  by Doubly-LinkedList to store elements as a node (value + memory address of
		  next & previous node) and link together using pointers.
		  
		  Performance considerations compare to ArrayList: 
		  1) Insertion & Deletion:
		  LL is better for frequent insertions & deletions in the middle of the list
		  bcz it does not require to shift elements, as in ArrayList.
		  2) Random access:
		  LL has slower performance compared to AL bcz it has to transverse the list
		  from beginning to reach the desired node.
		  3) Memory overhead:
		   LL requires more memory than AL bcz each node in LL requires extra memory 
		   to store references to next & previous nodes.
		  
		 */

		LinkedList<Integer> list = new LinkedList<>();

		list.add(23);
		list.add(78);
		list.add(21);
		list.add(34);
		list.get(3); // O(n) need to transverse to get desired node.
		list.addFirst(10); // O(1)
		list.addLast(67); // O(1)
		list.removeIf(e -> e % 2 == 0);

		/*
	      Vector is legacy class added to collection framework under List interface
		  similar to arraylist but synchronized (thread-safe) in nature. Due to
		  synchronized overhead it is not recommended to use in single-threaded env.
		  By default, size increases double of the current capacity.
		  All methods of Vector class are synchronized. 
		  For thread safe collection, the CopyOnArrayList or ConcurrentHashMap 
		  from java.util.concurrent package often recommended.
		  
		 */
		Vector<Integer> vector = new Vector<>(5, 3);
		vector.add(67);
		vector.add(65);
		vector.add(34);
		vector.add(12);
		vector.add(98);
		vector.add(45);
		System.out.println(vector.capacity());

		System.out.println("-----------------------------------------------");

		Stack<Integer> stack = new Stack<>();
		/*
		  Stack is an implementation of List works on LIFO principle. Since Stack
		  extends Vector, it is synchronized making it thread-safe.
		 */
		stack.push(12);
		stack.push(98);
		stack.push(78);

		int removedElm = stack.pop();
		System.out.println(removedElm);

		// just check top elm
		int peekElm = stack.peek();
		System.out.println(peekElm);

		System.out.println(stack.isEmpty());
		System.out.println(stack.size());
		// search checks Object position from top starts 1
		System.out.println(stack.search(98));

		// Using LL as a Stack
		LinkedList<Integer> stackLL = new LinkedList<>();
		stackLL.addLast(79);
		stackLL.addLast(24);
		stackLL.addLast(56); // like push()

		stackLL.getLast(); // like peek()
		stackLL.removeLast(); // like pop()

		stackLL.size();
		stackLL.isEmpty();
	}

}
