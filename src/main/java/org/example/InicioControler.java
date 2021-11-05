package org.example;

import javafx.fxml.FXML;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class InicioControler {


    @FXML
    private void establecerConexion() {
        Socket clientSocket = new Socket();

        InetSocketAddress address = new InetSocketAddress("localhost", 5555);

        try {
            clientSocket.connect(address);

            try (
                    InputStream is = clientSocket.getInputStream();
                    OutputStream os = clientSocket.getOutputStream();

                    InputStreamReader isr = new InputStreamReader(is);
                    OutputStreamWriter osw = new OutputStreamWriter(os);

                    BufferedReader bReader = new BufferedReader(isr);
                    PrintWriter pWriter = new PrintWriter(osw)) {
                System.out.println("Enviando mensaje");
                String mensaje = "mensaje desde el cliente";
                pWriter.print(mensaje);
                pWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void com1() throws IOException {

        App.setRoot("Robot");
    }

    @FXML
    private void salir(){
        System.exit(0);

    }
}
