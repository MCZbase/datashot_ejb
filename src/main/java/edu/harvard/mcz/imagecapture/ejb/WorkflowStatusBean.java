/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.ejb;

import edu.harvard.mcz.imagecapture.buisness.WorkflowState;
import edu.harvard.mcz.imagecapture.data.Users;
import edu.harvard.mcz.imagecapture.data.WorkFlowStatus;
import java.util.Iterator;
import java.util.List;
import jakarta.ejb.Stateless;

/**
 *
 * @author mole
 */
@Stateless
public class WorkflowStatusBean implements WorkflowStatusBeanLocal {
    
		public String[] getWorkFlowStatusValues() {
				return WorkFlowStatus.getChangableWorkFlowStatusValues();
		}
		public String[] getWorkFlowStatusValuesForUser(Users aUser) {
				List<String> values = WorkflowState.getAvailableStatesForUser(aUser);
				Iterator<String> i = values.iterator();
				String[] result = new String[values.size()];
				int x = 0;
				while (i.hasNext()) {
						result[x] = i.next();
						x++;
				}
				return result;
		}
 
}
