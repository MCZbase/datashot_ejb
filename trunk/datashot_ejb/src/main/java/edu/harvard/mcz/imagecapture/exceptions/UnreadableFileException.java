/**
 * CantReadFileException.java
 * edu.harvard.mcz.imagecapture.exceptions
 * Copyright 2009 President and Fellows of Harvard College
 * Author: Paul J. Morris
 */
package edu.harvard.mcz.imagecapture.exceptions;

/** UnreadableFileException can be thrown when a file can't be read
 * for any reason (non-existent, not allowed by security context, etc).  
 * 
 * @author mole
 *
 */
public class UnreadableFileException extends Exception {

	private static final long serialVersionUID = -8398415097676132152L;

	/** Exception to throw for any sort of problem reading 
	 * a file.
	 */
	public UnreadableFileException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public UnreadableFileException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public UnreadableFileException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public UnreadableFileException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
