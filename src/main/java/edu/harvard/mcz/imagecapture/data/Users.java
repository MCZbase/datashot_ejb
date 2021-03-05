/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.harvard.mcz.imagecapture.data;

import edu.harvard.mcz.imagecapture.exceptions.PasswordStrengthException;
import edu.harvard.mcz.imagecapture.utility.AuthenticationUtility;

import java.io.Serializable;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

/**
 *
 * @author mole
 */
@Entity
@Table(name = "Users", catalog = "lepidoptera", schema = "", uniqueConstraints = {
	@UniqueConstraint(columnNames = {"username"})})
@NamedQueries({
	@NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
	@NamedQuery(name = "Users.findByUserid", query = "SELECT u FROM Users u WHERE u.userid = :userid"),
	@NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
	@NamedQuery(name = "Users.findByFullname", query = "SELECT u FROM Users u WHERE u.fullname = :fullname"),
	@NamedQuery(name = "Users.findByDescription", query = "SELECT u FROM Users u WHERE u.description = :description"),
	@NamedQuery(name = "Users.findByRole", query = "SELECT u FROM Users u WHERE u.role = :role"),
	@NamedQuery(name = "Users.findByHash", query = "SELECT u FROM Users u WHERE u.hash = :hash")})
@NamedQuery(name = "Users.authenticate", query = "SELECT u FROM Users u WHERE u.username = :username and u.hash = :passwordhash")
public class Users implements Serializable {

	private static final long serialVersionUID = -5258394845638721810L;
	/**
	 * All rights, including editing/creating users (equivalent to DBA)
	 */
	public static final String ROLE_ADMINISTRATOR = "Administrator";

	/*
	 * Specialist able to mark records as specialist reviewed, plus able to
	 * create/edit/promote/demote users.
	 *
	 */
	public static final String ROLE_CHIEF_EDITOR = "Chief Editor";
	/**
	 * Specialist able to mark records as specialist reviewed.
	 */
	public static final String ROLE_EDITOR = "Editor";
	/**
	 * All of data entry rights, plus quality control and preprocessing.
	 */
	public static final String ROLE_FULL = "Full access";
	/**
	 * Search/Browse/Edit specimen records only.
	 */
	public static final String ROLE_DATAENTRY = "Data entry";

	public static final String[] ROLES = {ROLE_ADMINISTRATOR, ROLE_CHIEF_EDITOR, ROLE_EDITOR, ROLE_FULL, ROLE_DATAENTRY};
	@Id
	@GeneratedValue(generator = "UsersSeq")
	@SequenceGenerator(name = "UsersSeq", sequenceName = "SEQ_USERID", allocationSize = 1)
	@Basic(optional = false)
	@Column(name = "userid", nullable = false)
	private Integer userid;
	@Basic(optional = false)
	@Column(name = "username", nullable = false, length = 50)
	private String username;
	@Basic(optional = false)
	@Column(name = "fullname", nullable = false, length = 50)
	private String fullname;
	@Column(name = "description", length = 255)
	private String description;
	@Basic(optional = false)
	@Column(name = "role", nullable = false, length = 20)
	private String role;
	@Column(name = "hash", length = 41)
	private String hash;

	/*
	 * Temporary copy of new password to be stored as hash.
	 */
	@Transient
	private String password;
	
	public Users() {
	}

	public Users(Integer userid) {
		this.userid = userid;
	}

	public Users(Integer userid, String username, String fullname, String role) {
		this.userid = userid;
		this.username = username;
		this.fullname = fullname;
		this.role = role;
	}

	public Users(String username, String fullname, String role) {
		this.username = username;
		this.fullname = fullname;
		this.role = role;
	}

	public Users(String username, String fullname, String description,
			String role) {
		this.username = username;
		this.fullname = fullname;
		this.description = description;
		this.role = role;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
	
	/**
	 * Take the value of the new password, and if it is not null, not empty, 
	 * and passes the strength rules, then save it.
	 * 
	 * @return true if password hash was updated, false if password was empty and it was not.
	 * @throws PasswordStrengthException if password is non-empty if strength rules are not met.
	 */
	public boolean updateHashFromNewPassword() throws PasswordStrengthException { 
		boolean result = false;
		// if password is null, do nothing.
		if (password != null ) {
			// if password is an empty string, do nothing
			if (!password.trim().isEmpty()) {
				// test if password is strong enough
				if (AuthenticationUtility.isPasswordStrongEnough(password)) {
					this.hash = AuthenticationUtility.hashPassword(password);
					result = true;
				} else {
					throw new PasswordStrengthException("Password is not strong enough");
				}
			}
		}	
		return result;
	}

	public void setNewPassword(String password)  {
       this.password = password;
	}
	

	/** Dummy method to allow JSF to bind to get/set newPassword,
	 *  fills a set new password control with an empty string.
	 *
	 * @return an empty string
	 */
	public String getNewPassword() {
		return "";
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (userid != null ? userid.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Users)) {
			return false;
		}
		Users other = (Users) object;
		if ((this.userid == null && other.userid != null) || (this.userid != null && !this.userid.equals(other.userid))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "edu.harvard.mcz.imagecapture.data.Users1[userid=" + userid + "]";
	}

	/**
	 * Test whether a user has rights under a role.  Use UsersLifeCycle.isUserAdministrator() to test
	 * if a user has administrative rights instead of this method, as it validates the rights against
	 * an underlying Users database record, while this method only validates against an instance of
	 * the Users class.
	 *
	 * @see edu.harvard.mcz.imagecapture.data.UsersLifeCycle#isUserAdministrator
	 * @param aUserRole the role to test, one of ROLE_DATAENTRY, ROLE_FULL, or ROLE_ADMINISTRATOR.
	 * @return true if this User has rights under aUserRole.
	 */
	public boolean isUserRole(String aUserRole) {
		boolean result = false;
		if (this.role.equals(aUserRole)) {
			// If equals, then user has this role.
			result = true;
		} else {
			// Check more inclusive roles.
			if (this.role.equals(Users.ROLE_ADMINISTRATOR)) {
				// Administrator can do anything.
				result = true;
			}
			if (this.role.equals(Users.ROLE_CHIEF_EDITOR) && (aUserRole.equals(Users.ROLE_DATAENTRY) || aUserRole.equals(Users.ROLE_FULL) || aUserRole.equals(Users.ROLE_EDITOR))) {
				// Role chief editor includes roles full access, editor and data entry.
				result = true;
			}
			if (this.role.equals(Users.ROLE_EDITOR) && (aUserRole.equals(Users.ROLE_DATAENTRY) || aUserRole.equals(Users.ROLE_FULL))) {
				// Role editor  includes roles full and data entry.
				result = true;
			}
			if (this.role.equals(Users.ROLE_FULL) && aUserRole.equals(Users.ROLE_DATAENTRY)) {
				// Role full includes role data entry.
				result = true;
			}
		}
		return result;
	}
}
