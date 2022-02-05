import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.Statement;
import java.sql.Connection;


public class RegisterFrame extends JFrame{
    String url = "jdbc:mysql://localhost:3306/bank";
    String user = "root";
    String pass = "";
    
    private JLabel titleJLabel = new JLabel("Register");
    private JLabel nameJLabel = new JLabel("Name :");
    private JLabel bankNumberJLabel = new JLabel("Bank Number :");
    private JLabel phoneJLabel = new JLabel("Phone :");
    private JLabel emailLabel = new JLabel("Email :");
    private JLabel passwordLabel = new JLabel("Password :");
    private JTextField namaTextField = new JTextField();
    private JTextField bankTextField = new JTextField();
    private JTextField phoneTextField = new JTextField();
    private JTextField emailTextField = new JTextField();
    private JTextField passwordTextField = new JTextField();
    private JButton submitButton = new JButton("Submit");

    public RegisterFrame() {
        setFrame();
        initComponents();
        setListener();
    }

    private void setFrame() {
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setTitle("Register");
    }

    private void initComponents() {
        titleJLabel.setBounds(120, 25, 80, 20);
        nameJLabel.setBounds(30, 80, 200, 20);
        bankNumberJLabel.setBounds(30, 110, 200, 20);
        phoneJLabel.setBounds(30, 140, 200, 20);
        emailLabel.setBounds(30, 170, 200, 20);
        passwordLabel.setBounds(30, 200, 200, 20);
        namaTextField.setBounds(125, 80, 130, 20);
        bankTextField.setBounds(125, 110, 130, 20);
        phoneTextField.setBounds(125, 140, 130, 20);
        emailTextField.setBounds(125, 170, 130, 20);
        passwordTextField.setBounds(125, 200, 130, 20);
        submitButton.setBounds(100, 300, 80, 25);

        add(titleJLabel);
        add(nameJLabel);
        add(phoneJLabel);
        add(bankNumberJLabel);
        add(emailLabel);
        add(passwordLabel);
        add(namaTextField);
        add(bankTextField);
        add(phoneTextField);
        add(emailTextField);
        add(passwordTextField);
        add(submitButton);
    }

    private void setListener() {
        submitButton.addActionListener(evt -> {
            try {
                register();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public void register() throws ClassNotFoundException {
        String name = namaTextField.getText();
        String phone = phoneTextField.getText();
        String email = emailTextField.getText();
        String password = passwordTextField.getText();
        String bankNum = bankTextField.getText();
        int lastID = 1;

        Class.forName("com.mysql.jdbc.Driver");

        String queryInsert = "INSERT INTO account VALUES ('"+bankNum+"', '123', '0', 'regular')";
        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(queryInsert);
            //stmt.executeUpdate(queryInsert2);
        }catch (SQLException e) {
            e.printStackTrace();
        }

        String querySelect = "SELECT MAX(id_user) FROM user";
        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            ResultSet rs = con.createStatement().executeQuery(querySelect);
            rs.next();
            System.out.println(rs.getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String queryInsert1 = "INSERT INTO user (id_user, bank_number, name_user, phone_user) VALUES ('"+lastID+"', '"+bankNum+"', '"+name+"', '"+phone+"')";
        //String queryInsert2 = "INSERT INTO login ('email', 'password', 'role') VALUES ('"+email+"','"+password+"','user')";

        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(queryInsert1);
            //stmt.executeUpdate(queryInsert2);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

//SQL eror