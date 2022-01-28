import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
    
    JFrame loginFrame;
    //JFrame mainFrame;
    //JFrame registerFrame;
    
    public Main() throws ClassNotFoundException {
        //new MysqlCon();
        loginFrame = new LoginFrame();
        //mainFrame = new MainFrame();
    }

    /*
    public void switchFrame() {
        loginFrame.dispose();
        mainFrame.setVisible(true);
    }*/

    public static void main(String[] args) throws ClassNotFoundException {
        Main appMain = new Main();
    }
}

//register