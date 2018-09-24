/**
 * MetadataRetriever.java
 * edu.harvard.mcz.imagecapture.data
 * Copyright 2009 President and Fellows of Harvard College
 * Author: Paul J. Morris
 */
package edu.harvard.mcz.imagecapture.data;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.MaskFormatter;

import edu.harvard.mcz.imagecapture.ImageCaptureApp;

//import edu.harvard.mcz.imagecapture.ImageCaptureApp;
//import edu.harvard.mcz.imagecapture.MainFrame;

/** MetadataRetriever produces metadata (field lengths, tooltip texts, input masks, input verifiers)
 * for fields in tables in database.  
 * 
 * @author mole
 *
 */
public class MetadataRetriever {
	
	private final static Logger logger = Logger.getLogger(MetadataRetriever.class.getName());
	
	private static String repeat(String s, int count) { 
		StringBuffer result = new StringBuffer();
		for (int i=0; i<count; i++) { 
			result.append(s);
		}
		return result.toString();
	}
	
	/**Generates a MaskFormatter for a JFormattedTextField based upon the length (and potentially the type) of the
	 * underlying text field.  Doesn't work well for normal varchar() fields, as the JTextField appears to be full
	 * of spaces.   
	 * <BR>
	 * Usage:
	 * <pre>
	    JFormattedTextField jtext_for_fieldname = new JFormattedTextField(MetadataRetriever(tablename.class,"fieldname"));
	   </pre>
	 * 
	 * @param aTableClass
	 * @param fieldname
	 * @return a MaskFormatter for a jFormattedTextField
	 * 
	 * TODO: add field type recognition, currently returns only "****" masks. 
	 */
	@SuppressWarnings("unchecked")
	public static MaskFormatter getMask(Class aTableClass, String fieldname) { 
		MaskFormatter formatter = null;
		try {
			formatter = new MaskFormatter(repeat("*",getFieldLength(aTableClass,fieldname)));
		} catch (ParseException e) {
			// Shouldn't end up here unless tables have been redesigned and
			// MetadataRetriever.getFieldLength isn't returning a value.
			System.out.println("Bad Mask format string");
			e.printStackTrace();
		}
		return formatter;
	}
	
	public static String getRegex(Class aTableClass, String fieldname) {
		String result = "";
		if (aTableClass == Specimen.class && fieldname.equalsIgnoreCase("ISODate")) { 
			result = ImageCaptureApp.REGEX_DATE;
		}
		if (aTableClass == Determination.class && fieldname.equalsIgnoreCase("DateDetermined")) { 
			result = ImageCaptureApp.REGEX_DATE;
		}
		return result;
	}
				
	/**
	 * Generates an InputVerifier for a JTextField
	 * <BR>
	 * Usage:
	 * <pre>
	    JTextField jText_for_fieldname = new JTextField();
	    jText_for_fieldname.addInputVerifier(MetadataRetriever.getInputVerifier(tablename.class,"fieldname",jText_for_fieldname));
	   </pre>
	 * 
	 * @param aTableClass table proxy object for fieldname 
	 * @param fieldname field for which to check the fieldlength
	 * @param field  JTextField to which the InputVerifier is being added.
	 * @return an InputVerifier for the JTextField
	 * TODO: implement tests for more than just length. 
	 * 
	 */
	/*
	@SuppressWarnings("unchecked")
	public static InputVerifier getInputVerifier(final Class aTableClass, final String fieldname, final JTextField field)  {
		InputVerifier result = null;
		if (aTableClass == Specimen.class && fieldname == "ISODate") { 
			result = new InputVerifier() {
				public boolean verify(JComponent comp) {
					boolean returnValue = true;
					JTextField textField = (JTextField)comp;
					String content = textField.getText();
					if (content.length() > getFieldLength(aTableClass, fieldname)) {
						returnValue = false;
					} else { 
						if (!content.matches(ImageCaptureApp.REGEX_DATE)) { 
							returnValue = false;
						}
					}
					return returnValue;
				}
				public boolean shouldYieldFocus(JComponent input) {
					boolean valid = super.shouldYieldFocus(input);
					if (valid) {
						field.setBackground(Color.WHITE);
					} else { 
						field.setBackground(MainFrame.BG_COLOR_ERROR);
					}
					field.revalidate();
					return valid;
				}
			};			
		}  else { 
		result = new InputVerifier() {
			public boolean verify(JComponent comp) {
				boolean returnValue = true;
				JTextField textField = (JTextField)comp;
				String content = textField.getText();
				if (content.length() > getFieldLength(aTableClass, fieldname)) {
					returnValue = false;
				}
				return returnValue;
			}
			public boolean shouldYieldFocus(JComponent input) {
				boolean valid = super.shouldYieldFocus(input);
				if (valid) {
					if (fieldname.equalsIgnoreCase("Inferences")) { 
						field.setBackground(MainFrame.BG_COLOR_ENT_FIELD);
					} else { 
					    field.setBackground(Color.WHITE);
					} 
				} else { 
					field.setBackground(MainFrame.BG_COLOR_ERROR);
				}
				field.revalidate();
				return valid;
			}
		};
		}
		return result;
	}

	 *
	 */


