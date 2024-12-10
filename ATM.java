/*
 * Autor Jaosn nuñez
 * 4-866-1408
 * Programacion V
 * Proyecto Finel 
 * Banco con socket
 * 10/12/2024
 */

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.*;

public class ATM extends JFrame {

    private Banco banco;
    private Usuario usuario;
    static LoggerServer logger;
    static PrintWriter outputSocket;
    ArrayList<Usuario> cuentas;
    private JLabel saldoLabel;
    JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, be, bd;
    JTextField input;
    JTextArea atm_area;
    private static int PORT = 8001;
    private static String HOST = "localhost";

    public ATM(Banco banco, Usuario usuario) {

        this.banco = banco;
        this.usuario = usuario;
        initComponentsATM();
        pathControl();
    }

    private void abrirVentanaAdministrador() {
        AdminWindow adminWindow = new AdminWindow(banco);
        adminWindow.setVisible(true);
    }

    private void mostrarSaldo() {
        saldoLabel.setText("Saldo: $" + usuario.getSaldo());
        atm_area.append("Saldo $ " + usuario.getSaldo() + "\n");
        SocketManager.sendMessage("Mostrar saldo " + usuario.getSaldo() + " " + usuario.getNombre());

    }

    private void realizarDeposito() {

        String deposito_string = input.getText().trim();

        if (input.getText().isEmpty()) {
            System.out.println("Monto para deposito en blanco");
            JOptionPane.showMessageDialog(this, "Monto para deposito en blanco");
        } else {
            try {
                if (deposito_string != null) {

                    double monto = Double.parseDouble(deposito_string);
                    usuario.setSaldo(usuario.getSaldo() + monto);
                    mostrarSaldo();
                    atm_area.append("Deposito Realizado $" + deposito_string + "\n");
                    SocketManager.sendMessage("Deposito Realizado $" + deposito_string);

                }
            } catch (NumberFormatException e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(this, "Por favor, introduce un monto válido.");
            }

        }
    }

