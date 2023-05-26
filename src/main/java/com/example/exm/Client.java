package com.example.exm;

import javafx.application.Platform;
import java.io.*;
import java.util.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Client {

	public User user = new User();

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner q = new Scanner(System.in);
		HelloApplication app = new HelloApplication();
		Platform.startup(app);
		sleep(3000);
		HelloController hi = app.getHi();
		System.out.println(hi == null);
		System.out.println("hey");
		try (Socket client = new Socket("localhost", 5757)) {
			listen lis = new listen(client);
			lis.start();
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());

		} catch (IOException ex) {

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
				if (Thread.currentThread().isInterrupted()) {
					return;
				}
				Object o = in.readObject();
				if (o instanceof Tweet tweet) {
					System.out.print(tweet);
				} else {
					System.out.println((String) o);
				}
				sleep(5000);
			}
		} catch (IOException | ClassNotFoundException | InterruptedException ignored) {}
	}

}