import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;

public class Client {
	
	Socket clientSocket;
	DataOutputStream output;
	BufferedReader input;
	Scanner userInput;
	
	private void mainMethod(String[] args) {
		
		try {
			this.clientSocket = new Socket(args[0], Integer.parseInt(args[1]));			
			System.out.println("Connection successful");
			
			this.output = new DataOutputStream(this.clientSocket.getOutputStream());
			this.input = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
			this.userInput = new Scanner(System.in);
			
			/*
			while (true) {
				this.output.writeBytes(this.userInput.nextLine() + "\n");
				System.out.println(this.input.readLine());
			}
			*/
			
			Utility clientOperations = new Utility(this);
			clientOperations.main();
			
		} catch (IOException e) {
			System.out.println(e);
			System.out.println("Connection failed");
		}
	}
	
	public static void main(String[] args) {
		Client newClient = new Client();
		newClient.mainMethod(args);
	}
}
