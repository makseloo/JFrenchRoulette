package it.unibs.pajc.server;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;



public class Server1 {
	
	
	//private ServerModel serverModel;
		
    public static void main(String[] args) {
        int port = 1234;
        
        ServerModel serverModel = new ServerModel();
        serverModel.startTimer();
        //PnlCountdown pnlCountdown = new PnlCountdown(serverModel.getTimerDuration());
       // pnlCountdown.start();
        
        
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
    
    void updateTest(ActionEvent e) {
    	
    }
}
