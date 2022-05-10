package Gui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui extends JFrame {
    private JTextField isimTextField;
    private JTextField kartNumarasıTextField;
    private JButton sendButton;
    private JPanel Panel;
    private JLabel ErrorText;
    private JButton adminLoginButton;
    private JButton userLoginButton;
    int adminKey;



    public Gui()
    {
        add(Panel);
        setResizable(false);
        setSize(400,400);
        setTitle("Test Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ErrorText.setVisible(false);
        setTitle("Bank Managment System");
        setLocationRelativeTo(null);


        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ad,kartno;
                ad = isimTextField.getText();
                kartno = kartNumarasıTextField.getText();

                ErrorText.setVisible(false);

                if(ad.length() < 1) // Error Check
                {
                    ErrorText.setText("İsim alanı boş bırakılamaz !!");
                    ErrorText.setVisible(true);
                }

                else if(kartno.length() < 1) // Error Check
                {
                    ErrorText.setText("Kart Numarası alanı boş bırakılamaz");
                    ErrorText.setVisible(true);
                }

                else
                {
                    System.out.println("Login olmaya calisan kullanicinin adi : " + ad + "\nKart Numarasi : " + kartno);
                    adminKey = Integer.parseInt(String.valueOf(kartno.charAt(0)));
                    if(adminKey == 0)
                    {
                        System.out.println("Admin girisi tespit edildi !");
                        setVisible(false);
                        new AdminLogin();


                    }

                    else if(adminKey < 3)
                    {
                        System.out.println("Kullanici girisi tespit edildi.");
                    }

                    else
                    {
                        ErrorText.setText("Giris Bilgileri Yanlis");
                        System.out.println("Hatali giris");
                        ErrorText.setVisible(true);
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
    }

}
