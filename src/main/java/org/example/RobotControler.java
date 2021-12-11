package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    private Stage inicioStage;

    public void setSocketService(SocketService socketService) {
        this.socketService = socketService;
    }

    public void setInicioStage(Stage inicioStage){
        this.inicioStage = inicioStage;
    }

    @FXML
    private void logOut() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(new Scene(loader.load()));
            InicioControler controler = loader.<InicioControler>getController();
            controler.setRobotStage(stage);
            if(inicioStage!=null){
                inicioStage.close();
            }
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            this.socketService.sendInstruccion(instruccion);
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
