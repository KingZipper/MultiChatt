package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//Klass för server trådarna
public class ServerConnection extends Thread {

    private Socket socket;
    private Server server;
    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean shouldRun = true;



    //Konstruktor till server trådarna
    public ServerConnection(Socket socket, Server server){
        super("ServerConnectionThread");
        this.socket = socket;
        this.server = server;


    }

    // Skickar meddelande ut till klienterna
    public void sendStringToClient(String text){
        try {
            dos.writeUTF(text);
            dos.flush();
        } catch (IOException e) {
            shutDown();
            //e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.print("Tried to write to dead clients");
        }
    }

    // Kollar alla som anslutna till servern och skickar kallar på metoden som skickar iväg meddelandet
    public void sendStringToAllClients(String text){
            for(int index = 0; index < server.getConnections().size(); index++) {
                ServerConnection sc = server.getConnections().get(index);
                sc.sendStringToClient(text);
            }
    }

    //Tar bort ansluitningen ur listan av anslutningar
    protected void shutDown() {
        server.removeConnection(this);
        shouldRun = false;
    }

    //Run metod för server trådarna
    @Override
    public void run(){
       int count = 0;
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            while(shouldRun){

                while(dis.available() == 0){
                    try {
                        if(count == 100) {
                            sendStringToAllClients("test-ping");
                             count = 0;
                        }
                        Thread.sleep(1);
                        count += 1;
                    } catch (InterruptedException e) {

                    }
                }
                String textIn = dis.readUTF();
                sendStringToAllClients(textIn);
            }
            dis.close();
            dos.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
