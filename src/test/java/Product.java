import java.util.PrimitiveIterator;

public class Product {

    private int Pid;
    private String producNmae;
    private double pPrice;
    private  int pQty;

    public Product( String userNmae, double userPas, int pQty) {
        this.producNmae = userNmae;
        this.pPrice = userPas;
        this.pQty = pQty;
    }

    public Product(int pid, String producNmae, double pPrice, int pQty) {
        Pid = pid;
        this.producNmae = producNmae;
        this.pPrice = pPrice;
        this.pQty = pQty;
    }
    public Product(){

    }

    public int getPid() {
        return Pid;
    }

    public void setPid(int pid) {
        Pid = pid;
    }

    public String getUserNmae() {
        return producNmae;
    }

    public void setUserNmae(String userNmae) {
        this.producNmae = userNmae;
    }

    public double getUserPas() {
        return pPrice;
    }

    public void setUserPas(double userPas) {
        this.pPrice = userPas;
    }

    public int getpQty() {
        return pQty;
    }

    public void setpQty(int pQty) {
        this.pQty = pQty;
    }
}
