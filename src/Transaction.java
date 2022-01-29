import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Transaction {
    //query
    private String querySelect, queryInsert, queryUpdate;
    private String result; 
    private boolean userCheck;

    //date
    private long now = System.currentTimeMillis();
    private Date sqlDate = new Date(now);

    //access
    private String url = "jdbc:mysql://localhost:3306/bank";
    private String user = "root";
    private String pass = "";

    //check is user exist
    public boolean isUserExist(int bank_number) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        querySelect = "SELECT COUNT(*) FROM account WHERE bank_number='"+bank_number+"';";
        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            ResultSet rs = con.createStatement().executeQuery(querySelect);
            rs.next();
            if (rs.getString(1).equals("1")) {
                userCheck = true;
            } else {
                userCheck = false;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return userCheck;
    }

    //from bank_number to name
    public String toName(int bank_number) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        querySelect = "SELECT name_user FROM user WHERE bank_number='"+bank_number+"';";
        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            ResultSet rs = con.createStatement().executeQuery(querySelect);
            rs.next();
            result = rs.getString(1);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //update balance
    public String update(int bank_number) throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        querySelect = "SELECT balance FROM account WHERE bank_number = '"+bank_number+"';";
        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            ResultSet rs = con.createStatement().executeQuery(querySelect);
            rs.next();
            result = rs.getString(1);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //deposit
    public void deposit(int bank_number, int ammount) throws ClassNotFoundException {
        String date = sqlDate.toString();

        //prepare
        Class.forName("com.mysql.jdbc.Driver");
        queryInsert = "INSERT INTO transaction (bank_number, amount, detail, date) VALUES ('"+bank_number+"','"+ammount+"','deposit','"+date+"')";
        queryUpdate = "UPDATE account SET balance = balance + '"+ammount+"' WHERE bank_number = '"+bank_number+"';";

        //execute
        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(queryInsert);
            stmt.executeUpdate(queryUpdate);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //withdraw
    public void withdraw(int bank_number, int ammount) throws ClassNotFoundException {
        String date = sqlDate.toString();

        //prepare
        Class.forName("com.mysql.jdbc.Driver");
        queryInsert = "INSERT INTO transaction (bank_number, amount, detail, date) VALUES ('"+bank_number+"','"+ammount+"','withdraw','"+date+"')";
        queryUpdate = "UPDATE account SET balance = balance - '"+ammount+"' WHERE bank_number = '"+bank_number+"';";

        //execute
        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(queryInsert);
            stmt.executeUpdate(queryUpdate);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //transfer
    public void transfer(int bank_number, int rec_number, int ammount) throws ClassNotFoundException {
        String date = sqlDate.toString();

        //prepare tx
        Class.forName("com.mysql.jdbc.Driver");
        queryInsert = "INSERT INTO transaction (bank_number, amount, detail, date) VALUES ('"+bank_number+"','"+ammount+"','transfer','"+date+"')";
        queryUpdate = "UPDATE account SET balance = balance - '"+ammount+"' WHERE bank_number = '"+bank_number+"';";

        //execute tx
        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(queryInsert);
            stmt.executeUpdate(queryUpdate);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        //prepare rx
        queryInsert = "INSERT INTO transaction (bank_number, amount, detail, date) VALUES ('"+rec_number+"','"+ammount+"','receive','"+date+"')";
        queryUpdate = "UPDATE account SET balance = balance + '"+ammount+"' WHERE bank_number = '"+rec_number+"';";

        //execute rx
        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(queryInsert);
            stmt.executeUpdate(queryUpdate);
        }catch (SQLException e) {
            e.printStackTrace();
        }    
    }
}