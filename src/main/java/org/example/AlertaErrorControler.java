package org.example;

import javafx.fxml.FXML;

import java.io.IOException;

public class AlertaErrorControler {


    @FXML
    private void cambiarInicio() throws IOException{
        App.setRoot("Inicio");
    }
}
