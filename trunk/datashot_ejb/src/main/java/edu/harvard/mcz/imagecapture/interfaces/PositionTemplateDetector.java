/**
 * PositionTemplateDetector.java
 * edu.harvard.mcz.imagecapture.interfaces
 * Copyright 2009 President and Fellows of Harvard College
 * Author: Paul J. Morris
 */
package edu.harvard.mcz.imagecapture.interfaces;

import java.io.File;

import edu.harvard.mcz.imagecapture.exceptions.UnreadableFileException;

/** PositionTemplateDetector interface for detecting PositionTemplates for image files.
 * 
 * @author mole
 *
 */
public interface PositionTemplateDetector {

	/** Given a file, determine if a PositionTemplate applies to that file and if so
	 * return the identifier for the PositionTemplate.
	 * 
	 * @see edu.harvard.mcz.imagecapture.PositionTemplate
	 * @param anImageFile to check 
	 * @return the templateId of the PositionTemplate as a String 
	 * @throws UnreadableFileException if the file cannot be read.
	 */
	public String detectTemplateForImage(File anImageFile) throws UnreadableFileException;
	
}
