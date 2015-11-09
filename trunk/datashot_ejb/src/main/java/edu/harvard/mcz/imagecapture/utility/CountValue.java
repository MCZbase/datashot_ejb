/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.utility;

import edu.harvard.mcz.imagecapture.interfaces.CountValueChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Holds a string value and an integer count of number of instances of that value.
 * Use as a List<CountValue> to transport results from queries in the form
 * select count(*), field from table group by field.
 *
 * @author mole
 */
public class CountValue {

   private final static Logger logger = Logger.getLogger(CountValue.class.getName());

   private int count;
   private String value;
   private String newValue;

   private List<CountValueChangeListener> listeners;

	public CountValue() {
		count = 0;
	    value = "";
		newValue = "";
		listeners = new ArrayList<CountValueChangeListener>();
	}

	/**
	 * Construct an instance of a count, value pair.
	 *
	 * @param count
	 * @param value
	 */
	public CountValue(int count, String value) {
		this.count = count;
		setValue(value);
		setNewValue(value);
		listeners = new ArrayList<CountValueChangeListener>();
	}


	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getValue() {
		String result = value;
		if (result!=null) {
			result = result.replace("&", "&amp;");
		}
		return result;
	}

	public void setValue(String value) {
		if (value!=null && value.contains("&amp;")) {
			value = value.replace("&amp;", "&");
		}
		this.value = value;
	}

	public String getNewValue() {
		String result = newValue;
		if (result!=null) {
			result = result.replace("&", "&amp;");
		}
		return result;
	}

	public void setNewValue(String newValue) {
		// We'll end up with an XML Parsing Error: not well-formed
		// if & (e.g. Smith & Jones) ends up in the text sent to
		// and from the p:inplaceEditor, so we'll need to escape
		// at least the &.  Method here is to have get/set newValue
		// to retain the actual & in the stored value, but to
		// return the html entity &amp; to outside observers.
		// the notify listeners are notified of the actual &, not
		// the escaped value.

		// TODO: This works for display, but not for saving strings with & in them.

		if (newValue!=null && newValue.contains("&amp;")) {
			newValue = newValue.replace("&amp;", "&");
		}
		logger.log(Level.INFO,"NewValue:" + newValue);
		this.newValue = newValue;
		if (newValue!=null && this.newValue!=null && !newValue.equals(this.value)) {
			// notify only on change
		    notifyListeners();
			this.value = this.newValue;
		}
	}

	public void registerListener(CountValueChangeListener listener) {
		logger.log(Level.INFO, "Registering listener " + listener);
		listeners.add(listener);
	}

	public void notifyListeners() {
		logger.log(Level.INFO,"Notifying Listeners:" + listeners.size());
		Iterator<CountValueChangeListener> i = listeners.iterator();
		while (i.hasNext()) {
			i.next().notify(this.value, this.newValue);
		}
	}

	public void unregisterListener(CountValueChangeListener listener) {
		listeners.remove(listener);
	}


}
