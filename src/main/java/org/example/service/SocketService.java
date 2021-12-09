package org.example.service;

import org.example.entity.Instruccion;
import org.example.utils.Constantes;

import java.io.*;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketService {

    private PrintWriter printWriter;
    private BufferedReader bReader;

    public SocketService() {
    }

    public BufferedReader initConnection() {
        Socket clientSocket = new Socket();

        InetSocketAddress address = new InetSocketAddress(Constantes.ADDRS, Constantes.PORT);

        try {
            clientSocket.connect(address);
            socketConnect(clientSocket);
        } catch (ConnectException connectException) {
            Instruccion instruccion = new Instruccion(Constantes.ERROR, "No se ha podido encontrar el servidor");
            showAlert(instruccion);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bReader;
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

    public synchronized void showAlert(Instruccion instruccion) {
        if (instruccion.getInstruccion().equals(Constantes.ERROR)) {

        } else if (instruccion.getInstruccion().equals(Constantes.OK)) {

        }
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
