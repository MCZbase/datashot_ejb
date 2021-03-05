package edu.harvard.mcz.imagecapture.ejb;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.ejb.Singleton;

/**
 * Maintains a list of up to 100 most recent messages, count of user logins/logouts, and a list of 
 * active usernames, maintains list while application is running, but doesn't provide persistence between
 * web application or container or server restarts. 
 *
 * @author mole
 */
@Singleton(name="messageBean")
public class MessageBean  {
	
	private final static Logger logger = Logger.getLogger(MessageBean.class.getName());	
    
    private List<String> messages = Collections.synchronizedList(new LinkedList<String>());

    private List<String> users = Collections.synchronizedList(new LinkedList<String>());
    
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
    	if (message==null || message.trim().length()==0) {  
    		// Empty messages are used on the web side to trigger websocket
    		// messages to trigger ajax updates to pages, but don't add
    		// empty messages to the list of messages.
    		logger.log(Level.INFO, "empty message, not added to list");
    	} else { 

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
    }

	public int getUserCount() {
		return userCount;
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
	
	/**
	 * Update the list of current logged in usernames.
	 * 
	 * @param currentuserset set of strings of current usernames.  
	 */
	public void updateCurrentUserList(Set<String> currentuserset) {
		
		logger.log(Level.INFO, "currentuserset.size() = " + Integer.toString(currentuserset.size()));
		
		if (currentuserset!=null && !currentuserset.isEmpty()) {
			Set<String> currentusers = new HashSet<String>(); 
			Iterator <String> is = currentuserset.iterator();
			while (is.hasNext()) { 
			    String username = is.next();
			    currentusers.add(username);
			    logger.log(Level.INFO, "Added user: " + username);
			}
		    
			// check that each user in the users list is also a current user, if not, remove.
			Iterator<String> i = users.iterator();
		    while (i.hasNext()) { 
		    	String believedusername = i.next();
		    	if (!currentusers.contains(believedusername)) { 
		    	   users.remove(believedusername);	
		        }
		    }
		    // check to see if current users has entries not in the users list, if so, add.
		    Iterator<String> ic = currentusers.iterator();
		    while (ic.hasNext()) { 
		    	String currentuser = ic.next();
		    	if (!users.contains(currentuser)) { 
		    		users.add(currentuser);
		    	}
		    }
		    // reset the user count to the number of entries in the users list.
		    userCount = users.size();
		} else { 
			// no current users, expected initial condition.
			logger.log(Level.INFO, "updateCurrentUserList called with empty list");
		}
	}

	/**
	 * Obtain the list of usernames of current active users.
	 * 
	 * @return list of strings of distinct usernames.
	 */
	public List<String> getUserList() { 
		return users;
	}
}
