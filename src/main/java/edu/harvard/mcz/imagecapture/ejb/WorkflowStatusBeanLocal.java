/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.data.Users;
import jakarta.ejb.Local;

/**
 *
 * @author mole
 */
@Local
public interface WorkflowStatusBeanLocal {

		String[] getWorkFlowStatusValues();

		public String[] getWorkFlowStatusValuesForUser(Users aUser);
    
}
