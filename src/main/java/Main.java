import io.quarkus.runtime.Quarkus;

public class Main {
    public static void main(String[] args) {
        System.out.println("1 " + System.getProperty("quarkus.datasource.jdbc.url"));
        System.out.println("2 " + System.getProperty("%prod.quarkus.datasource.jdbc.url"));
        System.out.println("3 " + System.getProperty("prod.quarkus.datasource.jdbc.url"));
        Quarkus.run(args);
    }
}
