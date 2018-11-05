package edu.harvard.mcz.imagecapture.ejb;


import java.security.Principal;
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

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author mole
 */
@Singleton(name="messageBean")
public class MessageBean  {
	
	private final static Logger logger = Logger.getLogger(MessageBean.class.getName());	
    
    private List<String> messages = Collections.synchronizedList(new LinkedList<String>());

    private List<String> users = Collections.synchronizedList(new LinkedList<String>());
    
//	@EJB(beanName="usersFacade")
//	private UsersFacadeLocal usersFacade;
    
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

//	public void setUserCount(int userCount) {
//		this.userCount = userCount;
//	}

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
	
	public void updateCurrentUserList(Set<String> currentuserset) {
		
		logger.log(Level.INFO, "currentuserset.size() = " + Integer.toString(currentuserset.size()));
		
		if (currentuserset!=null && !currentuserset.isEmpty()) {
			Set<String> currentusers = new HashSet<String>(); 
			// look up fullnames for currentusers usernames
			Iterator <String> is = currentuserset.iterator();
			while (is.hasNext()) { 
			    String pname = is.next();
			    String username = pname;
			    // TODO: Update current users list from open websockets users.
//			    logger.log(Level.INFO, "current username: " +  pname);
//		        if (usersFacade.findByName(pname) != null) {
//			       username = usersFacade.findByName(pname).getFullname();
//		        }			
			    currentusers.add(username);
			    logger.log(Level.INFO, "looked up as: " + username);
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
			// no current users, clear the list.
			users.clear();
			userCount = 0;
		}
	}

//	public void setUserList(List<String> userList) {
//		if (userList!=null) { 
//			users.clear();
//			Iterator<String> i = userList.iterator();
//			while (i.hasNext()) { 
//				users.add(i.next());
//			}
//		}
//	}
//	
	public List<String> getUserList() { 
		return users;
	}
}
