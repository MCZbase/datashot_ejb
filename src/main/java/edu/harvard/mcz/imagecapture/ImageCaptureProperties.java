/**
 * ImageCaptureProperties.java
 * edu.harvard.mcz.imagecapture
 * Copyright 2009 President and Fellows of Harvard College
 * Author: Paul J. Morris
 */
package edu.harvard.mcz.imagecapture;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.table.AbstractTableModel;

import java.util.logging.Level;
import java.util.logging.Logger;

import edu.harvard.mcz.imagecapture.exceptions.NoSuchTemplateException;

/**Substantial application logic removed for EJB copy.   Needs Refactoring.
 *
 * TODO: Refactor for EJB
 *
 * Filesystem persistence and retrieval of properties for ImageCapture Application.
 * Includes constants for key names to use in properties file, and definition of default
 * values for properties to go with these keys if they aren't defined in the persistent file.
 *
 * @author mole
 *
 */
public class ImageCaptureProperties  extends AbstractTableModel {

	public static final String KEY_LASTPATH = "scanonebarcode.lastpath";
	public static final String KEY_IMAGEBASE = "images.basedirectory";
	public static final String KEY_TEMPLATEDEFAULT = "template.default";
	public static final String KEY_TESSERACT_EXECUTABLE = "program.tesseract";
	public static final String KEY_CONVERT_EXECUTABLE = "program.convert";
	public static final String KEY_CONVERT_PARAMETERS = "convert.parameters";
	public static final String KEY_DETAILS_SCROLL = "details.scroll";

	public static final String VALUE_DETAILS_SCROLL_FORCE_ON = "on";

	 private final static Logger logger = Logger.getLogger(ImageCaptureProperties.class.getName());

	private static final long serialVersionUID = -8078524277278506689L;
	private Properties properties = null;
	private String propertiesFilename = null;
	private StringBuffer propertiesFilePath = null;

	public ImageCaptureProperties() {
		propertiesFilename = "imagecapture.properties";
		propertiesFilePath = new StringBuffer(System.getProperty("user.dir"));
		propertiesFilePath.append(System.getProperty("file.separator"));
		propertiesFilePath.append(propertiesFilename);
		System.out.println("Opening properties file: " + propertiesFilePath.toString());
		try {
			loadProperties();
		} catch (Exception e) {
			// thrown if properties can't be loaded, create default properties.
			properties = new Properties();
		}
		checkDefaults();
		testDefaultTemplate();
	}

	/** Given a File (which could be a directory path as a File object), return
	 * the portion of the path to that file (directory) that is below the path
	 * described by KEY_IMAGEBASE.
	 *
	 * @param aFilename The file or directory (File object) from which to extract the path.
	 * @return a string representation of a path from imagebase using the system
	 * path separator character.
	 */
	public static String getPathBelowBase(File aFilename) {
		return getPathBelowBase(aFilename, File.separator);
	}

	/**
	 * Given a file, is that file inside the path described by ImageCaptureProperties.KEY_IMAGEBASE
	 *
	 * @param aFile
	 * @return true if aFile is inside imagebase, false otherwise.
	 */
	public static boolean isInPathBelowBase(File aFile) {
		boolean result = false;
		String base = Singleton.getSingletonInstance().getProperties().getProperties().getProperty(
				ImageCaptureProperties.KEY_IMAGEBASE);
		String filePath = aFile.getPath();

        if (aFile.isFile()) {
        	filePath = aFile.getParent();
        }
        logger.log(Level.INFO,"Provided path to test: " + filePath);
		if (File.separator.equals("\\")) {
			if (!base.endsWith("\\")) { base = base + "\\";  }
			// the separator "\" is represented in java as "\\" and in a java regular expression as "\\\\"
			base = base.replaceAll("\\\\", "\\\\\\\\");
			filePath = filePath.replaceAll("\\\\", "\\\\\\\\");
		} else {
			if (!base.endsWith("/")) { base = base + "/"; }
			if (!filePath.endsWith("/")) { filePath = filePath + "/"; }
		}
		logger.log(Level.INFO,"Base path for test: " + base);
		if (filePath.startsWith(base)) {
			result = true;
		}
		return result;
	}

