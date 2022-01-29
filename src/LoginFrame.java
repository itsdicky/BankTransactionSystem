import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class LoginFrame extends JFrame {
    private JLabel titleJlabel = new JLabel("Login");
    private JLabel emailJLabel = new JLabel("Email : ");
    private JLabel passJLabel = new JLabel("Password : ");
    private JTextField unameTextField = new JTextField();
    private JTextField passTextField = new JTextField();
    private JButton submitButton = new JButton("Submit");
    private JButton registerButton = new JButton("Register");

    private boolean userCheck;

    //database
    private String url = "jdbc:mysql://localhost:3306/bank";
    private String user = "root";
    private String pass = "";

    //frame
    MainFrame mainFrame = new MainFrame();
    RegisterFrame registerFrame = new RegisterFrame();

    //constructor
    public LoginFrame() throws ClassNotFoundException {
        setFrame();
        initComponents();
        setListener();
    }

    private void setFrame() {
        setSize(260,280);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initComponents() {
        titleJlabel.setBounds(105, 25, 80, 20);
        emailJLabel.setBounds(20, 70, 100, 20);
        passJLabel.setBounds(20, 110, 100, 20);
        unameTextField.setBounds(100,70,120,20);
        passTextField.setBounds(100,110,120,20);
        submitButton.setBounds(80,160,80,25);
        registerButton.setBounds(80,190,80,25);

        add(titleJlabel);
        add(emailJLabel);
        add(passJLabel);
        add(unameTextField);
        add(passTextField);
        add(submitButton);
        add(registerButton);
    }

    //set bank number at mainFrame
    private void setBankNumber(String email, String password) {
        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            ResultSet rs = con.createStatement().executeQuery("SELECT a.bank_number FROM user a JOIN login b USING(id_user) WHERE b.email='"+email+"' AND b.password='"+password+"'");
            rs.next();
            mainFrame.banknum = Integer.parseInt(rs.getString(1));
        }catch (SQLException e) {
            e.getStackTrace();
        }
    }

    //check email and password match
    private boolean loginCheck(String email, String password) {
        try(Connection con = DriverManager.getConnection(url,user,pass)) {
            ResultSet rs = con.createStatement().executeQuery("SELECT COUNT(*) FROM user a JOIN login b USING(id_user) WHERE b.email='"+email+"' AND b.password='"+password+"';");
            rs.next();
            mainFrame.banknum = Integer.parseInt(rs.getString(1));
            if (rs.getString(1).equals("1")) {
                userCheck = true;
            } else {
                userCheck = false;
            }
        }catch (SQLException e) {
            e.getStackTrace();
        }
        return userCheck;
    }

    //change frame to mainFrame and set balance label
    private void changeFrame() {
        dispose();
        mainFrame.setVisible(true);
        mainFrame.setBalanceLabel();
    }

    private void setListener() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");

        //submit button listener
        submitButton.addActionListener(evt -> {
            //get text from textfield
            String email = unameTextField.getText();
            String password = passTextField.getText();
            
            //login    
            if (loginCheck(email, password)) {
                setBankNumber(email, password);
                changeFrame();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //register button listener
        registerButton.addActionListener(evt -> {
            if (registerFrame.register()==true) {
                registerFrame.setVisible(true);
            }
        });
    }
}