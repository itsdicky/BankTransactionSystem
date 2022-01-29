import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Font;

public class MainFrame extends JFrame {
    
    private JLabel balanceLabel = new JLabel();
    private JRadioButton transferRadioButton = new JRadioButton("Transfer");
    private JRadioButton withdrawRadioButton = new JRadioButton("Withdraw");
    private JRadioButton depositRadioButton = new JRadioButton("Deposit");
    private ButtonGroup optionGroup = new ButtonGroup();
    private JButton submiButton = new JButton("Submit");
    private JTextField inputField = new JTextField();
    private JTextArea detailArea = new JTextArea();

    private String status = ""; //status for transaction
    public Integer banknum; //your bank number

    private Transaction tx = new Transaction();

    //constructor
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
        
        Font balanceFont = new Font("Dialog", 1, 32);
        balanceLabel.setFont(balanceFont);

        optionGroup.add(transferRadioButton);
        optionGroup.add(withdrawRadioButton);
        optionGroup.add(depositRadioButton);

        detailArea.setEditable(false);

        balanceLabel.setBounds(30, 20, 200, 30);
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

    //set balance label
    public void setBalanceLabel() {
        try {
            balanceLabel.setText(tx.update(banknum));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void successMsg() {
        JOptionPane.showMessageDialog(this, "Berhasil!", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setListener() {

        //submit button listener (transaction)
        submiButton.addActionListener(evt -> {
            
            int input = 0;
            String newStatus = "";
            
            try {
                input = Integer.parseInt(inputField.getText());

                if (transferRadioButton.isSelected()) { //transfer
                    int recnum = Integer.parseInt(JOptionPane.showInputDialog(this, "add recipient"));
                    if (tx.isUserExist(recnum)) {
                        newStatus = "Telah ditransfer ke " + tx.toName(recnum) + " sebesar " + input + "\n";
                        tx.transfer(banknum, recnum, input);
                        balanceLabel.setText(tx.update(banknum));
                        successMsg();
                    } else {
                        JOptionPane.showMessageDialog(this, "Transaksi Gagal!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    
                } else if (withdrawRadioButton.isSelected()) { //withdraw
                    newStatus = "Telah ditarik sebesar " + input + "\n";
                    tx.withdraw(banknum, input);
                    balanceLabel.setText(tx.update(banknum));
                    successMsg();
                } else if (depositRadioButton.isSelected()) { //deposit
                    newStatus = "Telah deposit sebesar " + input + "\n";
                    tx.deposit(banknum, input);
                    balanceLabel.setText(tx.update(banknum));
                    successMsg();
                } else {
                    JOptionPane.showMessageDialog(this, "Opsi belum dipilih!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                //print status to detailArea
                status = newStatus + status;
                detailArea.setText(status);

                //reset textfield
                optionGroup.clearSelection();
                inputField.setText("");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

//pin confirm