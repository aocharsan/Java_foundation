package java8_Features;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

public class DateNTimeAPI {
	public static void main(String[] args) {
		/*
		  Why Date & Time API introduced in java8? (package: java.time.*) .. legacy
		  Date & Calendar classes have some issues like they are mutable (i.e. it's
		  state can change after instantiation) and also not thread-safe(i.e. multiple
		  objects can't access it simultaneously) also have a limited
		  functionalities.(like there is difficult to express zones, durations,
		  periods)
		 */

		LocalDate today = LocalDate.now(); // returns current date from the system clock in the default time-zone.
		LocalDate customDate = LocalDate.of(2022, 9, 15);
		System.out.println(today.getDayOfMonth());
		System.out.println(today.getMonth());
		System.out.println(today.getYear());

		LocalDate pastDate = today.minusDays(100);
		System.out.println(pastDate);
		
		System.out.println("--------------------------------------------");

		LocalTime currentTime = LocalTime.now();
		LocalTime parsedDate = LocalTime.parse("15:30:45");
		System.out.println(currentTime.isBefore(parsedDate));
		System.out.println(currentTime.minusHours(2));
		
		System.out.println("--------------------------------------------");
		
		// Zoned Date & time based on UTC
		ZonedDateTime now=ZonedDateTime.now(ZoneId.of("Asia/Singapore"));
		System.out.println("Singapore current time: "+now);
		Set<String> allZoneIds=ZoneId.getAvailableZoneIds();
		//allZoneIds.forEach(System.out::println);
		
		// custom zone-time based on Zone-Id
		ZonedDateTime customSPoor=ZonedDateTime.of(2022, 3, 12, 13, 56, 34, 23, ZoneId.of("Asia/Singapore"));
		System.out.println(customSPoor);
		
		
		System.out.println("--------------------------------------------");
		
		long currentTimeMillis = System.currentTimeMillis();  //returns current time in milliseconds from 1 Jan 1970
		System.out.println(currentTimeMillis);    
		
		Instant instantNow = Instant.now(); //return current time in nanoseconds 
		System.out.println(instantNow.atZone(ZoneId.of("Asia/Singapore")));
		int sum=0;
		for(int i=0;i<=500000;i++) {
		     sum+=i;
		}
		
	  Instant end=Instant.now();
	  System.out.println(Duration.between(instantNow, end));  // generally used to deals with Hr, Minutes, Seconds....
	  System.out.println(Duration.of(1,ChronoUnit.MILLIS));
		
		System.out.println("------------------------------------------------");
		  LocalDate noww=LocalDate.now();
		  LocalDate then = LocalDate.of(2023, 3, 15);
		  Period period = Period.between(noww, then);
		  System.out.println(period);
		
		

	}

}
