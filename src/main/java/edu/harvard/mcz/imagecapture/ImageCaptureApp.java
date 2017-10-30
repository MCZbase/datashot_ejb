/**
 * ImageCaptureApp.java
 * edu.harvard.mcz.imagecapture
 * Copyright 2009 President and Fellows of Harvard College
 * Author: Paul J. Morris 
 * 
 * File last changed on $Date: 2010-10-22 14:05:55 -0400 (Fri, 22 Oct 2010) $ by $Author: mole $ in $Rev: 258 $.
 * $Id: ImageCaptureApp.java 258 2010-10-22 18:05:55Z mole $
 */
package edu.harvard.mcz.imagecapture;

//import edu.harvard.mcz.imagecapture.data.SpecimenLifeCycle;

import java.util.logging.Logger;

//import edu.harvard.mcz.imagecapture.interfaces.BarcodeBuilder;
//import edu.harvard.mcz.imagecapture.interfaces.BarcodeMatcher;

/** Main method removed for EJB copy.  Retains constants.
 *
 * Copy of: Main entry point for user interface of ImageCapture Java Application.
 * Creates a SingletonObject, loads the properties file, and opens a MainFrame
 * 
 * @see edu.harvard.mcz.imagecapture.Singleton
 * @see edu.harvard.mcz.imagecapture.ImageCaptureProperties
 * 
 * @author mole
 *
 */
public class ImageCaptureApp {
	
	private final static Logger logger = Logger.getLogger(ImageCaptureApp.class.getName());
	
	public static final String APP_VERSION = "1.3.1-SNAPSHOT";
	public static final String APP_NAME = "RapidCaptureWeb";
	public static final String APP_DESCRIPTION = "Rapid capture of data from images of pin Labels and pinned insect \nspecimens developed for the MCZ Lepidoptera collection";
	public static final String APP_COPYRIGHT = "Copyright 2009-2017 President and Fellows of Harvard College";
    public static final String APP_CONTRIBUTORS = "Design: Brendan Haley, Linda Ford, Rodney Eastwood, Paul J. Morris.  Code: Paul J. Morris";
    public static final String APP_LIBRARIES = "Hibernate, Tesseract, ZXing, Log4J, drew.metadata.exif, iText";
    public static final String APP_REV = "$Rev: 258 $";  // ImageCapture.jar file will be one revision behind latest commit. 
    
    /**
     * Regular expression for recognizing drawer numbers and unit tray numbers
     */
    public static final String REGEX_DRAWERNUMBER = "[0-9]{3}\\Q.\\E[0-9]+";
    /**
     * Regular expression for recognizing image filenames in pattern decided on for project.
     */
    public static final String REGEX_IMAGEFILE = "^IMG_[0-9]{6}\\.JPG$";
    
    /**
     * Match blank, or year or year/month or year/month/day. or date range.
     * ISO date format, except that role of - and / are reversed.
     */
    //public static final String REGEX_DATE = "^([12][0-9]{3}((/[01][0-9]){1}(/[0-3][0-9])?)?)??$";
    public static final String REGEX_DATE = "^([12][0-9]{3}((/[01][0-9]){1}(/[0-3][0-9])?)?)?(\\-([12][0-9]{3}((/[01][0-9]){1}(/[0-3][0-9])?)?)?)??$";
      
    /**
     * Code for a normal exit, pass to ImageCaptureApp.exit(EXIT_NORMAL).
     */
    public static final int EXIT_NORMAL = 0;
    /**
     * Error code for an exit after a fatal error. 
     * Pass to ImageCaptureApp.exit(EXIT_ERROR);
     */
    public static final int EXIT_ERROR = 1;



}
