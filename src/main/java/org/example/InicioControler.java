package org.example;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.example.entity.Instruccion;
import org.example.service.SocketService;
import org.example.utils.Constantes;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class InicioControler implements Initializable {


    private SocketService socketService;

    @FXML
    private Button buttonSeleccionar;

    @FXML
    private ChoiceBox<String> choiceBoxComs;

    @FXML
    private void establecerConexion() {
        socketService.initConnection();
    }

    @FXML
    private void salir() {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        socketService = new SocketService(choiceBoxComs,buttonSeleccionar);
    }

    @FXML
    private void setPuerto() {
        Instruccion instruccion = new Instruccion();
        instruccion.setInstruccion(Constantes.CONNECT_PORT);
        instruccion.setArgs(choiceBoxComs.getValue());
        socketService.sendInstruccion(instruccion);
    }
}
