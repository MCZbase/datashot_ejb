/**
 * NoSuchValueException.java
 * edu.harvard.mcz.imagecapture.exceptions
 * Copyright 2009 President and Fellows of Harvard College
 * Author: Paul J. Morris
 */
package edu.harvard.mcz.imagecapture.exceptions;

/** NoSuchValueException can be thrown when an input value is not found in a controlled vocabulary.
 * 
 * @author mole
 *
 */
public class NoSuchValueException extends Exception {

	private static final long serialVersionUID = 1969641427992727840L;

	/**
	 * 
	 */
	public NoSuchValueException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public NoSuchValueException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public NoSuchValueException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoSuchValueException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
