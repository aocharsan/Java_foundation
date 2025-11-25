package javaBasicConcepts;

public final class FinalClass {
	 /*
	   OOP's basically gives methodology to modeled real life entities and assign them roles & responsibilities.
	    also provides control over data security (with access modifier), extensibility and maintainability.
	      There are four principals provided to achieve this
	         1) Abstraction
	         2) Encapsulation
	         3) Inheritance
	         4) Polymorphism

             Abstraction:
              Hiding complex implementation & exposing only essential details.
              ➤ Why
                To reduce complexity
                To increase maintainability
              Using abstract classes or interfaces.

             Encapsulation:
              Wrapping data (variables) & code (methods) together inside a class.
              ➤ Why
                To protect data from unauthorized access
                To control modification using getters & setters

             Inheritance:
              One class acquires properties and behaviors of another.
              ➤ Why
                 Code reusability
                 Logical class hierarchy
                 Using extends keyword.

             Polymorphism:
              “Many forms” — same method behaves differently depending on the object.
              ➤ Why
                 To make code flexible and extensible
                 To achieve dynamic behavior

             Compile-time Polymorphism (Method Overloading):

               class Calculator {
                 int add(int a, int b) { return a + b; }
                 double add(double a, double b) { return a + b; }
                }

             Runtime Polymorphism (Method Overriding):
              class Animal {
               void sound() { System.out.println("Animal sound"); }
              }

             class Dog extends Animal {
               void sound() { System.out.println("Bark"); }
              }
         ✅ At runtime, JVM decides which method to call based on the object type.

             | Class   | Blueprint or template for creating objects.      |
             | Object  | Instance of a class that holds data & behavior.  |



	   
	     final keyword used to indicate that a variable, method or class can't be modified or extended.
	   final variable: when a variable declared as final, it's value can't be changed once it
	     has been initialized. Initialized at the time of declaration or inside constructor or inside instance
	     initialization block or
	        Inside Static Block (for static final variables)
                 class Config {
                      static final String APP_NAME;

                       static {
                          APP_NAME = "BankingApp";
                          }
                     }

               Final Reference vs Final Object
                final Address address = new Address("Mumbai");
                address.setCity("Pune");  // ✅ allowed — object content changed
                address = new Address("Delhi"); // ❌ compile-time error — reference can’t change

	     	     final variable different for different object, but can't be changed accidentally
	     	     or even intensionally.
	   final method: It can't be overridden by any subclasses.	   
	   final class: It can't be extended by any subclasses.  
	   
	   */
	public final float PI=3.14f;
	
	public final void doSomething() {
		int sum=0;
		for(int i=0;i<=10;i++) {
			sum+=i;
		}
		
		System.out.println("Sum is "+(sum+PI));
		
	}
	
	public static void main(String[] args) {
		FinalClass class1=new FinalClass();
		class1.doSomething();
	}

}
