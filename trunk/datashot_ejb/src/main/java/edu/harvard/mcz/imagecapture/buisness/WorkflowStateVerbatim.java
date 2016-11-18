/** WorkflowStateVerbatim.java 
 * 
 * edu.harvard.mcz.imagecapture.buisness
 * Copyright © 2016 President and Fellows of Harvard College
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of Version 2 of the GNU General Public License
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Author: Paul J. Morris
 */
package edu.harvard.mcz.imagecapture.buisness;

import edu.harvard.mcz.imagecapture.data.Users;
import edu.harvard.mcz.imagecapture.data.WorkFlowStatus;
import edu.harvard.mcz.imagecapture.exceptions.InvalidStateException;
import edu.harvard.mcz.imagecapture.exceptions.NotAllowedStateException;

/**
 * State machine subclass for workflow status in current state Verbatim.
 *
 * @author mole
 */
public class WorkflowStateVerbatim extends WorkflowState {

		@Override
		public WorkflowState changeStateTo(String newState, Users aUser) throws InvalidStateException, NotAllowedStateException {
				isStateValid(newState);
				WorkflowState result = this;

				if (newState.equals(WorkFlowStatus.STAGE_1) ) {
					// Anyone can change state to Stage 1
					result = new WorkflowStateTaxonEntered();
				}
			    if (newState.equals(WorkFlowStatus.STAGE_CLASSIFIED) ) {
					// Anyone can change state to Stage classified
					result = new WorkflowStateTextEntered();
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
				if (newState.equals(WorkFlowStatus.STAGE_0)) {
					throw new NotAllowedStateException("A record can't be returned from state " + this.getState() + " to state " + newState);
				}				

	            return result;
		}

		@Override
		public String getState() {
				return WorkFlowStatus.STAGE_VERBATIM;
		}
}