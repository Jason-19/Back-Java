
// /*
//  * Autor Jaosn nuñez
//  * 4-866-1408
//  * Programacion V
//  * Proyecto Finel 
//  * Banco con socket
//  * 10/12/2024
//  */
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LoggerServer extends JFrame {

    private JTextArea loggers_area;

    public LoggerServer() {
        setTitle("Servidor ATM Bank");
        setSize(900, 800);
        setResizable(false);
        getContentPane().setBackground(Color.BLACK);
        setLocationRelativeTo(null);
        init_Jarea();
        setVisible(true);
    }

    private void init_Jarea() {
        loggers_area = new JTextArea();
        loggers_area.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        JScrollPane scrollPane = new JScrollPane(loggers_area);
        loggers_area.append("WELCOME BANK SERVER\n");
        add(scrollPane);
    }

    public synchronized void logStatus(String status) {
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String fechaFormateada = fechaActual.format(formato);
        loggers_area.append(fechaFormateada + " - " + " " + status + "\n");
    }

    public static void main(String[] args) {
        int PORT = 8001;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            LoggerServer logger = new LoggerServer();

            logger.logStatus("SERVIDOR INICIADO EN PUERTO " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.logStatus(clientSocket.getInetAddress() + " Nuevo cliente conectado");

                // Crear un nuevo hilo para manejar al cliente
                new Thread(new MultipleClientsThread(clientSocket, logger)).start();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al iniciar el servidor: " +
                    e.getMessage());
            e.printStackTrace();
        }
    }
}

// multiples usuatios
class MultipleClientsThread extends Thread {
    private Socket clientSocket;
    private LoggerServer logger;

    public MultipleClientsThread(Socket clientSocket, LoggerServer logger) {
        this.clientSocket = clientSocket;
        this.logger = logger;
    }

    @Override
    public void run() {
        try (PrintWriter outputSocketClient = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader inputSocketClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            outputSocketClient.println("TE HAS CONECTADO CON EL SERVIDOR DEL BANCO");
            String responseClient;

            while ((responseClient = inputSocketClient.readLine()) != null) {
                logger.logStatus("[Cliente] " + responseClient);
                outputSocketClient.println("Mensaje recibido: " + responseClient);
            }

        } catch (Exception e) {
            logger.logStatus("Error al manejar al cliente: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                logger.logStatus("Cliente desconectado: " + clientSocket.getInetAddress());
            } catch (Exception e) {
                logger.logStatus("Error al cerrar el socket: " + e.getMessage());
            }
        }
    }
}
