package client;


import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

//Klient klassen
public class Client {

    private ClientConnection clientConnection;
    private ClientGui cg;
    private String username;

    //Stänger ner clienten
    protected void windowIsClosed() {
        clientConnection.closeDown();
    }

    //Sätter användarens användarnamn till användarens ip-adress
    private String assignUsername(){
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            username = ipAddress.getHostAddress();
            System.out.println(ipAddress.getHostAddress());
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
        return username;
    }

    // Hämtar användarnamnet
    public String getUsername(){
        return username;
    }

    //Konstruktor för klienten
    public Client(String host, int port){

        try {
            this.username = assignUsername();
            Socket socket = new Socket(host, port);
            cg = new ClientGui(this);
            clientConnection = new ClientConnection(socket, this);
            clientConnection.start();
            cg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        } catch (IOException e) {
            System.out.println("Ingen tillgänglig server, stänger klienten!");
            System.exit(0);
        }
    }

    //Kallar metod som Skickar meddelandet till servern
    protected void newChatMessage(String s ) {
        clientConnection.sendStringToServer(s);
    }

    //Tar emot inkommande meddelande
    protected void incomingChatMessage(String s) {
        cg.addChatMessage(s);
    }

    //Main metod för klienten
    public static void main(String[] args){
        String clientHost = "127.0.0.1";
        String clientPort = "2000";
        if (args.length > 0) {
            if(args[0] != null) {
                clientHost = args[0];
            }
            if(args[1] != null ) {
                clientPort = args[1];
            }
        }
        new Client(clientHost, Integer.parseInt(clientPort));
    }

}
