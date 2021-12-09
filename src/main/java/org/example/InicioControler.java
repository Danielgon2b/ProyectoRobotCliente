package org.example;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.entity.Instruccion;
import org.example.service.SocketService;
import org.example.utils.Constantes;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InicioControler implements Initializable {

    private boolean live;

    private SocketService socketService;

    private Thread hiloLectura;
    private BufferedReader bufferedReader;

    @FXML
    private Button buttonSeleccionar;

    @FXML
    private ChoiceBox<String> choiceBoxComs;

    public InicioControler() {
        this.live = true;
    }

    @FXML
    private void establecerConexion() {
        bufferedReader = socketService.initConnection();
        hiloLectura.start();
    }

    @FXML
    private void salir() {
        Instruccion instruccion = new Instruccion(Constantes.CLOSE);
        socketService.sendInstruccion(instruccion);
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        socketService = new SocketService();
        hiloLectura = new Thread(new Runnable() {
            @Override
            public void run() {
                while (live) {
                    try {
                        if (bufferedReader.ready()) {
                            String cadena = bufferedReader.readLine();
                            if (Instruccion.isInstruccion(cadena)) {
                                Instruccion instruccion = Instruccion.getInstruccion(cadena);
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (instruccion.getInstruccion().equals(Constantes.ERROR)) {
                                            Alert alert = new Alert(Alert.AlertType.ERROR);
                                            alert.setTitle(Constantes.ERROR);
                                            alert.setContentText(instruccion.getArgs());
                                            alert.show();
                                        } else if (instruccion.getInstruccion().equals(Constantes.OK)) {
                                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                            alert.setTitle(Constantes.OK);
                                            alert.setHeaderText(null);
                                            alert.setContentText(instruccion.getArgs());
                                            alert.show();
                                            accessApp();
                                        } else if (instruccion.getInstruccion().equals(Constantes.PORTS)) {
                                            ObservableList<String> items = FXCollections.observableArrayList(instruccion.getArgsList());
                                            choiceBoxComs.setItems(items);
                                            choiceBoxComs.setDisable(false);
                                            buttonSeleccionar.setDisable(false);
                                        }
                                    }
                                });
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @FXML
    private void setPuerto() {
        Instruccion instruccion = new Instruccion();
        instruccion.setInstruccion(Constantes.CONNECT_PORT);
        instruccion.setArgs(choiceBoxComs.getValue());
        socketService.sendInstruccion(instruccion);
    }

    public void accessApp() {
        access();
    }

    private Stage access() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Robot.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(new Scene(loader.load()));
            RobotControler controler = loader.<RobotControler>getController();
            controler.setSocketService(this.socketService);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stage;
    }
}
