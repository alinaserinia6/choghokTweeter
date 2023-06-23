package com.example.exm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;

public class Server {
	static HashMap<String, ObjectOutputStream> list = new HashMap<String, ObjectOutputStream>();
	public static HashMap<String, User> users = new HashMap<>();
	public static HashSet<String> keys = new HashSet<>();
	public static ArrayList<Tweet> tweets = new ArrayList<>();
	public static int TweetId;

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
		user.following.put("@support",new Following("@support", LocalDateTime.MIN));
		Tweet t = new Tweet("only heydar is amir al momenin", "@support");
		support.tweets.add(t);
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
		ObjectOutputStream out;
		try {
			in = new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
			while (true) {
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
							Server.list.put(user.getUsername(), out);
							ArrayList<ShowUser> userList = new ArrayList<>(); // TODO merge this and GET_USER
							for (User u : Server.users.values()) {
								if (u.getUsername().equals(user.getUsername())) continue;
								ShowUser showUser = new ShowUser(u.getFirstName() + " " + u.getLastName(), u.getUsername(), u.getBio());
								userList.add(showUser);
							}
							out.writeObject(userList);
						}
						case GET_USER -> {
							String username = (String) o.get1();
							user = Server.users.get(username);
							Server.list.put(username, out);
							ArrayList<ShowUser> userList = new ArrayList<>();
							for (User u : Server.users.values()) {
								if (u.getUsername().equals(user.getUsername())) continue;
								ShowUser showUser = new ShowUser(u.getFirstName() + " " + u.getLastName(), u.getUsername(), u.getBio());
								userList.add(showUser);
							}
							out.writeObject(userList);
						}
						case CHECK_PASS -> {
							String username = (String) o.get1();
							String pass = (String) o.get2();
							boolean res = Server.users.containsKey(username) && Server.users.get(username).getPassword().equals(pass);
							out.writeObject(res);
						}
						case ADD_TWEET -> {
							Tweet tw = (Tweet) o.get1();
							tw.setId(Server.TweetId);
							out.writeObject(Server.TweetId);
							System.out.println(Server.TweetId);
							Server.TweetId++;
							System.out.println(Server.TweetId);
							Server.tweets.add(tw);
						}
						case GET_TWEETS -> {
							System.out.println("GET_TWEETS REQUEST FROM " + user.getUsername() + ":");
							for (Following i : user.following.values()) {
								String username = i.getUser();
								LocalDateTime ldt = i.getTime();
								if (!i.isFollowing()) continue;
								System.out.println(username + " " + ldt);
								User u = Server.users.get(username);
								for (Tweet j : u.tweets) {
									System.out.print(j);
									if (j.getDt().isAfter(ldt)) {
										j.setName(u.getFirstName() + " " + u.getLastName() + " " + username);
										j.setAvatar(u.getAvatar());
										out.writeObject(j);
										System.out.println("  watch it");
									} else {
										System.out.println("  watched");
									}
								}
							}
							Tweet finish = new Tweet();
							out.writeObject(finish);
						}
						case LAST_SEEN_TIME -> {
							String username = (String) o.get1();
							LocalDateTime time = (LocalDateTime) o.get2();
							Following f = user.following.get(username);
							f.setTime(time);
						}
						case FOLLOW_REQUEST -> {
							String username = (String) o.get1();
							Following f = user.following.get(username);
							if (f == null) {
								f = new Following(username, LocalDateTime.MIN);
								user.following.put(username, f);
							} else {
								f.follow();
							}
						}
						case LIKE_TWEET -> {
							String username = (String) o.get1();
							int id = (int) o.get2();
							Tweet tweet = Server.users.get(username).tweets.get(id);
							if (tweet.likes.contains(user.getUsername())) {
								tweet.likes.remove(user.getUsername());
								user.likes.remove(id);
							} else {
								tweet.likes.add(user.getUsername());
								user.likes.put(id, tweet);
							}
						}
					}
				} catch (ClassNotFoundException e) {
					System.err.println("cant cast read from user to String!");
				} catch (EOFException e) {
					System.err.println("file is empty");
				}
				System.out.print("+");
				sleep(3000);
			}
		} catch (IOException | InterruptedException e) {
			Server.list.remove(user.getUsername());
			e.printStackTrace(System.out);
		}

	}
}