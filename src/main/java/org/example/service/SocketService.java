package org.example.service;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import org.example.entity.Instruccion;
import org.example.utils.Constantes;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketService {

    private PrintWriter printWriter;
    private BufferedReader bReader;
    private HiloLectura hiloLectura;
    private ChoiceBox<String> choiceBox;
    private Button button;

    public SocketService(ChoiceBox<String> choiceBox, Button button){
        this.choiceBox = choiceBox;
        this.button = button;
    }

    public void initConnection() {
        Socket clientSocket = new Socket();

        InetSocketAddress address = new InetSocketAddress(Constantes.ADDRS, Constantes.PORT);

        try {
            clientSocket.connect(address);

            socketConnect(clientSocket);
        } catch (
                IOException e) {
            e.printStackTrace();
        }finally {
            closeAll(clientSocket);
        }
    }
    private void socketConnect(Socket clientSocket) {
        try (
                InputStream is = clientSocket.getInputStream();
                OutputStream os = clientSocket.getOutputStream();

                InputStreamReader isr = new InputStreamReader(is);
                OutputStreamWriter osw = new OutputStreamWriter(os)) {
            bReader = new BufferedReader(isr);
            printWriter = new PrintWriter(osw);
            if(hiloLectura ==null) {
                hiloLectura = new HiloLectura(bReader,choiceBox,button);
                hiloLectura.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendInstruccion(Instruccion instruccion){
        if(instruccion.getArgsList()==null) {
            printWriter.print(instruccion.toString());
        }
        else{
            printWriter.print(instruccion.toStringList());
        }
        printWriter.flush();
    }

    private void closeAll(Socket socket) {
        if(socket!=null){
            try {
                socket.close();
                printWriter.close();
                bReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
