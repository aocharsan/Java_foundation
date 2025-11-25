
package java8_Features;


@FunctionalInterface
interface ParentA {

	int add(int a, int b);
    
	/*
	    What is Functional Interface?
	     Interface with exactly one abstract method, but may have any number of default & static
	     methods. In another way, we can say that it provides only functionality to implement.
	       Basically, introduced to support functional programming using Lambda's & method references.
	       These all Functional interfaces organized into new package java.util.function
	       
	       Used @FunctionalInterface annotation above interface, but not mandatory.
	       
	         Predicate                  Function               Consumer                Supplier
	       boolean Test(T t);        R apply(T t);         boolean accept(T t);          T get();
	       
	       Before Java8: Runnable, Comparator
	       
	       Functional Interface is used to provide reference to Lambda expression.
	       Comparator<String> cm=(s1,s2)->s1.compareTo(s2);
	     
     
	   why default method inside interface?
	      Default method allows interface to have a method with implementation without
	      affecting the classes that already implements this interface.
	        for that just use default keyword before method signature & provides whatever
	        implementation needed. 
	        e.g. forEach() from Iterable Interface 
	              stream() from Collection Interface
	 
	 */
	 default void sayHello() {
		System.out.println("Say Hello from A");
	}
	
	 static void sayHelloAgain() {
		/*
		    Why static method inside interface?
		     used when we need to provide some common implementation among all implementing
		     classes or not allow to change the behaviour by an implementing classes. can't override it. 
		     Call it even without implementing.  --> InterfaceName.staticMehod();
		        e.g. identity() from Function interface 
		             generate() (returns an infinite sequential unordered stream) from Stream API
		 */
		System.out.println("Say Hello from Static method of A");
	}

}

interface ParentB {
	default void sayHello() {
		System.out.println("Say Hello from B");
	}
}

class Child implements ParentA, ParentB {

	@Override
	public void sayHello() {
		// can override default method & provide our own implementation, but not compulsory.
		ParentB.super.sayHello(); // calling it from ParentB interface

		// Be remember: If we implement two interfaces having same default method,
		// then it is compulsory to override default method otherwise gives compilation error.
	}

	@Override
	public int add(int a, int b) {
		return a + b;
	}

}

public class DefaultNStaticMethod {
	public static void main(String[] args) {
		Child child = new Child();
		child.sayHello();
		int sum=child.add(12, 89);
		System.out.println("Sum of two numbers :"+sum);
		ParentA.sayHelloAgain();
	

	}

}
