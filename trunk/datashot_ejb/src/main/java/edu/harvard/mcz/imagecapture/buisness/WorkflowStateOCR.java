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
public class WorkflowStateOCR extends WorkflowState {

		@Override
		public WorkflowState changeStateTo(String newState, Users aUser) throws InvalidStateException, NotAllowedStateException {
				isStateValid(newState);
				WorkflowState result = this;

				if (newState.equals(WorkFlowStatus.STAGE_1) ) {
					// Anyone can change state to Stage 1
					result = new WorkflowStateTaxonEntered();
				}
			    if (newState.equals(WorkFlowStatus.STAGE_2) ) {
					// Anyone can change state to Stage 2
					result = new WorkflowStateTextEntered();
				}
			    if (newState.equals(WorkFlowStatus.STAGE_QC_FAIL)) {
					// Anyone can change state to flag QC problems
					result = new WorkflowStateQCProblems();
				}
				if (newState.equals(WorkFlowStatus.STAGE_QC_PASS)) {
					throw new NotAllowedStateException("A record can't be brought directly from state " + this.getState() + " to state " + newState);
				}
				if (newState.equals(WorkFlowStatus.STAGE_CLEAN)) {
					throw new NotAllowedStateException("A record can't be brought directly from state " + this.getState() + " to state " + newState);
				}
				if (newState.equals(WorkFlowStatus.STAGE_DONE)) {
					throw new NotAllowedStateException("A record can't be brought directly from state " + this.getState() + " to state " + newState);
				}

	            return result;
		}

		@Override
		public String getState() {
				return WorkFlowStatus.STAGE_0;
		}
}