	/** Determine the length of a field from the class of the proxy object
	 * for the table and the name of the field.
	 * <BR>
	 * Usage:
	 * <pre>
	     int genusSize = MetadataRetriever.getFieldLength(Specimen.class, "genus");
	   </pre>
	 * 
	 * @param aTableClass the class of the proxy object over the table.
	 * @param fieldname the name of the field in that table (case insensitive).  
	 * @return the number of characters that can be put into the field.
	 */
	@SuppressWarnings("unchecked")
	public static int getFieldLength(Class aTableClass, String fieldname) {
		int length = 0;
		if (aTableClass==Specimen.class) { 
            if (fieldname.equalsIgnoreCase("Barcode")) {  length=20; }  
            if (fieldname.equalsIgnoreCase("DrawerNumber")) { length=10; } 
            if (fieldname.equalsIgnoreCase("TypeStatus")) { length=50; }
            if (fieldname.equalsIgnoreCase("TypeNumber")) { length=255; }
            if (fieldname.equalsIgnoreCase("CitedInPublication")) { length=900; }
            if (fieldname.equalsIgnoreCase("Features")) { length=50; }
            if (fieldname.equalsIgnoreCase("Family")) { length=40; }
            if (fieldname.equalsIgnoreCase("Subfamily")) { length=40; }
            if (fieldname.equalsIgnoreCase("Tribe")) { length=40; }
            if (fieldname.equalsIgnoreCase("Genus")) { length=40; }
            if (fieldname.equalsIgnoreCase("SpecificEpithet")) { length=40; }
            if (fieldname.equalsIgnoreCase("SubspecificEpithet")) { length=255; }
            if (fieldname.equalsIgnoreCase("InfraspecificEpithet")) { length=40; }
            if (fieldname.equalsIgnoreCase("InfraspecificRank")) { length=40; }
            if (fieldname.equalsIgnoreCase("Authorship")) { length=255; }
            if (fieldname.equalsIgnoreCase("UnNamedForm")) { length=50; }
            if (fieldname.equalsIgnoreCase("IdentificationQualifier")) { length=50; }
            if (fieldname.equalsIgnoreCase("IdentifiedBy")) { length=255; }
            if (fieldname.equalsIgnoreCase("IdentificationDate")) { length=32; }
            if (fieldname.equalsIgnoreCase("NatureOfID")) { length=244; }
            if (fieldname.equalsIgnoreCase("IdentificationRemarks")) { length=65535; }
			if (fieldname.equalsIgnoreCase("Higher_Geography")) { length = 255; }
            if (fieldname.equalsIgnoreCase("Country")) { length=255; }
            if (fieldname.equalsIgnoreCase("PrimaryDivison")) { length=255; }
            if (fieldname.equalsIgnoreCase("PrimaryDivision")) { length=255; }
            if (fieldname.equalsIgnoreCase("SpecificLocality")) { length=65535; }
            if (fieldname.equalsIgnoreCase("VerbatimLocality")) { length=65535; }
            if (fieldname.equalsIgnoreCase("VerbatimCollector")) { length=65535; }
            if (fieldname.equalsIgnoreCase("VerbatimCollection")) { length=65535; }
            if (fieldname.equalsIgnoreCase("VerbatimNumbers")) { length=65535; }
            if (fieldname.equalsIgnoreCase("VerbatimUnclassifiedText")) { length=65535; }
            //if (fieldname.equalsIgnoreCase("VerbatimElevation")) { length=255; }
            if (fieldname.equalsIgnoreCase("Minimum_Elevation")) { length=5; }
            if (fieldname.equalsIgnoreCase("Maximum_Elevation")) { length=5; }
            if (fieldname.equalsIgnoreCase("Elev_Units")) { length=5; }
            if (fieldname.equalsIgnoreCase("CollectingMethod")) { length=255; }
            if (fieldname.equalsIgnoreCase("ISODate")) { length=21; }
            if (fieldname.equalsIgnoreCase("DateNOS")) { length=32; }
            if (fieldname.equalsIgnoreCase("DateEmerged")) { length=32; }
            if (fieldname.equalsIgnoreCase("DateEmergedIndicator")) { length=50; }
            if (fieldname.equalsIgnoreCase("DateCollected")) { length=32; }
            if (fieldname.equalsIgnoreCase("DateCollectedIndicator")) { length=50; }
            if (fieldname.equalsIgnoreCase("Collection")) { length=255 ; }
            if (fieldname.equalsIgnoreCase("SpecimenNotes")) { length=65535; }
            if (fieldname.equalsIgnoreCase("LifeStage")) { length=50; }
            if (fieldname.equalsIgnoreCase("Sex")) { length=50; }
            if (fieldname.equalsIgnoreCase("PreparationType")) { length=50; }
            if (fieldname.equalsIgnoreCase("Habitat")) { length=900; }
            if (fieldname.equalsIgnoreCase("Microhabitat")) { length=900; }
            if (fieldname.equalsIgnoreCase("AssociatedTaxon")) { length=900; }
            if (fieldname.equalsIgnoreCase("Questions")) { length=900; }
            if (fieldname.equalsIgnoreCase("Inferences")) { length=900; }
            if (fieldname.equalsIgnoreCase("LocationInCollection")) { length=50; }
            if (fieldname.equalsIgnoreCase("WorkFlowStatus")) { length=30; }
            if (fieldname.equalsIgnoreCase("CreatedBy")) { length=255; }
            if (fieldname.equalsIgnoreCase("DateLastUpdated")) { length=0; }
            if (fieldname.equalsIgnoreCase("LastUpdatedBy")){ length=255;  }
            if (fieldname.equalsIgnoreCase("ValidDistributionFlag")) {  length = 1; }
            if (fieldname.equalsIgnoreCase("Path")) { length=900; }
		}
		if (aTableClass==OtherNumbers.class) { 
			if (fieldname.equalsIgnoreCase("OtherNumber")) { length=50; }	
			if (fieldname.equalsIgnoreCase("NumberType")) { length=50; }	
		}
		if (aTableClass==Collector.class) { 
			if (fieldname.equalsIgnoreCase("CollectorName")) { length=255; }	
		}
		if (aTableClass==Determination.class) { 
            if (fieldname.equalsIgnoreCase("Genus")) { length=40; }
            if (fieldname.equalsIgnoreCase("SpecificEpithet")) { length=40; }
            if (fieldname.equalsIgnoreCase("SubspecificEpithet")) { length=255; }
            if (fieldname.equalsIgnoreCase("InfraspecificEpithet")) { length=40; }
            if (fieldname.equalsIgnoreCase("InfraspecificRank")) { length=40; }
            if (fieldname.equalsIgnoreCase("Authorship")) { length=255; }
            if (fieldname.equalsIgnoreCase("UnNamedForm")) { length=50; }
            if (fieldname.equalsIgnoreCase("IdentificationQualifier")) { length=50; }
            if (fieldname.equalsIgnoreCase("IdentifiedBy")) { length=255; }		
            if (fieldname.equalsIgnoreCase("TypeStatus")) { length=50; }
            if (fieldname.equalsIgnoreCase("SpeciesNumber")) { length=50; }
            if (fieldname.equalsIgnoreCase("VerbatimText")) { length=255; }
            
            if (fieldname.equalsIgnoreCase("IdentificationDate")) { length=32; }
            if (fieldname.equalsIgnoreCase("NatureOfID")) { length=244; }
            if (fieldname.equalsIgnoreCase("Remarks")) { length=65535; }
            
		}
		if (aTableClass==Users.class) { 
			if (fieldname.equalsIgnoreCase("username")) { length=50; }	
			if (fieldname.equalsIgnoreCase("fullname")) { length=50; }	
			if (fieldname.equalsIgnoreCase("description")) { length=255; }	
			if (fieldname.equalsIgnoreCase("role")) { length=20; }	
		}
		/**
		if (aTableClass==ICImage.class) { 
			if (fieldname.equalsIgnoreCase("rawBarcode")) { length=50; }	
			if (fieldname.equalsIgnoreCase("rawExifBarcode")) { length=50; }
			if (fieldname.equalsIgnoreCase("filename")) { length=50; }
			if (fieldname.equalsIgnoreCase("rawOcr")) { length=65535; }
			if (fieldname.equalsIgnoreCase("path")) { length=900; }
			if (fieldname.equalsIgnoreCase("uri")) { length=50; }
			if (fieldname.equalsIgnoreCase("templateId")) { length=50; }
			if (fieldname.equalsIgnoreCase("drawerNumber")) { length=10; }			
		}	
		*/
		if (aTableClass==Image.class) { 
			if (fieldname.equalsIgnoreCase("rawBarcode")) { length=50; }	
			if (fieldname.equalsIgnoreCase("rawExifBarcode")) { length=50; }
			if (fieldname.equalsIgnoreCase("filename")) { length=50; }
			if (fieldname.equalsIgnoreCase("rawOcr")) { length=65535; }
			if (fieldname.equalsIgnoreCase("path")) { length=900; }
			if (fieldname.equalsIgnoreCase("uri")) { length=50; }
			if (fieldname.equalsIgnoreCase("templateId")) { length=50; }
			if (fieldname.equalsIgnoreCase("drawerNumber")) { length=10; }			
		}		
		if (aTableClass==SpecimenPart.class) { 
			if (fieldname.equalsIgnoreCase("Part_Name")) { length = 255; }	
			if (fieldname.equalsIgnoreCase("Preserve_Method")){ length = 60; }	
			if (fieldname.equalsIgnoreCase("Lot_Count")) { length = 5; }	
			if (fieldname.equalsIgnoreCase("Lot_Count_Modifier")) { length = 5; }	
		}
		if (aTableClass==SpecimenPartAttribute.class) { 
			if (fieldname.equalsIgnoreCase("Attribute_Name")) { length = 30; }	
			if (fieldname.equalsIgnoreCase("Attribute_Value")) { length = 255; }	
			if (fieldname.equalsIgnoreCase("Attribute_Units")) { length = 30; }	
			if (fieldname.equalsIgnoreCase("Attribute_Remark")) { length = 4000; }	
			if (fieldname.equalsIgnoreCase("Attribute_Determiner")) { length = 255; }	
		}		
		if (aTableClass==MCZbaseGeogAuthRec.class) { 
			if (fieldname.equalsIgnoreCase("Higher_Geog")) { length = 255; }
		}
		if (aTableClass==LatLong.class) { 
			if (fieldname.equalsIgnoreCase("decLat")) { length = 11; }
			if (fieldname.equalsIgnoreCase("decLong")) { length = 11; }
			if (fieldname.equalsIgnoreCase("datum")) { length = 55; }
			if (fieldname.equalsIgnoreCase("geoRefMethod")) { length = 55; }
			if (fieldname.equalsIgnoreCase("origLatLongUnits")) { length = 20; }
			
			if (fieldname.equalsIgnoreCase("georefmethod")) { length = 55; }
			if (fieldname.equalsIgnoreCase("latLongRemarks")) { length = 4000; }
			if (fieldname.equalsIgnoreCase("verificationstatus")) { length = 40; }
			if (fieldname.equalsIgnoreCase("latLongRefSource")) { length = 500; }
			if (fieldname.equalsIgnoreCase("determinedByAgent")) { length = 255; }
			if (fieldname.equalsIgnoreCase("determinedDate")) { length = 10; }
			if (fieldname.equalsIgnoreCase("acceptedLatLongFg")) { length = 1; }
			if (fieldname.equalsIgnoreCase("fieldVerifiedFg")) { length = 1; }
			if (fieldname.equalsIgnoreCase("latLongForNnpFg")) { length = 1; }
			
			if (fieldname.equalsIgnoreCase("latDeg")) { length = 2; }
			if (fieldname.equalsIgnoreCase("latMin")) { length = 2; }
			if (fieldname.equalsIgnoreCase("decLatMin")) { length = 11; }
			if (fieldname.equalsIgnoreCase("latSec")) { length = 11; }
			if (fieldname.equalsIgnoreCase("latDir")) { length = 1; }
			if (fieldname.equalsIgnoreCase("longDeg")) { length = 3; }
			if (fieldname.equalsIgnoreCase("longMin")) { length = 2; }
			if (fieldname.equalsIgnoreCase("decLongMin")) { length = 11; }
			if (fieldname.equalsIgnoreCase("longSec")) { length = 11; }
			if (fieldname.equalsIgnoreCase("longDir")) { length = 1; }
			if (fieldname.equalsIgnoreCase("extent")) { length = 11; }
			
			if (fieldname.equalsIgnoreCase("gpsaccuracy")) { length = 11; }
			if (fieldname.equalsIgnoreCase("maxErrorDistance")) { length = 11; }
			if (fieldname.equalsIgnoreCase("maxErrorUnits")) { length = 2; }
			if (fieldname.equalsIgnoreCase("nearestNamedPlace")) { length = 255; }
			
			if (fieldname.equalsIgnoreCase("utmNs")) { length = 10; }
			if (fieldname.equalsIgnoreCase("utmEw")) { length = 10; }
			if (fieldname.equalsIgnoreCase("utmZone")) { length = 3; }
        
		}
		
		return length;
	}
    
