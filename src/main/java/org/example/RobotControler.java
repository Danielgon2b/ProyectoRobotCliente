package org.example;

import javafx.fxml.FXML;

import java.io.IOException;

public class RobotControler {

    @FXML
    private void volver(){

    }

    @FXML
    private void salir(){
        System.exit(0);
    }

    @FXML
    private void inicio() throws IOException {
        App.setRoot("Inicio");
    }
}
