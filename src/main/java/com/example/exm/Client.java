package com.example.exm;

import javafx.application.Platform;
import javafx.scene.layout.VBox;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Client {

	public static User user;
	public static String key;
	public static ObjectOutputStream out;
	public static ObjectInputStream in;
	public static VBox timeline = new VBox();
	public static VBox contacts = new VBox();
	public static LocalDateTime LAST_USER_SEEN = LocalDateTime.MIN;
	public static void main(String[] args) throws InterruptedException {
		Instant start = Instant.now();
		HelloApplication app = new HelloApplication();
		Platform.startup(app);
		Instant end = Instant.now();
		System.out.println("\u001B[32m" + "\t".repeat(7) + "{" + Duration.between(start, end) + "}\u001B[0m");
//		sleep(1000);
		try (Socket client = new Socket("localhost", 5757)) {
			System.out.println(client.getInetAddress());
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
//			listen lis = new listen(client, out, in);
//			lis.start();
			while (!client.isClosed()) {
				sleep(10000);
			}
		} catch (IOException ex) {
            System.err.println("i caught an exception in socket client");
		}
	}

	public static Object getObject() {
		while (true) {
			try {
				return in.readObject();
			} catch (ClassNotFoundException | IOException ignore) {
				try {
					System.out.print("+");
					sleep(100);
				} catch (InterruptedException ex) {
					System.err.println("sleep exception");
				}
			}
		}
	}
	public static Object getObject(String s) {
		while (true) {
			try {
				return in.readObject();
			} catch (ClassNotFoundException | IOException ignore) {
				try {
					System.out.print(s);
					sleep(100);
				} catch (InterruptedException ex) {
					System.err.println("sleep exception");
				}
			}
		}
	}

}