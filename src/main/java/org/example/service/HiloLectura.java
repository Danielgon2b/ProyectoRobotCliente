package org.example.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.example.entity.Instruccion;
import org.example.utils.Constantes;

import java.io.BufferedReader;
import java.io.IOException;

public class HiloLectura extends Thread {

    private BufferedReader bufferedReader;
    private boolean live;
    private ChoiceBox<String> choiceBox;
    private Button button;

    public HiloLectura(BufferedReader bufferedReader,ChoiceBox<String> choiceBox,Button button) {
        this.bufferedReader = bufferedReader;
        this.choiceBox = choiceBox;
        this.button = button;
        live=true;
    }

    @Override
    public void run() {
        while(live){
            try {
                while (!bufferedReader.ready()) {
                }
                String linea = bufferedReader.readLine();
                if(Instruccion.isInstruccion(linea)){
                    Instruccion instruccion = Instruccion.getInstruccion(linea);
                    readInstruccion(instruccion);
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void readInstruccion(Instruccion instruccion){
        if(instruccion.getInstruccion().equals(Constantes.ERROR)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(instruccion.getInstruccion());
            alert.setHeaderText(null);
            alert.setContentText(instruccion.getArgs());
            alert.show();
            destroyHilo();
        }
        else if(instruccion.getInstruccion().equals(Constantes.OK)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(instruccion.getInstruccion());
            alert.setHeaderText(null);
            alert.setContentText(instruccion.getArgs());
            alert.show();

        }else if(instruccion.getInstruccion().equals(Constantes.PORTS)){
            ObservableList<String> items = FXCollections.observableArrayList(instruccion.getArgsList());
            choiceBox.setItems(items);
            choiceBox.setDisable(false);
            button.setDisable(false);
        }
    }

    private void destroyHilo(){
        live=false;
    }
}
