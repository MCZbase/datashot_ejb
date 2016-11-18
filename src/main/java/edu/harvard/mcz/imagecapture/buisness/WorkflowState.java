/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.buisness;

import edu.harvard.mcz.imagecapture.data.Users;
import edu.harvard.mcz.imagecapture.data.WorkFlowStatus;
import edu.harvard.mcz.imagecapture.exceptions.InvalidStateException;
import edu.harvard.mcz.imagecapture.exceptions.NotAllowedStateException;
import java.util.ArrayList;
import java.util.List;

/**
 * State machine for workflow.  Text labels come from authority file proxy object WorkFlowStatus.
 * 
 * States extend WorkFlowState and have concrete implementations of the abstract methods
 * changeStateTo() and getState().
 *
 * @see edu.harvard.mcz.imagecapture.data.WorkFlowStatus
 * @author mole
 */
public abstract class WorkflowState {

	 public static WorkflowState createWorkflowState(String atState) throws InvalidStateException {
			 isStateValid(atState);
			 WorkflowState result = null;
             if (atState.equals(WorkFlowStatus.STAGE_0)) {
					 result = new WorkflowStateOCR();
			 }
             if (atState.equals(WorkFlowStatus.STAGE_1)) {
					 result = new WorkflowStateTaxonEntered();
			 }
             if (atState.equals(WorkFlowStatus.STAGE_VERBATIM)) {
					 result = new WorkflowStateTaxonEntered();
			 }
             if (atState.equals(WorkFlowStatus.STAGE_CLASSIFIED)) {
					 result = new WorkflowStateTaxonEntered();
			 }             
             if (atState.equals(WorkFlowStatus.STAGE_2)) {
					 result = new WorkflowStateTextEntered();
			 }
             if (atState.equals(WorkFlowStatus.STAGE_QC_FAIL)) {
					 result = new WorkflowStateQCProblems();
			 }
             if (atState.equals(WorkFlowStatus.STAGE_QC_PASS)) {
					 result = new WorkflowStateQCReviewed();
			 }
             if (atState.equals(WorkFlowStatus.STAGE_CLEAN)) {
					 result = new WorkflowStateSpecialistReviewed();
			 }
             if (atState.equals(WorkFlowStatus.STAGE_DONE)) {
					 result = new WorkflowStateDoneMigrated();
			 }
			 return result;
	 }

	 public static boolean isStateValid(String aState) throws InvalidStateException {
			 String[] validStates = WorkFlowStatus.getAllWorkFlowStatusValues();
			 boolean result = false;
			 for (int x=0; x<validStates.length; x++) {
				 if (validStates[x].equals(aState)) {
						 result = true;
				 }
			 }
			 if (!result) throw new InvalidStateException(aState + "is not a valid workflow state.");
			 return result;
	 }

	 /** Obtain a list of the available states that a particular user can change specimens to,
	  * of use in populating a pick list of WorkFlowStatus values.
	  *
	  * @param user the Users instance representing the individual for whom the check of possible
	  * states is to be performed.
	  * @return List<String> of values equivalent to the list of WorkFlowStatus.STATE_ constants that
	  *  are available to user, suitable for use with a f:selectItems tag.
	  */
	 public static List<String> getAvailableStatesForUser(Users user) {
			ArrayList<String> states = new ArrayList<String>();
			if (user.isUserRole(Users.ROLE_DATAENTRY)) {
					states.add(WorkFlowStatus.STAGE_1);
					states.add(WorkFlowStatus.STAGE_VERBATIM);
					states.add(WorkFlowStatus.STAGE_CLASSIFIED);
					states.add(WorkFlowStatus.STAGE_2);
					states.add(WorkFlowStatus.STAGE_QC_FAIL);
			}
			if (user.isUserRole(Users.ROLE_FULL)) {
					states.add(WorkFlowStatus.STAGE_QC_PASS);
			}
			if (user.isUserRole(Users.ROLE_EDITOR)) {
					states.add(WorkFlowStatus.STAGE_CLEAN);
			}
			if (user.isUserRole(Users.ROLE_CHIEF_EDITOR)) {
					// Chief editor has no special additional states over other editors.
			}
			if (user.isUserRole(Users.ROLE_ADMINISTRATOR)) {
					// Only the administrator can return a record to state OCR.
					states.add(WorkFlowStatus.STAGE_0);
					// Only the administrator can set a record to state Done.
					states.add(WorkFlowStatus.STAGE_DONE);
			}
			return states;
	 }

     public WorkflowState forceStateTo(String newState) throws InvalidStateException {
			 isStateValid(newState);
			 WorkflowState result = null;
             if (newState.equals(WorkFlowStatus.STAGE_0)) {
					 result = new WorkflowStateOCR();
			 }
             if (newState.equals(WorkFlowStatus.STAGE_1)) {
					 result = new WorkflowStateTaxonEntered();
			 }
             if (newState.equals(WorkFlowStatus.STAGE_VERBATIM)) {
					 result = new WorkflowStateTaxonEntered();
			 }
             if (newState.equals(WorkFlowStatus.STAGE_CLASSIFIED)) {
					 result = new WorkflowStateTaxonEntered();
			 }              
             if (newState.equals(WorkFlowStatus.STAGE_2)) {
					 result = new WorkflowStateTextEntered();
			 }
             if (newState.equals(WorkFlowStatus.STAGE_QC_FAIL)) {
					 result = new WorkflowStateQCProblems();
			 }
             if (newState.equals(WorkFlowStatus.STAGE_QC_PASS)) {
					 result = new WorkflowStateQCReviewed();
			 }
             if (newState.equals(WorkFlowStatus.STAGE_CLEAN)) {
					 result = new WorkflowStateSpecialistReviewed();
			 }
             if (newState.equals(WorkFlowStatus.STAGE_DONE)) {
					 result = new WorkflowStateDoneMigrated();
			 }
			 return result;
	 }

     public abstract WorkflowState changeStateTo(String newState, Users aUser) throws InvalidStateException, NotAllowedStateException;

	 public abstract String getState();


}
