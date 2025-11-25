package javaBasicConcepts;

  public interface IntefaceBasics {
	  
	  /*
	     What is Interface? (Blueprint for a class)
	        It is a reference type similar to class, but it contains only constants, abstract methods
	        (method with signature only). But from Java8 can also have default & static methods.
	        In simpler way we can say It is a blueprint of a class.
	        
	        As it is incomplete, we can't create an instance of Interface. They only implemented by classes
	        or extended by another interface.
	        
	        Why?     Used to achieve abstraction by Hiding internal implementation to consumer.
	                 To support multiple inheritance.
	                 To achieve loose coupling using polymorphism. (refer -- loose coupling vs tight coupling)
	                 
	                 
	        Marker or Tag Interface: It is empty interface having no methods or fields inside it. basically,
	        It is used to deliver type info during runtime to JVM so that it can take some action based on info
	        received.
	          e.g.  Cloneable  --obj. of impl class can be cloned Using Object.clone()
	                Serializable -- impl class converted into Byte-stream(serialization) & later reconstructed(deserialization) 
	                Remote: used in Java RMI(remote method invocation)
	                
	        Abstract Class: Partially defined parent class. It may or may not have abstract methods(method without impl). 
	        we can't create its instance, but can be subclassed. uses abstract keyword for class.
	        When abstract class is subclass, the child class need to provide it's impl if not declared
	        itself as abstract.
	         
	        Static variable: Whenever we declared variable as static, it's single copy shared among all
	        the instances of a class. Initialized only once, at the start of execution.
	        Initialized:      At the time of declaration
	                          Using static block
	                          private static method
	        
	        Static method: associated with class itself rather than individual instance of class. Directly
	        called using class name and can't use non-static data members and non-static method directly.
	          Also, this and super can't be used in static context.
	          Utility classes like Collections & Collectors are the best ex.
	                
                
	   */
	       String name="Mumbai";         //byDefault: public+static+final
	       void sayHello();              //byDefault: public+abstract
	  
     public static void main(String[] args) {
    	 // Yes. we can invoke main() from interface
		System.out.println("Hello, I am running from Interface !");
		
		
	}
}
  
class MyClass implements IntefaceBasics {

	@Override
	public void sayHello() {
		System.out.println("Implementation of sayHello() ");
	}

	public static void main(String[] args) {
		MyClass cs = new MyClass();
		cs.sayHello();
	}

}


