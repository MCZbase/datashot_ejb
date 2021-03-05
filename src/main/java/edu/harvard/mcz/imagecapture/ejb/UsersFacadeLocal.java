/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Users;

import java.util.List;
import java.util.Map;

import jakarta.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface UsersFacadeLocal {

	void create(Users users);

	void edit(Users users);

	void remove(Users users);

	Users find(Object id);

	List<Users> findAll();

	List<Users> findRange(int[] range);
	
	List<Users> findRangeQuery(int[] range, String[] sortFields, boolean sortOrder, Map<String, String> filters);

	int countFiltered(Map<String, String> filters);
	
	int count();

	List<Users> authenticate(String username, String password);

	Users findByName(String username);

	List<String> getUserRoleValues();


}
