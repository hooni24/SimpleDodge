package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket server;
	private Socket client;

	public Server() {
		try {
			server = new ServerSocket(7979);
			System.out.println("Server Opened!");
			
			while(true){
				System.out.println("Waiting.....");
				client = server.accept();
				System.out.println("Client connected! IP : " + client.getInetAddress());

				new Thread(new ServerThread(client)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
