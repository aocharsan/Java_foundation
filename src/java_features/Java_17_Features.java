package java_features;


public class Java_17_Features {
    public static void main(String[] args) {
    /*
      var (not a keyword but a reserved type name):--->
      It allows local variable type inference — meaning the compiler automatically determines
      the variable’s type based on the value assigned to it.
      The compiler infers the type at compile time, not at runtime.
      The inferred type is fixed once decided — it’s not dynamic typing (unlike JavaScript or Python).
      ✅ Rules
         Can only be used for local variables (inside methods, loops, blocks).
         The variable must be initialized when declared — otherwise, the compiler can’t infer the type.
       Cannot be used for:
         Fields (class-level variables)
         Method parameters or return types
         Lambda parameters (in older Java versions)

         var map = new HashMap<String, Integer>();  // clear and concise
         var names=new ArrayList<String>();
         var data = process();  // ❌ Type of data not obvious


        Sealed classes: restricts the classes to extend a class using sealed keywords.
         public sealed class SealedClass permits ChildSealedClass{ }
         public final/sealed/non-sealed class ChildSealedClass{ }

       Pattern matching for instanceOf method: Pattern Matching simplifies the process of checking an object’s type
       and then casting it automatically.
       This eliminates the need for complex instanceof checks and casting.

       Java 21 features:
         Virtual thread
         Record patterns


     */
        // Variable string is pattern variable, scoped only within the if block.
        Object name="Sanket Aochar";
        if(name instanceof String string){
            System.out.println(string.toUpperCase());
        }

        String circle = calculateShapeArea("rectangle");
        System.out.println(circle);

        // enhanced switch expression:
        Day day=Day.MONDAY;
        String result = switch (day) {
            case MONDAY, FRIDAY -> "Weekday";
            case SATURDAY, SUNDAY -> "Weekend";
            default -> throw new IllegalArgumentException("Invalid day");
        };
        System.out.println(result);




    }


    public static String calculateShapeArea(String str){
        switch (str.toLowerCase()){
            case "circle" -> {
                return "Hello Circle";
            }
            case "rectangle" -> {
                return "Hello rectangle";
            }
            default -> {
                return  "Nothing gonna happen here...";
            }
        }
    }

    enum Day {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY, MAY
    }




}


