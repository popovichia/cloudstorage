package ru.popovichia.cloudstorage.server;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.popovichia.cloudstorage.server.services.Client;
import ru.popovichia.cloudstorage.server.services.ClientsHealthyTask;
import ru.popovichia.cloudstorage.server.services.ConnectionsHandler;

public class FXMLController implements Initializable {
    
    @FXML
    private Label lServerIP;
    @FXML
    private TextField tfServerPort;   
    @FXML
    private Button bStart;
    @FXML
    private TextArea taLog;
    @FXML
    private ListView lvDirs;
    @FXML
    private ListView lvConnectedUsers;
    
    private ServerSocket serverSocket = null;
    private ConnectionsHandler connectionsHandler = null;
    private boolean isStopped = false;
    private ArrayList<Client> clientsArrayList = null;
    private ClientsHealthyTask clientsHealthyTask = null;
    
    @FXML
    private void handleStartServerMouseClick(MouseEvent mouseEvent) {
        if (bStart.getText().equals("Start")) {
            int serverPort = -1;
            try {
                serverPort = Integer.parseInt(tfServerPort.getText());
            } catch (NumberFormatException numberFormatException) {

            }
            if (serverPort >= 0 && serverPort <= 65535) {
                try {
                    serverSocket = new ServerSocket(serverPort);
                    bStart.setText("Stop");
                    tfServerPort.setEditable(false);
//                    clientsHealthyTask = new ClientsHealthyTask(this);
//                    new Thread(clientsHealthyTask).start();
                    addMessageToLog("Сервер запущен. IP: "
                            + lServerIP.getText()
                            + tfServerPort.getText() + ".\n");
                    connectionsHandler = new ConnectionsHandler(this, serverSocket);
                    Thread threadConnectionsHandler = new Thread(connectionsHandler);
                    threadConnectionsHandler.start();
                } catch (IOException ioException) {

                }
            } else {
                addMessageToLog("ERROR: Указанные порт "
                        + tfServerPort.getText() + " за границами допустимого диапазона "
                        + " от 0 до 65535.\n");
            }
        } else if (bStart.getText().equals("Stop")) {
            if (serverSocket != null) {
                try {
                    connectionsHandler.stop();
//                    clientsHealthyTask.stop();
                    serverSocket.close();                    
                    this.clientsArrayList.clear();
                    lvConnectedUsers.setItems(FXCollections.observableArrayList(this.clientsArrayList));                            
                    bStart.setText("Start");
                    tfServerPort.setEditable(true);
                    addMessageToLog("Сервер остановлен. IP: "
                            + lServerIP.getText()
                            + tfServerPort.getText() + ".\n");
                } catch (IOException ioException) {
                    
                }
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            InetAddress serverInetAddress = InetAddress.getLocalHost();
            lServerIP.setText(serverInetAddress.getHostAddress() + ":");
            File serverDir = new File("./serverside");
            if (!serverDir.exists() || !serverDir.isDirectory()) {
                serverDir.mkdir();
            }
            clientsArrayList = new ArrayList<Client>();
            addMessageToLog("Приложение запущенно.\n"
                    + "    Рабочая директория: "
                    + serverDir.getAbsolutePath() + "\n");
            ObservableList<String> itemsObservableList = FXCollections.observableArrayList(serverDir.list());
            lvDirs.setItems(itemsObservableList.sorted());
        } catch (UnknownHostException unknownHostException) {
        }
    }
    
    public void addMessageToLog(String stringMessage) {
        taLog.appendText(stringMessage);
    }
    
    public ArrayList<Client> getClientsArrayList() {
        return this.clientsArrayList;
    }
    
    public void addClientToList(Client client) {
        this.clientsArrayList.add(client);
        updateLvConnectedUsers(this.clientsArrayList);
    }
    
    public void delClientFromList(Client client) {
        this.clientsArrayList.remove(client);
        updateLvConnectedUsers(this.clientsArrayList);        
    }
    
    public void updateLvConnectedUsers(ArrayList<Client> clientsArrayList) {
        lvConnectedUsers.setItems(FXCollections.observableArrayList(clientsArrayList));
    }
}
