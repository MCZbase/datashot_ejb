/**
 * ImageLoadException.java
 * edu.harvard.mcz.imagecapture.exceptions
 * Copyright 2009 President and Fellows of Harvard College
 * Author: Paul J. Morris
 */
package edu.harvard.mcz.imagecapture.exceptions;

/** ImageLoadException to be thrown on any problem from trying to load an image.
 * 
 * @author mole
 *
 */
public class ImageLoadException extends Exception {

	private static final long serialVersionUID = -5230684298896173469L;

	/**
	 * 
	 */
	public ImageLoadException() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public ImageLoadException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public ImageLoadException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ImageLoadException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

}
