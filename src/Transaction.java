import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class Transaction {
    //MysqlCon connection;
    //query
    String querySelect, queryInsert, queryUpdate;
    String result;

    //date
    long now = System.currentTimeMillis();
    Date sqlDate = new Date(now);

    //access
    String url = "jdbc:mysql://localhost:3306/bank";
    String user = "root";
    String pass = "";
    
    public String update(Integer bank_number) throws ClassNotFoundException {
        String banknum = Integer.toString(bank_number);
        
        Class.forName("com.mysql.jdbc.Driver");
        querySelect = "SELECT balance FROM account WHERE bank_number = '"+banknum+"';";
        result = "";

        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            ResultSet rs = con.createStatement().executeQuery(querySelect);

            while(rs.next()){
                result = rs.getString(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deposit(int bank_number, int ammount) throws ClassNotFoundException {
        String bank,amm,date;
        bank = Integer.toString(bank_number);
        amm = Integer.toString(ammount);
        date = sqlDate.toString();

        Class.forName("com.mysql.jdbc.Driver");
        queryInsert = "INSERT INTO transaction (bank_number, amount, detail, date) VALUES ('"+bank+"','"+amm+"','deposit','"+date+"')";
        queryUpdate = "UPDATE account SET balance = balance + '"+ammount+"' WHERE bank_number = '"+bank_number+"';";

        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(queryInsert);
            stmt.executeUpdate(queryUpdate);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void withdraw(int bank_number, int ammount) throws ClassNotFoundException {
        String bank,amm,date;
        bank = Integer.toString(bank_number);
        amm = Integer.toString(ammount);
        date = sqlDate.toString();

        Class.forName("com.mysql.jdbc.Driver");
        queryInsert = "INSERT INTO transaction (bank_number, amount, detail, date) VALUES ('"+bank+"','"+amm+"','withdraw','"+date+"')";
        queryUpdate = "UPDATE account SET balance = balance - '"+ammount+"' WHERE bank_number = '"+bank_number+"';";

        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(queryInsert);
            stmt.executeUpdate(queryUpdate);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void transfer(int bank_number, int rec_number, int ammount) throws ClassNotFoundException {
        String bank,amm,date,rec;
        bank = Integer.toString(bank_number);
        rec = Integer.toString(rec_number);
        amm = Integer.toString(ammount);
        date = sqlDate.toString();

        Class.forName("com.mysql.jdbc.Driver");
        queryInsert = "INSERT INTO transaction (bank_number, amount, detail, date) VALUES ('"+bank+"','"+amm+"','transfer','"+date+"')";
        queryUpdate = "UPDATE account SET balance = balance - '"+ammount+"' WHERE bank_number = '"+bank_number+"';";

        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(queryInsert);
            stmt.executeUpdate(queryUpdate);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        queryInsert = "INSERT INTO transaction (bank_number, amount, detail, date) VALUES ('"+rec+"','"+amm+"','receive','"+date+"')";
        queryUpdate = "UPDATE account SET balance = balance + '"+ammount+"' WHERE bank_number = '"+rec_number+"';";

        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(queryInsert);
            stmt.executeUpdate(queryUpdate);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}