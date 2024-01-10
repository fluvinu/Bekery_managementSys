import java.sql.Timestamp;

public class Orders {
    private int oid;
    private  int pid;
    private int qty;
    private double cost;

    private  String name; // use as a role in cencel whole order
    private String userName;


    public Orders(int oid, int pid, int qty, double cost, String name, String userName) {
        this.oid = oid;
        this.pid = pid;
        this.qty = qty;
        this.cost = cost;
        this.name = name;
        this.userName = userName;
    }

    public int getOid() {
        return oid;
    }

    public int getPid() {
        return pid;
    }

    public int getQty() {
        return qty;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }
}
