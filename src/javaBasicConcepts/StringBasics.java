package javaBasicConcepts;

public class StringBasics {
	public static void main(String[] args) {
		/*
		 * String (Group of characters) reference data type
		 Why string is immutable?
		  Security: often used to stored sensitive data like password, secret keys and so on.
		  If string were mutable, it can be changed may leads to serious security problems.
		  As it is immutable no one can change it after initialization.
		  
		  Thread-safety: By default, immutable objects are thread safe. So it can be safely 
		  shared among multiple threads without ext. synchronization.
		  
		  Improve performance using SCP(String constant pool) using strong reference
		   i.e. it keeps literal value inside GC to use again & again if same literal value created
		    until program not terminate.
		  
		  String also often used as key in Hashmap, if modified leading to incorrect result when
		  retrieving data from Hashmap.
		  
		  String Constant Pool(SCP): It is reserved area inside heap memory, where it stores
		  unique string literals to saved memory & improve performance.
		  Supposed, String name="Jalana";  // Stores into SCP & refered from SCP
|         Method & Signature                  |                         Description                             |
| -----------------------------------------   | --------------------------------------------------------------- |
| `append(String str)`                        | Appends the specified string to the current sequence            |
| `insert(int offset, String str)`            | Inserts the specified string at the given index                 |
| `replace(int start, int end, String str)    | Replaces characters from start to end with the specified string |
| `delete(int start, int end)`                | Removes characters from start to end                            |
| `deleteCharAt(int index)`                   | Removes the character at the specified index                    |
| `reverse()`                                 | Reverses the sequence of characters                             |
| `toString()`                                | Returns a `String` representing the current sequence            |
| `charAt(int index)`                         | Returns the character at the specified index                    |
| `setCharAt(int index, char ch)`             | Sets the character at the specified index                       |
| `length()`                                  | Returns the length of the sequence                              |
| `capacity()`                                | Returns the current capacity (allocated buffer size)            |
| `ensureCapacity(int minimumCapacity)        | Ensures the capacity is at least the specified minimum          |
| `substring(int start)`                      | Returns a substring from start index to the end                 |
| `substring(int start, int end)`             | Returns a substring from start to end (exclusive)               |
| `indexOf(String str)`                       | Returns the index of the first occurrence of the substring      |
| `lastIndexOf(String str)`                   | Returns the index of the last occurrence of the substring       |

		  
		  
           Mutable String classes:
		     StringBuffer -- Thread safe so suitable in multi-threaded env. but slow performance compare to StringBuilder due to synchronized methods.
		     StringBuilder -- Not Thread safe mainly used in single-threaded env.
		        
		     Why Do We Need Mutable String Classes?
		      As we know, Strings are Immutable so any further modification creates new string
		      each time, so there is performance overhead. Imagine supposed if we concatenate
		      string too many times, so ultimately reduce performance.
		      But Mutable string like StringBuffer & StringBuilder, modifies the same string
		      object rather creating new object each time so improves performance & reduce memory overhead.

		      Clear Doubt:
		       🔸 Immutability means the object’s state cannot change.
               🔸 Reassignment only changes which object the reference points to.
 
		 */
		  String town="Pune";   // refers to SCP stored value.
		  String city=new String("Pune").intern();  // refers to heap stored value. but can refer SCP literal value using intern() since Java7
  
		  System.out.println(city==town);
		  String name="Jalandhar";
		  name="Chandigarh";  // now it's referring to "Chandigarh" without modifying existing "Jalandhar"
		  
		  System.out.println(name);
		  
		  
		  StringBuilder str=new StringBuilder("Jalgaon ");
		  StringBuilder nstr=str.append(name);
		  System.out.println(nstr==str);  //true because both refers to same object.
	
		  str.insert(8, "(Khandesh) ");
		  str.delete(8, 18); // end: exclusive;  StringIndexOutOfBoundsException - if start is negative, greater than length(), or greater than end.
		  str.reverse();  // reverse : Jalgaon --> noaglaJ
		  str.trimToSize();
		  int capacity= str.capacity();
		  str.length();
		  str.substring(8);  //end: exclusive; StringIndexOutOfBoundsException - if start is less than zero, or greater than the length of this object.
		  System.out.println(capacity);
		  System.out.println(str);
		  
		
	}

}
