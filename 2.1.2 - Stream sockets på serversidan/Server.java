package server;


import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//Server klassen
public class Server {

    private ServerSocket serverSocket;
    private ArrayList<ServerConnection> connections = new ArrayList<>();
    private boolean shouldRun = true;
    private ServerGUI sg;
    private String textIp;
    private String hostPort;



    //Metod för att hämta alla uppkopplade
    public ArrayList<ServerConnection> getConnections(){
        return connections;
    }

    //Tar bort utvald anslutning ur anslutningslistan och uppdaterar titeln på GUIt
    protected void removeConnection(ServerConnection toRemove) {
        connections.remove(toRemove);
        sg.updateTitle("Clients: (" + connections.size() +  ") " + " IP: " + textIp + "  Port: " + hostPort);
    }


    //Serverns konstruktor
    public Server(int port){
        try {
            serverSocket = new ServerSocket(port);
            InetAddress ip = InetAddress.getLocalHost();
            textIp = ip.getHostAddress();
            hostPort = serverSocket.getLocalPort() + "";
            sg = new ServerGUI();
            while(shouldRun){


                Socket socket = serverSocket.accept();
                ServerConnection sc = new ServerConnection(socket, this);
                sc.start();
                connections.add(sc);
                sg.updateTitle("Clients: (" + connections.size() +  ") " + "IP: " + textIp + "Port: " + hostPort);

            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Main metod för server sidan
    public static void main(String []args){
        String defaultPort = "2000";
        if (args.length > 0) {
            if(args[0] != null) {
                 defaultPort = args[0];
            }

        }
        new Server(Integer.parseInt(defaultPort));

    }


}
