/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dict;

import com.healthmarketscience.jackcess.ColumnBuilder;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;
import com.healthmarketscience.jackcess.Table;
import com.healthmarketscience.jackcess.TableBuilder;
import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import javax.swing.JOptionPane;


/**
 *
 * @author Henok Tesfaye softwareEngineering_@AAIT;
 */
public final class Database2{

    /**
     * @param args the command line arguments
     */
        private Connection myconn;
        private Statement mystmt;
        private ResultSet result;
        public Database2(){
            try{
                makeFolder("emperror");
                myconn = DriverManager.getConnection("jdbc:ucanaccess://C:/emperror/dictionary.mdb");
                mystmt = myconn.createStatement();
            }
            
            catch (SQLException e){
                System.err.println("SQL EXCEPTION");
            }
         }
        public void makeFolder(String folderName){
            File folder = new File("C:\\"+folderName);
            if (!folder.exists()){
                try {
                    folder.mkdir();
                    databaseGenerator("Dictionary","TestChannel");
                }
                catch (SecurityException e){

                }
            }
        }
        public void databaseGenerator(String databaseName,String tableName){
            try {
                Database db = DatabaseBuilder.create(Database.FileFormat.V2000,new File("C:\\emperror\\"+databaseName+".mdb"));
                Table newTable = new TableBuilder(tableName)
                    .addColumn(new ColumnBuilder("Id").setSQLType(Types.INTEGER).setAutoNumber(true))
                    .addColumn(new ColumnBuilder("Word").setSQLType(Types.VARCHAR))
                    .addColumn(new ColumnBuilder("Description").setSQLType(Types.LONGNVARCHAR))
                    .toTable(db);
            }
            catch(IOException | SQLException e){
            }
        }
        public void insertTuple(String word,String description) {
            try{
            String uQuery = "INSERT INTO TestChannel(Word,Description) VALUES (\'"+word+"\',\'"+description+"\')";
                mystmt.executeUpdate(uQuery);
                JOptionPane.showMessageDialog(null,"Added successefully");
            }
            catch (SQLException | HeadlessException e){
                System.err.print("Error insert tuple");
            }
        }
        public ResultSet search (Object what,Object value) throws Exception{
                String sQuery = "SELECT * FROM TestChannel WHERE "+what+" LIKE \"%"+value+"%\"";
                result = mystmt.executeQuery(sQuery);
                return result;
        }
        public ResultSet searchAll () throws Exception{
            String sQuery = "SELECT * FROM TestChannel ORDER BY WORD ASC";
            result = mystmt.executeQuery(sQuery);
            return result;
        }
        public boolean isInDatabase (Object value) throws Exception{
            String sQuery = "SELECT * FROM TestChannel WHERE Word = '"+value+"'";
            result = mystmt.executeQuery(sQuery);
            boolean yes = false;
            while (result.next()){
                String Word = result.getString("Word");
                yes = true;
                break;
            }
            return yes;
        }
        public int getNoOfRow() throws SQLException{
            int noOfRow = 0;
            String sQuery = "SELECT * FROM TestChannel";
            result = mystmt.executeQuery(sQuery);
            while (result.next()){
                noOfRow+=1;
            }
            return noOfRow;
        }
        public String searchWordForTheGivenId(int id) throws SQLException{
            String sQuery = "SELECT Word FROM TestChannel WHERE Id = '"+id+"'";
            result = mystmt.executeQuery(sQuery);
            String word = "";
            while (result.next()){
                word = result.getString("Word");
            }
            return word;
        }
}

