package collection;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

import utilsClasses.Student;

public class SetDemo {
	public static void main(String[] args) {
		
		SetDemo demo=new SetDemo();
		/*
		 During interview tell first 2 diff. if asked more, then tell other
		 
		 List                                                      Set
		 stored order collection                     stored unorder collection
		 of objects i.e. preserve                    of objects i.e. not design to
		 insertion order.                            preserve insertion order but
		                                             can achieve using LinkedHashSet impl.
		 
		 can store duplicates values              can't store duplicate values by default
		 even add multiple null.                  for primitive type. For custom type we
		                                          need to implement hashcode() & equals(),
		                                          otherwise duplicate entry happens.
		 
		 index-based access possible.             index-based access not possible
		 
		 Can use both Iterator &                  Can use Iterator but can't ListIterator
		 ListIterator for iterating.              for iterating.
		 
		 
		HashSet: Impl of Set interface used to store unique elements, but insertion order
		not preserved & backed by HashMap. search/insert/remove -- O(1) > One null permits.
		Need to implement hashCode() & equal() methods for custom type objects otherwise
		duplicate entry will be there. 
		
		LinkedHashMap: preserve insertion order.
		
		TreeSet: Impl of Set interface used to store unique elements in sorted order & backed
		by  Red-Black Tree. For sorting, either need to implement Comparable interface to class
		whose objects want to compare or need to pass custom Comparator to its constructor.
		otherwise throws  java.lang.ClassCastException.
		No null permit as it is not comparable, throws NullPointerException
		for all operation (except iterating) -- O(log(n))
		
		
	     Set vs Map?
	       What are they?
	       Internal working
	       Performance & Time Complexity -- Both provide O(1) for insert, search, delete
	       & O(log(n)) due to backed back Red-Black tree in case of hash-collision.
                 
 		Why primitive types not allowed as a keys in Hash based collection?
 		 1) Collections store objects, not primitives
 		 2) Primitive types don’t have hashCode() or equals()
 		 3) Wrapper classes solve this problem.




		 */
		//Set ---> HashSet, LinkedHashSet, TreeSet, EnumSet
		Set<String> hashSet=new HashSet<>();
		// search/insert-- O(1) -- faster performance
		hashSet.add("Sangrampur");
		hashSet.add("Khamgaon");
		hashSet.add("Amalner");
		hashSet.add("Satara");
		hashSet.add("Pune");
		hashSet.add("Akola");
		System.out.println(hashSet);
		
		hashSet.contains("Satara");
		hashSet.remove("Satara");
		System.out.println(hashSet);
		
		//hashSet.clear();
		if(hashSet.isEmpty()) {
			System.out.println("No elm there inside Set");
		}
		System.out.println("------------iterating over Set using Iterator-----------");
		Iterator<String> iterator = hashSet.iterator();

		while (iterator.hasNext()) {
			String element = iterator.next();
			
			if (element.equals("Pune")) {
		    	iterator.remove(); //  Safe removal
			  // hashSet.remove(element);  //throws ConcurrentModExcept
			}
			System.out.println(element);
		}

		System.out.println("-----------------------------------------");
		
		// for thread safety
		Collections.synchronizedSet(hashSet);  // not recommended bcz blocking in nature.
		
		// instead use, Fail-safe iterator also Iterator do not reflect modifications.
		// Copy-on-write mechanism
		
		Set<Integer> cowSet=new CopyOnWriteArraySet<>();
		for(int i=1;i<=5;i++) {
			cowSet.add(i);
		}
		// try to modify existing set while iterating, but reflects this modification on copy of this set
		for(int num:cowSet) {
			//System.out.println("Iterating over CopyOnWriteSet: "+num);
			// modify
			cowSet.add(35);
		}
		//System.out.println(cowSet);
		
		
		// instead use, if we require sorted order + concurrency
		 Set<String> concurrentSet= new ConcurrentSkipListSet<>(hashSet); 
		 System.out.println(concurrentSet);
		 System.out.println("---------------------------------------------------");
		
		 List<Student> students=Student.StudentUtils.getAllStudents();
		 
		 Set<Student> treeSet=new TreeSet<>(demo.new StudentComp());	
		  treeSet.addAll(students);
		  
		 // System.out.println(treeSet);
		  for(Student stu:treeSet) {
			  System.out.println(stu);
		  }
		
		
	}
	
	class StudentComp implements Comparator<Student>{

		@Override
		public int compare(Student s1, Student s2) {
			int sortByName = s1.getFirstName().compareTo(s2.getFirstName());
			return sortByName == 0 ? Integer.compare(s1.getAge(), s2.getAge()) : sortByName;
		}
		
	}

}
