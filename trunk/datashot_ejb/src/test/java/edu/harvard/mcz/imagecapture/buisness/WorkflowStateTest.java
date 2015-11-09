/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.buisness;

import java.util.ArrayList;
import edu.harvard.mcz.imagecapture.data.WorkFlowStatus;
import edu.harvard.mcz.imagecapture.data.Users;
import edu.harvard.mcz.imagecapture.exceptions.InvalidStateException;
import edu.harvard.mcz.imagecapture.exceptions.NotAllowedStateException;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mole
 */
public class WorkflowStateTest {

    public WorkflowStateTest() {
    }

		@BeforeClass
		public static void setUpClass() throws Exception {
		}

		@AfterClass
		public static void tearDownClass() throws Exception {
		}

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

		/**
		 * Test of createWorkflowState method, of class WorkflowState.
		 */ @Test
		public void testCreateWorkflowState() throws Exception {
				System.out.println("createWorkflowState");
				String atState = "";
				WorkflowState expResult = null;
		        try {
				    WorkflowState result = WorkflowState.createWorkflowState(atState);
				    fail("Failed to throw an InvalidStateException for [" + atState + "]");
				} catch (InvalidStateException e) {
						// expected result.
				}
		        try {
				    WorkflowState result = WorkflowState.createWorkflowState(WorkFlowStatus.STAGE_1);
				    assertEquals(WorkFlowStatus.STAGE_1, result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected InvalidStateException for [" + atState + "]");
				}
				String state = WorkFlowStatus.STAGE_0;
		        try {
				    WorkflowState result = WorkflowState.createWorkflowState(state);
				    assertEquals(state, result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected InvalidStateException for [" + state + "]");
				}
				state = WorkFlowStatus.STAGE_2;
		        try {
				    WorkflowState result = WorkflowState.createWorkflowState(state);
				    assertEquals(state, result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected InvalidStateException for [" + state + "]");
				}
				state = WorkFlowStatus.STAGE_CLEAN;
		        try {
				    WorkflowState result = WorkflowState.createWorkflowState(state);
				    assertEquals(state, result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected InvalidStateException for [" + state + "]");
				}
				state = WorkFlowStatus.STAGE_QC_FAIL;
		        try {
				    WorkflowState result = WorkflowState.createWorkflowState(state);
				    assertEquals(state, result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected InvalidStateException for [" + state + "]");
				}
				state = WorkFlowStatus.STAGE_QC_PASS;
		        try {
				    WorkflowState result = WorkflowState.createWorkflowState(state);
				    assertEquals(state, result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected InvalidStateException for [" + state + "]");
				}
				// Try more bad values
				state = "1";
		        try {
				    WorkflowState result = WorkflowState.createWorkflowState(state);
				    fail("Failed to throw an InvalidStateException for [" + state + "]");
				} catch (InvalidStateException e) {
						// expected result.
				}
				state = "Good";
		        try {
				    WorkflowState result = WorkflowState.createWorkflowState(state);
				    fail("Failed to throw an InvalidStateException for [" + state + "]");
				} catch (InvalidStateException e) {
						// expected result.
				}
				state = "ewqoda;lvakjvkfafamsd,vc;sdalkjfeoirladkmvlkdafedarevkmled'' fa\tdkl;ajf";
		        try {
				    WorkflowState result = WorkflowState.createWorkflowState(state);
				    fail("Failed to throw an InvalidStateException for [" + state + "]");
				} catch (InvalidStateException e) {
						// expected result.
				}
		}

		/**
		 * Test of isStateValid method, of class WorkflowState.
		 */ @Test
		public void testIsStateValid() throws Exception {
				System.out.println("isStateValid");
				String aState = "";
				boolean expResult = false;
				String[] validStates = WorkFlowStatus.getChangableWorkFlowStatusValues();
				for (int i=0; i<validStates.length; i++) {
				   try {
				      boolean result = WorkflowState.isStateValid(validStates[i]);
					  assertTrue(result);
				   } catch (InvalidStateException e) {
				      fail("Threw unexpected InvalidStateException for [" + validStates[i] + "]");
				   }
				}
				String state = "dasfj;da";
				try {
				    boolean result = WorkflowState.isStateValid(state);
				    fail("Failed to throw InvalidStateException for [" + state + "]");
				} catch (InvalidStateException e) {
						// expected result
				}
		}

		/**
		 * Test of getAvailableStatesForUser method, of class WorkflowState.
		 */ @Test
		public void testGetAvailableStatesForUser() {
				System.out.println("getAvailableStatesForUser");
				Users user = new Users();
				user.setRole(user.ROLE_DATAENTRY);
				ArrayList<String> expectedList = new ArrayList<String>();
				expectedList.add(WorkFlowStatus.STAGE_1);
				expectedList.add(WorkFlowStatus.STAGE_2);
				expectedList.add(WorkFlowStatus.STAGE_QC_FAIL);
				List<String> result = WorkflowState.getAvailableStatesForUser(user);
				assertEquals(expectedList.size(), result.size());
				for (int x=0; x<expectedList.size(); x++) {
					// Order of result is not specified, test independent of order
				    assertTrue(result.contains(expectedList.get(x)));
				}
				user.setRole(user.ROLE_FULL);
				expectedList = new ArrayList<String>();
				expectedList.add(WorkFlowStatus.STAGE_1);
				expectedList.add(WorkFlowStatus.STAGE_2);
				expectedList.add(WorkFlowStatus.STAGE_QC_FAIL);
				expectedList.add(WorkFlowStatus.STAGE_QC_PASS);
				result = WorkflowState.getAvailableStatesForUser(user);
				assertEquals(expectedList.size(), result.size());
				for (int x=0; x<expectedList.size(); x++) {
					// Order of result is not specified, test independent of order
				    assertTrue(result.contains(expectedList.get(x)));
				}
				user.setRole(user.ROLE_EDITOR);
				expectedList = new ArrayList<String>();
				expectedList.add(WorkFlowStatus.STAGE_1);
				expectedList.add(WorkFlowStatus.STAGE_2);
				expectedList.add(WorkFlowStatus.STAGE_QC_FAIL);
				expectedList.add(WorkFlowStatus.STAGE_QC_PASS);
				expectedList.add(WorkFlowStatus.STAGE_CLEAN);
				result = WorkflowState.getAvailableStatesForUser(user);
				assertEquals(expectedList.size(), result.size());
				for (int x=0; x<expectedList.size(); x++) {
					// Order of result is not specified, test independent of order
				    assertTrue(result.contains(expectedList.get(x)));
				}
				user.setRole(user.ROLE_CHIEF_EDITOR);
				expectedList = new ArrayList<String>();
				expectedList.add(WorkFlowStatus.STAGE_1);
				expectedList.add(WorkFlowStatus.STAGE_2);
				expectedList.add(WorkFlowStatus.STAGE_QC_FAIL);
				expectedList.add(WorkFlowStatus.STAGE_QC_PASS);
				expectedList.add(WorkFlowStatus.STAGE_CLEAN);
				result = WorkflowState.getAvailableStatesForUser(user);
				assertEquals(expectedList.size(), result.size());
				for (int x=0; x<expectedList.size(); x++) {
					// Order of result is not specified, test independent of order
				    assertTrue(result.contains(expectedList.get(x)));
				}
				user.setRole(user.ROLE_ADMINISTRATOR);
				expectedList = new ArrayList<String>();
				expectedList.add(WorkFlowStatus.STAGE_0);
				expectedList.add(WorkFlowStatus.STAGE_1);
				expectedList.add(WorkFlowStatus.STAGE_2);
				expectedList.add(WorkFlowStatus.STAGE_QC_FAIL);
				expectedList.add(WorkFlowStatus.STAGE_QC_PASS);
				expectedList.add(WorkFlowStatus.STAGE_CLEAN);
				expectedList.add(WorkFlowStatus.STAGE_DONE);
				result = WorkflowState.getAvailableStatesForUser(user);
				assertEquals(expectedList.size(), result.size());
				for (int x=0; x<expectedList.size(); x++) {
					// Order of result is not specified, test independent of order
				    assertTrue(result.contains(expectedList.get(x)));
				}
		}

		/**
		 * Test of forceStateTo method, of class WorkflowState.
		 */ @Test
		public void testForceStateTo() throws Exception {
				System.out.println("forceStateTo");
				String newState = WorkFlowStatus.STAGE_0;
				WorkflowState instance = new WorkflowStateOCR();
				WorkflowState expResult = new WorkflowStateOCR();
				try {
				    WorkflowState result = instance.forceStateTo(newState);
				    assertEquals(expResult.getState(), result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected InvalidStateException forcing state to " + newState);
				}
				newState = WorkFlowStatus.STAGE_1;
				instance = new WorkflowStateOCR();
				expResult = new WorkflowStateTaxonEntered();
				try {
				    WorkflowState result = instance.forceStateTo(newState);
				    assertEquals(expResult.getState(), result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected InvalidStateException forcing state to " + newState);
				}
				newState = WorkFlowStatus.STAGE_2;
				instance = new WorkflowStateOCR();
				expResult = new WorkflowStateTextEntered();
				try {
				    WorkflowState result = instance.forceStateTo(newState);
				    assertEquals(expResult.getState(), result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected InvalidStateException forcing state to " + newState);
				}
				newState = WorkFlowStatus.STAGE_QC_FAIL;
				instance = new WorkflowStateOCR();
				expResult = new WorkflowStateQCProblems();
				try {
				    WorkflowState result = instance.forceStateTo(newState);
				    assertEquals(expResult.getState(), result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected InvalidStateException forcing state to " + newState);
				}
				newState = WorkFlowStatus.STAGE_QC_PASS;
				instance = new WorkflowStateOCR();
				expResult = new WorkflowStateQCReviewed();
				try {
				    WorkflowState result = instance.forceStateTo(newState);
				    assertEquals(expResult.getState(), result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected InvalidStateException forcing state to " + newState);
				}
				newState = WorkFlowStatus.STAGE_CLEAN;
				instance = new WorkflowStateOCR();
				expResult = new WorkflowStateSpecialistReviewed();
				try {
				    WorkflowState result = instance.forceStateTo(newState);
				    assertEquals(expResult.getState(), result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected InvalidStateException forcing state to " + newState);
				}
				newState = "adlafweqgk;lqmre";
				instance = new WorkflowStateOCR();
				expResult = new WorkflowStateSpecialistReviewed();
				try {
				    WorkflowState result = instance.forceStateTo(newState);
				    fail("Failed to throw InvalidStateException forcing state to " + newState);
				} catch (InvalidStateException e) {
						// expected result
				}
		}

		/**
		 * Test of changeStateTo method, of class WorkflowState.
		 */ @Test
		public void testChangeStateTo() throws Exception {
				System.out.println("changeStateTo");
				String newState = WorkFlowStatus.STAGE_1;
				Users user = new Users();
				user.setRole(user.ROLE_DATAENTRY);
				WorkflowState instance = new WorkflowStateOCR();
				WorkflowState expResult = new WorkflowStateTaxonEntered();
				try {
				    WorkflowState result = instance.changeStateTo(newState, user);
				    assertEquals(expResult.getState(), result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected invalid state exception on changing from " + instance.getState() + " to " + newState + " for " + user.getRole());
				} catch (NotAllowedStateException e1) {
				    fail("Threw unexpected not allowed exception on changing from " + instance.getState() + " to " + newState + " for " + user.getRole());
				}
				newState = WorkFlowStatus.STAGE_2;
				user = new Users();
				user.setRole(user.ROLE_DATAENTRY);
				instance = new WorkflowStateOCR();
				expResult = new WorkflowStateTextEntered();
				try {
				    WorkflowState result = instance.changeStateTo(newState, user);
				    assertEquals(expResult.getState(), result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected invalid state exception on changing from " + instance.getState() + " to " + newState + " for " + user.getRole());
				} catch (NotAllowedStateException e1) {
				    fail("Threw unexpected not allowed exception on changing from " + instance.getState() + " to " + newState + " for " + user.getRole());
				}
				newState = WorkFlowStatus.STAGE_QC_FAIL;
				user = new Users();
				user.setRole(user.ROLE_DATAENTRY);
				instance = new WorkflowStateOCR();
				expResult = new WorkflowStateQCProblems();
				try {
				    WorkflowState result = instance.changeStateTo(newState, user);
				    assertEquals(expResult.getState(), result.getState());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected invalid state exception on changing from " + instance.getState() + " to " + newState + " for " + user.getRole());
				} catch (NotAllowedStateException e1) {
				    fail("Threw unexpected not allowed exception on changing from " + instance.getState() + " to " + newState + " for " + user.getRole());
				}
				newState = WorkFlowStatus.STAGE_QC_PASS;
				user = new Users();
				user.setRole(user.ROLE_DATAENTRY);
				instance = new WorkflowStateOCR();
				expResult = new WorkflowStateQCReviewed();
				try {
				    WorkflowState result = instance.changeStateTo(newState, user);
				    fail("Failed to throw not allowed exception on changing from " + instance.getState() + " to " + newState + " for " + user.getRole());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected invalid state exception on changing from " + instance.getState() + " to " + newState + " for " + user.getRole());
				} catch (NotAllowedStateException e1) {
				    // expected result
				    assertEquals(new WorkflowStateOCR().getState(), instance.getState());
				}
				newState = WorkFlowStatus.STAGE_CLEAN;
				user = new Users();
				user.setRole(user.ROLE_DATAENTRY);
				instance = new WorkflowStateOCR();
				expResult = new WorkflowStateSpecialistReviewed();
				try {
				    WorkflowState result = instance.changeStateTo(newState, user);
				    fail("Failed to throw not allowed exception on changing from " + instance.getState() + " to " + newState + " for " + user.getRole());
				} catch (InvalidStateException e) {
				    fail("Threw unexpected invalid state exception on changing from " + instance.getState() + " to " + newState + " for " + user.getRole());
				} catch (NotAllowedStateException e1) {
				    // expected result
				    assertEquals(new WorkflowStateOCR().getState(), instance.getState());
				}

				// TODO: test for other user roles.

				newState = "dafdafafa";
				user = new Users();
				user.setRole(user.ROLE_DATAENTRY);
				instance = new WorkflowStateOCR();
				expResult = new WorkflowStateTextEntered();
				try {
				    WorkflowState result = instance.changeStateTo(newState, user);
				    fail("Failed to throw invalid state exception on changing from " + instance.getState() + " to " + newState + " for " + user.getRole());
				} catch (InvalidStateException e) {
					// expected result
				    assertEquals(new WorkflowStateOCR().getState(), instance.getState());
				} catch (NotAllowedStateException e1) {
				    fail("Threw unexpected not allowed exception on changing from " + instance.getState() + " to " + newState + " for " + user.getRole());
				}
		}

		/**
		 * Test of getState method, of class WorkflowState.
		 */ @Test
		public void testGetState() {
				System.out.println("getState");
				WorkflowState instance = new WorkflowStateOCR();
				String expResult = WorkFlowStatus.STAGE_0;
				String result = instance.getState();
				assertEquals(expResult, result);
				instance = new WorkflowStateTaxonEntered();
				expResult = WorkFlowStatus.STAGE_1;
				result = instance.getState();
				assertEquals(expResult, result);
				instance = new WorkflowStateTextEntered();
				expResult = WorkFlowStatus.STAGE_2;
				result = instance.getState();
				assertEquals(expResult, result);
				instance = new WorkflowStateQCProblems();
				expResult = WorkFlowStatus.STAGE_QC_FAIL;
				result = instance.getState();
				assertEquals(expResult, result);
				instance = new WorkflowStateQCReviewed();
				expResult = WorkFlowStatus.STAGE_QC_PASS;
				result = instance.getState();
				assertEquals(expResult, result);
				instance = new WorkflowStateSpecialistReviewed();
				expResult = WorkFlowStatus.STAGE_CLEAN;
				result = instance.getState();
				assertEquals(expResult, result);
		}


}