/**
 * 
 */
package edu.harvard.mcz.imagecapture.data;

/**Authority list for values of Specimen.workFlowStatus.
 * 
 * @author mole
 *
 */
public class WorkFlowStatus {
	
	/**
	 * Specimen record created from barcode, with current determination, higher taxon, and drawer number
	 * populated by OCR and parsing of unit tray label.   Set to this level by software agent.
	 */
	public static final String STAGE_0 = "OCR";
	/**
	 * Specimen record with clean with current determination, higher taxon, and drawer number, either after human
	 * cleanup of OCR, or when record was populated from taxon label data encoded in a barcode on the unit tray label.
	 * Set to this level by any user of data entry or higher.
	 */
	public static final String STAGE_1 = "Taxon Entered";
	/**
	 * Specimen record where the text from the pin labels has been transcribed into verbatim database fields
	 * by a data entry person (or in an external transcription process).  Data transcribed into verbatim fields, 
	 * but still needs to be interpreted further into atomic fields.
	 */
	public static final String STAGE_VERBATIM = "Verbatim Entered";
	/**
	 * Specimen record where the text from the pin labels has been transcribed into verbatim database fields
	 * by a data entry person (or in an external transcription process) and this verbatim data has been 
	 * further interpreted into atomic fields, but all data may not yet have been entered from the image.
	 */	
	public static final String STAGE_CLASSIFIED = "Verbatim Classified";	
	/**
	 * Specimen record where the text from the pin labels has been transcribed into database fields by a data
	 * entry person.   Set to this level by any user of data entry or higher
	 */
	public static final String STAGE_2 = "Text Entered";
	/**
	 * State change for a specimen record where a human or an automated process has identified a 
	 * quality control issue with the record.  Set to this level by any user of data entry or higher
	 */
	public static final String STAGE_QC_FAIL = "QC Problems";
	/**
	 * State change for a specimen record on review after text entry by a person other than the data entry person who did 
	 * the text entry.  Set to this level by any user of full access or higher.
	 */
	public static final String STAGE_QC_PASS = "QC Reviewed";
	/**
	 * State change for a specimen record indicating that the specimen record that has been reviewed by a taxonomist.
	 * Set to this level by any user of editor or higher.
	 */
	public static final String STAGE_CLEAN = "Specialist Reviewed";

	public static final String STAGE_DONE = "Moved to MCZbase";

	private static final String[] CHANGABLE_VALUES = {STAGE_0, STAGE_1,  STAGE_VERBATIM, STAGE_CLASSIFIED, STAGE_2,STAGE_QC_FAIL,STAGE_QC_PASS,STAGE_CLEAN};
	private static final String[] VALUES = {STAGE_0, STAGE_1, STAGE_VERBATIM, STAGE_CLASSIFIED, STAGE_2,STAGE_QC_FAIL,STAGE_QC_PASS,STAGE_CLEAN,STAGE_DONE};
	
	public static String[] getChangableWorkFlowStatusValues() {
		String[] result = VALUES;  
		return result;
	}
	public static String[] getAllWorkFlowStatusValues() {
		String[] result = VALUES;
		return result;
	}
	
	public static boolean allowsVerbatimUpdate(String workflowStatus) { 
		boolean result = false;
		
		if (workflowStatus.equals(STAGE_0)) { result = true; } 
		if (workflowStatus.equals(STAGE_1)) { result = true; } 
		
		return result;
	}	

}
