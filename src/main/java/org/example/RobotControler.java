package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.example.entity.Instruccion;
import org.example.service.SocketService;
import org.example.utils.Comands;
import org.example.utils.Constantes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RobotControler implements Initializable {

    private Timeline timeline;
    private String movimiento;
    private SocketService socketService;
    private TranslateTransition animationMenu;
    private boolean menuDesplegado;
    private int cadera;
    private int caderaSumador;
    private int hombro;
    private int hombroSumador;
    private int codo;
    private int codoSumador;
    private int pitch;
    private int pitchSumador;
    private int roll;
    private int rollSumador;

    @FXML
    private HBox hBoxPrincipal;

    @FXML
    private VBox vBoxMenu;

    @FXML
    private Label labelInstrucciones;

    @FXML
    private TextArea textAreaMacro;

    public RobotControler(){
        this.cadera=0;
        this.hombro=0;
        this.codo=0;
        this.pitch=0;
        this.roll=0;
    }

    public void setSocketService(SocketService socketService) {
        this.socketService = socketService;
        this.labelInstrucciones.setText("NT\tOrigen Mecanico\nMJ\tMover por articulaciones\nGC\tCerrar la pinza\nGO\tAbrir la pinza\nMO\tMover a la posicion\nHE\tEstablecer posicion\nED\tFinaliza el programa");
    }

    @FXML
    private void logOut() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inicio.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
        try {
            stage.setScene(new Scene(loader.load()));
            Stage thisStage = (Stage) hBoxPrincipal.getScene().getWindow();
            Instruccion instruccion = new Instruccion(Constantes.CLOSE,"LogOut");
            this.socketService.sendInstruccion(instruccion);
            thisStage.close();
            stage.setOnCloseRequest(event -> {
                this.salir();
            });
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
    private void caderaMas() {
        timeline.play();
        movimiento = Comands.MOVER_POR_ARTICULACIONES;
        caderaSumador=1;
    }

    @FXML
    private void caderaMenos() {
        timeline.play();
        movimiento = Comands.MOVER_POR_ARTICULACIONES;
        caderaSumador=-1;
    }

    @FXML
    private void hombroMas() {
        timeline.play();
        movimiento = Comands.MOVER_POR_ARTICULACIONES;
        hombroSumador=1;
    }

    @FXML
    private void hombroMenos() {
        timeline.play();
        movimiento = Comands.MOVER_POR_ARTICULACIONES;
        hombroSumador=-1;
    }

    @FXML
    private void codoMas() {
        timeline.play();
        movimiento = Comands.MOVER_POR_ARTICULACIONES;
        codoSumador=1;
    }

    @FXML
    private void codoMenos() {
        timeline.play();
        movimiento = Comands.MOVER_POR_ARTICULACIONES;
        codoSumador=-1;
    }

    @FXML
    private void pitchMas() {
        timeline.play();
        movimiento = Comands.MOVER_POR_ARTICULACIONES;
        pitchSumador=1;
    }

    @FXML
    private void pitchMenos() {
        timeline.play();
        movimiento = Comands.MOVER_POR_ARTICULACIONES;
        pitchSumador=-1;
    }

    @FXML
    private void rollMas() {
        timeline.play();
        movimiento = Comands.MOVER_POR_ARTICULACIONES;
        rollSumador=1;
    }

    @FXML
    private void rollMenos() {
        timeline.play();
        movimiento = Comands.MOVER_POR_ARTICULACIONES;
        rollSumador=-1;
    }

    @FXML
    private void abrirPinza() {
        timeline.play();
        movimiento = Comands.ABRIR_PINZA;
    }

    @FXML
    private void cerrarPinza() {
        timeline.play();
        movimiento = Comands.CERRAR_PINZA;
    }

    @FXML
    private void nullMovimiento() {
        movimiento = "";
        caderaSumador=0;
        hombroSumador=0;
        codoSumador=0;
        pitchSumador=0;
        rollSumador=0;
        timeline.stop();
    }

    @FXML
    private void animacionMenu() {
        if (menuDesplegado) {
            animationMenu.setFromX(vBoxMenu.getPrefWidth());
            animationMenu.setToX(0);
            menuDesplegado = false;
        } else {
            animationMenu.setFromX(0);
            animationMenu.setToX(vBoxMenu.getPrefWidth());
            menuDesplegado = true;
        }
        animationMenu.play();
    }

    @FXML
    private void eviarMacro(){
        if(textAreaMacro.getText()!=null || textAreaMacro.getText().equals("")){
            Instruccion instruccion = new Instruccion(Constantes.COMD,textAreaMacro.getText());
            this.socketService.sendInstruccion(instruccion);
        }
    }

    private void moverBrazo() {
        if (!movimiento.equals("")) {
            Instruccion instruccion = new Instruccion();
            instruccion.setInstruccion(Constantes.COMD);
            if(movimiento.equals(Comands.MOVER_POR_ARTICULACIONES)){
                movimiento = movimiento + (cadera+caderaSumador) + "," + (hombro+hombroSumador)+","+(codo+codoSumador)+","+(pitch+pitchSumador)+","+(roll+rollSumador);
            }
            else{
                instruccion.setArgs(movimiento);
            }
            this.socketService.sendInstruccion(instruccion);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        movimiento = "";
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            moverBrazo();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        this.animationMenu = new TranslateTransition(Duration.millis(100),hBoxPrincipal);
        this.animationMenu.setToX(0);
        this.animationMenu.setToX(vBoxMenu.getPrefWidth());
        this.animationMenu.setCycleCount(1);
        menuDesplegado = false;
    }
}
