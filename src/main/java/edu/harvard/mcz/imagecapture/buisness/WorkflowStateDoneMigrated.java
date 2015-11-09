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
public class WorkflowStateDoneMigrated extends WorkflowState {

		@Override
		public WorkflowState changeStateTo(String newState, Users aUser) throws InvalidStateException, NotAllowedStateException {
				this.isStateValid(newState);
				WorkflowState result = this;
                if (newState.equals(WorkFlowStatus.STAGE_0)) {
						throw new NotAllowedStateException("Can't revert a record to state " + WorkFlowStatus.STAGE_0 + " from state " + this.getState());
				}
				if (newState.equals(WorkFlowStatus.STAGE_1) ) {
						throw new NotAllowedStateException("Can't revert a record to state " + WorkFlowStatus.STAGE_1 + " from state " + this.getState());
				}
			    if (newState.equals(WorkFlowStatus.STAGE_2) ) {
						throw new NotAllowedStateException("Can't revert a record to state " + WorkFlowStatus.STAGE_2 + " from state " + this.getState());
				}
			    if (newState.equals(WorkFlowStatus.STAGE_QC_FAIL)) {
						throw new NotAllowedStateException("Can't revert a record to state " + WorkFlowStatus.STAGE_QC_FAIL + " from state " + this.getState());
				}
				if (newState.equals(WorkFlowStatus.STAGE_QC_PASS)) {
						throw new NotAllowedStateException("Can't revert a record to state " + WorkFlowStatus.STAGE_QC_FAIL + " from state " + this.getState());
				}
				if (newState.equals(WorkFlowStatus.STAGE_CLEAN)) {
						throw new NotAllowedStateException("Can't revert a record to state " + WorkFlowStatus.STAGE_CLEAN + " from state " + this.getState());
				}
				if (newState.equals(WorkFlowStatus.STAGE_DONE)) {
						// anyone can leave this record in this state.
				}

	            return result;
		}

		@Override
		public String getState() {
				return WorkFlowStatus.STAGE_CLEAN;
		}

}