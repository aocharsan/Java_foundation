package collection;

import utilsClasses.Employee;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapDemo {

	public static void main(String[] args) {
       /*
         Map is a collection used to store key-value pairs. Each key maps to exactly one value,
         and the keys must be unique, while values can be duplicate.
         It does not extend collection interface.
           characteristics:
             key-value pair
             unique keys
             One value per key
             Order: Some impls maintains order (LinkedHashMap), sorted order (TreeMap)
             or no order (HashMap)
             
          Impl classes --
            HashMap(I):
               LinkedHashMap
               WeakedHashMap
               IndentityHashMap   
            HashTable(legacy)
            SortedMap(I)
                 NavigableMap(I)
                     TreeMap
            
         	HashMap:
         	  key characteristics:
         	    Unordered: does not maintain any order of its elements
         	    Allows null key & values: can have one null key & multiple null values.
         	    Non Synchronized: Not thread-safe; requires external synchronization if used
         	                      in multi-threaded env.
         	    Performance: Offers constant time performance O(1) for basic operations
         	                        like get() and put(), assuming hash functions disperses elem. properly.

         	  Bonus: When the key is null, Java’s HashMap always stores it in bucket index 0.

         	  Internal working of HashMap: (works on hashing principle)
         	    HashMap internally uses dynamic array as its underlying data structure. It consists of array of Nodes
         	    called buckets of default size of 16.
         	      Node contains:
         	         hash value -- 234456
         	         key -- 101
         	         value -- "Jay Hind!"
         	         Pointer to next Node -- null
         	     Each bucket can hold one or more entries using LL or Balanced tree(Red-Black tree)
         	     in case of collision.
         	   
         	   ReHashing: When the number of elements grows & exceeds a certain load factor(0.75 def.),
         	   HashMap auto. resizes the buckets to double to hold more elements.
         	   
         	   Load factor: It's threshold value used to control resizing. actual number of entries/ Total no.of entries.
         	   
         	   Why balanced Binary-tree introduced in Java8 to store elements
         	   if LL Node increases def. size 8? 
         	   Basically, HashMap provides constant time O(1) performance for basic ops 
         	   like put(), get(), remove(),containsKey(),size() (but assuming there is no collision). 
         	   However, if there are many collisions, and many entries stored in a same bucket in the
         	   form of LL, the performance can degrade to O(n), n -- no.of elements in the bucket
         	   But after, Java8 this LL converts to balanced binary-tree (Red-Black tree)
         	   to improved performance O(log(n)).
         	  
         	   Override equals() not hashCode():
         	      Even though both objects are same, there hashCode may be different,
         	      so both objects will be stored in two different bucket.
         	      
         	   Override hashCode() not equals():
         	      As we know if we don't override equal() method inside our Custom object
         	      class, it would call Object class equal() which compares objects based on
         	      references not their content. Even both object have a same hashCode
         	      & it will consider as unique & it's entry stored as a another Node inside 
         	      same bucket.

         	   equals() & hashCode() contract: to maintain uniqueness of keys and to find entries quickly


         	       
           
         	   Storing data:
         	     When we call put() it internally call hash function to calculate hashCode for given
         	     type by calling hashCode() method.  Calculating the bucket index to decide which
         	     bucket going to hold key-value pair. The key-value pair then stored inside a calculated
         	     bucket index. Each bucket can hold multiple key value pairs in case of collision.
         	  Retrieving Data:
         	     calculate hashcode 
         	     find bucket index from hashcode
         	     searching in the bucket
         	     
         	  Hash Collision : In case of duplicate key entry, we get the same hashcode for a given key.
         	  so, if we try to add this without implementing equals() method 
         	         
         	             
         	    HashMap                                         HashTable
      It is non-synchronized,                        Synchronized impl of Map used in multi-
      can't use in Multi-threaded                    threaded environment.
      env.
      
      Allows one null key & Multiple                 Not allows null key or values.
      null values.
      
      A LinkedHashMap impl class                      Insertion order not maintain.
      can be used to maintain insertion
      order.
      
      fail-fast iterator, throws                       Enumerator of HashTable is fail-safe. 
      ConcurrentModExcpt if any thread
      try to modify the map.
      
      faster performance bcz non-blocking               Slower performance bcz blocking in nature
      in nature.   	                                    due to synchronized methods.(i.e. acquiring lock
                                                        on entire object not allows another thread
                                                        until current operation not completed )
                                                                 

     Bonus:
      TreeMap traverses the tree to find the correct location.
       If compareTo() returns:
        < 0 → go left (smaller)
        > 0 → go right (larger)
        == 0 → key already exists → replace value (no duplicate keys allowed) so, there is no need to
           provide implementation as TreeMap backed-by Red-Black tree --> No hashing — handles ordering conflicts via comparison
       Hence, there is no “collision resolution” using buckets or chaining — it’s purely handled by comparison logic.


         	
        */

		Employee employee1=new Employee(101,"Sanket",28,"male","IT",2021,56000);
		Employee employee2=new Employee(101,"Sanket",28,"male","IT",2021,56000);
		System.out.println("Employee 1 hashCode....."+employee1.hashCode());
		System.out.println("Employee 2 hashCode....."+employee2.hashCode());



		HashMap<Integer, String> map = new HashMap<>();
		map.put(101, "Dinesh");
		map.put(102, "Sanket");
		// for duplicate key entry it will replace with current value
		map.put(103, "Shamali");
		map.put(103, "Sagar");

		// previous value associated with the specified key, or null
		// if there was no mapping for the key
		map.putIfAbsent(101, "Harshwardhan");
		map.putIfAbsent(105, "Sam Bahadur");
//		map.put(null, "Samartha");
//		map.put(null, "Kalpesh");    // one null allows ---> Samartha replace with Kalpesh
		
		System.out.println(map.get(101));
		System.out.println(map.getOrDefault(987, "Nahi hai be......."));
		System.out.println(map.get(109)); // return null
		System.out.println(map.containsKey(102));
		System.out.println(map.containsValue("Virat")); // case-sensitive

		// Getting all keys
		System.out.println("Printing keys from map");
		Set<Integer> keys = map.keySet();
		for (int i : keys) {
			System.out.println(i);
		}

		// Getting all values
		System.out.println("Printing values from map");
		Collection<String> values = map.values();
		for (String value : values) {
			System.out.println(value);
		}

		// Iterating over Map Using advanced for loop
		System.out.println("Iterating over map");
		Set<Map.Entry<Integer, String>> entries = map.entrySet();

		for (Map.Entry<Integer, String> entry : entries) {
			entry.setValue(entry.getValue().toUpperCase());
			System.out.println(entry.getKey() + " : " + entry.getValue());
		}
		System.out.println("Iterating over map using Iterator");
	          Iterator<Map.Entry<Integer,String>> itr=map.entrySet().iterator();
	          
	          while(itr.hasNext()) {
	        	  Integer key=itr.next().getKey();
//	        	  if(key.equals(103)) {
//	        		  itr.remove();
//	        	  }
	        	  System.out.println(key+" : "+itr.next().getValue());
	          }
	    
		// removing element from map
		// map.remove(101);
		map.remove(101, "Kapil"); // not such value corresponding to key; return false

		// hash collision demo Let's watch Selenium express video

		Map<Integer, Person> mapOfPerson = new HashMap<>(14, 0.5f);
		mapOfPerson.put(101, new Person("Sanket A", "Pune", 27));
		mapOfPerson.put(102, new Person("Jaywant D", "Sangali", 43));
		mapOfPerson.put(103, new Person("Rahul Patil", "Sangrampur", 34));
		mapOfPerson.put(101, new Person("Roshan A", "Satara", 23));
		mapOfPerson.put(104, new Person("Sagar D", "Nirod", 26));
	  
	  System.out.println("--------------LinkedHashMap-----------------------");
	// double-linked list --- accessOrder the ordering mode - true for access-order, false for insertion-order, 
	// so can be used to check least-recently used element -- Most commonly use-case: LRU Cache
	 LinkedHashMap<String, Integer> lkMap=new LinkedHashMap<>(14,0.5f,true);  
		lkMap.put("Apple", 23);
		lkMap.put("Orange", 98);
		lkMap.put("Kiwi", 12);

		lkMap.get("Apple");
		lkMap.get("Orange");
		lkMap.get("Apple");
		lkMap.get("Kiwi");
		lkMap.get("Orange");
		lkMap.get("Apple");
 
		for (Map.Entry<String, Integer> lk : lkMap.entrySet()) {
			System.out.println(lk.getKey() + " : " + lk.getValue());
		}
	 
		System.out.println("--------------IdentityHashMap-----------------------");
		Map<Integer,Integer> idmap=new IdentityHashMap<>();  //default size 21
		// It will always use Object class hashCode()(calculate hash based on memory address)
		//& equals() (checks referential integrity)
//		String key1=new String("Sanket");   
//		String key2=new String("Sanket");
//		System.out.println(key1==key2);  //false; so both entries be there  true:replace value
		Integer key1=241;  // new Integer() deprecated from Java v-9
		Integer key2=241;
		
		// primitive Wrapper classes ---> creates two different instances, so both entries happen.
		// but if you use primitive, --> both referring to same value, so just In IDMap override value
		System.out.println(key1==key2);  // false  
		System.out.println(key1.equals(key2));  // check content-- true; this method not used in primitive context.
		idmap.put(key1, 101);
		idmap.put(key2, 102);
		
		System.out.println(idmap);
		
		  System.out.println("--------------SortedMap(I)-----------------------");
       /*
         SortedMap is an interface that extends Map & guarantees that the entries are
         sorted based on keys, either in their natural ordering or by a specified Comparator. 
        */
		  
		  SortedMap<String, Integer> treeMap=new TreeMap<>();
		  treeMap.put("Jagan R", 91);
		  treeMap.put("Rangaswamy D", 78);
		  treeMap.put("Mohit C", 83);
		  treeMap.put("Harshwardhan C", 96);
		  treeMap.put("Ramnath K", 67);
		  
		  System.out.println(treeMap);
		  
		  // for Custom type, either implements Comparable to class whose object want to sorted
		  // otherwise provide Comparator inside TreeMap constructor
		  // Comparator.reverseOrder() --- uses CompareTo() from Comparable to sort naturally desc.
		  
		  SortedMap<Person, Integer> customTM=new TreeMap<>( Comparator.comparing(Person::getName).thenComparing(Person::getCity));
		  
		  customTM.put(new Person("Harshwardhan C", "Kanpur",30 ), 400000);
		  customTM.put(new Person("Rangaswamy D", "Amravati",23 ), 34000);
		  customTM.put(new Person("Harshwardhan C", "Jalgaon",30 ), 340000);
		  customTM.put(new Person("Ramnath K", "Patana",39 ), 17000);
		  customTM.put(new Person("Jagan R", "Hyderabad",56 ), 210000);
		  customTM.put(new Person("Mohit C", "Surat",21 ), 47000);
		  // pass a Comparator to the TreeMap constructor,
		  // the Comparator takes precedence over the Comparable implementation.
		  // if Both not, throws ClassCastExceptions at runtime.
		 for(Map.Entry<Person, Integer> custom:customTM.entrySet() ) {
			 System.out.println(custom);
		 }
 
		 
		  /*
		    NavigableMap(I) extends SortedMap interface, providing more powerful
		    navigation's options such as finding the closest matching key or
		    retrieving the map in reverse order.
		       NavigableMap<Person, Integer> naviMap=new TreeMap<>();
		      
		   */
		 System.out.println("--------------Hashtable-----------------------");

		  Hashtable<String, Integer> hashTable=new Hashtable<>();
		  hashTable.put("Ganpat", 12);
		  hashTable.put("Jayram", 24);
		  hashTable.put("Pralhad", 98);
		  
		  // Iterate over keys using Enumeration
			Enumeration<String> enumerator = hashTable.keys();
			System.out.println("Iterating over keys:");
			while (enumerator.hasMoreElements()) {
				String key = enumerator.nextElement();
				if (key.equals("Ganpat")) {
					hashTable.remove(key); // not throws Exception
				}
			 //System.out.println("Key: " + key + ", Value: " + hashTable.get(key));

			}
		    //Map<Integer, String> synMap = Collections.synchronizedMap(map);
            /*
              ConcurrentHashMap:-
              Java7:
               segment based locking --> 16 segments --> like smaller hashmap
               Only the segment being written to or read from is locked
               read: do not require locking unless there is a write operation happening 
                     on the same segment
               write/update: lock segment  
               
                Java8:
                No segmentation
                compare & swap approach --> no locking except resizing & collision
                e.g. Thread A last saw -- x=45
                Thread A work ---> x to 50
                if x is still 45, then change it to 50 else don't change & retry
                incremental resizing there.
                      
                Map<String, Integer> conMap=new ConcurrentHashMap<>();
                
                Map ---> Sorted + achieve Concurrency---> ConcurrentSkipListMap
                Uses SkipList DS -- probabilistic DS that allows efficient search, insertion
                & deletion operations. It similar to sorted LL but with multiple layers
                that 'skip' over portions of the list to provide faster access.
                e.g.
                1,2,3,4,5,6,7,8,9   ---->Layer 1
                1---3----5---7---9  ----> Layer 2
                1---------5--------9  ----> Layer 3  
             */
			// always prefer when associated enum compare to HashMap -- No need to calculate
			// hashcode it directly associated to enum ordinal value.
			// no resizing require  --> faster + memory efficient
			 Map<Day,String> enumMap=new EnumMap<>(Day.class);
			 enumMap.put(Day.SATURDAY, "Study Hard");
			 System.out.println(Day.SATURDAY.ordinal());
			 enumMap.put(Day.SUNDAY, "Going to treking");
			 enumMap.put(Day.MONDAY, "Go to office");
			 System.out.println(enumMap);
			
			// immutable Map
			 Map<String,Integer> hmap=new HashMap<>();
			 hmap.put("Sam", 23);
			 hmap.put("David", 45);
			 
			 Map<String,Integer> umap=Collections.unmodifiableMap(hmap);
			// umap.put("Jack", 56);  //throws UnsupportedExcpt
			// but can add to existing map so prefer List.of() but add at most 10 key:value pair
			 hmap.put("Steven", 34);
			 //System.out.println(umap);
			 
		Map<String,Integer> ofMap=Map.of("Jagan",34,"Harsh",30);	
		//ofMap.put("Kalyan", 26); //throws UnsupportedExcpt 
			 
		// Map.of() limited to 10 entries --> so, used Map.ofEntries()
	    Map<String,Integer> ofEMap=Map.ofEntries(Map.entry("Sanket", 27),Map.entry("Dyandev", 78));
		//ofEMap.put("Jagdev", 98); // throws UnsupportedExcpt
		  
		  
         
		 
		 

		  
	    
	
		
	}

}

enum Day {
	MONDAY, TUESDAY, WEDNEDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
}

class Person // implements Comparable<Person> 
{

	private String name;
	private String city;
	private int age;

	public Person(String name, String city, int age) {
		this.name = name;
		this.city = city;
		this.age = age;
	}

	public int getAge() {
		return age;
	}

	public String getName() {
		return name;
	}

	public String getCity() {
		return city;
	}

	@Override
	public String toString() {
		return "Person [Age=" + age + ", name=" + name + ", city=" + city + "]";
	}
	
	
	

	  @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;                  // Same reference
	        if (obj == null || getClass() != obj.getClass()) return false;  // Null or different class

	        Person other = (Person) obj;
	        return age == other.age &&
	               Objects.equals(name, other.name);       
	    }

	    // Override hashCode()
	    @Override
	    public int hashCode() {
	        return Objects.hash(name, age);  
	    }

//	@Override
//	public int compareTo(Person that) {
//		return this.name.compareTo(that.name);
//		}

}
