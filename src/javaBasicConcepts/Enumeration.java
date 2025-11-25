package javaBasicConcepts;

public class Enumeration {
     public static void main(String[] args) {
		System.out.println(Day.MONDAY);
		System.out.println(Day.THRUSDAY.ordinal());
	}	
}

enum Day {
	MONDAY, TUESDAY, WEDNSDAY, THRUSDAY, FRIDAY, SATURDAY, SUNDAY
}
