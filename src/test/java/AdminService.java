public interface AdminService extends Service{

    int appProduct(Product p);

    int updateProduct(Product p);

    int deleteProduct(int id);
}
