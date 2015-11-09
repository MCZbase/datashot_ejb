package edu.harvard.mcz.imagecapture.ejb;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.ejb.Singleton;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author mole
 */
@Singleton
public class MessageBean  {
    
    private List<String> messages = Collections.synchronizedList(new LinkedList<String>());

	private boolean latestFromServer = false;
	private int userCount = 0;

	public String getMessages() {
		StringBuilder result = new StringBuilder();
		ListIterator<String> i = messages.listIterator(messages.size());
		while (i.hasPrevious()) {
			result.append(i.previous());
		}
		return result.toString();
	}

    public boolean isLatestFromServer() {
		return latestFromServer;
	}

    public static final String SERVER_MESSAGE_SOURCE = "Server";

	public void addMessage(String user, String message) {
		if (messages.size()>100) {
			messages.remove(0);
		}
		message.replaceAll("<", "&lt;");
		message.replaceAll(">", "&gt;");
		if (user.equals(SERVER_MESSAGE_SOURCE)) {
			latestFromServer = true;
		} else {
			latestFromServer = false;
		}
		StringBuilder msg = new StringBuilder();
	    Date now = new Date();
		DateFormat dateFormat = new SimpleDateFormat("EEEE HH:mm z");
		msg.append("<p>");
        msg.append(dateFormat.format(now));
		msg.append(" ");
        msg.append(user);
		msg.append(": ");
		msg.append(message);
		msg.append("</p>\n");
		messages.add(msg.toString());
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public void incrementUserCount() {
		userCount ++;
	}

	public void decrementUserCount() {
		userCount --;
		if (userCount<0) { userCount = 0; }
	}

	public int getMessageCount() {
		return messages.size();
	}

}
