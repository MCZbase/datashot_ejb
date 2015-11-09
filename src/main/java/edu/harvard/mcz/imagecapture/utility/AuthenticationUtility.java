/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.utility;

import javax.swing.JPasswordField;

/**
 *
 * @author mole
 */
public class AuthenticationUtility {

	/** Construct a password hash from a JPasswordField.
	 * 
	 * @param ajPasswordField a password
	 * @return hash of the password
	 */
    public static String hashPassword(JPasswordField ajPasswordField) {
        return getHash(String.valueOf(ajPasswordField.getPassword()));
    }

	/** Construct a password hash from a String
	 *  TODO: Change to a char[].
	 *
	 * @param aPassword a password
	 * @return hash of the password
	 */
    public static String hashPassword(String aPassword) {
		return getHash(aPassword);
	}

    /** Specific hash function to use to construct password hash.
	 *
	 * @param ofString
	 * @return hash of the password
	 */
	private static String getHash(String ofString) {
		return HashUtility.getSHA1Hash(ofString);
	}

	public static String getPasswordStrengthRules() {
	    return "Password must be at least 11 characters, contain at least one number, at least one upper case letter, and at least one lowercase letter. ";
	}

    public static boolean isPasswordStrongEnough(String aPassword) {
	    boolean result = false;
		if (aPassword!=null) {
            if (aPassword.length()>10) {
				if (aPassword.matches(".*[0-9].*")) {
				    if (aPassword.matches(".*[A-Z].*")) {
				        if (aPassword.matches(".*[a-z].*")) {
				            result = true;
						}
					}
				}
			}
	    }
		return result;
	}

}
