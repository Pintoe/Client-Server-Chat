import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;

public class Server {
	public ServerSocket codeSocket;
	public Socket clientSocket;
	public PrintWriter output;
	public BufferedReader input;
	public Scanner userInput;
	
	private void mainMethod(String[] args) {
		try {
			this.codeSocket = new ServerSocket(Integer.parseInt(args[0]));
			this.clientSocket = this.codeSocket.accept();
			
			System.out.println("Connection successful");
			
			this.output = new PrintWriter(this.clientSocket.getOutputStream(), true);
			this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			this.userInput = new Scanner(System.in);
			
			/*
			String line = "";
			
			while (true) {
				System.out.println(this.input.readLine());				
				this.output.println(this.userInput.nextLine());
			}
			*/
			
			Utility serverOperations = new Utility(this);
			serverOperations.main(); 
			
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Connection failed");
		}
	}
	
	public static void main(String[] args) {
		Server newServer = new Server();
		newServer.mainMethod(args);
	}
}
