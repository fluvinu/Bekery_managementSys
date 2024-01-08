import java.sql.*;

public class Login {

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

public static String checkRol(User u) {
        String role = null;
        String q= "select roleIs from user_info where uname=? and pass=?";
        try {
            PreparedStatement pstmt = Login.conn.prepareStatement(q);
            pstmt.setString(1,u.getUserName());
            pstmt.setString(2,u.getUserPass());
            ResultSet rs=pstmt.executeQuery();
            while (rs.next()){
               role = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return role;
    }
}
