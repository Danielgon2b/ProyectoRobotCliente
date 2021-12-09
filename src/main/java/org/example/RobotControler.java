package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.util.Duration;
import org.example.entity.Instruccion;
import org.example.service.SocketService;
import org.example.utils.Constantes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RobotControler implements Initializable {

    private Timeline timeline;

    private String movimiento;

    private SocketService socketService;

    public void setSocketService(SocketService socketService) {
        this.socketService = socketService;
    }

    @FXML
    private void volver() {

    }

    @FXML
    private void salir() {
        Instruccion instruccion = new Instruccion(Constantes.CLOSE, "Cerrar");
        this.socketService.sendInstruccion(instruccion);
        System.exit(0);
    }

    @FXML
    private void inicio() throws IOException {
        App.setRoot("Inicio");
    }

    @FXML
    private void caderaMas() {
        timeline.play();
        movimiento = "OG";
    }

    private void moverBrazo() {
        System.out.println(movimiento);
        if (!movimiento.equals("")) {
            Instruccion instruccion = new Instruccion();
            instruccion.setInstruccion(Constantes.COMD);
            instruccion.setArgs(movimiento);
        }
    }

    @FXML
    private void nullMovimiento() {

        movimiento = "";
        timeline.stop();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        movimiento = "";
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            moverBrazo();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();
    }
}
