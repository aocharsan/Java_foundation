package java_features;

public non-sealed class DepositTransaction extends Transaction {
    // use modifier either sealed, non-sealed or final


    @Override
    void sayHello() {
        System.out.println("Hey Hello from DepositTransaction......");
    }

    @Override
    String sayCustomHello(String name) {
        return "What you are doing here........: "+name;
    }

    public static void main(String[] args) {
        DepositTransaction transaction=new DepositTransaction();
        String jayMaharashtra = transaction.sayCustomHello("Jay Maharashtra");
        System.out.println(jayMaharashtra);
        transaction.sayHello();
    }
}

