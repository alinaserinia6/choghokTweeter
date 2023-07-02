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
	public static HashMap<String, Integer> hashtag = new HashMap<>();
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
		c.setId(66);
		t.comments.add(c);
		user.tweets.put(66, c);
		t.getLikes().add("amin");
		t.setId(110);
		t.updateUser(support.getAvatarAsString(), support.getName());
		support.tweets.put(110, t);
		tweets.add(t);
		try {
			FileInputStream fos = new FileInputStream("/home/ali/B/exm/src/main/resources/com/example/exm/choghok.bin");
			ObjectInputStream oos = new ObjectInputStream(fos);
			users = (ConcurrentHashMap<String, User>) oos.readObject();
			keys = (HashSet<String>) oos.readObject();
			tweets = (ArrayList<Tweet>) oos.readObject();
			hashtag = (HashMap<String, Integer>) oos.readObject();
			oos.close();
		} catch(EOFException ignore){
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

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
		} finally {
			try {
				FileOutputStream fos = new FileOutputStream("/home/ali/B/exm/src/main/resources/com/example/exm/choghok.bin");
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				oos.writeObject(users);
				oos.writeObject(keys);
				oos.writeObject(tweets);
				oos.writeObject(hashtag);
				oos.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
	}
}
class Accept extends Thread {
	private final Socket client;
	private User user;

	private final int MAX_READ = 5;
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
							for (ObjectOutputStream i : Server.onlineUser.values()) {
								i.writeUnshared(new Request(RM.NEW_USER, user));
								i.flush();
								i.reset();
							}
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
						case GET_HASHTAG -> {
							out.writeObject(Server.hashtag);
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
							for (String i : tw.getText().split("( )|(\n)")) {
								if (i.startsWith("#")) {
									if (Server.hashtag.containsKey(i)) {
										Server.hashtag.put(i, Server.hashtag.get(i) + 1);
									} else {
										Server.hashtag.put(i, 1);
									}
								}
							}
						}
						case GET_TWEETS -> { // TODO sort by time
							int readed = 0;
							System.out.println("GET_TWEETS REQUEST FROM " + user.getUsername() + ":");
							for (Following i : user.following.values()) {
								String username = i.getUsername();
								LocalDateTime ldt = i.getTime();
								if (!i.isFollowing()) continue;
								System.out.println(username + " " + ldt);
								User u = Server.users.get(username);
								for (Tweet j : u.tweets.values()) {
									System.out.print(j);
									if (j.getDt().isAfter(ldt)) {
										j.updateUser(u.getAvatarAsString(), u.getName());
										if (!j.getUsername().equals(username)) j.setRetweetByUser(username);
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
							User thatUser = Server.users.get(username);
							Following f = user.following.get(username);
							if (f == null) {
								f = new Following(username, LocalDateTime.MIN);
								f.setUser(thatUser);
								user.following.put(username, f);
								thatUser.followers.add(user.getUsername());
							} else if (!f.isFollowing()) {
								f.follow();
								user.following.put(username, f);
								thatUser.followers.add(user.getUsername());
							} else {
								thatUser.followers.remove(user.getUsername());
								f.follow();
							}
						}
						case LIKE_TWEET -> {
							String username = (String) o.get1();
							Integer id = (Integer) o.get2();
							Tweet tweet = Server.users.get(username).tweets.get(id);
							synchronized (tweet) {
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
							}
							ObjectOutputStream stream = Server.onlineUser.get(username);
							System.out.print("stream is ");
							if (stream != null) {
								System.out.println("not null");
								stream.writeObject(new Request(RM.LIKE_TWEET, user, tweet));
							} else {
								System.out.println("null");
							}
						}
						case RETWEET -> {
							String username = (String) o.get1();
							Integer id = (Integer) o.get2();
							Tweet tweet = Server.users.get(username).tweets.get(id);
							synchronized (tweet) {
								if (tweet.retweet.contains(user.getUsername())) {
									user.tweets.remove(id);
									tweet.retweet.remove(user.getUsername());
								} else {
									user.tweets.put(id, tweet);
									tweet.retweet.add(user.getUsername());
								}
								Server.users.get(username).tweets.put(id, tweet);
							}
						}
						case QUOTE -> {
							synchronized (this) {
								String username = (String) o.get1();
								Integer id = (Integer) o.get2();
								Quote q = (Quote) o.get3();
								q.setId(Server.TweetId);
								System.out.println(username + " " + id + " quote");
								out.writeObject(Server.TweetId);
								Tweet tweet = Server.users.get(username).tweets.get(id);
								user.tweets.put(Server.TweetId, q);
								tweet.quotes.add(q);
								Server.tweets.add(q);
								Server.TweetId++;
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
							System.out.println("\u001B[34m" + "\t".repeat(7) + "{" + id + "}\u001B[0m");
							User u = Server.users.get(username);
							Tweet tweet = u.tweets.get(id);
							tweet.setName(u.getName());
							tweet.setAvatar(u.getAvatarAsString());
							Request r = new Request(RM.UPDATE_TWEET, tweet);
							out.writeUnshared(r); // TODO THANK GOD! :))))))
							out.flush();
							out.reset();
						}
						case UPDATE_USER -> {
							this.user = (User) o.get1();
						}
						case SEE_USER -> {
							String username = (String) o.get1();
							out.writeObject(Server.users.get(username));
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
				sleep(1500);
			}
		} catch (IOException | InterruptedException e) {
			try {
				Server.onlineUser.remove(user.getUsername());
			} catch(NullPointerException ignore){}
//			e.printStackTrace(System.out);
		}

	}
}