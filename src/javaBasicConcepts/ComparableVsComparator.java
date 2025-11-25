package javaBasicConcepts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import utilsClasses.Employee;

public class ComparableVsComparator {
	
     class Student implements Comparable<Student>{
		/*
		 Comparable interface (java.lang) used to define the natural ordering of objects &
		 must be implemented by the class whose instances need to compare.(Only one sorting rule)
		 It is required the impl class needs to implement compareTo(Object o), which compares
		 the object with another object of the same type otherwise throws ClassCastException.
		 
		 Limitations: 
		    1) Single sorting sequence
		    2) Tight coupled as comparison logic inside class itself so reduces flexibility & reusability.
		    3) Breaks OCP (Open/Closed Principle)
		          Supposed if we want to change the sorting logic, we must modify the class. 
		          This goes against the Open/Closed Principle of OOP, which says 
		          that classes should be open for extension but closed for modification.
		 */
		 int id;
		 String name;
		 String dept;
		
		public Student(int id,String name,String dept) {
			this.id=id;
			this.name=name;
			this.dept=dept;
		}

		@Override
		public String toString() {
			return "Student [id=" + id + ", name=" + name + ", dept=" + dept + "]";
		}

		@Override
		public int compareTo(Student that) {
			int comp=this.dept.compareTo(that.dept);
			return comp;
		}
	
	}
	
	 class StudentComparator implements Comparator<Student> {
		 /*
		   Comparator interface(java.util) can't modify the class whose object being sort.
		   capable of comparing two objects of same type.
		   Basically, Comparator has its own definition of how to compare two objects, so
		   even used to compare objects other than natural ordering plus multiple sorting
		   possible. Gives flexibility & reusability of class whose objects compared.
		  */	 
		@Override
		public int compare(Student s1, Student s2) {
			int sortByname = s1.name.compareToIgnoreCase(s2.name);
			// Sorting by department name if name is same.
			return sortByname == 0 ? s1.dept.compareToIgnoreCase(s2.dept) : sortByname;
		}

	}
	
	public static void main(String[] args) {
		
		ComparableVsComparator cmp=new ComparableVsComparator();
		//Compare Student based on their names.
	     List<Student> students=new ArrayList<>();	
	     students.add(cmp.new Student(101,"Sanket","Comp Science"));          
		 students.add( cmp.new Student(102,"Ramesh","Auto Engg"));		                    
		 students.add(cmp.new Student(103,"Digambar","Mech Engg"));		                     
		 students.add( cmp.new Student(104,"Kamlesh","Civil Engg"));		
		 students.add( cmp.new Student(105,"Ram","Comp Science"));	
	    // Collections.sort(students);
	    Collections.sort(students, cmp.new StudentComparator());
		 
		 // Directly used Lambda's to compare without creating particular Comparator.
		 List<Employee> employees =Employee.EmployeeUtils.getEmployees();
		 
		 // from Java8 onwards used list.sort() instead Collection.sort()
		 // we can also used Comparator.comparing(method reference)
			employees.sort((e1, e2) -> {
				int sortByName = e1.getName().compareToIgnoreCase(e2.getName());
				// Sorting by yearOfJoining if name is same.
				return sortByName == 0 ? Integer.compare(e1.getYearOfJoining(), e2.getYearOfJoining()) : sortByName;
			});
			employees.forEach(System.out::println);

			System.out.println("Sorting using Comparator.comparing(method reference) ");

		// sort by using Comparator.comparing(Comparator<T>)
		  employees.sort(Comparator.comparing(Employee::getName)
				                   .thenComparingInt(Employee::getYearOfJoining));
		 // employees.forEach(System.out::println);
		 
		/*
		  Bonus to improve stream performance:
		     for compare primitive fields always prefer to use Comparator.comparingInt(method ref); (also similar for Double, Long, etc.)
		     It improves performance drastically as there is no conversion from int to Integer object i.e. no boxing involve.

		  Mistakes to Avoid with Custom Objects Having Primitive Fields in Stream API:
		     ❌ 1. Using Comparator.comparing() for primitive fields

                    students.stream()
                            .sorted(Comparator.comparing(Student::getMarks))
                            .collect(Collectors.toList());
                  Problem: marks (int) is auto-boxed to Integer.
                  Overhead: Creates extra objects for large lists → slower.
                  ✅ Better:
                   students.stream()
                           .sorted(Comparator.comparingInt(Student::getMarks))
                           .collect(Collectors.toList());
                   Avoids boxing, faster for large lists.

             ❌ 2. Mapping to wrapper streams unnecessarily

                  students.stream()
                          .map(Student::getMarks)  // int → Integer (boxing)
                          .collect(Collectors.toList());
               ✅ Better (use IntStream):
                IntStream marksStream = students.stream()
                                                .mapToInt(Student::getMarks); // keeps it primitive
                   int sum = marksStream.sum();  // efficient aggregation
                   Avoids auto-boxing.
                 Aggregate operations (sum, average, max) are optimized for primitives.

             ❌ 3. Using .boxed() unnecessarily

                   students.stream()
                           .mapToInt(Student::getMarks)
                           .boxed()  // converts back to Integer unnecessarily
                           .filter(m -> m > 50)
                           .collect(Collectors.toList());
                  ✅ Better:
                   List<Integer> highMarks = students.stream()
                                                     .mapToInt(Student::getMarks)
                                                     .filter(m -> m > 50)
                                                     .boxed()  // only box at the very end if you need a List<Integer>
                                                     .collect(Collectors.toList());
                   Box only when necessary (e.g., converting to List of objects).

             ❌ 4. Multiple mappings between primitive and object streams
                Avoid chaining mapToInt().boxed().mapToInt() unnecessarily.
                It causes extra object creation.
                ✅ Keep it simple:
                 int maxMarks = students.stream()
                                        .mapToInt(Student::getMarks)
                                        .max()
                                        .orElse(0);

             ❌ 5. Forgetting to use comparingInt in chained sorting

                for multi-field sorting:
                 students.stream()
                         .sorted(Comparator.comparing(Student::getYearOfAdmission)
                                           .thenComparing(Student::getMarks))
                         .collect(Collectors.toList());
                Problem: Both fields are primitives → auto-boxing happens.
                ✅ Better:
                   students.stream()
                           .sorted(Comparator.comparingInt(Student::getYearOfAdmission)
                                             .thenComparingInt(Student::getMarks))
                           .collect(Collectors.toList());
                  Efficient, no unnecessary boxing.




        */
		 
	   
	     
		
		
	}

}
