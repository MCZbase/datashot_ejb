/** 
 * ValueLister.java
 * edu.harvard.mcz.imagecapture.interfaces
 * Copyright 2009 President and Fellows of Harvard College
 * Author: Paul J. Morris
 */
package edu.harvard.mcz.imagecapture.interfaces;

/**
 * Interface for providing a string array of values that can be used
 * for purposes such as populating a pick list.
 * 
 * @author mole
 *
 */
public interface ValueLister {

	/**
	 * Obtain a list of Valid values in a controlled vocabulary, or a list of values in current use in a user
	 * extendible controlled vocabulary.
	 * 
	 * @return a string array where each entry in the array is a valid value within a controlled vocabulary.
	 */
	public String[] getValues();
	
}