	/** Warning: For unit testing only.  Do not invoke this method.  Always use getPathBelowBase(File aFilename) instead.
	 *
	 * @see edu.harvard.mcz.imagecapture.ImageCaptureProperties#getPathBelowBase(File)
	 */
	public static String getPathBelowBase(File aFilename, String fileSeparator) {
		String result = "";
		String base = Singleton.getSingletonInstance().getProperties().getProperties().getProperty(
				ImageCaptureProperties.KEY_IMAGEBASE);   // this is what we are going to strip off aFilename
        //String filename = "";  // name of file if aFilename is a file rather than a directory

        result = aFilename.getPath();
        logger.log(Level.INFO,"Local path to file: " + result);
        if (aFilename.isFile()) {
        	result = aFilename.getParent();
        }

		if (fileSeparator.equals("\\")) {
			if (!base.endsWith("\\")) { base = base + "\\";  }
			// the separator "\" is represented in java as "\\" and in a java regular expression as "\\\\"
			base = base.replaceAll("\\\\", "\\\\\\\\");
		} else {
			if (!base.endsWith("/")) { base = base + "/"; }
			if (!result.endsWith("/")) { result = result + "/"; }
		}
		logger.log(Level.INFO,"Base path to remove: " + base);
		// strip base out of canonical form of aFilename
		if (base.equals(result)) {
			result = "";
		} else {
		    result = result.replaceFirst(base, "");
		}
		// make sure that path ends with fileSeparator
		if (!result.endsWith(fileSeparator)) { result = result + fileSeparator; }

		// if result is only a separator set it to an empty string
		if (fileSeparator.equals("\\")) {
			if (result.equals("\\")) { result = ""; }
		} else {
			if (result.equals("/")) { result = ""; }
		}

		logger.log(Level.INFO,"Path below basepath: " + result);

		return result;
	}

	/**
	 * Given a path from the image base (property ImageCaptureProperties.KEY_IMAGEBASE)
	 * and a filename, returns the full path to that file, including the image base
	 * using the file separators for the current system.  For "images/testimages/" and
	 * "imagefile.jpg" returns a string like "/mount/lepidoptera/images/testimages/imagefile.jpg"
	 * or "Z:\\lepidoptera\images\testimages\imagefile.jpg"
	 *
	 * @param aDirectoryPath
	 * @param aFileName
	 * @return String containing the full path to the file
	 */
	public static String assemblePathWithBase(String aDirectoryPath, String aFileName) {
		return assemblePathWithBase(aDirectoryPath, aFileName, File.separator);
	}
	/**
	 * Warning: For unit testing only.  Do not invoke this method.  Use assemblePathWithBase(String aDirectoryPath, String aFileName) instead.
	 * @see edu.harvard.mcz.imagecapture.ImageCaptureProperties#assemblePathWithBase(String, String)
	 */
	public static String assemblePathWithBase(String aDirectoryPath, String aFileName, String fileSeparator) {
		StringBuilder result = new StringBuilder();
		String base = Singleton.getSingletonInstance().getProperties().getProperties().getProperty(
				ImageCaptureProperties.KEY_IMAGEBASE);
		String path = aDirectoryPath;
		// Gracefully handle the case where we've been given a null path.
		if (path==null) { path = ""; }
		// First, correct the aDirectoryPath to the local file separator.
		//logger.log(Level.INFO,"File separator = '" + fileSeparator + "'");
		//logger.log(Level.INFO,path);
		//logger.log(Level.INFO,base);
		if (fileSeparator.equals("/")) {
			// unix filesystem
			path = path.replaceAll("\\\\", "/");
		} else {
			// windows filesystem
			path = path.replaceAll("/", "\\\\");
		}
		// Second, if base path doesn't end with a file separator, add one.
		if (!base.endsWith(fileSeparator)) {
		    base = base + fileSeparator;
		}
		// Third, assemble the components.
		if (path.endsWith(fileSeparator)) {
			result.append(base).append(path).append(aFileName);
		} else {
		    result.append(base).append(path).append(fileSeparator).append(aFileName);
		}
		logger.log(Level.INFO,result.toString());
		return result.toString();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int column) {
		String returnValue = "";
		if (column==0) { returnValue = "Key"; }
		if (column==1) { returnValue = "Property value"; }
 		return returnValue;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		boolean returnValue = false;
		if (columnIndex==1) { returnValue = true; }
 		return returnValue;
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
		if (columnIndex==1) {
			Enumeration<Object> p = properties.keys();
			int element = 0;
			while (p.hasMoreElements()) {
			    String e = (String)p.nextElement();
			    if (element==rowIndex) {
			    	properties.setProperty(e, (String)value);
			    }
			    element++;
			}
		}
	}

