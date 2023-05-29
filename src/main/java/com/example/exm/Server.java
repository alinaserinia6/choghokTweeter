package com.example.exm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;

public class Server {
	static HashMap<String, ObjectOutputStream> list = new HashMap<String, ObjectOutputStream>();
	public static HashMap<String, User> users = new HashMap<>();
	public static HashSet<String> keys = new HashSet<>();

	public static void main(String[] args) {
		System.out.println("\t".repeat(7) + "{SERVER}\n");
		try (ServerSocket serverSocket = new ServerSocket(5757)){
			System.out.println("server socket is created");
			int i = 0;
			while (true) {
				System.out.println("i am in " + (++i) + "-th loop");
				Socket client = serverSocket.accept();
				System.out.println(client.getInetAddress());
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
		ObjectInputStream in;
		ObjectOutputStream out = null;
		try {
			in = new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
//			Server.list.add(out);
			while (true) {
				System.out.println("i am listening to " + Server.users.size());
				for (User i : Server.users.values()) {
					System.out.println(i);
				}
				try {
					Request o = (Request) in.readObject();
					RM method = o.getMethod();
					switch(method) {
						case DUPLICATE_KEY -> {
							String key = (String) o.get1();
							boolean res = Server.keys.contains(key);
							out.writeObject(res);
						}
						case DUPLICATE_ID -> {
							String username = (String) o.get1();
							boolean res = Server.users.containsKey(username);
							out.writeObject(res);
						}
						case ADD_USER -> {
							User user = (User) o.get1();
							String key = (String) o.get2();
							Server.keys.add(key);
							Server.users.put(user.getUsername(), user);
						}
						case CHECK_PASS -> {
							String username = (String) o.get1();
							String pass = (String) o.get2();
							boolean res = Server.users.containsKey(username) && Server.users.get(username).getPassword().equals(pass);
							out.writeObject(res);
						}
					}
				} catch (ClassNotFoundException e) {
					System.err.println("cant cast read from user to String!");
				} catch (EOFException e) {
					System.err.println("file is empty");
				}
				System.out.print("+");
				sleep(5000);
			}
		} catch (IOException | InterruptedException e) {
			Server.list.remove(out);
			e.printStackTrace(System.out);
		}

	}
}