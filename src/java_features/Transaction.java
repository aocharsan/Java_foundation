package java_features;

public sealed class Transaction permits DepositTransaction{

    void sayHello(){
        System.out.println("Hello Brother..........");
    }
    String sayCustomHello(String name){
        return name;
    }
}
