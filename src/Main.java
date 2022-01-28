import javax.swing.JFrame;

public class Main {
    
    JFrame loginFrame;
    
    public Main() throws ClassNotFoundException {
        loginFrame = new LoginFrame();
    }

    public static void main(String[] args) throws ClassNotFoundException {
        Main appMain = new Main();
    }
}

//register