    private void realizarRetiro() {

        String retiro_string = input.getText().trim();
        if (input.getText().isEmpty()) {

            System.out.println("Prompt vacio");
            JOptionPane.showMessageDialog(this, "Monto de retiro en blanco");
            SocketManager.sendMessage(usuario.getNombre() + " Monto de retiro en blanco");

        } else {

            try {
                if (input != null) {

                    double monto = Double.parseDouble(retiro_string);
                    if (usuario.getSaldo() >= monto) {
                        usuario.setSaldo(usuario.getSaldo() - monto);
                        mostrarSaldo();
                        JOptionPane.showMessageDialog(this,
                                "Retiro de fondos ha sido un exito\n" + "Retiro de $" + monto);
                        SocketManager.sendMessage("Retiro de fondos has sido un exito " + monto);

                    } else {
                        JOptionPane.showMessageDialog(this, "Fondos insuficientes.");
                        SocketManager.sendMessage("Fondos insuficientes para hacer el Retiro");
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un monto válido.");
            }

        }
    }

    /* init components */
    void initComponentsATM() {
        setTitle("JAVA BANK ATM");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.red);

        /* Menu del navbar */

        JMenuBar menuBar = new JMenuBar();
        JMenu opcionesMenu = new JMenu("Opciones");
        JMenuItem adminMenuItem = new JMenuItem("Usuarios");

        adminMenuItem.addActionListener(e -> abrirVentanaAdministrador());
        opcionesMenu.add(adminMenuItem);
        menuBar.add(opcionesMenu);
        setJMenuBar(menuBar);

        /* Ventena princopal del ATM */
        JLabel lblUsuario = new JLabel("Bienvenido " + usuario.getNombre());
        lblUsuario.setBounds(400, 20, 200, 30);
        lblUsuario.setFont(new Font("Times new roman", Font.BOLD, 20));
        add(lblUsuario);

        /* */
        saldoLabel = new JLabel();// ("Saldo: $" + usuario.getSaldo());
        saldoLabel.setBounds(400, 550, 500, 30);
        saldoLabel.setFont(new Font("Times new roman", Font.BOLD, 30));
        add(saldoLabel);

        /* */
        JButton consultarSaldo = new JButton("Display Account Balance");
        consultarSaldo.setBounds(50, 50, 250, 70);
        add(consultarSaldo);
        /* */
        JButton hacerDeposito = new JButton("Make a deposit");
        hacerDeposito.setBounds(50, 120, 250, 70);
        add(hacerDeposito);

        JButton hacerRetiro = new JButton("Make a withdrawal");
        hacerRetiro.setBounds(50, 190, 250, 70);
        add(hacerRetiro);

        // area para logs

        atm_area = new JTextArea();
        atm_area.setBounds(380, 100, 300, 300);
        atm_area.setFont(new Font("Arial", Font.PLAIN, 18));
        add(atm_area);

        /* ACTION LISTENER DE BOTONES */
        consultarSaldo.addActionListener(e -> mostrarSaldo());
        hacerDeposito.addActionListener(e -> realizarDeposito());
        hacerRetiro.addActionListener(e -> realizarRetiro());
    }

    /* paara el path de numeos */
    void pathControl() {

        // campo
        input = new JTextField();
        input.setBounds(50, 265, 250, 50);
        add(input);

        // numeros
        b1 = new JButton("1");
        b1.setBounds(50, 320, 70, 70);
        add(b1);
        b2 = new JButton("2");
        b2.setBounds(130, 320, 70, 70);
        add(b2);
        b3 = new JButton("3");
        b3.setBounds(210, 320, 70, 70);
        add(b3);

        b4 = new JButton("4");
        b4.setBounds(50, 395, 70, 70);
        add(b4);
        b5 = new JButton("5");
        b5.setBounds(130, 395, 70, 70);
        add(b5);
        b6 = new JButton("6");
        b6.setBounds(210, 395, 70, 70);
        add(b6);

        b7 = new JButton("7");
        b7.setBounds(50, 470, 70, 70);
        add(b7);
        b8 = new JButton("8");
        b8.setBounds(130, 470, 70, 70);
        add(b8);
        b9 = new JButton("9");
        b9.setBounds(210, 470, 70, 70);
        add(b9);

        b0 = new JButton("0");
        b0.setBounds(50, 550, 70, 70);
        add(b0);
        bd = new JButton("DEL");
        bd.setBounds(210, 550, 70, 70);
        add(bd);

        be = new JButton("");
        be.setBounds(130, 550, 70, 70);
        add(be);

        // listeners
        b1.addActionListener(e -> {
            this.input.setText(this.input.getText() + "1");
        });
        b2.addActionListener(e -> {
            this.input.setText(this.input.getText() + "2");
        });
        b3.addActionListener(e -> {
            this.input.setText(this.input.getText() + "3");
        });
        b4.addActionListener(e -> {
            this.input.setText(this.input.getText() + "4");
        });
        b5.addActionListener(e -> {
            this.input.setText(this.input.getText() + "5");
        });
        b6.addActionListener(e -> {
            this.input.setText(this.input.getText() + "6");
        });
        b7.addActionListener(e -> {
            this.input.setText(this.input.getText() + "7");
        });
        b8.addActionListener(e -> {
            this.input.setText(this.input.getText() + "8");
        });
        b9.addActionListener(e -> {
            this.input.setText(this.input.getText() + "9");
        });
        b0.addActionListener(e -> {
            this.input.setText(this.input.getText() + "0");
        });

        bd.addActionListener(e -> {
            this.input.setText(" ");
        });

        // b1.addActionListener(e->{
        // this.input.setText(this.input.getText() +"1");
        // });
    }


    public Usuario iniciarSesion(String user_login, String contraseña) {
        for (Usuario usuario : cuentas) {

            if (usuario.getNombre().equals(user_login) && usuario.getContraseña().equals(contraseña)) {
                SocketManager.sendMessage("Bienvenido " + usuario.getNombre());
                return usuario;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {

            SocketManager.connect(HOST, PORT); // conexion con el soket

            new Thread(() -> {
                try {
                    String response;
                    while ((response = SocketManager.getInputSocket().readLine()) != null) {
                        System.out.println("[Server]: " + response);
                    }
                } catch (IOException e) {
                    System.err.println("Error al leer mensajes del servidoer: " + e.getMessage());
                    JOptionPane.showMessageDialog(null,
                            "Erro de comunicacion con el servidor " + HOST + ":" + PORT + "\n" + e);
                }

            }).start();
            // instancias
            Banco banco = new Banco();
            banco.cargarCuentasDesdeArchivo("cuentas.txt"); // cuentas desde un archivo
            SocketManager.sendMessage("Cliente concestado ");
            new LoginWindow(banco).setVisible(true);

        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor en " + HOST + ":" + PORT);
            JOptionPane.showMessageDialog(null, "No se pudo conectar al servidor en " + HOST + ":" + PORT + "\n" + e);
            e.printStackTrace();
        }

    }
}