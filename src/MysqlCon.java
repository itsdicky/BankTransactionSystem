import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

class MysqlCon{  
    ResultSet rs;
    Statement stmt;

    //URL,username,password
    String url,user,pass;

    public MysqlCon(String sqlUrl, String username, String password) throws ClassNotFoundException {
        //class
        Class.forName("com.mysql.jdbc.Driver");

        //URL,username,password
        this.url = sqlUrl;
        this.user = username;
        this.pass = password;

        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            //ResultSet rs = con.createStatement().executeQuery(query);
            
            /*
            while(rs.next()){
                String data = rs.getString("detail");
                System.out.println(data);
            }*/
            
            System.out.println("Koneksi berhasil");
            
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MysqlCon() {
    }  
} 