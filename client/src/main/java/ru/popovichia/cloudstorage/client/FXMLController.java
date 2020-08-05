package ru.popovichia.cloudstorage.client;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.popovichia.cloudstorage.client.services.InputChannelClient;
import ru.popovichia.cloudstorage.client.services.OutputChannelClient;

public class FXMLController implements Initializable {
    
    @FXML
    private TextField tfServerIP; 
    @FXML
    private TextField tfServerPort;
    @FXML
    private TextField tfLogin;    
    @FXML
    private PasswordField pfPassword;    
    @FXML
    private Button bLogin;    
    @FXML
    private Label lClientStatus;
    
    private Socket socket = null;
    private InputChannelClient inputChannelClient = null;
    private OutputChannelClient outputChannelClient = null;
    
    @FXML
    private void handleLvClientMouseClick(MouseEvent mouseEvent) {
    }

    @FXML
    private void handleLvServerMouseClick(MouseEvent mouseEvent) {
    }
    
    @FXML
    private void handleBLoginMouseClick(MouseEvent mouseEvent) throws IOException {
        if ((socket == null || socket.isClosed())
                && bLogin.getText().equals("Login")) {
            try {
                socket = new Socket(tfServerIP.getText(), Integer.parseInt(tfServerPort.getText()));
            } catch (IOException ioException) {
            }
            if (socket != null) {
                lClientStatus.setText("Connected.");
                tfServerIP.setEditable(false);
                tfServerPort.setEditable(false);
                tfLogin.setEditable(false);
                pfPassword.setEditable(false);
                bLogin.setText("Logout");
                inputChannelClient = new InputChannelClient(socket);
                new Thread(inputChannelClient).start();
                outputChannelClient = new OutputChannelClient(socket);
            }
        } else if (socket != null && bLogin.getText().equals("Logout")) {            
            outputChannelClient.send("stop");
            lClientStatus.setText("Disconnected.");
            tfServerIP.setEditable(true);
            tfServerPort.setEditable(true);
            tfLogin.setEditable(true);
            pfPassword.setEditable(true);
            bLogin.setText("Login");           
            socket.close();
            socket = null;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
}
