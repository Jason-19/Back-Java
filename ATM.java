
import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.*;

public class ATM extends JFrame {
    private Banco banco;
    private Usuario usuario;
    Logger logger;
    private static PrintWriter out;
    ArrayList<Usuario> cuentas;
    private JLabel saldoLabel;
    JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, be, bd;
    JTextField input;
    JTextArea atm_area;

    public ATM(Banco banco, Usuario usuario) {

        this.banco = banco;
        this.usuario = usuario;

        // components
        initComponentsATM();
        // controles
        pathControl();

    }

    private void abrirVentanaAdministrador() {
        AdminWindow adminWindow = new AdminWindow(banco);
        adminWindow.setVisible(true);
    }

    private void mostrarSaldo() {
        saldoLabel.setText("Saldo: $" + usuario.getSaldo());
        // atm_area.append("Saldo $ " + usuario.getSaldo() + "\n");
    }

    private void realizarDeposito() {

        String deposito_string = input.getText().trim();

        if (input.getText().isEmpty()) {

            System.out.println("Monto para deposito en blanco");
            JOptionPane.showMessageDialog(this, "Monto para deposito en blanco");

        } else {

            if (deposito_string != null) {

                double monto = Double.parseDouble(deposito_string);
                usuario.setSaldo(usuario.getSaldo() + monto);
                mostrarSaldo();

                atm_area.append("Deposito Realizado $" + deposito_string + "\n");
                String message = "Deposito Realizado $" + deposito_string;
                logger.status(message, usuario);
                ;

            }
        }
    }

    private void realizarRetiro() {

        String retiro_string = input.getText().trim();
        if (input.getText().isEmpty()) {

            System.out.println("Prompt vacio");
            JOptionPane.showMessageDialog(this, "Monto de retiro en blanco");

        } else {

            if (input != null) {

                double monto = Double.parseDouble(retiro_string);
                if (usuario.getSaldo() >= monto) {
                    usuario.setSaldo(usuario.getSaldo() - monto);
                    mostrarSaldo();
                    JOptionPane.showMessageDialog(this, "Retiro de fondos ha sido un exito\n"+"Retiro de $"+monto);

                } else {
                    JOptionPane.showMessageDialog(this, "Fondos insuficientes.");
                }

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
        // getContentPane().setBackground(Color.black);

        /* Menu del navbar */

        JMenuBar menuBar = new JMenuBar();
        JMenu opcionesMenu = new JMenu("Opciones");
        JMenuItem adminMenuItem = new JMenuItem("Usuarios");

        adminMenuItem.addActionListener(e -> abrirVentanaAdministrador());
        opcionesMenu.add(adminMenuItem);
        menuBar.add(opcionesMenu);
        setJMenuBar(menuBar);

        /* Ventena princopal del ATM */
        JLabel lblUsuario = new JLabel("Usuario: " + usuario.getNombre());
        lblUsuario.setBounds(620, 20, 200, 30);
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

    /* Controlde botones activos */
    void EnableButton(boolean is_enable) {

        b0.setEnabled(is_enable);
        b1.setEnabled(is_enable);
        b2.setEnabled(is_enable);
        b3.setEnabled(is_enable);
        b4.setEnabled(is_enable);
        b5.setEnabled(is_enable);
        b6.setEnabled(is_enable);
        b7.setEnabled(is_enable);
        b8.setEnabled(is_enable);
        b9.setEnabled(is_enable);
        be.setEnabled(is_enable);
        bd.setEnabled(is_enable);
        // field
        input.setEnabled(is_enable);
    }

    public Usuario iniciarSesion(String user_login, String contraseña) {
        for (Usuario usuario : cuentas) {

            if (usuario.getNombre().equals(user_login) && usuario.getContraseña().equals(contraseña)) {
                System.out.println("Existo! Login");
                return usuario;
            }
        }
        return null;
    }


    // Método para iniciar conexión con el servidor
    private void init_socket_cliente() {
        int PORT = 8001;
        String HOST = "localhost";

        try {
            Socket socket = new Socket(HOST, PORT);
            out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Hilo para recibir respuestas del servidor
            new Thread(() -> {
                String response;
                try {
                    while ((response = in.readLine()) != null) {
                        System.out.println("[SERVER]: " + response);
                    }
                } catch (IOException e) {
                    System.err.println("Error de comunicación con el servidor.");
                    e.printStackTrace();
                }
            }).start();

            System.out.println("CLIENTE IS RUNNING....");

        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor en " + HOST + ":" + PORT);
            e.printStackTrace();
        }
    }
    

    public static void main(String[] args) {

        Banco banco = new Banco();
        banco.cargarCuentasDesdeArchivo("cuentas.txt"); // cuentas desde un archivo

        new ATM(banco, banco.iniciarSesion("jason", "admin")).setVisible(true);
        // new LoginWindow(banco).setVisible(true);
        // new AdminWindow(banco).setVisible(true);
    }

}
