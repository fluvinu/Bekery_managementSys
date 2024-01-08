import java.util.List;

public interface CostermerService extends Service{

    int placeOrder(String role, int pid, int qty);

    boolean valoOk(int pid, int qty);


    int cencelOrderByName(String pName, int oId, String role);

    List<Orders> viewOrders(String role);

    int deleteWholeOrder(int oidDeletAll);

    List<Product> vieProductById(int pidS);
}
