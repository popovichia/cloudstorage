/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.popovichia.cloudstorage.client.services;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class OutputChannelClient {

    private Socket socket = null;
    private OutputStream outputStream = null;
    
    public OutputChannelClient(Socket socket) {
        this.socket = socket;
    }
    
    public void send(final String stringMessage) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OutputChannelClient.this.outputStream = OutputChannelClient.this.socket.getOutputStream();
                } catch (IOException ex) {
                    System.out.println("ERROR: outputStream - " + OutputChannelClient.this.outputStream);
                }
                System.out.println("socket.getLocalAddress(): " + OutputChannelClient.this.socket.getLocalAddress());
                System.out.println("socket.getPort(): " + OutputChannelClient.this.socket.getPort());
                System.out.println("socket.getLocalPort(): " + OutputChannelClient.this.socket.getLocalPort());
                try {
                    OutputChannelClient.this.outputStream.write(stringMessage.getBytes());
                    OutputChannelClient.this.outputStream.flush();
                } catch (IOException ex) {
                    System.out.println("ERROR: outputStream - " + OutputChannelClient.this.outputStream);
                }
            }
        }).start();
    }
    
}
