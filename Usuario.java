/*
 * Jason nuñez
 */

public class Usuario {
    private String nombre;
    private String numeroCuenta;
    private String contraseña;
    private double saldo;
    private String rol;

    public Usuario(String nombre, String numeroCuenta, String contraseña, double saldo, String rol) {

        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
        this.contraseña = contraseña;
        this.saldo = saldo;
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getContraseña() {
        return contraseña;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getRol() {
        return rol;
    }
    
}
