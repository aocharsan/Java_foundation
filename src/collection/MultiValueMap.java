package collection;

import java.util.*;

public class MultiValueMap<K,V> {

   /*
     A MultiValuedMap (or Multimap) is a type of map where a single key can be associated with multiple values.
       👉 In a normal Map<K, V> —
           each key maps to exactly one value.
       👉 In a MultiValuedMap<K, V> —
           each key maps to a collection of values (e.g., a List<V> or Set<V>).
           Supports One-to-Many Mapping

    Implementations in Java:
     There is no direct MultiValuedMap in the Java standard library,
      but there are popular implementations in external libraries:

              Library	                                                        Class Description
     Apache Commons Collections	ArrayListValuedHashMap	     Uses ArrayList for values (duplicates allowed)
     Guava	Multimap / ListMultimap / SetMultimap	         Google’s collection framework with rich support
     Custom Implementation	Map<K, List<V>>	                 Manual approach using Java Collections

    Use Cases:
     A student and their multiple subjects
      → studentId → List<Subject>
     A department and multiple employees
      → dept → List<Employee>
     HTTP request headers
      → headerName → List<String> (some headers can appear multiple times)




    */
    private final Map<K, List<V>> map = new HashMap<>();

    // Add a single value for a key
    public void put(K key, V value) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    // Get all values for a key
    public List<V> get(K key) {
        return map.getOrDefault(key, Collections.emptyList());
    }

    // Remove a specific value for a key
    public boolean remove(K key, V value) {
        List<V> values = map.get(key);
        if (values != null) {
            boolean removed = values.remove(value);
            if (values.isEmpty()) {
                map.remove(key);
            }
            return removed;
        }
        return false;
    }

    // Remove all values for a key
    public void removeAll(K key) {
        map.remove(key);
    }

    // Get all keys
    public Set<K> keySet() {
        return map.keySet();
    }

    // Check if key exists
    public boolean containsKey(K key) {
        return map.containsKey(key);
    }

    // Check if key-value pair exists
    public boolean containsEntry(K key, V value) {
        List<V> values = map.get(key);
        return values != null && values.contains(value);
    }

    // Print or view internal map
    @Override
    public String toString() {
        return map.toString();
    }



    public static void main(String[] args) {

        MultiValueMap<String,List<String>> multiValueMap=new MultiValueMap<>();
        multiValueMap.put("West Maharashtra",List.of("Pune","Satara","Sangli","Kolhapur","Solapur"));
        multiValueMap.put("Kokan",List.of("Thane","Raigad","Ratnagiri","Sindhudurg","Palghar","Mumbai"));
        multiValueMap.put("Marathwada",List.of("Chh. Sambhaji Nagar","Dharashiv","Latur","Beed"));
        multiValueMap.put("Vidharbha",List.of("Amravati","Akola","Buldana","Yawatmal","Washim"));
        System.out.println(multiValueMap);


    }

}
