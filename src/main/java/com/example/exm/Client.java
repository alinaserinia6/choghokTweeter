package com.example.exm;

import javafx.application.Platform;

import java.io.*;
import java.util.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Client {

	public static User user = new User();

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner q = new Scanner(System.in);
		HelloApplication app = new HelloApplication();
		Platform.startup(app);
		sleep(3000);
		System.out.println("after 3 seconds sleep");
		try (Socket client = new Socket("localhost", 5757)) {
            System.out.println("i am in try socket");
			listen lis = new listen(client);
            System.out.println("lis in created");
			lis.start();
			ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
            System.out.println("i created out");
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
            System.out.println("i am in run of listening man!");
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("I created inputSteam man!");
			while (true) {
                System.out.println("i am wait for user:");
                System.out.println(Client.user);
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
		} catch (IOException | ClassNotFoundException | InterruptedException e) {
            System.err.println("exception error in run listening");
            e.printStackTrace(System.out);
        }
	}

}