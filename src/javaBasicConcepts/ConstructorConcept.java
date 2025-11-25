package javaBasicConcepts;

public class ConstructorConcept {
	
	String name;
     /*
        What is Constructor?
        Used to initialize the instance of a class. Constructor args allows us to provide parameters
        for initialization of object to ensure valid state.
             Types:
               1) Default // If not provided any constructor, During compilation auto generated.
               2) No-args
               3) Parameterized
 
       Can we inherit Constructor?
       No. If we try to do so, it will throw compilation error & also it is against java rule as
       constructor need to have same name as a class. But we can call Parent class from Child class
       using super() keyword.

       this: is a reference variable in Java that refers to the current object — i.e., the object whose
             method or constructor is being executed.
             Why Do We Need this?
              To differentiate between:
               Instance variables and local variables (when they have the same name)
               To call current class methods or constructors.

       super: is a reference variable that refers to the immediate parent class object.
        It is used to:
         Call parent class variables & methods (when overridden or hidden).
         Call parent class constructor.
        ✅ Java automatically adds super() if you don’t write it,
            but only if the parent has a no-arg constructor.
            If parent has a parameterized constructor, you must call it explicitly.

        Bonus: can't call this() and super() together in the same constructor. Only one first line is allowed.
               execution order ---> always from parent to child class.







      */
	public ConstructorConcept() {
		//super();              //  gives compilation error & used to call constructor of super class & must be a first statement.
		this("Ganpat");    //used call another constructor within same class
		this.name = "Hello Brother";
		System.out.println("no arg constructor being called");
	}

	public ConstructorConcept(String name) {
        
		this.name = name;
        System.out.println(name);
	}

	@Override
	public String toString() {
		return "ConstructorConcept [name=" + name + "]";
	}
	
	public static void main(String[] args) {
		ConstructorConcept concept=new ConstructorConcept();
		System.out.println(concept);
		
	}
	

}