	/**
	 * Test to see if the KEY_TEMPLATEDEFAULT matches a valid PositionTemplate.
	 * Change to PositionTemplate.TEMPLATE_DEFAULT and Log error if it does not.
	 * Note: if the KEY_TEMPLATEDEFAULT property does not match a hardcoded
	 * PositionTemplate, a database lookup will be triggered and, if the request
	 * is being made at application launch, a login dialog will be launched.
	 *
	 * Take no action if there is no match to the KEY_TEMPLATEDEFAULT
	 *
	 * @return true if template in properties exists, false if no match to key or
	 * if template was changed.
	 */
	private boolean testDefaultTemplate() {
		boolean result = false;
		if (properties.containsKey(KEY_TEMPLATEDEFAULT)) {
		    String templateId = properties.getProperty(KEY_TEMPLATEDEFAULT);
		    try {
				PositionTemplate template = new PositionTemplate(templateId);
				template.getClass();  // added to suppress findbugs DLS_DEAD_LOCAL_STORE
				// no exception thrown, this template is OK.
				result = true;
			} catch (NoSuchTemplateException e) {
				// Template isn't recognized, set property to default template.
				properties.setProperty(KEY_TEMPLATEDEFAULT, PositionTemplate.TEMPLATE_DEFAULT);
			}
		}
		return result;
	}

	/** Make sure required properties are present as keys, if they aren't add
	 * them with default values.  This is where the default properties are defined.
	 *
	 */
	private void checkDefaults() {
		if (!properties.containsKey(KEY_IMAGEBASE))  {
			// Root of the path of the place where all image files should be stored.
			properties.setProperty(KEY_IMAGEBASE,"/opt/glassfish/images");
		}
		if (!properties.containsKey(KEY_TEMPLATEDEFAULT)) {
			// PostitionTemplate to use by default
		    properties.setProperty(KEY_TEMPLATEDEFAULT, PositionTemplate.TEMPLATE_DEFAULT);
		}
		if (!properties.containsKey(KEY_TESSERACT_EXECUTABLE)) {
			// name of the tesseract executable, probably tesseract on unix, tesseract.exe on windows
		    properties.setProperty(KEY_TESSERACT_EXECUTABLE, "tesseract ");
		}
		if (!properties.containsKey(KEY_CONVERT_EXECUTABLE)) {
		   properties.setProperty(KEY_CONVERT_EXECUTABLE, "convert ");
	    }
		if (!properties.containsKey(KEY_CONVERT_PARAMETERS)) {
			// default ImageMagick convert properties used for JPG to TIFF conversion to
			// prepare a file for tesseract.
			properties.setProperty(KEY_CONVERT_PARAMETERS, " -depth 8 -compress None -type Grayscale ");
		}
		if (!properties.containsKey(KEY_DETAILS_SCROLL)) {
			// default value is no scroll bars for SpecimenDetailsViewPane.
			properties.setProperty(KEY_DETAILS_SCROLL, "none");
		}

	}

	/* Place where properties in this instance are persisted.
	 *
	 * @returns a text string representing the storage location from which this instance of
	 * properties was loaded such ast the path and filename of the file from which the values for
	 * this instance of properties was retrieved.
	 */
	public String getPropertiesSource() {
		return propertiesFilePath.toString();
	}

	protected void loadProperties() throws Exception {
		properties = new Properties();
		FileInputStream propertiesStream = null;
		try {
		    propertiesStream = new FileInputStream(propertiesFilePath.toString());
		    properties.load(propertiesStream);
		} catch (FileNotFoundException e)  {
			System.out.println("Error: Properties file not found.");
			throw e;
		} catch (Exception ex) {
			System.out.println("Error loading properties.");
			System.out.println(ex.getMessage());
			throw ex;
		} finally {
			if (propertiesStream!=null) {
			    propertiesStream.close();
			}
		}
	}

	public void saveProperties() throws Exception{
		FileOutputStream propertiesStream = null;
		try {
			System.out.println("Saving properties file: " + propertiesFilePath.toString());
		    propertiesStream = new FileOutputStream(propertiesFilePath.toString());
			properties.store(propertiesStream, ImageCaptureApp.APP_NAME + " " + ImageCaptureApp.APP_VERSION +  " Properties" );
			propertiesStream.close();
		} catch (Exception e) {
			System.out.println("Error saving properties.");
			System.out.println(e.getMessage());
			e.printStackTrace();
			throw e;
		}finally {
			if (propertiesStream!=null) {
			    propertiesStream.close();
			}
		}
	}

	public Properties getProperties() {
		return properties;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return properties.size();
	}



	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		String value = "";
		Enumeration<Object> p = properties.keys();
		int element = 0;
		while (p.hasMoreElements()) {
		    String e = (String)p.nextElement();
		    if (element==rowIndex) {
		    	if (columnIndex==0) {
		    	    value = e;
		    	} else {
		    		value = properties.getProperty(e);
		    	}
		    }
		    element++;
		}
		return value;
	}

}
