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
    private JTextField namaTextField = new JTextField();
    private JTextField bankTextField = new JTextField();
    private JTextField phoneTextField = new JTextField();
    private JButton submitButton = new JButton("Submit");

    private String email;
    private String password;

    public RegisterFrame() {
        //register();
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
        titleJLabel.setBounds(105, 25, 80, 20);
        nameJLabel.setBounds(30, 80, 200, 20);
        phoneJLabel.setBounds(30, 110, 200, 20);
        bankNumberJLabel.setBounds(30, 140, 200, 20);
        namaTextField.setBounds(125, 80, 130, 20);
        bankTextField.setBounds(125, 110, 130, 20);
        phoneTextField.setBounds(125, 140, 130, 20);
        submitButton.setBounds(100, 300, 80, 25);

        add(titleJLabel);
        add(nameJLabel);
        add(phoneJLabel);
        add(bankNumberJLabel);
        add(namaTextField);
        add(bankTextField);
        add(phoneTextField);
        add(submitButton);
    }

    private void setListener() {
        submitButton.addActionListener(evt -> {
            //masukan data
            try {
                formRegist();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    public boolean register() {
        boolean x = false;
        email = JOptionPane.showInputDialog(this, "Email");
        if (email == null) {
            JOptionPane.showMessageDialog(this, "Input email!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            password = JOptionPane.showInputDialog(this, "Password");
        }

        if (password == null) {
            JOptionPane.showMessageDialog(this, "Input email!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            //input to database
            x = true;
        }
        return x;
    }

    public void formRegist() throws ClassNotFoundException {
        String name = namaTextField.getText();
        String phone = phoneTextField.getText();
        int bankNum = Integer.parseInt(bankTextField.getText());

        Class.forName("com.mysql.jdbc.Driver");
        String queryInsert1 = "INSERT INTO user ('bank_number', 'name_user', 'phone_user') VALUES ('"+bankNum+"', '"+name+"', '"+phone+"')";
        String queryInsert2 = "INSERT INTO login ('email', 'password', 'role') VALUES ('"+email+"','"+password+"','user')";

        String querySelect = "SELECT id_user FROM user WHERE MAX(id_user);";

        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            ResultSet rs = con.createStatement().executeQuery(querySelect);

            while(rs.next()){
                rs.getString(1);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            Statement stmt = con.createStatement();
            stmt.executeUpdate(queryInsert1);
            stmt.executeUpdate(queryInsert2);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

//SQL eror