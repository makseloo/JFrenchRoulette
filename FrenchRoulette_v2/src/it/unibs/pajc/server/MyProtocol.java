package it.unibs.pajc.server;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyProtocol implements Runnable {

    private Socket clientSocket;
    private String clientName;
    private ServerModel serverModel;
    private PrintWriter out;
    private ObjectOutputStream oos;

    public MyProtocol(Socket clientSocket, String clientName, ServerModel serverModel) {
        this.clientSocket = clientSocket;
        this.clientName = clientName;
        this.serverModel = serverModel;
    }

    public void run() {
        try (
        		ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
        ) {
        	 oos = new ObjectOutputStream(clientSocket.getOutputStream());
            System.out.printf("\nClient connesso: %s [%d] - Name: %s\n",
                    clientSocket.getInetAddress(), clientSocket.getPort(), clientName);

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    sendTimeUpdate();
                }
            }, 0, 100); // Send time update every second

            while (true) {
                // Read any incoming requests from the client if necessary
                // ...

                // Wait for the next iteration
                //Thread.sleep(10);
            }
        } catch (IOException  ex) {
            ex.printStackTrace();
        }

        System.out.printf("\nSessione terminata, client: %s\n", clientName);
    }

    private void sendTimeUpdate() {
        int seconds = serverModel.getSeconds();
        RouletteGameState gameState = serverModel.getPrevGameState();
        try {
            Message message = new Message(gameState.toString(), seconds);
            oos.writeObject(message);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
