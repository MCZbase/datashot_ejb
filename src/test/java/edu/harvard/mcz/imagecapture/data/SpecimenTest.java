/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.data;

import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**  Run unit tests, including on persistence of the Specimen class.
 *
 * @author mole
 */
public class SpecimenTest {

//    private static EntityManagerFactory emf;
//    private static EntityManager em;
//    private static EntityTransaction tx;

    public SpecimenTest() {
    }

//	@BeforeClass
//	public static void setUpClass() throws Exception {
//        // this persistence configuration is using Derby on localhost and will
//	    // need to have derby started with, e.g. /usr/local/java/java/db/bin/startNetworkServer &
//        emf = Persistence.createEntityManagerFactory("imagecapture_test_ejbPU");
//	    em = emf.createEntityManager();
//	}
//
//	@AfterClass
//	public static void tearDownClass() throws Exception {
//		if (em!=null) {
//		    em.close();
//		}
//		if (emf!=null) {
//		    emf.close();
//		}
//	}
//
//    @Before
//    public void setUp() {
//	    tx = em.getTransaction();
//    }

    @After
    public void tearDown() {
    }


	/**
	 * Test of persistence of class Specimen.
	 */
//	@Test
//	public void testPersistence() {
//		System.out.println("getBarcode");
//		Specimen instance = new Specimen();
//		String expResult = "999999";
//		instance.setBarcode(expResult);
//		String result = instance.getBarcode();
//		assertEquals(expResult, result);
//
//		// Set up for derby, which isn't setting values for the datecreated.
//		instance.setDateCreated(new Date());
//
//		// test persistence
//		tx.begin();
//		em.persist(instance);
//		tx.commit();
//		assertNotNull("SpecimenID should not be null for persisted instance.", instance.getSpecimenId());
//
//		// test retireval
//		List<Specimen> specimens = em.createNamedQuery("Specimen.findAll").getResultList();
//		assertNotNull("After persisting a specimen instance, findAll should return at least one result", specimens);
//	}

	/**
	 * Test the truncation of overlength drawerNumber values.
	 *
	 */
	@Test
	public void testDrawerNumber() {
	    Specimen instance = new Specimen();
	    int fieldLength = MetadataRetriever.getFieldLength(Specimen.class, "DrawerNumber");
	    StringBuffer validBuff = new StringBuffer();
	    for(int j=0; j<fieldLength; j++) {
                validBuff.append("X");
             }
	    String valid = validBuff.toString();
	    String tooLong = validBuff.toString() + "Z";
	    instance.setDrawerNumber(valid);
	    assertTrue(valid.equals(instance.getDrawerNumber()));
	    instance.setDrawerNumber(tooLong);
	    // expectation is that overlength strings will be truncated to the maximum field length.
	    assertFalse(tooLong.equals(instance.getDrawerNumber()));
	    assertTrue(valid.equals(instance.getDrawerNumber()));
         
	}

	/**
	 * Test the truncation of overlength family values, and removal of trailing/leading spaces.
	 *
	 */
	@Test
	public void testFamily() {
	    Specimen instance = new Specimen();
	    int fieldLength = MetadataRetriever.getFieldLength(Specimen.class, "Family");
	    StringBuffer validBuff = new StringBuffer();
	    for(int j=0; j<fieldLength; j++) {
                validBuff.append("X");
             }
	    String valid = validBuff.toString();
	    String tooLong = validBuff.toString() + "Z";
	    instance.setFamily(valid);
	    assertTrue(valid.equals(instance.getFamily()));
	    instance.setFamily(tooLong);
	    // expectation is that overlength strings will be truncated to the maximum field length.
	    assertFalse(tooLong.equals(instance.getFamily()));
	    assertTrue(valid.equals(instance.getFamily()));

            String padded = " Family ";
	    instance.setFamily(padded);
	    assertFalse(padded.equals(instance.getFamily()));
	    assertTrue(instance.getFamily().equals(padded.trim()));

	}

	@Test
	public void testWorkFlowStateBooleans() {
	    Specimen instance = new Specimen();
		instance.setWorkFlowStatus(WorkFlowStatus.STAGE_0);
		assertTrue(instance.isStateOCR());
		instance.setWorkFlowStatus(WorkFlowStatus.STAGE_1);
		assertFalse(instance.isStateOCR());
		instance.setWorkFlowStatus(null);
		try {
		   assertFalse(instance.isStateOCR());
		   fail("Failed to catche Null Pointer Exception");
		} catch (NullPointerException e) { 
			// expected
		}
	}


}