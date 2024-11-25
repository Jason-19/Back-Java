import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Logger extends JFrame {

    private Usuario usuario;
    JTextArea loggers;
    private String messageStatus;


    public Logger(String messageStatus, Usuario usuario) {
        this.usuario =  usuario;
        this.messageStatus =  messageStatus;

        setTitle("SERVER ATM");
        setSize(900, 800);
        setResizable(false);
        status(messageStatus,usuario);
        getContentPane().setBackground(Color.LIGHT_GRAY);
    }

    public void status(String status, Usuario usuario) {
        LocalDateTime fechaActual = LocalDateTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String fechaFormateada = fechaActual.format(formato);
        init_Jarea();

        for(int i=0;i<10;i++){
            // System.out.println(i);
            loggers.append(" ROOT@SERVER~ "+fechaFormateada+" "+usuario.getNombre().toUpperCase()+" "+status + "\n");
        }
    }

    void init_Jarea(){
    
        loggers = new JTextArea();
        // loggers.setBounds(0,50,600,700);
        // loggers.setForeground(Color.white); 
        // loggers.setBackground(Color.black); 
        loggers.setFont(new Font("Times New Roman",Font.PLAIN,15));
        // add(loggers);    

        JScrollPane scrollPane = new JScrollPane(loggers);
        add(scrollPane);
    }

    // public static void main(String[] args) {
        
    //     new Logger("Mensaje de coneccion de prueba",new Usuario("jason", "147855","admin",500,"true")).setVisible(true);
    // }
}
