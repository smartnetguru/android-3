package com.irccloud.android;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class UsersDataSource {
	public class User {
		int cid;
		String channel;
		String nick;
		String hostmask;
		String mode;
		int away;
	}

	public class comparator implements Comparator<User> {
		public int compare(User u1, User u2) {
			return u1.nick.compareToIgnoreCase(u2.nick);
		}
	}
	
	private ArrayList<User> users;
	
	private static UsersDataSource instance = null;
	
	public static UsersDataSource getInstance() {
		if(instance == null)
			instance = new UsersDataSource();
		return instance;
	}

	public UsersDataSource() {
		users = new ArrayList<User>();
	}

	public void clear() {
		users.clear();
	}
	
	public User createUser(int cid, String channel, String nick, String hostmask, String mode, int away) {
		User u = new User();
		u.cid = cid;
		u.channel = channel;
		u.nick = nick;
		u.hostmask = hostmask;
		u.mode = mode;
		u.away = away;
		users.add(u);
		return u;
	}

	public void deleteUser(int cid, String channel, String nick) {
		User u = getUser(cid,channel,nick);
		if(u != null)
			users.remove(u);
	}

	public void deleteUsersForChannel(int cid, String channel) {
		ArrayList<User> usersToRemove = new ArrayList<User>();
		
		Iterator<User> i = users.iterator();
		while(i.hasNext()) {
			User u = i.next();
			if(u.cid == cid && u.channel.equals(channel))
				usersToRemove.add(u);
		}
		
		i=usersToRemove.iterator();
		while(i.hasNext()) {
			User u = i.next();
			users.remove(u);
		}
	}

	public void updateNick(int cid, String channel, String old_nick, String new_nick) {
		User u = getUser(cid,channel,old_nick);
		if(u != null)
			u.nick = new_nick;
	}
	
	public void updateAway(int cid, String channel, String nick, int away) {
		User u = getUser(cid,channel,nick);
		if(u != null)
			u.away = away;
	}
	
	public void updateHostmask(int cid, String channel, String nick, String hostmask) {
		User u = getUser(cid,channel,nick);
		if(u != null)
			u.hostmask = hostmask;
	}
	
	public void updateMode(int cid, String channel, String nick, String mode) {
		User u = getUser(cid,channel,nick);
		if(u != null)
			u.mode = mode;
	}
	
	public ArrayList<User> getUsersForChannel(int cid, String channel) {
		ArrayList<User> list = new ArrayList<User>();
		Iterator<User> i = users.iterator();
		while(i.hasNext()) {
			User u = i.next();
			if(u.cid == cid && u.channel.equals(channel))
				list.add(u);
		}
		Collections.sort(list, new comparator());
		return list;
	}

	public User getUser(int cid, String channel, String nick) {
		Iterator<User> i = users.iterator();
		while(i.hasNext()) {
			User u = i.next();
			if(u.cid == cid && u.channel.equals(channel) && u.nick.equals(nick))
				return u;
		}
		return null;
	}
}
