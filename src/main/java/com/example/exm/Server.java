package com.example.exm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class Server {
	static HashMap<String, ObjectOutputStream> list = new HashMap<String, ObjectOutputStream>();
	public static HashMap<String, User> users = new HashMap<>();
	public static HashSet<String> keys = new HashSet<>();
	public static ArrayList<Tweet> tweets = new ArrayList<>();
	public static HashMap<String, LocalDateTime> followingTime;

	public static void main(String[] args) {
		User user = new User();
		user.setUsername("ali");
		user.setFirstName("ali");
		user.setLastName("farahbaksh");
		user.setPassword("ali");
		users.put("ali", user);
		User support = new User();
		support.setUsername("@support");
		support.setFirstName("support");
		support.setLastName("Choghok");
		users.put("@support", support);
		user.following.put("@support", LocalDateTime.MIN);
//		followingTime.put("")
		Tweet t = new Tweet("only heydar is amir al momenin", user);
		tweets.add(t);


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
	private User user;
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
							this.user = user;
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
						case ADD_TWEET -> {
							Server.tweets.add((Tweet) o.get1());
							System.out.println(Server.tweets.size());
						}
						case GET_TWEETS -> {
							for (Map.Entry<String, LocalDateTime> i : Server.followingTime.entrySet()) {
								String username = i.getKey();
								LocalDateTime ldt = i.getValue();
								for (Tweet j : Server.users.get(username).tweets) {
									if (j.getDt().isAfter(ldt)) {
										out.writeObject(j);
									}
								}
							}
						}
						case LAST_SEEN_TIME -> {
							Server.followingTime.put((String) o.get1(), (LocalDateTime) o.get2());
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