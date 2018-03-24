/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatapp;

import java.io.Serializable;
import java.util.function.Consumer;

/**
 *
 * @author Micky
 */
public class Server extends NetworkConnection {

    private int port;
    
    /**
     *
     * @param port
     * @param onReceiveCallback
     */
    public Server(int port,Consumer<Serializable> onReceiveCallback) {
        super(onReceiveCallback);
        this.port = port;
    }

    @Override
    protected boolean isServer() {
         return true;
    }

    @Override
    protected String getIP() {
        return null;
    }

    @Override
    protected int getPort() {
        return port;
    }
    
}
