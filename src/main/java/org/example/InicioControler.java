package org.example;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.layout.AnchorPane;
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
    private TranslateTransition animationMenu;
    private boolean menuDesplegado;

    @FXML
    private Button buttonSeleccionar;

    @FXML
    private ChoiceBox<String> choiceBoxComs;

    @FXML
    private VBox vboxPrincipal;

    @FXML
    private AnchorPane anchorPaneMenu;

    @FXML
    private TextField textIp;

    @FXML
    private TextField textPuerto;

    public InicioControler() {
        this.live = true;
    }

    @FXML
    private void animacionMenu() {
        if (menuDesplegado) {
            animationMenu.setFromX(anchorPaneMenu.getMaxWidth());
            animationMenu.setToX(0);
            menuDesplegado = false;
        } else {
            animationMenu.setFromX(0);
            animationMenu.setToX(anchorPaneMenu.getMaxWidth());
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
        if(socketService.isConnected()) {
            socketService.sendInstruccion(instruccion);
        }
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textPuerto.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    textPuerto.setText(newValue.replaceAll("[^\\d]",""));
                }
            }
        });
        animationMenu = new TranslateTransition(Duration.millis(100), vboxPrincipal);
        animationMenu.setFromX(0);
        animationMenu.setToX(anchorPaneMenu.getMaxWidth());
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
            Stage thisStage =(Stage) vboxPrincipal.getScene().getWindow();
            thisStage.close();
            stage.setOnCloseRequest(event -> {
                salir();
                System.exit(0);
            });
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