	/**
	 * Given a proxy class for a table and the name of a field return a help text for that field.
	 *  
	 * @param aTableClass
	 * @param fieldname
	 * @return a String containing a help text suitable for use as a tooltip or other context help display.
	 */
	@SuppressWarnings("unchecked")
	public static String getFieldHelp(Class aTableClass, String fieldname) {
		String help = "No help available";
		if (aTableClass==Specimen.class) { 
            if (fieldname.equalsIgnoreCase("Barcode")) {  help="The barcode of the specimen, in the form 'MCZ-ENT01234567'"; }  
            if (fieldname.equalsIgnoreCase("DrawerNumber")) { help="The drawer number from the specimen unit tray label."; } 
            if (fieldname.equalsIgnoreCase("TypeStatus")) { help="Not a type, or the kind of type (e.g. Holotype) that this specimen is.  Normal workflow uses value 'not a type'.  This type status refers to the type status of this specimen for the name in the specimen record (secondary types may have type status set in other determinations)."; }
            if (fieldname.equalsIgnoreCase("TypeNumber")) { help="The number from the type catalog number series that applies to this specimen"; }
            if (fieldname.equalsIgnoreCase("CitedInPublication")) { help="Publications this specimen is cited in, as found on labels.  Separate publications with a semicolon ' ; '"; }
            if (fieldname.equalsIgnoreCase("Features")) { help="Description of features of this specimen that aren't named forms, un-named forms, sex, or life stage.  Examples: features could include eclosion defect, runt (unusually small), deformed, faded colours, scales stripped for venation, greasy, stained, psocid damaged."; }
            if (fieldname.equalsIgnoreCase("Family")) { help="The family into which this specimen is placed, from the unit tray label"; }
            if (fieldname.equalsIgnoreCase("Subfamily")) { help="The subfamily into which this specimen is placed, from the unit tray label"; }
            if (fieldname.equalsIgnoreCase("Tribe")) { help="The tribe into which this specimen is placed, if any, from the unit tray label."; }
            if (fieldname.equalsIgnoreCase("Genus")) { help="The generic name from the unit tray label.  The current identification for non-primary types, the type name for primary types.  Example: 'Delias' from 'Delias argenthona Fabricius, 1793'"; }
            if (fieldname.equalsIgnoreCase("SpecificEpithet")) { help="The specific part of the species group name from the unit tray label. The current identification for non-primary types, the type name for primary types.  Include indicators of uncertanty associated with this part of the name such as nr, cf, ?.  May be 'sp.'  Example: 'argenthona' from 'Delias argenthona Fabricius, 1793'.  Example: 'dominula' from 'Anisynta dominula draco Waterhouse, 1938'."; }
            if (fieldname.equalsIgnoreCase("SubspecificEpithet")) { help="The subspecific part (if present) of the species group name from the unit tray label.  Include indicators of uncertanty associated with this part of the name such as nr, cf, ?.  May be 'ssp.'.  Example: 'draco' from 'Anisynta dominula draco Waterhouse, 1938'."; }
            if (fieldname.equalsIgnoreCase("InfraspecificEpithet")) { help="The form, varietal, or other part of a name with a rank below subspecies from the unit tray label"; }
            if (fieldname.equalsIgnoreCase("InfraspecificRank")) { help="The rank (e.g. form, variety) of the infraspecific name from the unit tray label."; }
            if (fieldname.equalsIgnoreCase("Authorship")) { help="Authorship at the lowest taxonomic rank.  The author of the species group name from the unit tray label.  Include year and parentheses if present.  Example: 'Fabricius, 1793' from 'Delias argenthona Fabricius, 1793'.  Example: '(W.S. Macleay, 1826)' from  'Eleppone anactus (W.S. Macleay, 1826)'  The author of the specific name in a binomial, the author of the subspecific name in a trinomial."; }
            if (fieldname.equalsIgnoreCase("UnNamedForm")) { help="e.g. 'Winter form', informal descriptive name of the form of this specimen (for informal form names not regulated by the ICZN)."; }
            if (fieldname.equalsIgnoreCase("IdentificationQualifier")) { help="Don't use this field."; }
            if (fieldname.equalsIgnoreCase("IdentifiedBy")) { help="Name of the person, if known, who made this identification."; }
            if (fieldname.equalsIgnoreCase("IdentificationDate")) { help="The date on which the identification was made, in the form YYYY/MM/DD"; }
            if (fieldname.equalsIgnoreCase("NatureOfID")) { help="The nature of the identification, use legacy, except when determiner name is on label for the current identification."; }
            if (fieldname.equalsIgnoreCase("IdentificationRemarks")) { help="Remarks concerning this current identification"; }
            if (fieldname.equalsIgnoreCase("Higher_Geography")) { help="A controlled vocabulary for the higher geography of the locality, type at least 4 characters to get the picklist.  Will filter on country or state/province if these have values.  If no suitable value is available, type one in the form 'Continent, Country, State/Province'"; }
            if (fieldname.equalsIgnoreCase("Country")) { help="The current country name of the country from which this specimen was collected.   Inferred current name, not verbatim. Infer if you have specialist knowledge and annotate in Inferences if doubtfull, border change, or not obvious."; }
            if (fieldname.equalsIgnoreCase("PrimaryDivison")) { help="The state, province, or other primary geopolitical division of the country from which this specimen was collected.  Inferred current name, not verbatim.  Infer if you have specialist knowlege and annotate in Inferences if doubtfull, border change, or not obvious."; }
            if (fieldname.equalsIgnoreCase("PrimaryDivision")) { help="The state, province, or other primary geopolitical division of the country from which this specimen was collected.  Inferred current name, not verbatim.  Infer if you have specialist knowlege and annotate in Inferences if doubtfull, border change, or not obvious."; }
            if (fieldname.equalsIgnoreCase("SpecificLocality")) { help="Placenames, offsets, and other text describing where this specimen was collected."; }
            if (fieldname.equalsIgnoreCase("VerbatimLocality")) { help="Verbatim transcription of locality information found on this specimen's labels.  Separate lines with a semicolon."; }
            if (fieldname.equalsIgnoreCase("VerbatimCollector")) { help="Verbatim transcription of collector information found on this specimen's labels.  Separate collectors with a |  (pipe/vertical bar)."; }
            if (fieldname.equalsIgnoreCase("VerbatimCollection")) { help="Verbatim transcription of previous collection (e.g. Mattoni Collection) information found on this specimen's labels."; }
            if (fieldname.equalsIgnoreCase("VerbatimNumbers")) { help="Verbatim transcription of numbers found on this specimen's labels.  Separate numbers with a |  (pipe/vertical bar)."; }
            if (fieldname.equalsIgnoreCase("VerbatimUnclassifiedText")) { help="Verbatim transcription of arbitrary text found on this specimen's labels.  Separate labels with a |  (pipe/vertical bar)."; }
            // if (fieldname.equalsIgnoreCase("VerbatimElevation")) { help="Verbatim transcription of elevation information, including units, found on this specimen's labels"; }
            if (fieldname.equalsIgnoreCase("Minumum_Elevation")) { help="Minimum elevation in units provided (m or ft or unknown), found on this specimen's labels"; }
            if (fieldname.equalsIgnoreCase("Maximum_Elevation")) { help="Maximum elevation in units provided, found on this specimen's labels.  Same value as minimum elevation if only one number is given."; }
            if (fieldname.equalsIgnoreCase("Elev_Units")) { help="Units for the minimum/maximum elevation.  Meters, Feet, or unknown."; }
            if (fieldname.equalsIgnoreCase("CollectingMethod")) { help="If specified on a label, the method by which this specimen was collected."; }
            if (fieldname.equalsIgnoreCase("DateNOS")) { help="The default date field, a verbatim date associated with this specimen that isn't marked as either a date collected or date emerged, and might be either of these or some other date."; }
            if (fieldname.equalsIgnoreCase("ISODate")) { help="The date collected or the default date in ISO date format yyyy/mm/dd.  Optionally, a range in the form yyyy/mm/dd-yyyy/mm/dd.  Use zeroes as place holders in a date range where day or month is uncertain, e.g. 1882/00/00-1883 for 1882-1883"; }
            if (fieldname.equalsIgnoreCase("DateEmerged")) { help="The date at which this butterfly emerged, if indicated on a label."; }
            if (fieldname.equalsIgnoreCase("DateEmergedIndicator")) { help="The verbatim text from the label that indicates that this is a date emerged, e.g. 'ex pupa'."; }
            if (fieldname.equalsIgnoreCase("DateCollected")) { help="The date at which this butterfly was collected from the wild."; }
            if (fieldname.equalsIgnoreCase("DateCollectedIndicator")) { help="The verbatim text from the label that indicates that this is a date collected."; }
            if (fieldname.equalsIgnoreCase("Collection")) { help="The name of a collection of which this specimen was a part.  Use a standard format rather than verbatim text.  Often found on a separate label with the word 'collection'." ; }
            if (fieldname.equalsIgnoreCase("SpecimenNotes")) { help="Notes from the labels about this specimen.  Use a semicolon ; to separate multiple notes."; }
            if (fieldname.equalsIgnoreCase("LifeStage")) { help="The life stage of this specimen."; }
            if (fieldname.equalsIgnoreCase("Sex")) { help="The sex of this specimen. If known or indicated on label"; }
            if (fieldname.equalsIgnoreCase("PreparationType")) { help="The preparation type of this specimen.  Most specimens are Pinned.  Other preparation types include Genitalia, Pinned ventral up, ."; }
            if (fieldname.equalsIgnoreCase("Habitat")) { help="Text from the labels descrbing the habitat from which this specimen was collected."; }
            if (fieldname.equalsIgnoreCase("Microhabitat")) { help="Text from the labels descrbing the fine scale microhabitat from which this specimen was collected."; }
            if (fieldname.equalsIgnoreCase("AssociatedTaxon")) { help="If this specimen represents an associated taxon such as a attendant ant, put the actual identification of this specimen, to whatever level it is available here (e.g. 'ant'), and put the name of the butterfly from the unit tray label in the identification (i.e. genus, species, etc. fields)."; }
            if (fieldname.equalsIgnoreCase("Questions")) { help="Describe any questions or problems you have with the transcription of the data from this specimen."; }
            if (fieldname.equalsIgnoreCase("Inferences")) { help="If you have specialist knowledge about this specimen, briefly describe the basis for any inferences you are making in adding data to this record that isn't present on the specimen labels.  Example: 'Locality for this species known to be in Australia, pers obs.'"; }
            if (fieldname.equalsIgnoreCase("LocationInCollection")) { help="General Collection, Type Collection, or other major storage division of the Lepidoptera collection."; }
            if (fieldname.equalsIgnoreCase("WorkFlowStatus")) { help="The current state of this database record in the workflow."; }
            if (fieldname.equalsIgnoreCase("CreatedBy")) { help="The name of the person or automated process that created this record."; }
            if (fieldname.equalsIgnoreCase("DateLastUpdated")) { help="The date and time at which this record was most recently updated."; }
            if (fieldname.equalsIgnoreCase("LastUpdatedBy")){ help="The name of the person who most recenly updated this record.";  }
            if (fieldname.equalsIgnoreCase("ValidDistributionFlag")) {  help = "Uncheck if the locality does not reflect the collection of this specimen from nature (e.g. uncheck for specimens that came from a captive breeding program).  Leave checked if locality represents natural biological range. "; }
		}
		if (aTableClass==OtherNumbers.class) { 
			if (fieldname.equalsIgnoreCase("OtherNumber")) { help="A number (including alphanumeric identifiers) found on a label of this specimen. Are there any numbers, of known or unknown nature on the label or on separate labels? "; }
			if (fieldname.equalsIgnoreCase("NumberType")) { help="If known, what sort of number this is and where it came from."; }	
		}
		if (aTableClass==Collector.class) { 
			if (fieldname.equalsIgnoreCase("CollectorName")) { help="The name of a collector.  Usually found on a data label.  May be indicated by 'Leg.'"; }
		}
		if (aTableClass==Determination.class) { 
			if (fieldname.equalsIgnoreCase("TypeStatus")) { help="Not a type, or the kind of type (e.g. Topotype) that this specimen is for this particular name."; }
			if (fieldname.equalsIgnoreCase("Genus")) { help="The generic name used in the identification."; }
            if (fieldname.equalsIgnoreCase("SpecificEpithet")) { help="The specific part of the species group name used in the identification."; }
            if (fieldname.equalsIgnoreCase("SubspecificEpithet")) { help="The subspecific part (if present) of the species group name used in the identification."; }
            if (fieldname.equalsIgnoreCase("InfraspecificEpithet")) { help="The form, varietal, or other part of a name with a rank below subspecies used in the identification."; }
            if (fieldname.equalsIgnoreCase("InfraspecificRank")) { help="The rank (e.g. form, variety, abberation, morph, lusus, natio) of the infrasubspecific name."; }
            if (fieldname.equalsIgnoreCase("Authorship")) { help="The author of the species group name used in the determination."; }
            if (fieldname.equalsIgnoreCase("UnNamedForm")) { help="e.g. 'Winter form', informal descriptive name of the form of this specimen (not regulated by the ICZN)."; }
            if (fieldname.equalsIgnoreCase("IdentificationQualifier")) { help="A question mark or other qualifier that indicates the identification of this specimen is uncertain."; }
            if (fieldname.equalsIgnoreCase("IdentifiedBy")) { help="Name of the person, if known, who made this identification."; }		
            if (fieldname.equalsIgnoreCase("SpeciesNumber")) { help="A species number associated with (that forms part of) this identification."; }
            if (fieldname.equalsIgnoreCase("VerbatimText")) { help="The verbatim text of the identification found on the label.  Use this field for a bare epithet or an ambiguous abbreviated generic name followed by a specific epithet.  Use any time that you can't cleanly parse the identification into separate name fields.  Do not fill in both Verbatim and Genus/Species/Subspecies/Author fields."; }
            if (fieldname.equalsIgnoreCase("IdentificationDate")) { help="The date on which the identification was made, in the form YYYY/MM/DD"; }
            if (fieldname.equalsIgnoreCase("NatureOfID")) { help="The nature of the identification, use expert ID when determiner name is on label, otherwise use legacy."; }
            if (fieldname.equalsIgnoreCase("Remarks")) { help="Remarks concerning this identification"; }
		}
		if (aTableClass==Users.class) { 
			if (fieldname.equalsIgnoreCase("username")) { help="Database username of this person."; }	
			if (fieldname.equalsIgnoreCase("fullname")) { help="The full name of this person."; }	
			if (fieldname.equalsIgnoreCase("description")) { help="What this person's role in the project and specialties are."; }	
			if (fieldname.equalsIgnoreCase("role")) { help="Unused"; }	
		}
		if (aTableClass==Label.class) {
			if (fieldname.equalsIgnoreCase("VerbatimText")) { help="A verbatim transcription of the text present on this label."; }
			if (fieldname.equalsIgnoreCase("Intepretation")) { help="As detailed an interpretation as you can provide of this label and it's text.  Indicate concepts including date collected and collector explicitly, with the expectation that the reader will have no knowlege of entomological labels."; }
		}
		if (aTableClass==SpecimenPart.class) { 
			if (fieldname.equalsIgnoreCase("Part_Name")) { help="The part type (e.g. whole animal) for this specimen."; }	
			if (fieldname.equalsIgnoreCase("Preserve_Method")) { help="The preservation method for this specimen."; }	
			if (fieldname.equalsIgnoreCase("Lot_Count")) { help="Number of specimens with this part name and preservation method."; }	
			if (fieldname.equalsIgnoreCase("Lot_Count_Modifier")) { help="Modifier for estimated lot counts.  Leave blank if lot count is exact."; }	
		}
		if (aTableClass==SpecimenPartAttribute.class) { 
			if (fieldname.equalsIgnoreCase("Attribute_Name")) { help="The type of attribute."; }	
			if (fieldname.equalsIgnoreCase("Attribute_Value")) { help="The value for this attribute."; }	
			if (fieldname.equalsIgnoreCase("Attribute_Units")) { help="Units, if any, for the remark, leave blank if inapplicable."; }	
			if (fieldname.equalsIgnoreCase("Attribute_Remark")) { help="Remarks about the attribute."; }	
			if (fieldname.equalsIgnoreCase("Attribute_Determiner")) { help="Person who determined the value."; }	
		}
		if (aTableClass==MCZbaseGeogAuthRec.class) { 
			if (fieldname.equalsIgnoreCase("Higher_Geog")) { help="Higher geography (continent/ocean, country, state/province, etc) from geography authority file."; }
		}
		if (aTableClass==LatLong.class) { 
			if (fieldname.equalsIgnoreCase("decLat")) { help = "Latitude in Decimal Degrees."; }
			if (fieldname.equalsIgnoreCase("decLong")) { help= "Longitude in Decimal Degrees"; }
			if (fieldname.equalsIgnoreCase("datum")) {  help = "Geodetic Datum, if known, otherwise 'unknown'"; }
			if (fieldname.equalsIgnoreCase("geoRefMethod")) { help="Method by which the georeference was made"; }
			if (fieldname.equalsIgnoreCase("origLatLongUnits")) { help="Original form of the Georeference"; }
		}
		
		return help;
	}
	
