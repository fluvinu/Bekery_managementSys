import java.util.List;

public interface Service {
//    Service checkRol(User u);

    int appProduct(Product p);

    int updateProduct(Product pro);

    int deleteProduct(int id);
    List<Product> vieProduct(String role);
}
