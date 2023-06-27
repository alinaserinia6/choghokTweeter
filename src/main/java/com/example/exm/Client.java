package com.example.exm;

import javafx.application.Platform;
import javafx.scene.layout.VBox;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.net.Socket;
import java.util.LinkedHashMap;

import static java.lang.Thread.sleep;

public class Client {

	public static User user;
	public static String key;
	public static ObjectOutputStream out;
	public static ObjectInputStream in;
	public static VBox timeline = new VBox();
	public static VBox contacts = new VBox();
	public static VBox notification = new VBox();
	public static LinkedHashMap<String, Direct> directs = new LinkedHashMap<>();
	public static LocalDateTime LAST_USER_SEEN = LocalDateTime.MIN;
	public static void main(String[] args) throws InterruptedException {
		Instant start = Instant.now();
		HelloApplication app = new HelloApplication();
		Platform.startup(app);
		Instant end = Instant.now();
		System.out.println("\u001B[32m" + "\t".repeat(7) + "{" + Duration.between(start, end) + "}\u001B[0m");
		sleep(300);
		try (Socket client = new Socket("localhost", 5757)) {
			System.out.println(client.getInetAddress());
			out = new ObjectOutputStream(client.getOutputStream());
			in = new ObjectInputStream(client.getInputStream());
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
				Object x = in.readObject();
				if (x instanceof Request) {
					Request r = (Request) x;
					RM method = r.getMethod();
					System.out.println("\u001B[35m" + "\t".repeat(7) + "{" + method + "}\u001B[0m");
					switch(method) {
						case LIKE_TWEET -> {
							User showUser = (User) r.get1();
							Tweet t = (Tweet) r.get2();
							System.out.println(showUser.getUsername() + " like tweet: " + t.getText());
							LikeNotification likeNotification = new LikeNotification(showUser, t);
							Client.notification.getChildren().add(0, likeNotification.toPane());
						}
						case DIRECT_MASSAGE -> {
							User user = (User) r.get1();
							Massage massage = (Massage) r.get2();
							String username = user.getUsername();
							Direct direct = Client.directs.get(username);
							if (direct == null) direct = new Direct(user);
							direct.getMassages().add(massage);
							Client.directs.put(username, direct);
							direct.getController().addMassage(massage);
							// TODO notification icon use better
						}
					}
					System.out.println("\u001B[35m" + "\t".repeat(7) + "{END" + "}\u001B[0m");
				} else {
					return x;
				}
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

}