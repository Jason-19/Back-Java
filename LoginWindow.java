import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

public class LoginWindow extends JFrame {
    private Banco banco;

    public LoginWindow(Banco banco) {
        this.banco = banco;

        setTitle("Login - JAVA BANK ATM");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);
        // getContentPane().setBackground(Color.gray); 



        JLabel lbusuario = new JLabel("Usuario");
        lbusuario.setBounds(50, 50, 150, 30);
        lbusuario.setFont(new Font("Arial",Font.BOLD,20));
        add(lbusuario);
        

        JTextField txtusuario = new JTextField();
        txtusuario.setBounds(200, 50, 150, 30);
        add(txtusuario);
        
        JLabel lblContraseña = new JLabel("Contraseña:");
        lblContraseña.setBounds(50, 100, 150, 30);
        lblContraseña.setFont(new Font("Arial",Font.BOLD,20));
        add(lblContraseña);

        JPasswordField txtContraseña = new JPasswordField();
        txtContraseña.setBounds(200, 100, 150, 30);
        
        add(txtContraseña);

        JButton btnLogin = new JButton("Iniciar Sesión");
        btnLogin.setBounds(150, 200, 120, 50);
        btnLogin.setFocusPainted(false); 

        add(btnLogin);

        /* listener ara le login del usario */
        btnLogin.addActionListener(e -> {

            String numeroCuenta = txtusuario.getText();
            String contraseña = new String(txtContraseña.getPassword());

            Usuario usuario = banco.iniciarSesion(numeroCuenta, contraseña);
            
            if (usuario != null) {
                // JOptionPane.showMessageDialog(this, "¡Bienvenido, " + usuario.getNombre() + "!");
                new ATM(banco, usuario).setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
            }
        });
    }
}