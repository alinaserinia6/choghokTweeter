package com.example.exm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
	static HashMap<String, ObjectOutputStream> onlineUser = new HashMap<String, ObjectOutputStream>();
	public static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();
	public static HashSet<String> keys = new HashSet<>();
	public static ArrayList<Tweet> tweets = new ArrayList<>();
	public static int TweetId;

	public static void main(String[] args) {
		// preprocess
		User user = new User("ali", "farahbaksh", "ali", "ali", LocalDateTime.now());
		User support = new User("support", "Choghok", "support", "1234", LocalDateTime.now());
		users.put("ali", user);
		users.put("support", support);
		user.following.put("support",new Following("support", LocalDateTime.MIN));
		Tweet t = new Tweet("only heydar is amir al momenin", "support");
		Comment c = new Comment("thank god", "ali");
//		t.comments.add();
		t.getLikes().add("amin");
		t.getRetweet().add("hey");
		t.setId(110);
		t.update(support.getAvatar(), support.getFirstName() + " " + support.getLastName());
		support.tweets.put(110, t);
		tweets.add(t);
//		Image image = new Image(Server.class.getResource("PchoghockIcon.png").toString());
//		user.setAvatar(image);

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

	private final int MAX_READ = 5;
	public Accept(Socket client) {
		this.client = client;
	}
	@Override
	public void run() {
		ObjectInputStream in;
		ObjectOutputStream out;
		try { // TODO JSON WEB TOKENS
			in = new ObjectInputStream(client.getInputStream());
			out = new ObjectOutputStream(client.getOutputStream());
			while (true) {
				try {
					Request o = (Request) in.readObject();
					RM method = o.getMethod();
					Instant start = Instant.now();
					System.out.println("\u001B[35m" + "\t".repeat(7) + "{" + method + "}\u001B[0m");
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
							Server.onlineUser.put(user.getUsername(), out);
						}
						case GET_USER -> {
							String username = (String) o.get1();
							user = Server.users.get(username);
							Server.onlineUser.put(username, out);
							out.writeObject(user);
						}
						case DISCOVER_USERS -> {
							LocalDateTime LAST_SEEN = (LocalDateTime) o.get1();
							ArrayList<User> userList = new ArrayList<>();
							LocalDateTime NEW_LAST = LAST_SEEN;
							for (User u : Server.users.values()) {
								if (u.getUsername().equals(user.getUsername()) || !LAST_SEEN.isBefore(u.getJoinDate())) continue;
								userList.add(u);
								if (NEW_LAST.isBefore(u.getJoinDate())) NEW_LAST = u.getJoinDate();
							}
							out.writeObject(NEW_LAST);
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
							Server.tweets.add(tw);
							user.tweets.put(Server.TweetId, tw);
							synchronized (this) {
								Server.TweetId++;
							}
						}
						case GET_TWEETS -> { // TODO sort by time
							int readed = 0;
							System.out.println("GET_TWEETS REQUEST FROM " + user.getUsername() + ":");
							for (Following i : user.following.values()) {
								String username = i.getUser();
								LocalDateTime ldt = i.getTime();
								if (!i.isFollowing()) continue;
								System.out.println(username + " " + ldt);
								User u = Server.users.get(username);
								for (Tweet j : u.tweets.values()) {
									System.out.print(j);
									if (j.getDt().isAfter(ldt)) {
										j.update(u.getAvatar(), u.getFirstName() + " " + u.getLastName());
										out.writeObject(j);
										readed++;
										System.out.println("  watch it");
									} else {
										System.out.println("  watched");
									}
									if (readed == MAX_READ) break;
								}
								if (readed == MAX_READ) break;
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
							synchronized (this) {
								String username = (String) o.get1();
								Integer id = (Integer) o.get2();
								System.out.print("#" + username + ":" + id + " * ");
								Tweet tweet = Server.users.get(username).tweets.get(id);
								if (tweet.getLikes().contains(user.getUsername())) {
									user.likes.remove(id);
									tweet.getLikes().remove(user.getUsername());
									System.out.println("dislike -> " + tweet.getLikes().size());
								} else {
									user.likes.add(id);
									tweet.getLikes().add(user.getUsername());
									System.out.println("like -> " + tweet.getLikes().size());
								}
								Server.users.get(username).tweets.put(id, tweet);
								ObjectOutputStream stream = Server.onlineUser.get(username);
								System.out.print("stream is ");
								if (stream != null) {
									System.out.println("not null");
									stream.writeObject(new Request(RM.LIKE_TWEET, user, tweet));
								} else {
									System.out.println("null");
								}
							}
						}
						case DIRECT_MASSAGE -> {
							User user = (User) o.get1();
							Massage massage = (Massage) o.get2();
							ObjectOutputStream stream = Server.onlineUser.get(user.getUsername());
							System.out.print("stream is ");
							if (stream != null) {
								System.out.println("not null");
								stream.writeObject(new Request(RM.DIRECT_MASSAGE, this.user, massage));
							} else {
								System.out.println("null");
							}
						}
						case GET_MASSAGES -> {
							out.writeObject(new Massage());
						}
						case UPDATE_TWEET -> {
							String username = (String) o.get1();
							int id = (int) o.get2();
							Tweet tweet = Server.users.get(username).tweets.get(id);
							for (String i : tweet.getLikes()) System.out.println(i);
							Request r = new Request(RM.UPDATE_TWEET, tweet);
							r.setA(tweet.getLikes());
							out.writeUnshared(r); // TODO THANK GOD! :))))))
							out.flush();
							out.reset();
						}
						case END_PROCESS -> {
							throw new InterruptedException();
						}
					}
					Instant end = Instant.now();
					Duration timeElapsed = Duration.between(start, end);
					System.out.println("\u001B[32m" + "\t".repeat(7) + "{" + timeElapsed + "}\u001B[0m");
				} catch (ClassNotFoundException e) {
					System.err.println("cant cast read from user to String!");
				} catch (EOFException e) {
					System.err.println("file is empty");
				}
				System.out.print("+");
				sleep(150);
			}
		} catch (IOException | InterruptedException e) {
			try {
				Server.onlineUser.remove(user.getUsername());
			} catch(NullPointerException ignore){}
//			e.printStackTrace(System.out);
		}

	}
}