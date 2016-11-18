/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.buisness;

import edu.harvard.mcz.imagecapture.data.Users;
import edu.harvard.mcz.imagecapture.data.WorkFlowStatus;
import edu.harvard.mcz.imagecapture.exceptions.InvalidStateException;
import edu.harvard.mcz.imagecapture.exceptions.NotAllowedStateException;

/**
 *
 * @author mole
 */
public class WorkflowStateQCProblems extends WorkflowState {

		@Override
		public WorkflowState changeStateTo(String newState, Users aUser) throws InvalidStateException, NotAllowedStateException {
				this.isStateValid(newState);
				WorkflowState result = this;
                if (newState.equals(WorkFlowStatus.STAGE_0)) {
						throw new NotAllowedStateException("Can't revert a record to state " + WorkFlowStatus.STAGE_0 + " from state " + this.getState());
				}
                if (newState.equals(WorkFlowStatus.STAGE_VERBATIM)) {
						throw new NotAllowedStateException("Can't revert a record to state " + WorkFlowStatus.STAGE_VERBATIM + " from state " + this.getState());
				}	
                if (newState.equals(WorkFlowStatus.STAGE_CLASSIFIED)) {
						throw new NotAllowedStateException("Can't revert a record to state " + WorkFlowStatus.STAGE_CLASSIFIED + " from state " + this.getState());
				}                
				if (newState.equals(WorkFlowStatus.STAGE_1) ) {
						if (aUser.isUserRole(Users.ROLE_FULL)) {
                                result = new WorkflowStateTaxonEntered();
						} else {
								throw new NotAllowedStateException("A user in role " + aUser.getRole() + " isn't able to bring a record out of state QC Problems. " );
						}
				}
			    if (newState.equals(WorkFlowStatus.STAGE_2) ) {
						if (aUser.isUserRole(Users.ROLE_FULL)) {
                                result = new WorkflowStateTextEntered();
						} else {
								throw new NotAllowedStateException("A user in role " + aUser.getRole() + " isn't able to bring a record out of state QC Problems. " );
						}
				}
			    if (newState.equals(WorkFlowStatus.STAGE_QC_FAIL)) {
						// anyone can leave this record in this state.
				}
				if (newState.equals(WorkFlowStatus.STAGE_QC_PASS)) {
						if (aUser.isUserRole(Users.ROLE_FULL)) {
                                result = new WorkflowStateQCReviewed();
						} else {
								throw new NotAllowedStateException("A user in role " + aUser.getRole() + " isn't able to bring a record to the state " + newState);
						}
				}
				if (newState.equals(WorkFlowStatus.STAGE_CLEAN)) {
						if (aUser.isUserRole(Users.ROLE_EDITOR)) {
                                result = new WorkflowStateQCReviewed();
						} else {
								throw new NotAllowedStateException("A user in role " + aUser.getRole() + " isn't able to bring a record to the state " + newState);
						}
				}
				if (newState.equals(WorkFlowStatus.STAGE_DONE)) {
					throw new NotAllowedStateException("A record can't be brought directly from state " + this.getState() + " to state " + newState);
				}

	            return result;
		}

		@Override
		public String getState() {
				return WorkFlowStatus.STAGE_QC_FAIL;
		}

}
