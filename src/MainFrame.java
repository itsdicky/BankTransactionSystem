import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.sql.Date;

//import java.awt.Font;

public class MainFrame extends JFrame {
    
    private JLabel balanceLabel = new JLabel();
    private JRadioButton transferRadioButton = new JRadioButton("Transfer");
    private JRadioButton withdrawRadioButton = new JRadioButton("Withdraw");
    private JRadioButton depositRadioButton = new JRadioButton("Deposit");
    private ButtonGroup optionGroup = new ButtonGroup();
    private JButton submiButton = new JButton("Submit");
    private JTextField inputField = new JTextField();
    private JTextArea detailArea = new JTextArea();

    private String status = "";

    public Integer banknum;

    private Transaction tx = new Transaction();

    public MainFrame() {
        setFrame();
        initComponents();
        setListener();
    }

    private void setFrame() {
        setSize(300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        //setVisible(true);
    }

    private void initComponents() {
        
        //Font balanceFont = new Font("Dialog", 1, 32);
        //balanceLabel.setFont(balanceFont);

        optionGroup.add(transferRadioButton);
        optionGroup.add(withdrawRadioButton);
        optionGroup.add(depositRadioButton);

        detailArea.setEditable(false);

        balanceLabel.setBounds(30, 20, 80, 30);
        transferRadioButton.setBounds(30, 60, 80, 20);
        withdrawRadioButton.setBounds(30, 80, 80, 20);
        depositRadioButton.setBounds(30, 100, 80, 20);
        inputField.setBounds(150, 60, 100, 25);
        submiButton.setBounds(150, 90, 100, 25);
        detailArea.setBounds(30, 150, 220, 180);

        add(balanceLabel);
        add(transferRadioButton);
        add(withdrawRadioButton);
        add(depositRadioButton);
        add(inputField);
        add(submiButton);
        add(detailArea);
    }

    public void setBalanceLabel() {
        try {
            balanceLabel.setText(tx.update(banknum));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setBankNum(Integer number) {
        this.banknum = number;
    }

    private void setListener() {
        submiButton.addActionListener(evt -> {
            int input = 0;
            String newStatus = "";
            
            try {
                input = Integer.parseInt(inputField.getText());

                if (transferRadioButton.isSelected()) {
                    //detailArea.setText("Telah ditransfer sebesar " + input);
                    int recnum = Integer.parseInt(JOptionPane.showInputDialog(this, "add recipient"));
                    newStatus = "Telah ditransfer sebesar " + input + "\n";
                    tx.transfer(banknum, recnum, input);
                    balanceLabel.setText(tx.update(banknum));
                } else if (withdrawRadioButton.isSelected()) {
                    //detailArea.setText("Telah ditarik sebesar " + input);
                    newStatus = "Telah ditarik sebesar " + input + "\n";
                    tx.withdraw(banknum, input);
                    balanceLabel.setText(tx.update(banknum));
                } else if (depositRadioButton.isSelected()) {
                    //detailArea.setText("Telah deposit sebesar " + input);
                    newStatus = "Telah deposit sebesar " + input + "\n";
                    tx.deposit(banknum, input);
                    balanceLabel.setText(tx.update(banknum));
                } else {
                    JOptionPane.showMessageDialog(this, "Opsi belum dipilih!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                //print to detailArea
                status = newStatus + status;
                detailArea.setText(status);

                JOptionPane.showMessageDialog(this, "Berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE); //bug

                //reset
                optionGroup.clearSelection();
                inputField.setText("");

            } catch (Exception e) {
                //TODO: handle exception
                //JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        });
    }
}

//pin confirm