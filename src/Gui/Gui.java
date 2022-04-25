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
    int adminKey;



    public Gui()
    {
        add(Panel);
        setResizable(false);
        setSize(800,800);
        setTitle("Test Program");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ErrorText.setVisible(false);


        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ad,kartno;
                ad = isimTextField.getText();
                kartno = kartNumarasıTextField.getText();
                System.out.println("Login olmaya calisan kullanicinin adi : " + ad + "\nKart Numarasi : " + kartno);
                adminKey = Integer.parseInt(String.valueOf(kartno.charAt(0)));


                ErrorText.setVisible(false);
                if(adminKey == 0)
                {
                    System.out.println("Admin girisi tespit edildi !");
                }

                else if(adminKey < 3)
                {
                    System.out.println("Kullanici girisi tespit edildi.");
                }

                else
                {
                    System.out.println("Hatali giris");
                    ErrorText.setVisible(true);
                }
            }
        });




    }

}
