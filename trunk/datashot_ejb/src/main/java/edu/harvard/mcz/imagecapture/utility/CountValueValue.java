/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.utility;

import edu.harvard.mcz.imagecapture.interfaces.CountValueValueChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Holds a string value a second value, and an integer count of number of
 * instances of those two distinct values.
 *
 * Use as a List<CountValueValue> to transport results from queries in the form
 * select count(*), field, field1 from table group by field, field1.
 *
 * Allows updates on the second value, field1.
 *
 * @author mole
 */
public class CountValueValue {

   private final static Logger logger = Logger.getLogger(CountValueValue.class.getName());

   private int count;
   private String value;
   private String newValue;
   private String value1;
   private String newValue1;

   private List<CountValueValueChangeListener> listeners;

	public CountValueValue() {
		count = 0;
	    value = "";
		newValue = "";
	    value1 = "";
		newValue1 = "";
		listeners = new ArrayList<CountValueValueChangeListener>();
	}

	/**
	 * Construct an instance of a count, value pair.
	 *
	 * @param count
	 * @param value
	 */
	public CountValueValue(int count, String value, String value1) {
		this.count = count;
		setValue(value);
		setNewValue(value);
		setValue1(value1);
		setNewValue1(value1);
		listeners = new ArrayList<CountValueValueChangeListener>();
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
		}
	}

	public String getValue1() {
		String result = value1;
		if (result!=null) {
			result = result.replace("&", "&amp;");
		}
		return result;
	}

	public void setValue1(String value1) {
		if (value1!=null && value1.contains("&amp;")) {
			value1 = value1.replace("&amp;", "&");
		}
		this.value1 = value1;
	}

	public String getNewValue1() {
		String result = newValue1;
		if (result!=null) {
			result = result.replace("&", "&amp;");
		}
		return result;
	}

	public void setNewValue1(String newValue1) {

		// TODO: This works for display, but not for saving strings with & in them.

		if (newValue1!=null && newValue1.contains("&amp;")) {
			newValue1 = newValue1.replace("&amp;", "&");
		}
		logger.log(Level.INFO,"NewValue:" + newValue1);
		this.newValue1 = newValue1;
		if (newValue1!=null && this.newValue1!=null && !newValue1.equals(this.value1)) {
			// notify only on change
		    notifyListeners();
		    this.value1 = this.newValue1;
		}
	}


	public void registerListener(CountValueValueChangeListener listener) {
		logger.log(Level.INFO, "Registering listener " + listener);
		listeners.add(listener);
	}

	public void notifyListeners() {
		logger.log(Level.INFO,"Notifying Listeners:" + listeners.size());
		Iterator<CountValueValueChangeListener> i = listeners.iterator();
		while (i.hasNext()) {
			i.next().notify(this.value1, this.newValue1, this.value);
		}
	}

	public void unregisterListener(CountValueValueChangeListener listener) {
		listeners.remove(listener);
	}


}
