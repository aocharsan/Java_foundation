package javaBasicConcepts;

import java.lang.reflect.*;
import java.util.Arrays;

public class ReflectionAPI {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
     /* Why Reflection API Exists?
         we know everything about your classes at compile-time — the class name, methods, fields, etc.
          But what if you want to:
           Inspect or modify a class at runtime,
           Access private fields or methods dynamically,
           Load classes whose names you don’t know until runtime,
           Build frameworks/tools like Spring, Hibernate, or IDEs which depend on class metadata?

           That’s where Reflection API comes in — it allows your program to analyze and manipulate class
            structure and behavior at runtime.

            🧠 Real Use Cases:
            Use Case                                    	Example
           Dependency Injection ---> Spring uses reflection to inject beans into other classes.
           ORM Mapping --->	Hibernate maps entity classes to DB tables using reflection.
           Testing Frameworks --->	JUnit uses reflection to find and execute @Test methods.
           IDEs & Tools	IntelliJ or Eclipse ---> use reflection to show class structure, methods, etc.
         So, Reflection = Runtime Inspection + Runtime Modification

         Bonus:
           ⚠️ Security Consideration:
              setAccessible(true) breaks encapsulation and should be used only in trusted environments like
             frameworks or test cases.
           ⚙️ Performance:
              Reflection operations are slower than direct access because they involve metadata lookups and
             security checks.
           💡Since Java 9:
               Accessing private members of classes in other modules may cause InaccessibleObjectException unless
              the module explicitly allows it using --> may need --add-opens JVM options to bypass restrictions.

               Spring Framework uses Reflection to inject dependencies, even if they’re private fields referring to
              other classes (like your Employee → Address example).
              🔹Performance Considerations
                Reflection is slower than direct access because:
                 JVM can’t optimize reflective calls like normal method calls.
                 It involves runtime type checking and security checks.

               🟡 Tip for production:
                Avoid using Reflection repeatedly in performance-critical code.
                Instead, use it once and cache metadata (like Method or Field references).




      */

        // getting class, method, modifier, fields, Constructors details at Runtime
       //   ✅ Step 1: Get the Class object
        //1) using class
        Class<?> clazz = Employee.class;

        // using getClass method on class instance
        Employee employee=new Employee();
        employee.setName("Rajaram");
        employee.setAddress(new Address("Lohgad"));
        Class<? extends Employee> empClass = employee.getClass();
        System.out.println(empClass);

        // using Class.forName()
        Class<?> aClass = Class.forName("javaBasicConcepts.Employee");
        System.out.println(aClass);
        System.out.println("------------------------------------------------------");

        // ✅ Step 2: Get class information
        String listAllInterfaces=Arrays.toString(clazz.getInterfaces());
        System.out.println(listAllInterfaces);
        String parentClassName = clazz.getSuperclass().getName();
        System.out.println(parentClassName);

        System.out.println("--------------------------------------------------------");

        // ✅ Step 3: Access fields, methods, and constructors
        Field [] allFields= clazz.getDeclaredFields();
        for(Field field:allFields){
            System.out.println(field);
        }
        System.out.println("--------------------------------------------------------");
       Method[] allMethods=clazz.getDeclaredMethods();
        for(Method method:allMethods){
            System.out.println(method);
        }
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            System.out.println("Constructor: " + constructor);
        }

        System.out.println("--------------------------------------------------------");
        // ✅ Step 4: Access private members
        Field nameField = clazz.getDeclaredField("name");
        Field addressField = clazz.getDeclaredField("address");
        nameField.setAccessible(true);
        addressField.setAccessible(true);
        nameField.set(employee,"Jaywant");
        // now getting Address Obj using get method on that referring field
        Object addressObject=addressField.get(employee);
        System.out.println("Address: " + addressObject);
        Class<?> addClass = addressObject.getClass();
        Field cityField = addClass.getDeclaredField("city");
        cityField.setAccessible(true);
        cityField.set(addressObject,"Indore");
        System.out.println(employee);

        System.out.println("--------------------------------------------------------");
        Method sayHello = clazz.getDeclaredMethod("sayHelloToEveryOne");
        sayHello.setAccessible(true);
        sayHello.invoke(employee);



    }
}
