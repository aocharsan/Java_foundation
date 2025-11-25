package java8_Features;

import java.util.ArrayList;
import java.util.List;
import utilsClasses.Student;

public class Generics {
	/*
	  Generics: Basically allows us to write code that can work with any object
	   type while ensuring type safety at compile time.
	   It helps to write more flexible & reusable code e.g. without generics, we would have to
	   write multiple versions of the same class or method to handle different data types,
	   leading to code duplication.

	  why generics used? It provides:
	   1) Type-safety --> Generics allow us to catch
	       type errors at compile time rather than at runtime.

	   2) Eliminate explicit type casting --> When you use generics, we don’t need
	       to cast objects when retrieving them from a collection.

	   3) Code reusability --> Generics allow us to write a single class, interface or method
	       that works with different types.

	  How internally works?
	   Generics in Java are implemented through a process called type erasure. This means
	   that the generic types are replaced with their bounds or Object during compilation.
	   At runtime, there is no knowledge of the type T being String. The class Box only knows
	   that it stores an Object, and all operations on the generic type are done as if it were
	   an Object. However, the code that interacts with Box<String> still works safely because
	   the compiler has enforced the correct type usage at compile-time.

	   Bounded Generics:
	     restrict types to a specific hierarchy
	       class Calculator<T extends Number> {   // i.e. allows only classes that extended to Number.
             public double square(T number) {
               return number.doubleValue() * number.doubleValue();
                    }
                 }
       Wildcard in Generics:
         1. Unbounded Wildcard — <?>
             only need to read elements as Object and don’t care about the actual type.
              void printList(List<?> list) {
                for (Object obj : list) {  // can read as Object
                  System.out.println(obj);
                  }
             }
         ✅ Works for any type: List<String>, List<Integer>, List<Employee>
         ❌ Can’t add anything except null.

         2. Upper Bounded Wildcard — <? extends T>
              “Use upper bounded wildcard (<? extends T>) when your method needs to read items of type T
               or its subclass.”
               void printNumbers(List<? extends Number> list) {
                  for (Number n : list) {  // can read safely as Number
                  System.out.println(n.doubleValue());
                  }
               }
               Why can’t you add?
                Because the compiler doesn’t know if it’s List<Integer> or List<Double>.
                Adding a Double to a List<Integer> would break type safety.

          3. Lower Bounded Wildcard — <? super T>
              accept any unknown type that is a supertype of T or T itself.
              void addIntegers(List<? super Integer> list) {
                       list.add(10);
                       list.add(20);
                }

                Accepts List<Integer>, List<Number>, List<Object>
                You can safely add Integer values.
                But when reading, you only get Object (because you don’t know the exact subtype)


	 */
	public static void main(String[] args) {
		// Without Generics scenario:
		List list = new ArrayList();
		list.add("MS Dhoni");
		list.add(007);

		String st = (String) list.get(0);
		int nm = (int) list.get(1); // No compile time checking ---> leading ClassCastException
		System.out.println(st + " " + nm);

		// With Generics scenario:
		List<String> list1 = new ArrayList<>();
		list1.add("MS Dhoni");
		list1.add("Virat");
		list1.add("Rohit");

		String ms = list1.get(0);
		String vt = list1.get(1);
	   //	int ro = list1.get(2);  // compile-time checking 
		String ro=list1.get(2);
		
		System.out.println(ms+"  "+ vt+"  "+ro);
		
		Generics gn =new Generics();		
		Car car=gn.new Car();
		car.setEngine("Volvo 3.2l");
		String volvo=(String)car.getEngine();   // Even Object type class have same manual type-casting & no compile-time check
		
		System.out.println("Volve engine details: "+volvo);   
		
		 // Generic-Type class
	     Box<String> box=gn.new Box<>();
	     box.setShapeType("Circle");
	     String circle=box.getShapeType();
	    // int circle=box.getShapeType(); // types are enforced at compile time
	     System.out.println(circle);
		
	     
	     //Calling static generic method
	   String hello= Generics.getDetails("Say Hello to everyone !");
	   Student student=Generics.getDetails(new Student(101,"Sanket",27,"Male","Cs","Tamgaon"
			                                                                ,10, List.of("7028301355","8379946441")));
	   
	   System.out.println(student.getFirstName()+" "+hello);
	   
	   //calling generic instance method
	   String[] names= {"John","Ronaldo","Virat","Messi"};
	   gn.printArray(names);



	   
		

	}
	
	public static <T> T getDetails(T input) {
		return input;
	}

	   // Wildcard represents unknown type in generics.
	   /*   let's validate why Wildcard replace Object class because this will not accept List<String> or
		    List<Integer> because List<String> is not a subtype of List<Object>.
		    printList(names); not accept String List if we replace Object with wildcard as In Generics —
		    type parameters do NOT follow inheritance hierarchy.

| Concept             | Relationship   |                    Example                       | Allowed?      |
| ------------------- | -------------- | ------------------------------------------------ | ------------- |
| Class inheritance   | Covariant      | `String extends Object`                          | ✅            |
| Generic inheritance | Invariant      | `List<String>` is not `List<Object>`             | ❌            |
| Wildcard usage      | Covariant-like | `List<? extends Object>` can hold `List<String>` | ✅ (read-only)|



	    */
	public static void printList(List<Object> list){
          for (Object object:list){
			  System.out.println(object);
		  }

	}
	
	public <T> void printArray(T[] array) {
	        for (T element : array) {
	            System.out.print(element + " ");
	        }
	}
	
	
	class Car {

		private Object engine;

		public Object getEngine() {
			return engine;
		}

		public void setEngine(Object engine) {
			this.engine = engine;
		}

	}
	   
	class Box<T> {    // type parameter T will be replaced with a specific type at the time of object creation.

		private T shapeType;

		public T getShapeType() {
			return shapeType;
		}

		public void setShapeType(T shapeType) {
			this.shapeType = shapeType;
		}

	}
	
	interface Container<T extends Number> {  // set Upper-Bound type ---> GI- Comparable<T> interface
	/*	When you implement a generic interface, you need to specify the type for 
	  the generic parameter, or you can continue to make the implementation 
	  generic by using type parameters.
	      syntax for multiple bounds in Java Generics :
	        <T extends ClassType & Interface1 & Interface2>
	*/	
		void add(T item);
		T get();
	}
	   
	class Pair {
	    // Generic constructor with two type parameters
		//the generic type parameter for the constructor may be different 
		//from the generic type parameter of the class
	    <A, B extends Number> Pair(A first, B second) {
	        System.out.println("First: " + first + ", Second: " + second);
	    }	    
	}
	
	class MyClass<T> {
	    private T instanceVar;  // Valid
	  // static T staticVar;      Invalid - static members cannot use type parameter T
	    /*
	     One restriction with generic classes is that static members cannot use type parameters. 
	     The reason for this is that static members belong to the class itself rather than to 
	     any instance, and the type parameter is tied to an instance.
	      
	     */
	}
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   

}
