package com.example.exm;

import javafx.application.Platform;

import java.io.*;
import java.util.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class Client {

	public static User user = new User();
	public static ObjectOutputStream out;
	public static ObjectInputStream in;

	public static void main(String[] args) throws IOException, InterruptedException {
		Scanner q = new Scanner(System.in);
		HelloApplication app = new HelloApplication();
		Platform.startup(app);
		sleep(3000);
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
					sleep(100);
				} catch (InterruptedException ex) {
					System.err.println("sleep exception");
				}
			}
		}
	}

}

class listen extends Thread {
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;

	public listen(Socket socket, ObjectOutputStream out, ObjectInputStream in) {
		this.socket = socket;
		this.out = out;
		this.in = in;
	}

	@Override
	public void run() {
		try {
			in = new ObjectInputStream(socket.getInputStream());
			while (true) {
                System.out.println(Client.user);
//				if (Thread.currentThread().isInterrupted()) {
//					return;
//				}
				Object o;
				try {
					o = in.readObject();
				} catch (EOFException | ClassNotFoundException e) {
					sleep(3000);
				}
			}
		} catch (IOException | InterruptedException e) {
            System.err.println("exception error in run listening");
            e.printStackTrace(System.out);
        }
	}

}