/**
 * NoSuchRecordException.java
 * edu.harvard.mcz.imagecapture.exceptions
 * Copyright 2009 President and Fellows of Harvard College
 * Author: Paul J. Morris
 */
package edu.harvard.mcz.imagecapture.exceptions;

/** NoSuchRecordException can be thrown when an attempt to look up a particular record in a 
 * database returns no matches.  
 * 
 * @author mole
 *
 */
public class NoSuchRecordException extends Exception {

	private static final long serialVersionUID = 2430363609924870002L;

	/**
	 * 
	 */
	public NoSuchRecordException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public NoSuchRecordException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public NoSuchRecordException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoSuchRecordException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
