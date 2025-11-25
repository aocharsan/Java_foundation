package java8_Features;

import java.util.List;
import java.util.Optional;

import utilsClasses.Student;

public class OptionalClass {
	public static void main(String[] args) {
		
		/*
		   Why Optional class added in java8?
		    A container object that may or may not contain a non-null value.
		    Optional introduced to handle NullPointerException. Before java8,
		    if method doesn't return anything in result we usually return null,
		    so caller need to handle null check manually everytime to avoid NullPointerException.
		    But from Java8, instead of returning null, we return Optional object. This clearly
		    tells caller: 'This value might be missing. You need to handle it safely.'
		    
		    Optional<User> user = userService.findById(10);
            user.ifPresent(u -> System.out.println(u.getName()));
            
            It also gives us helpful methods like isPresent(), orElse(), and orElseThrow()
            to safely deal with the value.

            Static Methods:
            Optional.of()  ----> Creates an Optional with a non-null value. Throws NPE if null.
            Optional.ofNullable() ---> Creates an Optional that may hold a null value.
            Optional.empty() ---> create Optional with empty container.

            Instance Methods:
            return boolean
            isPresent();
            isEmpty();

            get(); ---> gives value from container if not, throw NoSuchElementException
            ifPresent(Consumer<T>);  ---> executes the given lambda only if value is present.
            orElse(T other);
            orElseThrow(Supplier<? extends Throwable>);
            orElseGet(Supplier<? extends T>) ---> executes a Supplier function only if Optional is empty.

            map(Function<? super T, ? extends U>);
            flatMap(Function<? super T, ? extends U>);
            filter(Predicate<? super T>);
	  
		 */
		
	Optional<Student> optionalStu=Optional.of(new Student(101, "Sanket D", 27, "Male", "Computer Science", "Singapore", 1001,List.of("8379946441", "7028301355")));
	optionalStu.ifPresent(System.out::println);
		
	Optional<String> name=OptionalClass.getName(101);
	//System.out.println("Hello Java lovers");
	 name.ifPresent(System.out::println);   // If a value is present, performs the given action with the value, otherwise does nothing.
	 
	// String nameget=name.isPresent()?name.get():"NA";   //If a value is present, returns the value, otherwise throws NoSuchElementException.
	String nameget=name.orElse("NA");
    System.out.println(nameget.toUpperCase());
	
    String stringDB=name.orElseGet(()->{	 // 
		       return "Getting value from backup method";
	       });   
    System.out.println(stringDB);
    
   // name.orElseThrow(()-> new NoSuchElementException("Elements not there with given ID number "));
     
	if(name.isPresent()) {
		System.out.println("Ram, mil gaya ! ");
	}
	
	// create Optional with empty container but why?
		Optional<Object> emptyContainer = Optional.empty();

	}
	
	private static Optional<String> getName(int id) {
		return Optional.of("Ram");
		//return Optional.empty();
	}

}
