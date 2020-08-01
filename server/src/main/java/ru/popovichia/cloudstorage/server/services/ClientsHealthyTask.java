/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.popovichia.cloudstorage.server.services;

import java.util.ArrayList;
import ru.popovichia.cloudstorage.server.FXMLController;


public class ClientsHealthyTask implements Runnable {
    
    FXMLController fxmlController = null;
    private boolean isStopped = false;
    private ArrayList<Client> clientsArrayList;
    
    public ClientsHealthyTask(FXMLController fxmlController) {
        this.fxmlController = fxmlController;
        this.clientsArrayList = fxmlController.getClientsArrayList();
    }
    
    @Override
    public void run() {
        while (!isStopped) {
            try {                
                Thread.sleep(5000);
                System.out.println("Количество клиентов:" + clientsArrayList.size());
                if (clientsArrayList != null && !clientsArrayList.isEmpty()) {
                    ArrayList<Client> liveClientsArrayList = new ArrayList<>();
                    for (Client client : clientsArrayList) {
                        System.out.println("Клиент: " + client.getSocket().getLocalPort());
                        System.out.println("Клиент статус: " + client.getSocket().isBound());
                        if (client.getSocket().isConnected()) {
                            liveClientsArrayList.add(client);
                        }
                    }
                    this.fxmlController.updateLvConnectedUsers(liveClientsArrayList);
                }
            } catch (InterruptedException interruptedException) {
            }
        }
    }
    
    public void stop() {
        this.isStopped = true;
    }
}
