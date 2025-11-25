package collection;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import utilsClasses.Student;

public class QueueDemo {
	
	public static void main(String[] args) throws Exception {	
		// LL use as Queue impl
		LinkedList<Integer> list=new LinkedList<>();
		list.addLast(34);
		list.addLast(56);
		list.addLast(23);
		list.addLast(98);  // enqueue
		
		System.out.println(list.removeFirst());  // dequeue
		list.getFirst();  // peek
		
		// But may create confusion during impl so use Queue interface ref. and use it's methods.
		/*
		 Queue interface works on FIFO principle. LinkedList, PriorityQueue impl classes
		 */
		Queue<Integer> queue=new LinkedList<>();
		queue.add(23);
		queue.add(89);
		queue.add(90);
		queue.add(12);  //enqueue
		
		queue.offer(95);
		
		queue.poll();  // return null if Queue is empty
		
		if(!queue.isEmpty()){
			queue.remove();  //dequeue; it will throw Exception if Queue is empty.
		}
		queue.peek();
		queue.element(); // throws Exception if Queue is empty.
		
		Queue<Integer> queue2=new ArrayBlockingQueue<>(2);
		queue2.add(56);
		queue2.offer(59);
		
	 //	queue2.add(78);  throws Exception Queue is full
		queue2.offer(23); // return false rather throwing exception		
		
		System.out.println("-----------------------------------------");
		/*
		 PriorityQueue: impl of Queue interface which gives head element based on priority .
		 for primitive -- natural ordering (i.e. lowest first)
		 for custom-type -- used custom comparator
		 null not permit at all.
		 PriorityQueue is implemented as a min-heap by default(for natural ordering)
		 retrieving head -- O(1)
		 remaining -- O(log(n))
		 */
		
		PriorityQueue<Integer> pq=new PriorityQueue<>();
		pq.add(34);
		pq.add(89);
		pq.add(12);
		pq.add(5);
		pq.add(1);

		//System.out.println(pq); // not sorted as per priority
		while(!pq.isEmpty()) {
			System.out.println(pq.poll());
		}
		System.out.println("-------------------------------------------");
		 List<Student> students= Student.StudentUtils.getAllStudents();
		
		// set priority for custom-type 
		PriorityQueue<Student> pqCustom=new PriorityQueue<>(new DeptComparator());
		pqCustom.addAll(students);
		while (!pqCustom.isEmpty()) {
            System.out.println(pqCustom.poll());
        }
		
		System.out.println("-----------------------------------------");
		/*
		    Deque: Double ended queue which allows insertion & removal of elements
		    from both ends. More versatile than regular Queue & Stack bcz they
		    support all operations of both.
		      impl classes:  
		      ArrayDeque: Resizable array implementation (internally uses circular array
		       just shift head & tail pointer, not all elements)
		      so faster performance , low memory
		      null not allowed.
		      
		      LinkedList: use if frequent modification there bcz insert/remove takes O(1)
    
		 */
		Deque<Integer> deque=new ArrayDeque<>();  // initial capacity 16 --> doubles if full 
		deque.addFirst(34);
		deque.addFirst(67);
		deque.addLast(56);
		System.out.println(deque.peek());  // equ. to peekFirst()
		System.out.println(deque.peekLast());
		while(!deque.isEmpty()) {
			System.out.println(deque.poll());  // equ. to pollFirst
		}
		
		System.out.println("-----------------------------------------");
		/*
		  BlockingQueue: Thread-safe impl
		  wait for queue to become non-empty / wait for space
		  Simplify concurrency problem like producer-consumer
		  put() --> blocks if the queue is full until space becomes available
		  take() --> blocks if the queue is empty until element becomes available
		  offer() --> waits for space to become available, up to the specified timeout.
		  
		  impl classes:
		     BlockingQueue
		     LinkedBlockingQueue
		     PriorityBlockingQueue
		     SynchronousQueue
		     
		     But in Standard Queue ---> immediate operations happens
		       // empty -- remove() (no waiting)
		       // full --> add() (no waiting)
		   
		  
		 */
		BlockingQueue<Integer> arrayBQ=new ArrayBlockingQueue<>(5);
		// A bounded, blocking queue backed by circular array.
		// low memory overhead
		// uses a single lock, for enqueue & dequeue operations.
		// more threads --> creates problem
		Thread thread1=new Thread(new Producer(arrayBQ));
		//thread1.start();
		
		Thread thread2=new Thread(new Consumer(arrayBQ));
		//thread2.start();
		
		BlockingQueue<Integer> linkedBQ=new LinkedBlockingQueue<>();
		// optionally bounded backed by LinkedList.
		// uses two seperate locks for enqueue & dequeue operations.
		// higher concurrency between producers & consumers.
		
		BlockingQueue<Integer> priorityBQ=new PriorityBlockingQueue<>(5,Comparator.reverseOrder());
		// unbounded backed by Binary Heap as array & can grow dynamically.
		// default capacity 11.
		// Head is based on their natural ordering or a provided Comparator.
		
		BlockingQueue<String> synQueue = new SynchronousQueue<>();
		// each insert operation must wait for a corrosponding remove operation
		// by another and vice-versa.
		// it can't store elements, capacity at most one element.
		Thread producer=new Thread(()->{
			try {
				System.out.println("Producer is waiting to transfer.... ");
				synQueue.put("Hello from producer");
				System.out.println("producer has transfer the message successfully!");
			} catch (Exception e) {
				Thread.currentThread().interrupt();
				System.out.println("producer thread interrupted successfully!");
			}		
		});
		
		
		Thread consumer=new Thread(()->{
			try {
				System.out.println("Consumer is waiting to receive.... ");
				String msg=synQueue.take();
				System.out.println("Consumer has received the message "+msg);
			} catch (Exception e) {
				Thread.currentThread().interrupt();
				System.out.println("Consumer thread interrupted successfully!");
			}
	});
		producer.start();
		consumer.start();
		
	  System.out.println("-------------------------------------------");
	  
	  BlockingQueue<DelayedTask> delayQ=new DelayQueue<>();
	  // Thread-safe unbounded blocking Queue
	  // Elements can only be taken from the queue when their delay has expired
	  // useful for scheduling tasks to be executed when their delay has expired
	  // internally uses priority queue
	  // need to implement Delay iterface for Type
	  
	  delayQ.put(new DelayedTask("Task1", 5, TimeUnit.SECONDS));
	  delayQ.put(new DelayedTask("Task2", 2, TimeUnit.SECONDS));
	  delayQ.put(new DelayedTask("Task3", 3, TimeUnit.SECONDS));
	  while(!delayQ.isEmpty()) {
		DelayedTask task=delayQ.take();
		  System.out.println("Executed: "+task.getTaskName()+" at "+System.currentTimeMillis());
	  }
	  System.out.println("---------------------------------------");
	       Queue<String> conQueue=new ConcurrentLinkedQueue<>();
	       // An impl of Queue interface that supports lock-free, thread-safe operations.
	       // i.e. put() & take() executes simultaneously.
	       // compare & swap stratergy.
	       
	       ConcurrentLinkedDeque<String> conLD=new ConcurrentLinkedDeque<>();
	        // An impl of Deque interface that supports lock-free, thread-safe operations
	       // compare & swap stratergy.
	  

    }
}

