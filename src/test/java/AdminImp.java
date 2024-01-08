import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminImp implements AdminService{

    static String url="jdbc:mysql://localhost:3306/BekeryMngement";
    static String uname = "root";
    static String pass="tiger";

    static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(url,uname,pass);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public int appProduct(Product p){
        int n=0;
        String q ="insert into product values(?,?,?,?)";
        PreparedStatement pstmt;

    {
        try {
            pstmt = conn.prepareStatement(q);
            pstmt.setInt(1,p.getPid());
            pstmt.setString(2,p.getUserNmae());
            pstmt.setDouble(3,p.getUserPas());
            pstmt.setInt(4,p.getpQty());
             n= pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    return n;
}

    public int updateProduct(Product p){
        int n=0;
       String q= "update product set pId=?,pName=?,pPrice=?,aValQty=? where pId=?;";
        try {
            PreparedStatement pstmt= conn.prepareStatement(q);
            pstmt.setInt(1,p.getPid());
            pstmt.setString(2,p.getUserNmae());
            pstmt.setDouble(3,p.getUserPas());
            pstmt.setInt(4,p.getpQty());
            pstmt.setInt(5,p.getPid());
            n= pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return n;
    }

    @Override
    public int deleteProduct(int id) {
        int n=0;
        String q="delete from product where pId = ?";
        try {
            PreparedStatement pstmt=conn.prepareStatement(q);
            pstmt.setInt(1,id);
            n= pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return n;
    }

    @Override
    public List<Product> vieProduct(String role) {
        List<Product> pro=new ArrayList<>();
        String q = "select * from product";
        try {
            Statement stmt= conn.createStatement();
            ResultSet rs =stmt.executeQuery(q);
            while (rs.next()){
                Product p = new Product(rs.getInt(1),rs.getString(2),rs.getDouble(3),rs.getInt(4));
                pro.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return pro;
    }
}
