package org.example;

import javafx.animation.TranslateTransition;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.entity.Instruccion;
import org.example.service.SocketService;
import org.example.utils.Constantes;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class InicioControler implements Initializable {

    private boolean live;
    private SocketService socketService;
    private Thread hiloLectura;
    private BufferedReader bufferedReader;
    private Stage robotStage;
    private TranslateTransition animationMenu;
    private boolean menuDesplegado;

    @FXML
    private Button buttonSeleccionar;

    @FXML
    private ChoiceBox<String> choiceBoxComs;

    @FXML
    private VBox vboxPrincipal;

    @FXML
    private VBox vboxMenu;

    @FXML
    private TextField textIp;

    @FXML
    private TextField textPuerto;

    public InicioControler() {
        this.live = true;
    }

    public void setRobotStage(Stage robotStage) {
        this.robotStage = robotStage;
    }

    @FXML
    private void animacionMenu() {
        if (menuDesplegado) {
            animationMenu.setFromX(vboxMenu.getPrefWidth());
            animationMenu.setToX(-5);
            menuDesplegado = false;
        } else {
            animationMenu.setFromX(-5);
            animationMenu.setToX(vboxMenu.getPrefWidth());
            menuDesplegado = true;
        }
        animationMenu.play();
    }

    @FXML
    private void establecerConexion() {
        guargarArchivo();
        if (socketService.initConnection(textIp.getText(),Integer.parseInt(textPuerto.getText()))) {
            this.bufferedReader = socketService.getBReader();
            hiloLectura.start();
        }
    }

    @FXML
    private void salir() {
        Instruccion instruccion = new Instruccion(Constantes.CLOSE);
        socketService.sendInstruccion(instruccion);
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vboxPrincipal.setStyle("-fx-background-color: #FFFFFF;");
        animationMenu = new TranslateTransition(Duration.millis(100), vboxPrincipal);
        animationMenu.setFromX(-5);
        animationMenu.setToX(vboxMenu.getPrefWidth());
        animationMenu.setCycleCount(1);
        menuDesplegado = false;
        File file = new File(Constantes.ADDRSFILE, Constantes.FILENAME);
        if (file.exists()) {
            try (FileReader fileReader = new FileReader(file);
                 BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                String texto = bufferedReader.readLine();
                String[] strings = texto.split(Constantes.REGEX_DIV);
                textIp.setText(strings[0]);
                textPuerto.setText(strings[1]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


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

    private void access() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Robot.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(new Scene(loader.load()));
            RobotControler controler = loader.<RobotControler>getController();
            controler.setSocketService(this.socketService);
            controler.setInicioStage(stage);
            if (robotStage != null) {
                robotStage.close();
            }
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void guargarArchivo() {
        File ruta = new File(Constantes.ADDRSFILE);
        if (!ruta.exists()) {
            ruta.mkdirs();
        }
        File file = new File(Constantes.ADDRSFILE, Constantes.FILENAME);
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            String texto = textIp.getText() + ";" + textPuerto.getText();
            bufferedWriter.write(texto);
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
