package javaBasicConcepts;

import java.lang.ref.WeakReference;

public class GarbageCollector {
	public static void main(String[] args) {
		/*
		Java Architecture: defines how Java code is written, compiled, and executed — involving multiple layers from
		    source code → bytecode → JVM → OS.
		    Java is popular for "Write once & run anywhere"

		       .java Source Code
                     ↓  (javac)
               .class Bytecode (platform-independent)
                     ↓  (JVM)
                Machine Code (platform-dependent)
                     ↓
                CPU Executes

        Components of Java architecture: JDK, JRE and JVM
           +----------------------------------------------------+
           |                  JDK (Developer Kit)               |
           |  +----------------------------------------------+  |
           |  |   JRE (Runtime Environment)                  |  |
           |  |   +--------------------------------------+   |  |
           |  |   |   JVM (Virtual Machine)              |   |  |
           |  |   +--------------------------------------+   |  |
           |  |   +-- Core Libraries & Runtime Files ----+   |  |
           |  +----------------------------------------------+  |
           |  +-- Development Tools (javac, jar, etc.) ------+  |
           +----------------------------------------------------+

         JRE = JVM + Core Libraries + Supporting Files
         JRE provides the runtime environment — a bridge between your bytecode and the operating system.
         Without JRE, OS won’t know how to execute the bytecode created by javac.
         It provides the runtime environment that allows Java programs to run, but not to develop or compile.

         In Java 9 and later, JRE and JDK are no longer distributed separately.
         Now everything is modularized under the JDK, but conceptually, the JRE part still exists inside it
         — it’s just not a separate installation.

       | Component                  |                 Description                                     | Example                                       |
       | ---------------------------| --------------------------------------------------------------- | --------------------------------------------- |
       | Core Libraries             | Set of prebuilt Java classes and APIs                           | `java.lang`, `java.util`, `java.io`           |
       | Runtime Files              | OS-level configuration files that support JVM to execute code   | `.dll`, `.so`, `.properties`, `.policy` files |
       | Together they form the JRE | The environment needed to run Java apps                         | JVM + Core Libs + Runtime Files               |


		Java Memory Management(JMM):
		   process of allocating & releasing memory automatically to store objects created during program execution.
                                   +---------------------------+
                                   |       Class Loader        |
                                   +---------------------------+
                                   |      Runtime Data Area    |   ← (Memory Management happens here)
                                   +---------------------------+
                                   |       Execution Engine    |
                                   +---------------------------+

         Java divides memory into several logical regions:
            JVM Memory (Runtime Data Area)
            ├── Heap Area
            ├── Stack Area
            ├── Method Area (a.k.a. Metaspace in Java 8+)
            ├── PC Registers
            ├── Native Method Stack


            |           Region              |                   Description                          |
            | ----------------------------- | ------------------------------------------------------ |
            | Method Area / Metaspace       | Stores class metadata, methods, and static variables.  |
            | Heap                          | Stores Java objects (managed by Garbage Collector).    |
            | Stack (per thread)            | Stores method calls, local variables, and references.  |
            | Native Memory (C/C++ level)   | Used by JVM internals, threads, and DirectByteBuffers. |
            | Code Cache                    | Holds JIT-compiled native machine code.                |


         Heap area(Main memory): largest memory area inside JVM.used to store objects & shared among all threads.
           Subdivided Into:
        ==> Young Generation (Eden + Survivor Spaces)
            Where new objects are created.
            Divided into:
            Eden Space → new objects first allocated here.
            Survivor Spaces (S0 and S1) → objects that survive GC moved from Eden space to Survivor space.

        ==> Old Generation (Tenured Space)
           Contains objects that have survived multiple GC cycles.
           Usually long-lived objects.

          Lifecycle in Heap:  New Object → Eden → Survivor (S0/S1) → Old Gen (after several GCs)

         Permanent Generation / Metaspace:
          Before Java 8: metadata (class info, method info, static variables, constant pool) stored in PermGen.
          Java 8 onwards: stored in Metaspace, which is outside heap and uses native OS memory &
           grows dynamically (so, no PermGen space anymore).

         Stack Area (Thread-Specific): 🧠 Primitives and object references (not actual objects) are stored &
         also stores local variables during method execution.
          Each thread in Java gets its own stack. Stores method call frames.
          Each frame holds:
            Local variables
            Method parameters
            Return address
          When a method is called → a new frame is pushed to the stack.
          When the method returns → the frame is popped out from stack.
            Variables within scope only visible & as soon as any variable goes out of scope, it
		    removed from Stack in LIFO manner, if Stack full throws StackOverflowError.

         PC Register (Program Counter): Each thread has its own PC Register. It Keeps track of the
         address of the currently executing JVM instruction. It also helps JVM know which instruction
         to execute next.

         Native Method Stack: Used when Java interacts with native (non-Java) code using JNI (Java Native Interface).
         Stores native method information for C/C++ calls.

                 ┌────────────────────────┐
                 │        JVM Memory      │
                 ├────────────────────────┤
                 │        Method Area     │ ← class info, static vars
                 ├────────────────────────┤
                 │         Heap           │ ← objects, instance data
                 │ ┌───── Young Gen ────┐ │
                 │ │ Eden │ S0 │ S1     │ │
                 │ └─────── Old Gen ────┘ │
                 ├────────────────────────┤
                 │      Thread Stack      │ ← local vars, references
                 ├────────────────────────┤
                 │   PC Register & Native │
                 └────────────────────────┘



		 Garbage Collection: It is a background process i.e. run as demon thread(low priority)
		 which runs indeterministically & cleans unreferenced objects from heap memory.
		  mark & sweep algorithm used.
		  Bonus: Part of the execution engine because it manages runtime memory while execution happens.
		 
		 We can also request JVM to run garbage collector but doesn't give guarantee to execute.
		 There are two ways:
		     1) Using System.gc();  
		     2) Runtime.getRuntime().gc();
		     
		 finalize(): Object class method called by GC automatically just before destroying
		 unreferenced objects from heap memory.

		 Class Loader: responsible for loading java .class files (byte-code) dynamically to JVM during runtime
		 by using ClassLoader.loadClass() method which tries to load class based on their fully qualified name.(lazily loaded)
		 Each class loader delegates loading to its parent first:
		     CustomClassLoader → SystemClassLoader → PlatformClassLoader → BootstrapClassLoader
		 Command to execute java code:
		  It does 3 things:
           Loading → Loads your Hello.class into memory.
           Linking → Verifies bytecode for security & consistency.
           Initialization → Runs static blocks, initializes static variables.

		  javac MyClass.java --> converts into bytecode (MyClass.class)
		  java MyClass -->
		   1️⃣ JVM starts.
           2️⃣ The Bootstrap ClassLoader loads core Java classes (like java.lang.Object, String, etc.)
           3️⃣ The System ClassLoader loads MyApp.class.
           4️⃣ Once loaded, the JVM executes the main() method.


         Types of ClassLoader:
          1) BootStrap classLoader -- loads all java core classes from predefined packages.
          2) System classLoader -- loads all classes from dependencies added to classpath.
          3) Platform classLoader -- loads all classes from extension library.
           If class not found during loading, it will throw classNotFoundException.

        How Java Codes executes step-by-step:
          1️⃣ Source code (.java) → Compiled by javac → Bytecode (.class)
          2️⃣ ClassLoader → Loads bytecode into Method Area of JVM
          3️⃣ Execution Engine → Reads bytecode instructions and executes them on CPU

          Interpreter:Reads and executes bytecode line by line (instruction by instruction).
          JIT (Just-In-Time) Compiler:Compiles frequently used bytecode into native machine code for faster execution &
          stores in code cache. Once compiled, that native code is stored in the Code Cache, so it can run directly on
          the CPU — superfast. So, the Execution Engine is the bridge between the JVM bytecode and your hardware (CPU).

                              +-----------------------------------------+
                              |            Execution Engine             |
                              +-----------------------------------------+
                              |   Interpreter                           |
                              |   JIT Compiler                          |
                              |   Garbage Collector                     |
                              |   JNI (Java Native Interface)           |
                              +-----------------------------------------+
                                            ↓ Executes on
                                         Hardware (CPU, Memory)

     1️⃣ What Is JVM Tuning?
         adjusting the memory & performance parameters of the JVM to achieve optimal performance
          — balancing speed, throughput, and memory usage.

     2️⃣ Why JVM Tuning Is Needed
       By default, JVM chooses memory and GC settings automatically.
       But for large enterprise apps (like Spring Boot microservices or BFSI systems),
       default settings are not always optimal.

       Common issues that indicate JVM tuning is needed:
        High latency or slow response times
        Frequent GC pauses
        OutOfMemoryError
        High CPU usage
        Application crashes under load

        | Parameter                               | Meaning                               |
        | --------------------------------------- | ------------------------------------- |
        | `-Xms`                                  | Initial heap size                     |
        | `-Xmx`                                  | Maximum heap size                     |
        | `-Xss`                                  | Stack size per thread                 |
        | `-XX:MetaspaceSize`                     | Initial size of Metaspace             |
        | `-XX:MaxMetaspaceSize`                  | Maximum size of Metaspace             |
        | `-XX:+UseG1GC`                          | Use G1 Garbage Collector              |
        | `-XX:+UseParallelGC`                    | Use Parallel GC (good for throughput) |
        | `-XX:+UseZGC` or `-XX:+UseShenandoahGC` | Low-latency GCs for large-scale apps  |
        | `-XX:+PrintGCDetails`                   | Print detailed GC logs                |
        | `-Xlog:gc*`                             | Log GC events (Java 9+)               |

         Command eg. --> java -Xms512m -Xmx2g -XX:+UseG1GC -jar myapp.jar

      3️⃣ Tools Used for JVM Monitoring & Tuning:
        | Tool                           | Purpose                                  |
        | ------------------------------ | ---------------------------------------- |
        | **JVisualVM**                  | Monitor heap, threads, GC in real-time   |
        | **JConsole**                   | Basic JVM metrics                        |
        | **Java Mission Control (JMC)** | Advanced profiling with Flight Recorder  |
        | **GC Logs Analysis**           | To identify memory leaks or frequent GCs |
        | **Prometheus + Grafana**       | Common in production monitoring setups   |

      Java 9 – Java 17	G1 GC (Garbage-First)	Balances throughput and low pause times — became the default.
      Java 18+	G1 GC remains default, but ZGC and Shenandoah GC are production-ready and can be used for low-latency systems.

      You can check which GC your JVM is using with: java -XX:+PrintCommandLineFlags -version


		  
		 */
		   Person person=new Person(); //Strong reference
		   getPersonDetails(person);
		  // person=null;
		   System.gc();  // request JVM to execute it, but not guarantee.
		   System.out.println(person);
		
		   WeakReference<Person> person1=new WeakReference<Person>(new Person("Jagan R",102));
		   System.out.println(person1.get());
		   System.gc();
		   try {
			  Thread.sleep(5000);
		  } catch (Exception ignored) {
			  Thread.currentThread().interrupt();
			  System.out.println("Thread interrupted!");
		}	   
		   System.out.println(person1.get());
		
		
	}

	public static void getPersonDetails(Person person){
		Person person1=person;
		person1.setName("Ramlal Singh");
		person1.setId(101);
		String department="Computer Science";
		System.out.println(person1+" : Dept: "+department);

	}
}

class Person {

	private String name;
	private int id;

	public Person(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public Person() {
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", id=" + id + "]";
	}

}
