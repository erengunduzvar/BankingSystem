import Gui.Gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        System.out.println("Program Calisiyor !!");


//        try{
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectdatabase","root","19691964");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("select * from user");
//
//            while (resultSet.next())
//            {
//                System.out.print(resultSet.getString("nameUser"));
//            }
//
//           // System.out.println("Holy Fuck Morty We Did !");
//        }catch (Exception e)
//        {
//            System.out.println(e);
//        }



            Gui Screen = new Gui();
            Screen.setVisible(true);


    }

}