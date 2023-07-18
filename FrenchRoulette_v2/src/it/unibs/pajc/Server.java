package it.unibs.pajc;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import it.unibs.pajc.ServerTimer.TimerListener;

public class Server implements ServerTimer.TimerListener{
	
	private static final int TIMER_DURATION = 10; // Duration of the timer in seconds
	private ServerTimer serverTimer;
	private int remainingSeconds;
		
    public static void main(String[] args) {
        int port = 1234;
        Server server = new Server();
        server.startTimer();
        
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Waiting for client connection...");

            
            int id = 0;
            while (true) {
                Socket client = serverSocket.accept();
              
                MyProtocol clientProtocol = new MyProtocol(client, "CLI#" + id++ + "\n");
				Thread clientThread  = new Thread(clientProtocol);
				clientThread.start();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startTimer() {
        serverTimer = new ServerTimer(TIMER_DURATION);
        serverTimer.setTimerListener(this);
        serverTimer.start();
    }

    @Override
    public void onTick(int remainingSeconds) {
    	this.remainingSeconds = remainingSeconds;
        System.out.println("secondi: " + remainingSeconds);
    }
    
    public int getSeconds() {
    	return this.remainingSeconds;
    }
    
}
