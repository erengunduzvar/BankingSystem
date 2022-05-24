package Gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Gui extends JFrame {
    private JTextField isimTextField;
    private JTextField kartNumarasıTextField;
    private JButton sendButton;
    private JPanel Panel;
    private JLabel ErrorText;
    private JButton adminLoginButton;
    private JButton userLoginButton;
    private JLabel BankName;
    int adminKey;



    public Gui()
    {
        add(Panel);
        setResizable(false);
        setSize(600,600);
        setTitle("Test Program");
        BankName.setIcon(new ImageIcon("img/bank.png"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ErrorText.setVisible(false);
        setTitle("Bank Managment System");
        setLocationRelativeTo(null);


        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId,tcNo;
                userId = isimTextField.getText();
                tcNo = kartNumarasıTextField.getText();

                ErrorText.setVisible(false);

                if(userId.length() < 1) // Error Check
                {
                    ErrorText.setText("User Id alanı boş bırakılamaz !!");
                    ErrorText.setVisible(true);
                }

                else if(tcNo.length() < 1) // Error Check
                {
                    ErrorText.setText("Tc No alanı boş bırakılamaz");
                    ErrorText.setVisible(true);
                }

                else
                {
                    try{
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdatabase","root","19691964");
                        Statement statement = connection.createStatement();
                        ResultSet resultSet = statement.executeQuery("select * from user u,account a where a.userId = u.idUser");

                        while (resultSet.next())
                        {
                            //Tl hesabi var mi ?
                            if(userId.equals(resultSet.getString("idUser")) && tcNo.equals(resultSet.getString("tcNoUser")) && "0".equals(resultSet.getString("currencyId")))
                            {
                                int authLevel = Integer.parseInt(resultSet.getString("authorizationUser"));

                                System.out.println("Kisi Bulundu");
                                setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                                switch (authLevel)
                                {
                                    case 0: //user
                                        new UserLogin(Integer.parseInt(userId),0);
                                        break;
                                    case 1: //Employee
                                        //employee Gui
                                        break;
                                    case 2: //Manager
                                        new AdminLogin();

                                }




                            }
                            else
                            {
                                ErrorText.setText("Girilen bilgilere uygun bir hesap bulunamadı");
                                ErrorText.setVisible(true);
                            }
                        }

                        // System.out.println("Holy Fuck Morty We Did !");
                    }catch (Exception exception)
                    {
                        System.out.println(exception);
                    }

                }


            }
        });


        adminLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new AdminLogin();
            }
        });
        userLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new UserLogin(0,0);
            }
        });
    }

}
