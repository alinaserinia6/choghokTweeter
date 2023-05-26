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
			while (true) {
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
			user = (User) in.readObject();
			String username = user.getUsername();
			Server.users.put(username, user);
			while (true) {
				Tweet tweet;
				try {
					tweet = (Tweet) in.readObject();
				} catch (StreamCorruptedException e) {
					continue;
				}
				user.tweets.add(tweet);
			}
		} catch (IOException | ClassNotFoundException e) {
			Server.list.remove(out);
		}

	}
}