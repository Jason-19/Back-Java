import java.io.*;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Banco {
    private ArrayList<Usuario> cuentas;

    public Banco() {
        cuentas = new ArrayList<>();
    }


    public void agregarCuenta(Usuario usuario) {
        cuentas.add(usuario);
    }

    public ArrayList<Usuario> getCuentas() {
        return cuentas;
    }

    public Usuario iniciarSesion(String user_login, String contraseña) {
        for (Usuario usuario : cuentas) {

            if (usuario.getNombre().equals(user_login) && usuario.getContraseña().equals(contraseña)) {
                System.out.println("Existo! Login");
                return usuario;
            }
        }
        return null; // Devuelve null si no se encuentra el usuario
    }

    public void cargarCuentasDesdeArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            while ((linea = br.readLine()) != null) {
                
                String[] datos = linea.split(",");

                if (datos.length == 5) {
                    String nombre = datos[0].trim();
                    String numeroCuenta = datos[1].trim();
                    String contraseña = datos[2].trim();
                    double saldo = Double.parseDouble(datos[3].trim());
                    String rol = datos[4].trim();

                    agregarCuenta(new Usuario(nombre, numeroCuenta, contraseña, saldo,rol));
                }
            }
        } catch (IOException e) {

            System.err.println("Error al leer el archivo: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: cuentas.txt (El sistema no puede encontrar el archivo especificado)");
        }
    }

    //  ELIMINAR CUENTA 
    public void eliminarCuenta(String numeroCuenta) {
        cuentas.removeIf(usuario -> usuario.getNumeroCuenta().equals(numeroCuenta));
    }

    
}
