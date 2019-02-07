package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//Klient-trådsklassen
public class ClientConnection extends Thread {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean shouldRun = true;
    private Client client;

    //Konstruktor för klient tråden
    public ClientConnection(Socket socket, Client client){
        this.socket = socket;
        this.client = client;
    }

    //Skickar meddelandet till servern
    public void sendStringToServer(String text){
        try {
            dos.writeUTF(client.getUsername() + " - " + text);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            closeMethod();
        }

    }

    //Stänger av tråden, och kallar på stängmetoden till sockets och streams
    protected void closeDown() {
        shouldRun = false;
        closeMethod();
    }

    //Run metod för tråden
    public void run() {

        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            while (shouldRun) {
                try {
                    while (dis.available() == 0) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            closeMethod();
                        }
                    }
                    String reply = dis.readUTF();
                    if(reply.equals("test-ping")){
                        continue;
                    }

                    if(!reply.equals("test-ping")) {
                        client.incomingChatMessage(reply);
                    }

                } catch(IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            closeMethod();
        }

    }

    //Metod för att stänga strömmarna och sockets
    public void closeMethod(){
        System.out.println("Shut down method");
        try {
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
