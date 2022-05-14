package Gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class UserLogin extends JFrame {
    private JPanel Panel;
    private JTabbedPane TabbedPane;
    private JLabel Bakiye;
    private JPanel BakiyeKutusu;
    private JPanel BorcPaneli;
    private JPanel YillikBorcPaneli;
    private JTextField Date;
    private JTextPane UserInfos;
    private JPanel AylikBorcPaneli;
    private JLabel totalBorcShow;
    private JLabel aylikBorcShow;
    private JComboBox<String> comboTest;
    private JButton button1;
    private JButton çıkışYapButton;
    private JButton Aktarma;
    private JButton BorcOde;
    String systemtime;
    float bal,totalDebt;
    String curName;

    List<Integer> allCurIds=new ArrayList<Integer>();

    public UserLogin(int id,int currencyId) {


        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdatabase", "root", "19691964");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from systemtime");

            if (resultSet.next())
            {
                Date.setText(resultSet.getString("Time"));
                systemtime = resultSet.getString("Time");
            }
            // System.out.println("Holy Fuck Morty We Did !");
        }catch (Exception e)
        {
            System.out.println(e);
        }

        try { //On progress
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdatabase", "root", "19691964");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from debt d,account a where a.idaccount = d.idaccount");
            totalDebt = 0;
            while (resultSet.next())
            {
                String[] systemTimeParts = systemtime.split("-"); //0 yil 1 ay 2 gun
                String[] debtTimeparts = resultSet.getString("ExpireDate").split("-");


                if(systemTimeParts[1].equals(debtTimeparts[1]))
                {
                    aylikBorcShow.setText("Aylık Umumi Borcunuz "+ resultSet.getString("amountDebt") +" TL");
                }
                totalDebt += Float.parseFloat(resultSet.getString("amountDebt"));

            }
            totalBorcShow.setText("Tum Borcunuz "+ totalDebt +" Tl");
            // System.out.println("Holy Fuck Morty We Did !");
        }catch (Exception e)
        {
            System.out.println(e);
        }



        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdatabase", "root", "19691964");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from user u,currency c where u.idUser = "+id+ " AND c.idCurrency = "+currencyId);

            if (resultSet.next())
            {
                UserInfos.setText("K. Adı: "+resultSet.getString("nameUser")+" " +resultSet.getString("surnameUser") + "\nPara Birimi: " + resultSet.getString("nameCurrency"));
            }
            // System.out.println("Holy Fuck Morty We Did !");
        }catch (Exception e)
        {
            System.out.println(e);
        }








        System.out.println(currencyId);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdatabase", "root", "19691964");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from account a,currency c where " + id + " = a.userId AND a.currencyId = " + currencyId+ " AND a.currencyId = c.idCurrency");

            if (resultSet.next())
            {
                bal = Float.parseFloat(resultSet.getString("balance"));
                curName = resultSet.getString("nameCurrency");
            }
            // System.out.println("Holy Fuck Morty We Did !");
        }catch (Exception e)
        {
            System.out.println(e);
        }


        try{
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdatabase","root","19691964");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM account a,currency c where a.currencyId = c.idCurrency AND " + id + " = userId AND a.currencyId <> "+currencyId);


            comboTest.addItem("Lütfen bir hesap seçiniz");
            while (resultSet.next())
            {
                comboTest.addItem("\nHesap ID : " + resultSet.getString("idaccount") + " Bakiye : "+resultSet.getString("balance")+" "+resultSet.getString("nameCurrency"));
                allCurIds.add(Integer.parseInt(resultSet.getString("currencyId")));
               // System.out.println(resultSet.getString("nameCurrency"));
            }

            // System.out.println("Holy Fuck Morty We Did !");
        }catch (Exception e)
        {
            System.out.println(e);
        }

        System.out.println("\n" + allCurIds);
//        comboTest.addItem("Bir Para birimi seçiniz");
//        comboTest.addItem("Hesap ID : 0 TL {500.00}");


        add(Panel);
        setSize(800,800);
        setLocationRelativeTo(null);//ekrana ortalar
        setResizable(false);
        setTitle("User Arayüzü");




        Bakiye.setText("Hesabınızdaki para "+ bal +" " + curName);

        TabbedPane.setIconAt(0,new ImageIcon("img/info.png"));
        TabbedPane.setIconAt(1,new ImageIcon("img/process.png"));
        TabbedPane.setIconAt(2,new ImageIcon("img/changes.png"));
        setVisible(true);




        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboTest.getSelectedIndex() != 0){
                    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                    setVisible(false);
                    new UserLogin(id,allCurIds.get(comboTest.getSelectedIndex()-1));
                }
                else
                    System.out.println("Lutfen bir secim yapiniz");


            }
        });
        Aktarma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MoneyTransfer(2,id,currencyId,totalDebt);
            }
        });
        BorcOde.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MoneyTransfer(1,id,currencyId,totalDebt);
            }
        });
    }
}
