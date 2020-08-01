/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.popovichia.cloudstorage.server.services;

import java.net.Socket;

/**
 *
 * @author igor
 */
public class Client {
    
    private Socket socket = null;
    private InputChannelServer inputChannelServer = null;
    private OutputChannelServer outputChannelServer = null;
    
    public Client(Socket socket) {
        this.socket = socket;
        this.inputChannelServer = new InputChannelServer(this.socket);
        new Thread(inputChannelServer).start();
        this.outputChannelServer = new OutputChannelServer(this.socket);        
        new Thread(outputChannelServer).start();
    }
    
    public Socket getSocket() {
        return this.socket;
    }
    
    @Override
    public String toString() {
        return this.socket.getLocalAddress()
                + ":" +
                this.socket.getPort();
    }
}
