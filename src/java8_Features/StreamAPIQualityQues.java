package java8_Features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StreamAPIQualityQues {
	public static void main(String[] args) {
		//1) Given List of Students & Subject-wise list of scores,
		// find top-performing student for each subject;
		List<Student> students = List.of(
				new Student(101, "Sanket A", "10A", List.of(new Score("Maths", 67), new Score("English", 91))),
				new Student(102, "Harshwardhan C", "10A", List.of(new Score("Maths", 79), new Score("English", 99))),
				new Student(103, "Ramanand A", "10B", List.of(new Score("Maths", 47), new Score("English", 78))),
				new Student(104, "Prem G", "10A", List.of(new Score("Maths", 97), new Score("English", 97))),
				new Student(105, "Jagan R", "10A", List.of(new Score("Maths", 68), new Score("English", 38))));	
	
		// Goal:
		// Output a Map<Subject,Topper>
		//The flatMap() operation has the effect of applying a one-to-many transformation 
		//to the elements of the stream, and then flattening the resulting elements into a new stream.
		
	   Map<String,Topper> subTopperList=students.stream()
	                                         .flatMap(st->st.getScores().stream()
	                                        		 .map(score->Map.entry(score.getSubject(), new Topper(
	  	                                        		  st.getName() , 
	  	                                        		 score.getMark()))) 
	                                        		 ).collect(Collectors.toMap(
	                                                		 Map.Entry::getKey,  //keyMapper
	                                                		 Map.Entry::getValue, //valueMapper
	                                                		 //merge function, used to resolve collisions between values
	                                                		 //associated with the same key
	                                        				 (exist,repl) ->repl.mark()>exist.mark()?repl:exist)
	                                        				 );
	   subTopperList.entrySet().forEach(System.out::println);
	   
	   System.out.println("-----------------------------------------------------------");
	   //2) Group Strings by First-Character
	   String[] names= {"Amar","Jagan","Vitthal","Ramesh","Banrakas","Jagan","Baban","Banrakas"};
	   Map<Character,List<String>> grpStrByFirst= 
			   Arrays.asList(names)
	                    .stream()
	                    .distinct()
	                    .collect(Collectors.groupingBy(str->str.charAt(0)));  //classifier
	   System.out.println(grpStrByFirst);
	           
	                                                         
	                                         
	
	
	
	
	
	
	
	
	}

}

class Student {

	private int rollNo;
	private String name;
	private String standard;
	private List<Score> scores=new ArrayList<>();

	public Student(int rollNo, String name, String standard, List<Score> scores) {
		// super();
		this.rollNo = rollNo;
		this.name = name;
		this.standard = standard;
		this.scores = scores;
	}

	public int getRollNo() {
		return rollNo;
	}

	public String getName() {
		return name;
	}

	public String getStandard() {
		return standard;
	}

	public List<Score> getScores() {
		return scores;
	}

	@Override
	public String toString() {
		return "Student [rollNo=" + rollNo + ", name=" + name + ", standard=" + standard + ", scores=" + scores + "]";
	}

}

class Score {
	private String subject;
	private double mark;

	public Score(String subject, double mark) {
		super();
		this.subject = subject;
		this.mark = mark;
	}

	public String getSubject() {
		return subject;
	}

	public double getMark() {
		return mark;
	}

	@Override
	public String toString() {
		return "Score [subject=" + subject + ", mark=" + mark + "]";
	}

}

record Topper(String name,double mark){
	//create immutable class
	//auto provides getters(), constructor(), hashCode(), equals() & toString() 
}

