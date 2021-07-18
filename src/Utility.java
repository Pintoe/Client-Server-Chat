import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;

enum ThreadOperations {
	PRINT_MESSAGE, SEND_MESSAGE
}

enum ObjectTypes {
	SERVER, CLIENT
}

public class Utility implements Runnable {
	private int numberOfThreads = 0;
	private Server serverObject;
	private Client clientObject;
	private ObjectTypes objectType;
	private ThreadOperations currentOperation;
	private boolean inProcess = false;
	
	Utility(Server serverObject) {
		this.serverObject = serverObject;
		this.objectType = ObjectTypes.SERVER;
	}
	
	Utility(Client clientObject) {
		this.clientObject = clientObject;
		this.objectType = ObjectTypes.CLIENT;
	}
	/*
	private void cleanup() {
		switch(this.objectType) {
			case SERVER:
				this.serverObject.userInput.close();
				this.serverObject.input.close();
				this.serverObject.output.close();
				this.serverObject.codeSocket.close();
				this.serverObject.clientSocket.close();
			case CLIENT:
				
		}
	}
	*/
	private void printMessage() {
		BufferedReader currentScanner;
		
		if (this.objectType == ObjectTypes.SERVER) {
			currentScanner = this.serverObject.input;
		} else {
			currentScanner = this.clientObject.input;
		}
		
		String line = "";
		
		while (line != null) {
			try {line = currentScanner.readLine(); } catch (IOException e) {}
			System.out.println(line);
		}
		
	}
	
	private void sendMessage(ObjectTypes currentObjectType) {
		switch(currentObjectType) {
			case SERVER:
				while (true) {
					this.serverObject.output.println("Server: " + this.serverObject.userInput.nextLine());
				}
			case CLIENT:
				while (true) {
					try {this.clientObject.output.writeBytes("Client: " + this.clientObject.userInput.nextLine() + "\n");} catch (IOException e) {}
				}
		}
	}
	
	public void run() {
		ThreadOperations tOperation = this.currentOperation;
		this.inProcess = false;

		switch(tOperation) {
			case PRINT_MESSAGE:
				this.printMessage();
			case SEND_MESSAGE:
				this.sendMessage(this.objectType);
		}
	}
	
	public void main() {
		Thread t1 = new Thread(this, "Thread" + ++numberOfThreads);
		this.currentOperation = ThreadOperations.PRINT_MESSAGE;
		t1.start();
		
		Thread t2 = new Thread(this, "Thread" + ++numberOfThreads);
		while (inProcess) { try {Thread.sleep(1);} catch (InterruptedException e) { } }
		this.currentOperation = ThreadOperations.SEND_MESSAGE;
		t2.start();
	}
	
}
