/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;


import javafx.application.Application;
import javafx.application.Platform;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Micky
 */
public class ChatApp extends Application {
    
    //Running this application as it is will start the Server
    //change the the boolean value to false to start the client while the server is running
    private final boolean isServer = true;
    private TextArea messages = new TextArea();
    private NetworkConnection connection = isServer ? createServer() : createClient();
    private Parent createContent(){
        
        messages.setPrefHeight(550);
        TextField input = new TextField();
        
        input.setOnAction(event ->{
        String message = isServer ? "Server: " : "Client: ";
        message += input.getText();
        input.clear();
        
        messages.appendText(message + "\n");
        try{
        connection.send(message);
        }
        catch(Exception e){
           messages.appendText(message);
        }
        });
        VBox root = new VBox(20, messages, input);
        root.setPrefSize(400, 400);
        return root;
    }
    
    @Override
    public void init() throws Exception{
      connection.startConnection();
    
    }
    
   
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

  public void Stop() throws Exception{
  
      connection.closeConnection();
      
  }
  
  
  private Client createClient() {
      return new Client("127.0.0.1", 55555, data->{
      Platform.runLater(() ->{
          messages.appendText(data.toString() + "\n");
      });
      });
    }

    private Server createServer() {
       return new Server(55555, data ->{
         Platform.runLater(()->{
             messages.appendText(data.toString() + "\n");
         });
       });
    }
    


    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}