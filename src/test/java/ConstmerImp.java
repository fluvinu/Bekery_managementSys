import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConstmerImp implements CostermerService {
    Scanner sc = new Scanner(System.in);

    String url="jdbc:mysql://localhost:3306/BekeryMngement";
    String uname = "root";
    String pass="tiger";

    Connection conn;

    {
        try {
            conn = DriverManager.getConnection(url,uname,pass);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public int appProduct(Product p) {
        System.out.println("login as admin");
        return 0;
    }

    @Override
    public int updateProduct(Product pro) {
        System.out.println("login as admin");
        return 0;
    }

    @Override
    public int deleteProduct(int id) {
        System.out.println("login as admin");
        return 0;
    }

    @Override
    public List<Product> vieProduct(String role) {
        List<Product> p=new ArrayList<>();
        String q= "select * from product";
        try {
            Statement stmt= conn.createStatement();
           ResultSet rs =stmt.executeQuery(q);
           while (rs.next()){
               Product pro=new Product();
               pro.setPid(rs.getInt(1));
               pro.setUserNmae(rs.getString(2));
               pro.setUserPas(rs.getDouble(3));
               p.add(pro);
           }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return p;
    }



    @Override
    public int placeOrder(String role, int pid, int qty) {
        int n=0;
        boolean status=true;
        String rl="user";
        double proCostId=0;
        int orderId = 0;
        String addOID2ordr="INSERT INTO order_info (user_id) VALUES (?);";
        String procostIdq= "select pPrice from product where pId=?";
        String FindOid="select max(oId) from order_info";
        String storeDta="INSERT INTO orders_info (Oid, pId, pQty, cost) VALUES (?, ?, ?, ?);";
        String reQtyty="update product set aValQty=aValQty-?  where pId = ?;";
        String totalCostOrder="update order_info set costTotal=(select sum(cost) from orders_info where Oid=?) where oId=?;";
        try {
            //inserting oid aoto incremt
            PreparedStatement pstmt= conn.prepareStatement(addOID2ordr);
            pstmt.setString(1,rl);
            pstmt.executeUpdate(); // uer insertd

          while (status){
              // stonging cost
              PreparedStatement pstmt1= conn.prepareStatement(procostIdq);
              pstmt1.setInt(1,pid);
              ResultSet rs= pstmt1.executeQuery();
              while (rs.next()){
                  proCostId=rs.getDouble(1)*qty;
              }
              // find order id
              Statement stmt2=conn.createStatement();
              ResultSet rsId=stmt2.executeQuery(FindOid);
              while (rsId.next()){
                  orderId=rsId.getInt(1);
              }
              // store data
              PreparedStatement pstmt3=conn.prepareStatement(storeDta);
              pstmt3.setInt(1,orderId);
              pstmt3.setInt(2,pid);
              pstmt3.setInt(3,qty);
              pstmt3.setDouble(4,proCostId);
              n= pstmt3.executeUpdate();

              //reduce quantity
              PreparedStatement pstmt4=conn.prepareStatement(reQtyty);
              pstmt4.setInt(1,qty);
              pstmt4.setInt(2,pid);
              n=pstmt4.executeUpdate();

              // total cost
              PreparedStatement pstmt5=conn.prepareStatement(totalCostOrder);
              pstmt5.setInt(1,orderId);
              pstmt5.setInt(2,orderId);
              pstmt5.executeUpdate();

              //more order in same oid
              System.out.println("placed order sussfully");
              System.out.println("do you want to order smthg els");
              System.out.println("1 yes ");
              System.out.println("2 no");
              int ch = sc.nextInt();
              switch (ch){
                  case 1:
                      boolean status2=true;
                      while (status2) {
                          System.out.println("enter product id ");
                          pid = sc.nextInt();
                          System.out.println("enter qty");
                          qty = sc.nextInt();
                          boolean status2w=valoOk(pid,qty);
                          if (status2w){
                              status2=false;
                          }else {
                              System.out.println("stock not presnt");
                          }
                      }
                      break;
                  case 2:
                      status=false;
                      break;
              }
          }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return n;
    }

    @Override
    public boolean valoOk(int pid, int qty) {
        int actualQty=0;
        String q = "select aValQty from product where pId="+pid+";";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs= stmt.executeQuery(q);
            while (rs.next()){
                actualQty = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        if(actualQty>=qty){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public int cencelOrderByName(String pName, int oId, String role) {
        int n=0;
        try {
            CallableStatement cstmt= conn.prepareCall("{call cancelOrder(?,?,?)}");
            cstmt.setInt(1,oId);
            cstmt.setString(2,pName);
            cstmt.setString(3,role);
            cstmt.execute();
            n=cstmt.getUpdateCount();
        } catch (SQLException e) {
            System.out.println(e);
        }

        return n;

    }

    @Override
    public List<Orders> viewOrders(String role) {
        List<Orders> pro=new ArrayList<>();
        String viwPro="select  o.oId ,o.user_id,\n" +
                "ors.Oid,ors.pId,ors.pQty,ors.cost,\n" +
                "p.pName from order_info o inner join orders_info ors on o.oId=ors.Oid inner join product p on ors.pId=p.pId where o.user_id=? order by ors.Oid";

        try {
            PreparedStatement pstmt= conn.prepareStatement(viwPro);
            pstmt.setString(1,role);
           ResultSet rs= pstmt.executeQuery();
           while (rs.next()){
               int oId=rs.getInt(1);
               String userNmae=rs.getString(2);
               int Pid=rs.getInt(4);
               int pQty=rs.getInt(5);
               double Cost=rs.getDouble(6);
               String pNmae=rs.getString(7);
               Orders urO=new Orders(oId,Pid,pQty,Cost,pNmae,userNmae);
               pro.add(urO);
           }
        } catch (SQLException e) {
            System.out.println(e);
        }
    return pro;

    }

    @Override
    public int deleteWholeOrder(int oidDeletAll, String role) {
        int n=0;
        String quary4odrs= "select ors.Oid,p.pId,p.pName,o.user_id from orders_info ors inner join product p on ors.pId = p.pId inner join order_info o on o.oId = ors.Oid where o.Oid=? and o.user_id=?;";
        String quary4oder="delete from order_info where oId=?";
        try {

            // ordrers delete
            PreparedStatement pstmt= conn.prepareStatement(quary4odrs);
            pstmt.setInt(1,oidDeletAll);
            pstmt.setString(2,role);
            ResultSet rs =pstmt.executeQuery();
            while (rs.next()){
               String pname=rs.getString(3);
                CallableStatement cstmt= conn.prepareCall("{call cancelOrder(?,?,?)}");
                cstmt.setInt(1,oidDeletAll);
                cstmt.setString(2,pname);
                cstmt.setString(3,role);
               boolean v= cstmt.execute();
               int count=0;
               if(v){
                   count++;
               }
                System.out.println(count);
            }
            // order delete
            pstmt=conn.prepareStatement(quary4oder);
            pstmt.setInt(1,oidDeletAll);
           n += pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return n;
    }

    @Override
    public List<Product> vieProductById(int oid) {
        List<Product> or= new ArrayList<>();
        String quaryK="select * from product where pId=?";
        try {
            PreparedStatement pstmt= conn.prepareStatement(quaryK);
            pstmt.setInt(1,oid);
           ResultSet rs= pstmt.executeQuery();
           while (rs.next()){
               Product pro= new Product(rs.getInt(1),rs.getString(2),rs.getDouble(3),0);
               or.add(pro);
           }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return or;
    }


}