	/**
	 * Test to see whether a field allowed to be updated by an external process. 
	 * 
	 * @param aTableClass the class for the table in which the field occurs.
	 * @param fieldname the name of the field (case insensitive).
	 * 
	 * @return true if the field is allowed to be updated by an external process, false otherwise.
	 */
	public static boolean isFieldExternallyUpdatable(Class aTableClass, String fieldname) {
		boolean result = false;
		if (aTableClass==Specimen.class) { 
            if (fieldname.equalsIgnoreCase("TypeStatus")) { result=true; }
            if (fieldname.equalsIgnoreCase("TypeNumber")) { result=true; }
            if (fieldname.equalsIgnoreCase("CitedInPublication")) { result=true; }
            if (fieldname.equalsIgnoreCase("Features")) { result=true; }
			if (fieldname.equalsIgnoreCase("Higher_Geography")) { result = true; }
            if (fieldname.equalsIgnoreCase("SpecificLocality")) { result=true; }
            if (fieldname.equalsIgnoreCase("VerbatimLocality")) { result=true; }
            if (fieldname.equalsIgnoreCase("VerbatimCollector")) { result=true; }
            if (fieldname.equalsIgnoreCase("VerbatimCollection")) { result=true; }
            if (fieldname.equalsIgnoreCase("VerbatimNumbers")) { result=true; }
            if (fieldname.equalsIgnoreCase("VerbatimUnclassifiedText")) { result=true; }
            if (fieldname.equalsIgnoreCase("Minimum_Elevation")) { result=true; }
            if (fieldname.equalsIgnoreCase("Maximum_Elevation")) { result=true; }
            if (fieldname.equalsIgnoreCase("Elev_Units")) { result=true; }
            if (fieldname.equalsIgnoreCase("CollectingMethod")) { result=true; }
            if (fieldname.equalsIgnoreCase("ISODate")) { result=true; }
            if (fieldname.equalsIgnoreCase("DateNOS")) { result=true; }
            if (fieldname.equalsIgnoreCase("DateEmerged")) { result=true; }
            if (fieldname.equalsIgnoreCase("DateEmergedIndicator")) { result=true; }
            if (fieldname.equalsIgnoreCase("DateCollected")) { result=true; }
            if (fieldname.equalsIgnoreCase("DateCollectedIndicator")) { result=true; }
            if (fieldname.equalsIgnoreCase("Collection")) { result=true ; }
            if (fieldname.equalsIgnoreCase("SpecimenNotes")) { result=true; }
            if (fieldname.equalsIgnoreCase("LifeStage")) { result=true; }
            if (fieldname.equalsIgnoreCase("Sex")) { result=true; }
            if (fieldname.equalsIgnoreCase("PreparationType")) { result=true; }
            if (fieldname.equalsIgnoreCase("Habitat")) { result=true; }
            if (fieldname.equalsIgnoreCase("Microhabitat")) { result=true; }
            if (fieldname.equalsIgnoreCase("AssociatedTaxon")) { result=true; }
            if (fieldname.equalsIgnoreCase("Questions")) { result=true; }
            if (fieldname.equalsIgnoreCase("Inferences")) { result=true; }
            if (fieldname.equalsIgnoreCase("LocationInCollection")) { result=true; }
            if (fieldname.equalsIgnoreCase("ValidDistributionFlag")) {  result = true; }
		}
		if (aTableClass==Collector.class) { 
			if (fieldname.equalsIgnoreCase("CollectorName")) { result=true; }	
		}
		if (aTableClass==Determination.class) { 
            if (fieldname.equalsIgnoreCase("VerbatimText")) { result=true; }
		}
		if (aTableClass==OtherNumbers.class) { 
            if (fieldname.equalsIgnoreCase("Number")) { result=true; }
            if (fieldname.equalsIgnoreCase("NumberType")) { result=true; }
		}
		
		return result;
	}	
	
	/**
	 * Test to see whether a field in a table is intended to hold verbatim values.
	 * 
	 * @param aTableClass the class for the table.
	 * @param fieldname the field to check (not case sensitive)
	 * @return true if the field is intended to hold verbatim data, false otherwise.
	 */
	public static boolean isFieldVerbatim(Class aTableClass, String fieldname) {
		boolean result = false;
		if (aTableClass==Specimen.class) { 
            if (fieldname.equalsIgnoreCase("VerbatimLocality")) { result=true; }
            if (fieldname.equalsIgnoreCase("VerbatimCollector")) { result=true; }
            if (fieldname.equalsIgnoreCase("VerbatimCollection")) { result=true; }
            if (fieldname.equalsIgnoreCase("VerbatimNumbers")) { result=true; }
            if (fieldname.equalsIgnoreCase("DateNOS")) { result=true; }
            if (fieldname.equalsIgnoreCase("VerbatimUnclassifiedText")) { result=true; }
		}
		if (aTableClass==Collector.class) { 
		}
		if (aTableClass==Determination.class) { 
            if (fieldname.equalsIgnoreCase("VerbatimText")) { result=true; }
		}
		if (aTableClass==OtherNumbers.class) { 
		}
		
		return result;
	}		
	
}
