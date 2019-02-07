package server;


import javax.swing.*;
import java.awt.*;

//Klass för server GUI
public class ServerGUI extends JFrame {

    private JTextArea chatWindow;

    // Konstruktor för serverns GUI
    public ServerGUI(){
        super("Serversidan");

        chatWindow = new JTextArea();
        add(new JScrollPane(chatWindow), BorderLayout.CENTER);
        setSize(600,300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    //Uppdaterar titeln på server fönstret
    protected void updateTitle(String s){
            setTitle("Serversidan: " + s);
    }

}
