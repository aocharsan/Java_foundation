package javaBasicConcepts;

public class Cloning {

	public static void main(String[] args) {
		/*
		  Cloning: process of creating exact copy of existing object in memory.
		  for that clone() from Object class used & also need to implement Clonable
		  interface to the class whose object going to clone. By default, clone()
		  creates a shallow copy of object it copies primitive fields but for custom object
		  it just copy reference. so, any changes made to clone object will be reflected to
		  original one or vice versa.
		  
		  Deep copy: creating a new object that is a fully independent copy of the
		  original object, including copies of all objects referenced by the original.
		  There are two ways: 1) Using Copy constructor 2) By Implementing clonable interface

		  |     Type         |                       Meaning                                           |           Example                |
          | ---------------- | ----------------------------------------------------------------------- | -------------------------------- |
          | Shallow Copy     | Copies primitive fields, but copies references as-is for objects.       | `obj.clone()` (default behavior) |
          | Deep Copy        | Copies primitives & creates **new copies of referenced objects.         | Custom logic in `clone()`        |

          How to check deep copy or shallow copy?
		    | Type of Copy     |          Object reference behavior    |         Example with `Address` in `Employee`              |
            | ---------------- | ------------------------------------- | --------------------------------------------------------- |
            | Shallow Copy     | Inner objects (references) are shared | `employee.getAddress() == employee1.getAddress()` → true  |
            | Deep Copy        | Inner objects are newly created       | `employee.getAddress() == employee1.getAddress()` → false |

		 


	  
		 */
		Address address = new Address("Chh. Sambhaji nagar");
		Employee employee = new Employee("Sanket", address);
		employee.setName("Roshan Aochar");
		employee.getAddress().setCity("Mumbai");

		// Create a shallow copy
		Employee employee1 = employee.clone();

		// Modify the copy
		employee1.setName("Ramchandra");
		employee1.getAddress().setCity("Bangalore");

		// Demonstrating shallow copy
		System.out.println("Original object: "+employee);
		System.out.println("Cloned object: "+employee1);
		System.out.println("referential integrity for shallow cloning: "+(employee.getAddress()==employee1.getAddress()));

		System.out.println("--------------------------------------------");
		// crate a deep copy
		Employee employee2=new Employee(employee);
		employee2.setName("John Kennedy");
		employee2.getAddress().setCity("Prayagraj");

		// demonstrating deep copy
		System.out.println("Original object: "+employee);
		System.out.println("Cloned object: "+employee2);
		System.out.println("referential integrity for deep cloning: "+(employee.getAddress()==employee2.getAddress()));


	}

}

class Address implements Cloneable {
	String city;

	// Regular constructor
	public Address(String city) {
		this.city = city;
	}

	// Copy constructor
	public Address(Address other) {
		this.city = other.city; // primitives / immutable fields can be copied directly
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return city;
	}

    @Override
    public Address clone() {
        try {
            Address clone = (Address) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}

class Employee implements Cloneable {
	private String name;
	private Address address;

	public Employee() {
	}

	// Regular constructor
	public Employee(String name, Address address) {
		this.name = name;
		this.address = address;
	}


	public Employee(Employee other) {
		this.name = other.name;
		this.address = new Address(other.getAddress()); // deep copy of referenced object
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return name + " | " + address;
	}

    @Override
    public Employee clone() {
        try {
            Employee clone = (Employee) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

	private void sayHelloToEveryOne(){
		System.out.println("Hello, Everyone I am Sanket and going to become senior Java developer soon..........");
	}
}

