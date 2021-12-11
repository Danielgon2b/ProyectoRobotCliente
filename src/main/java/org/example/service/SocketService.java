package org.example.service;

import javafx.scene.control.Alert;
import org.example.entity.Instruccion;
import org.example.utils.Constantes;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class SocketService {

    private PrintWriter printWriter;
    private BufferedReader bReader;

    public SocketService() {
    }

    public BufferedReader getBReader(){
        return this.bReader;
    }

    public boolean initConnection(String addrs,int puerto) {
        Socket clientSocket = new Socket();
        InetSocketAddress address = new InetSocketAddress(addrs, puerto);
        try {
            clientSocket.connect(address);
            socketConnect(clientSocket);
        } catch (SocketException socketException) {
            Alert errorconexion = new Alert(Alert.AlertType.ERROR);
            errorconexion.setTitle("Error Conexion");
            errorconexion.setHeaderText(null);
            errorconexion.setContentText("Se ha detectado un error durante la conexion. \nCompruebe:\n-El programa de servidor esta en ejecucion.\n-Si usted tiene conexion con la red del servidor \n-Si los valores de IP/Puertos son correctas");
            errorconexion.show();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void socketConnect(Socket clientSocket) {
        try {
            InputStream is = clientSocket.getInputStream();
            OutputStream os = clientSocket.getOutputStream();
            InputStreamReader isr = new InputStreamReader(is);
            OutputStreamWriter osw = new OutputStreamWriter(os);
            bReader = new BufferedReader(isr);
            printWriter = new PrintWriter(osw);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendInstruccion(Instruccion instruccion) {
        String cadena;
        if (instruccion.getArgsList() == null) {
            cadena = instruccion.toString();
        } else {
            cadena = instruccion.toStringList();
        }
        printWriter.print(cadena);
        printWriter.flush();

    }

    private void closeAll(Socket socket) {
        if (socket != null && printWriter != null && bReader != null) {
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
