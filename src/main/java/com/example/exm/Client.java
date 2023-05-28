package com.example.exm;

import javafx.application.Platform;

import java.io.*;
import java.util.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Client {

	public static User user = new User();
	public static ObjectOutputStream out;

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner q = new Scanner(System.in);
		HelloApplication app = new HelloApplication();
		Platform.startup(app);
		sleep(3000);
		try (Socket client = new Socket("localhost", 5757)) {
			System.out.println(client.getInetAddress());
			listen lis = new listen(client);
			lis.start();
			out = new ObjectOutputStream(client.getOutputStream());
			while (!client.isClosed()) {
				sleep(10000);
			}
		} catch (IOException ex) {
            System.err.println("i caught an exception in socket client");
		}
	}

}

class listen extends Thread {
	private Socket socket;

	public listen(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			while (true) {
                System.out.println(Client.user);
//				if (Thread.currentThread().isInterrupted()) {
//					return;
//				}
//				Object o;
//				try {
//					o = in.readObject();
//				} catch (EOFException e) {
//					continue;
//				}
//				if (o instanceof Tweet tweet) {
//					System.out.print(tweet);
//				} else {
//					System.out.println((String) o);
//				}
				sleep(10000);
			}
		} catch (IOException | InterruptedException e) {
            System.err.println("exception error in run listening");
            e.printStackTrace(System.out);
        }
	}

}