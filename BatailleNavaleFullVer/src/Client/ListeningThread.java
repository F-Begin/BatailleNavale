package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ListeningThread extends Thread{
	BufferedReader in;
	Scanner scan;
	
	public ListeningThread(Socket s) throws IOException {
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		scan = new Scanner(in);
	}
	
	public void run(){
		while (true) {
			try {
			System.out.println(scan.nextLine());
			}catch(NoSuchElementException e) {}
		}
	}
}