class DelayedTask implements Delayed{
       
	private final String taskName;
	private final long startTime;
	
	public DelayedTask(String taskName, long delay, TimeUnit  unit) {
		this.taskName = taskName;
		this.startTime = System.currentTimeMillis() + unit.toMillis(delay);
	}
	
	public String getTaskName() {
		return taskName;
	}

	@Override
	public int compareTo(Delayed o) {
		if(this.startTime<((DelayedTask)o).startTime) return -1;
		if(this.startTime>((DelayedTask)o).startTime) return 1;
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		long remaining = startTime - System.currentTimeMillis();
		return unit.convert(remaining,TimeUnit.MILLISECONDS);
	}
	
}

class Producer implements Runnable {
   private BlockingQueue<Integer> queue;
   private int value=0;
   
	public Producer(BlockingQueue<Integer> queue) {
	this.queue = queue;
}

   @Override
	public void run() {
		while(true) {			
			try {
				System.out.println("Producer produced value: "+value);
				queue.put(value++);				
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			      Thread.currentThread().interrupt();
			      System.out.println("Producer interrupted");
			}
		}		
	}		
}

class Consumer implements Runnable {
       private BlockingQueue<Integer> queue;
       
		public Consumer(BlockingQueue<Integer> queue) {
		this.queue = queue;
	}
       @Override
		public void run() {
			while(true) {			
				try {
					int value=queue.take();
					System.out.println("Consumer consumed value: "+value);
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				      Thread.currentThread().interrupt();
				      System.out.println("Consumer interrupted");
				}
			}		
		}		
	}

class DeptComparator implements Comparator<Student> {

	@Override
	public int compare(Student s1, Student s2) {
		// set priority based on dept="CS"
		boolean isCSStu1 = s1.getDept().equalsIgnoreCase("Computer Engineering");
		boolean isCSStu2 = s2.getDept().equalsIgnoreCase("Computer Engineering");

		if (isCSStu1 && !isCSStu2)
			return -1; // means s1 comes before s2
		if (!isCSStu1 && isCSStu2)
			return 1; // means s2 comes before s1
		// else other based on dept natural ordering
		int sortByDept = s1.getDept().compareTo(s2.getDept());

		return sortByDept == 0 ? Integer.compare(s1.getRank(), s2.getRank()) : sortByDept;
	}
	

}



