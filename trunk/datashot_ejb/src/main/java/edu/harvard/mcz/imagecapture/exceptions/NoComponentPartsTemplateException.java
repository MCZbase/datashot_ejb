/**
 * NoComponentPartsTemplateExeption.java
 * edu.harvard.mcz.imagecapture.exceptions
 * Copyright 2009 President and Fellows of Harvard College
 * Author: Paul J. Morris
 */
package edu.harvard.mcz.imagecapture.exceptions;

/** NoComponentPartsTemplateExeption is to handle the special case of an image that should be shown only
 * as an entire image without being divided into component parts using a template.  The PositionTemplate constant
 * TEMPLATE_NO_COMPONENT_PARTS can be used to identify such images, and when a PositionTemplate is instantiated
 * with this constant as the template, a NoComponentPartsTemplateExeption will be thrown, allowing code to determine 
 * that the entire image should be shown.   This is an alternative to passing the image to the PositionTemplate so that 
 * the image size is returned from all PositionTemplate methods.
 * 
 * @deprecated
 * @author mole
 *
 */
public class NoComponentPartsTemplateException extends Exception {

	private static final long serialVersionUID = 8393319549685966260L;

	/**
	 * 
	 */
	public NoComponentPartsTemplateException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public NoComponentPartsTemplateException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public NoComponentPartsTemplateException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public NoComponentPartsTemplateException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
