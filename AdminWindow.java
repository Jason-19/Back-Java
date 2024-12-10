/*
 * Autor Jaosn nuñez
 * 4-866-1408
 * Programacion V
 * Proyecto Finel 
 * Banco con socket
 * 10/12/2024
 */

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;

public class AdminWindow extends JFrame {
    private JTable table;
    private Banco banco;
    JTextField juser, jpass, jsaldo;
    JCheckBox is_admin;

    public AdminWindow(Banco banco) {
        this.banco = banco;
        initComponents();
        cargarCuentas();
    }

    private void cargarCuentas() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // Limpiar tabla

        for (Usuario usuario : banco.getCuentas()) {
            model.addRow(new Object[] { usuario.getNombre(), usuario.getNumeroCuenta(), usuario.getSaldo(),
                    usuario.getRol() });
        }
        SocketManager.sendMessage("Reload Accound..");
    }

    /* agrefagar cuentas */
    private void agregarCuenta() {

        String nombre = juser.getText();
        String password = jpass.getText();
        String cuenta = String.valueOf(numberAcount());
        double saldo = Double.parseDouble(jsaldo.getText());
        String admin = String.valueOf(is_admin.isSelected());
        System.out.println(nombre + " " + password + " " + cuenta + " " + saldo + " " + admin);

        if (juser.getText().isEmpty() && jpass.getText().isEmpty() && jsaldo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Datos en blanco");

        } else {
            if (saldo < 0) {
                JOptionPane.showMessageDialog(this, "El saldo no puede ser menor");
            } else {

                JOptionPane.showMessageDialog(this, "Agregado correctamente");
                banco.agregarCuenta(new Usuario(nombre, cuenta, password, saldo, admin));
                cargarCuentas(); // relod la rtable
                juser.setText("");
                jpass.setText("");
                jsaldo.setText("");

            }
        }

        cargarCuentas();
    }

    private void eliminarCuenta() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String cuenta = table.getValueAt(selectedRow, 1).toString();
            banco.eliminarCuenta(cuenta);
            cargarCuentas();
            SocketManager.sendMessage("Cuenta eliminada");

        } else {
            JOptionPane.showMessageDialog(this, "Seleccione una cuenta para eliminar.");
        }
    }

    /* Cuenta automatica */
    public int numberAcount() {
        int digCuenta;
        Random random = new Random();
        digCuenta = 10000000 + random.nextInt(90000000);
        return digCuenta;

    }

    /* inti components */
    void initComponents() {
        setTitle("Administrador de Cuentas");
        setSize(1000, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(Color.red);
        setLocationRelativeTo(null);

        // Modelo de tabla
        String[] columnNames = { "Nombre", "Cuenta", "Saldo", "Admin" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 20, 550, 250);
        add(scrollPane);

        JButton agregarBtn = new JButton("Agregar");
        agregarBtn.setBounds(630, 220, 150, 30);
        add(agregarBtn);

        JButton eliminarBtn = new JButton("Eliminar Cuenta");
        eliminarBtn.setBounds(200, 300, 150, 30);
        add(eliminarBtn);

        /* labels para el agregar cuentas */

        JLabel labeltitle = new JLabel("AGREGAR CUENTAS");
        labeltitle.setBounds(630, 10, 150, 30);
        labeltitle.setFont(new Font("Arial", Font.BOLD, 15));
        add(labeltitle);

        JLabel labeluser = new JLabel("Nombre");
        labeluser.setBounds(630, 50, 100, 30);
        labeluser.setFont(new Font("Arial", Font.BOLD, 12));
        add(labeluser);

        JLabel labelpass = new JLabel("Contraseña");
        labelpass.setBounds(630, 90, 150, 30);
        labelpass.setFont(new Font("Arial", Font.BOLD, 12));
        add(labelpass);

        JLabel labelsaldo = new JLabel("Saldo");
        labelsaldo.setBounds(630, 130, 100, 30);
        labelpass.setFont(new Font("Arial", Font.BOLD, 12));
        add(labelsaldo);

        juser = new JTextField();
        juser.setBounds(710, 50, 200, 30);
        add(juser);

        jpass = new JTextField();
        jpass.setBounds(710, 90, 200, 30);
        add(jpass);

        jsaldo = new JTextField();
        jsaldo.setBounds(710, 130, 200, 30);
        add(jsaldo);

        // is admin
        is_admin = new JCheckBox("Admin");
        is_admin.setBounds(630, 170, 100, 30);
        add(is_admin);

        /* ACTION LISTENER */
        agregarBtn.addActionListener(e -> agregarCuenta());
        eliminarBtn.addActionListener(e -> eliminarCuenta());
    }

}
