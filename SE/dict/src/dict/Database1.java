/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dict;

import java.sql.*;
import javax.swing.JOptionPane;


/**
 *
 * @author User
 */
public class Database1{

    /**
     * @param args the command line arguments
     */
        private Connection myconn;
        private Statement mystmt;
        private ResultSet result;
        String url = "jdbc:mysql://localhost/dictionary";
        String user = "root";
        String password = "root";
        public Database1(){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                System.out.println("Driver loaded");
                myconn = DriverManager.getConnection(url, user, password);
                System.out.println(myconn);
                mystmt = myconn.createStatement();
            }
            catch (ClassNotFoundException | SQLException e){
                System.out.println("error "+e.getMessage());
            }
         }
        public void clear() throws Exception{
            String dQuery = " DELETE FROM henokDictionary";
            mystmt.executeUpdate(dQuery);
            System.out.println("Cleared Successfully");
        }
        public void insertTuple(String Word,String Text) throws Exception{
            String uQuery = "INSERT INTO henokDictionary(Word,Description) VALUES (\'"+Word+"\',\'"+Text+"\')";
            mystmt.executeUpdate(uQuery);
            JOptionPane.showMessageDialog(null,"Added successefully");
        }
        //()
            //String uQuery = "UPDATE myjdbc SET Name = "+Name+",age = "+age+",Sex = "+sex+" WHERE Name = "+Name+""; 
        
        public void rowDeletion(String name) throws SQLException {
            String dQuery = "DELETE FROM henokDictionary WHERE Name = '"+name+"'";
            mystmt.executeUpdate(dQuery);
            System.out.println("Deleted Successefully");
        }
        public ResultSet search (Object what,Object value) throws Exception{
            String sQuery = "SELECT * FROM henokDictionary WHERE "+what+" LIKE \""+value+"%\"";
            result = mystmt.executeQuery(sQuery);
            /*while (result.next()){
                String Name = result.getString("Name");
                int Age = result.getInt("Age");
                String Sex = result.getString("Sex");
                System.out.println(Name+"     "+Age+"     "+Sex);
            }*/
            return result;
        }
        public boolean isInDatabase (Object value) throws Exception{
            String sQuery = "SELECT * FROM henokDictionary WHERE Word = '"+value+"'";
            result = mystmt.executeQuery(sQuery);
            while (result.next()){
                String Word = result.getString("Word");
                return true;
            }
            return false;
        }
        public void getQuery() throws Exception{
            String gQuery = "SELECT * FROM henokDictionary";
            result = mystmt.executeQuery(gQuery);
            System.out.println("Record from the table");
            System.out.println("Name"+"     "+"Age"+"     "+"Sex");
            while (result.next()){
                String Name = result.getString("Word");
                String Description = result.getString("Description");
                System.out.println(Name+"     "+Description);
            }
            
        }
}

