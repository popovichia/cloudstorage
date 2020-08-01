/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.popovichia.cloudstorage.server.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class InputChannelServer implements Runnable {
    
    private Socket socket = null;
    private InputStream inputStream = null;
    
    public InputChannelServer(Socket socket) {
        this.socket = socket;
        try {
            inputStream = socket.getInputStream();
        } catch (IOException ioException) {

        }
    }
    
    @Override
    public void run() {

        try {
            while (socket.isConnected()) {
                byte[] bytesBuffer = new byte[4096];
                String s = "";
                while (inputStream.read(bytesBuffer) != -1) {
                    System.out.println("socket.getLocalAddress(): " + socket.getLocalAddress());
                    System.out.println("socket.getPort(): " + socket.getPort());
                    System.out.println("socket.getLocalPort(): " + socket.getLocalPort());
                    System.out.println("dataInputStream.available(): " + inputStream.available());
                    int i = 0;
                    while (inputStream.available() > 0) {
                        s += (char) bytesBuffer[i];
                        i++;
                    }
                    System.out.println("string: " + s);
                }
            }
        } catch (IOException ioException) {
        } finally {
            try {
                inputStream.close();
            } catch (IOException ioException) {
            }
        }
        
    }

}
