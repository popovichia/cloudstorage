/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.popovichia.cloudstorage.server.services;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author igor
 */
public class OutputChannelServer implements Runnable {

    private Socket socket;
    private boolean isStopped = false;
    
    public OutputChannelServer(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
//        OutputStream outputStream = null;
//        while (!isStopped) {
//            try {
//                outputStream = socket.getOutputStream();
//                outputStream.write("Hello".getBytes());
//                outputStream.flush();
//            } catch (IOException ioException) {
//            }
//        }
//        
//        try {
//            outputStream.close();
//        } catch (IOException ioException) {
//        }
        
    }

    public void stop(boolean isStopped) {
        this.isStopped = true;
    }
    
}
