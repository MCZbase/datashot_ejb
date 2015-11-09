/**
 * DefaultPositionTemplateDetector.java
 * edu.harvard.mcz.imagecapture
 * Copyright 2009 President and Fellows of Harvard College
 * Author: Paul J. Morris
 */
package edu.harvard.mcz.imagecapture;

import java.io.File;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.harvard.mcz.imagecapture.exceptions.NoSuchTemplateException;
import edu.harvard.mcz.imagecapture.exceptions.OCRReadException;
import edu.harvard.mcz.imagecapture.exceptions.UnreadableFileException;
import edu.harvard.mcz.imagecapture.interfaces.PositionTemplateDetector;

/** DefaultPositionTemplateDetector find a template by the position of a barcode in an image file.
 * This class makes the assumption that a template can be uniqely idenfified by the location of the
 * barcode in the image.  Each template must have the bardcode in a uniquely different place.
 * 
 * @author mole
 *
 * @see edu.harvard.mcz.imagecapture.PositionTemplate
 *
 */
public class DefaultPositionTemplateDetector implements	PositionTemplateDetector {

	private static final Log log = LogFactory.getLog(DefaultPositionTemplateDetector.class);
	
	/* (non-Javadoc)
	 * @see edu.harvard.mcz.imagecapture.interfaces.PositionTemplateDetector#detectTemplateForImage(java.io.File)
	 */
	@Override
	public String detectTemplateForImage(File anImageFile) throws UnreadableFileException {
		String result = PositionTemplate.TEMPLATE_NO_COMPONENT_PARTS;
		// We will be calling getBarcodeText(PositionTemplate aTemplate) below, so it doesn't matter
		// that we are instatiating the scannable file here with a default template.
		CandidateImageFile scannableFile = null;
		scannableFile = new CandidateImageFile(anImageFile, new PositionTemplate());

		PositionTemplate p = new PositionTemplate();
		List<String> templates = p.getTemplateIds();
		// iterate through templates and check until the first template where a barcode is found
		ListIterator<String> i = templates.listIterator();
		boolean found = false;
		while (i.hasNext() && !found) {
			try {
				// get the next template from the list
				PositionTemplate template = new PositionTemplate((String)i.next());
				log.debug("Testing template: " + template.getTemplateId());
				if (template.getTemplateId().equals(PositionTemplate.TEMPLATE_NO_COMPONENT_PARTS))  { 
					// skip, this is the default result if no other is found.
				}  else { 
					// Check to see if the barcode is in the part of the template
					// defined by getBarcodeULPosition and getBarcodeSize.
					String text = scannableFile.getBarcodeText(template);
					log.debug("Found:[" + text + "] status="+ scannableFile.getBarcodeStatus());
					if (scannableFile.getBarcodeStatus()==CandidateImageFile.RESULT_BARCODE_SCANNED) { 
						// RESULT_BARCODE_SCANNED is only returned if the reader read a QR code barcode inside
						// the area defined by the template for containing the barcode.  
						// If we got here, we found a barcode in the expected place and know which template
						// to return.
						found = true;
						log.debug("Match to:" + template.getTemplateId());
						result = template.getTemplateId();
					}
				}
			} catch (NoSuchTemplateException e) {
				// Ending up here means a serious error in PositionTemplate
				// as the list of position templates returned by getTemplates() includes
				// an entry that isn't recognized as a valid template.  
				log.fatal("Fatal error.  PositionTemplate.getTemplates() includes an item that isn't a valid template.");
				log.trace(e);
			} 
		}
		return result;
	}

}
