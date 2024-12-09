// administrador de los sockets para la enviar y recbir inforamcio globalmente 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

public class SocketManager {

    private static Socket socket;
    private static PrintWriter outputSocket;
    private static BufferedReader inputSocket;

    public static void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        outputSocket = new PrintWriter(socket.getOutputStream(), true);
        inputSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static PrintWriter getOutputSocket() {
        return outputSocket;
    }

    public static BufferedReader getInputSocket() {
        return inputSocket;
    }

    public static void sendMessage(String message) {
        if (outputSocket != null) {
            outputSocket.println(message);
        } else {
            System.out.println("Error: No se puede enviar el mensaje.\nEl servidor no está conectado");
            JOptionPane.showMessageDialog(null, "Error: No se puede enviar el mensaje.\nEl servidr no esta conectado");
        }
    }

    public static void closeConnection() throws IOException {
        if (socket != null) {
            socket.close();
        }
    }
}