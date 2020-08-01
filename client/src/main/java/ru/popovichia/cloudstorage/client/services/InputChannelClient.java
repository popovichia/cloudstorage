/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.popovichia.cloudstorage.client.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class InputChannelClient implements Runnable {
    
    private Socket socket = null;
    private InputStream inputStream = null;
    
    public InputChannelClient(Socket socket) {
        this.socket = socket;
        try {
            this.inputStream = socket.getInputStream();
        } catch (IOException ioException) {
        }
    }
    
    @Override
    public void run() {
        try {
            while (socket.isConnected()) {
                byte[] byteBuffer = new byte[4096];
                while (inputStream.available() > 0) {
                    inputStream.read(byteBuffer);
                    System.out.println(byteBuffer.toString());
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
