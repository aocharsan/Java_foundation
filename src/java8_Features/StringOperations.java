package java8_Features;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;



public class StringOperations {
	
	public static void main(String[] args) {
		String input="ilovejavatechie";
		
		//1. find each char occurance
		Map<String,Long> countEachChar=Arrays.stream(input.split(""))
		                               .collect(Collectors.groupingBy(Function.identity(),
		    		                                    Collectors.counting()));
		
		//2. find duplicates chars
		List<String> findDuplicates = countEachChar.entrySet().stream().filter(s -> s.getValue() > 1)
				                           .map(Map.Entry::getKey).toList();
		System.out.println(findDuplicates);
		
		//3. find first non-repeat element from given string
		       
		LinkedHashMap<String, Long> firstNonRepeatElem = Arrays.stream(input.split(""))
                                                               .collect(Collectors.groupingBy(Function.identity(),
                                                            	  LinkedHashMap::new,Collectors.counting()));
		String first = firstNonRepeatElem.entrySet()
		                  .stream()
		                  .filter(s->s.getValue()==1)
		                  //.map(Map.Entry::getKey)
		                  .findFirst().get().getKey();
		System.out.println(first);
		
		//4. find second higest no.from given 
		int[] arr= {23,45,67,21,17,9};
		IntStream intstream = Arrays.stream(arr);
		Optional<Integer> secondHighest=intstream.boxed()
		                                          .sorted(Comparator.reverseOrder())
		                                          .skip(1)
		                                          .findFirst();
		         System.out.println(secondHighest.get());
		         
		  //5. find longest string from given array       
		 String [] names= {"java","springboot","microservices","dockerk8amazonwebservices","madam","radar"} ;
	     String longestWord=Arrays.stream(names)
	    		                        .reduce((word1,word2)->word2.length()>word1.length()?word2:word1)
		                               //.sorted(Comparator.comparing(String::length).reversed())
		                               .get();
	     System.out.println(longestWord);

	     Arrays.stream(arr).boxed().map(s -> s+"")
	              .filter(str->str.startsWith("2"))
	              .forEach(System.out::println);
	     
	     System.out.println("---------------------------------");
	     //Given a list of strings, return a list of their lengths using map.
	   List<Integer> lengthOfEachString= Arrays.stream(names)
	                                                                .map(String::length)                     //str->str.length()
	                                                                .toList();
	   System.out.println(lengthOfEachString);
	     
	   System.out.println("---------------------------------");
		
	//   Flatten a list of lists using flatMap.
	   
	 List<List<Integer>> listOfLists=Arrays.asList(
	        	                	Arrays.asList(1,23,55,67),
	        		               Arrays.asList(34,76),
	        		               Arrays.asList(98,12,45)
	    		                 );
	   List<Integer> flatMapToList= listOfLists.stream().flatMap(num->num.stream())
			   .sorted(Comparator.reverseOrder())
			   .toList();
	   System.out.println(flatMapToList);
		
		// Counting each word from given String.
	      String sentence="hello world hello java world";
	      Map<String,Long> countEachWord=Arrays.stream(sentence.split(" "))
			                                                      .collect(Collectors.groupingBy(s->s,Collectors.counting()));
	        System.out.println(countEachWord);
	   
	    // check palindrome strings from list 
	       List<String> listOfPalindrome=Stream.of(names)
	                                                             .filter(StringOperations::isPalindrome)
	                                                             .toList();
	       listOfPalindrome.forEach(System.out::print);
	     

	   

	}
	
	public static boolean isPalindrome(String str) {
	  String string=str.trim().toLowerCase();
	  String revStr=new StringBuilder(string).reverse().toString();
	  return string.equals(revStr);
	}

}
