package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientGui extends JFrame {

    private JTextArea chatWindow;
    private JTextField userText;
    private Client client;

    //GUI till klientsidan
    public ClientGui(Client client){
        super("Klientsidan");
        this.client = client;

        chatWindow = new JTextArea();
        userText = new JTextField();
        userText.setEditable(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.windowIsClosed();
            }
        });

        userText.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               client.newChatMessage(e.getActionCommand());
               userText.setText("");
           }
       });
       add(userText, BorderLayout.SOUTH);
       add(new JScrollPane(chatWindow), BorderLayout.CENTER);
       setSize(600, 300);
       setVisible(true);

    }

    //Lägger till meddelandet i gui:t
    protected void addChatMessage(String message) {
        chatWindow.append("\n" + message);
    }
}
