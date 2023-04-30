package Redis_Basics.Redis_Cache.model;

public class Order {

    private int id;
    private String name;

    public Order(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
