import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Server {

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket socketserver;
		Socket socketduserveur;
		Runnable envoie;
		Runnable recois;
		final BufferedReader in;
		final PrintWriter out;
		final Scanner sc = new Scanner(System.in); // Lis à partir du clavier
		
		
		try {
			socketserver = new ServerSocket(5000);
			socketduserveur = socketserver.accept();
			
			out = new PrintWriter(socketduserveur.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socketduserveur.getInputStream()));
		

			envoie = new Runnable() {
				String message;
				
				@Override
				public void run() {			
					while(true) {
						message = sc.nextLine();
						out.println(message);
						out.flush();
					}
				}
			};
			
			Thread envoyer = new Thread(envoie);
			
			envoyer.start();
			
			recois = new Runnable() {
				String message;
				
				@Override
				public void run() {		
					while(true) {
						try {
							message = in.readLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println("Client : "+message);
					}
				}
			};
			
			Thread recevoir = new Thread(recois);
			
			recevoir.start();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
