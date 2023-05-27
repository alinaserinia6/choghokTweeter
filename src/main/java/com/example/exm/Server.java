package com.example.exm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
	static ArrayList<ObjectOutputStream> list = new ArrayList<ObjectOutputStream>();
	public static HashMap<String, User> users = new HashMap<>();

	public static void main(String[] args) {
		System.out.println("\t".repeat(7) + "{SERVER}\n");
		try (ServerSocket serverSocket = new ServerSocket(5757)){
			System.out.println("server socket is created");
			int i = 0;
			while (true) {
				System.out.println("i am in " + (++i) + "-th loop");
				Socket client = serverSocket.accept();
				Accept ac = new Accept(client);
				ac.start();
			}
		} catch (IOException exception) {
			exception.printStackTrace();
		}
	}
}


class Accept extends Thread {
	private Socket client;
	public Accept(Socket client) {
		this.client = client;
	}
	@Override
	public void run() {
		User user;
		ObjectInputStream in;
		ObjectOutputStream out = null;
		try {
			in = new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
			Server.list.add(out);
			while (true) {
				System.out.println("i am listening!");
				try {
					String o = (String) in.readObject();
					System.out.println(o);
				} catch (ClassNotFoundException e) {
					System.err.println("cant cast read from user to String!");
				}
				sleep(5000);
			}
		} catch (IOException | InterruptedException e) {
			Server.list.remove(out);
			e.printStackTrace(System.out);
		}

	}
}