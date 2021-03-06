import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		
		Socket socket;
		Runnable envoie;
		Runnable recois;
		final BufferedReader in;
		final PrintWriter out;
		final Scanner sc = new Scanner(System.in); // Lis � partir du clavier
		
		try {
			socket = new Socket("127.0.0.1", 5000);
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
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
						System.out.println("Serveur : "+message);
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

