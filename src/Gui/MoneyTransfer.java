package Gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


//Bankaya Transfer Borc Odeme KOD 1 - Kendi Hesaplarına Transfer KOD 2
public class MoneyTransfer extends JFrame {


    private JComboBox comboBox1;
    private JPanel panel;
    private JButton Send;
    private JLabel borc_hesapId;
    private JTextField tutarGirinizTextField;
    private JLabel ErrorBox;
    int id;
    float senderBal,receiverBal,amount,senderCurChangeRate,receiverCurChangeRate,debt;
    int currencyId;
    public MoneyTransfer(int operation,int id,int currencyId,float debt){

        add(panel);
        setResizable(false);
        setSize(600,600);
        setTitle("Transfer Program");
        setVisible(true);
        ErrorBox.setVisible(false);

        this.currencyId = currencyId;
        this.id = id;
        this.debt = debt;


        tutarGirinizTextField.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                tutarGirinizTextField.setText("");
            }
        });

        if(operation == 1) // Borc Odeme KOD 1
        {
            borc_hesapId.setText("Ödemek istediğiniz borç miktarını giriniz.");
            comboBox1.addItem("BANKA");
            comboBox1.setEnabled(false);

        }
        else //Kendi Hesaplarına Transfer KOD 2
        {
            borc_hesapId.setText("Aktarım yapmak istediğiniz hesabınızı giriniz.");
        }


        Send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(operation == 1)
                {
                    amount = Float.parseFloat(tutarGirinizTextField.getText());
                    System.out.println(amount);

                            try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdatabase","root","19691964");
            Statement statement = connection.createStatement();
            ResultSet resultSetSender = statement.executeQuery("select * from account a,currency c where a.userId = "+id + " AND a.currencyId = "+currencyId+" AND a.currencyId = c.idCurrency");

            if (resultSetSender.next())
            {
                senderBal = Float.parseFloat(resultSetSender.getString("balance"));
                senderCurChangeRate = Float.parseFloat(resultSetSender.getString("exchangeRateCurrency"));
            }



           // System.out.println("Holy Fuck Morty We Did !");
        }catch (Exception exception)
        {
            System.out.println(exception);
        }

                    try{
                        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdatabase","root","19691964");
                        Statement statement = connection.createStatement();
                        ResultSet resultSetReceiver = statement.executeQuery("select * from account a,currency c where a.userId = -1 AND a.currencyId = 0 AND a.currencyId = c.idCurrency");

                        if (resultSetReceiver.next())
                        {
                            receiverBal = Float.parseFloat(resultSetReceiver.getString("balance"));
                            receiverCurChangeRate = Float.parseFloat(resultSetReceiver.getString("exchangeRateCurrency"));
                        }


                        // System.out.println("Holy Fuck Morty We Did !");
                    }catch (Exception exception)
                    {
                        System.out.println(exception);
                    }

                    System.out.println("Sender bal : "+senderBal+" Receiver bal : "+receiverBal);
                    if(amount > senderBal)
                    {
                        ErrorBox.setText("Hesaptaki bakiye yetersiz !!");
                        ErrorBox.setVisible(true);
                    }
                    else if (((senderCurChangeRate/receiverCurChangeRate*amount)) > debt){
                        ErrorBox.setText("Hesapta bu kadar borcunuz bulunmamaktadir total borc : " + ((receiverCurChangeRate/senderCurChangeRate*debt)));
                        ErrorBox.setVisible(true);
                    }

                    else
                    {


                        try{
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdatabase","root","19691964");
                            Statement statement = connection.createStatement();
                            statement.execute("update account set balance = "+((senderCurChangeRate/receiverCurChangeRate*amount)+receiverBal) +" where userId = -1");
                            ErrorBox.setVisible(true);
                            ErrorBox.setText("Kur degisiminden oturu yolladiginiz para miktari : " + ((senderCurChangeRate/receiverCurChangeRate*amount)));

                        }catch (Exception exception)
                        {
                            System.out.println(exception);
                        }

                        try{
                            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdatabase","root","19691964");
                            Statement statement = connection.createStatement();
                            statement.execute("update account set balance = "+(senderBal-amount)+" where userId = "+id);

                        }catch (Exception exception)
                        {
                            System.out.println(exception);
                        }


                    }



                }
                else
                {

                }

            }
        });
    }
}
