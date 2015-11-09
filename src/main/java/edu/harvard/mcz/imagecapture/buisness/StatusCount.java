/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.buisness;

/**
 * Supporting class to display results of select count(*), workflowStatus group by workflowStatus.
 *
 * @author mole
 */
public class StatusCount {

	private String status;
	private Long count;
	private Long groupCount1;
	private Long groupCount2;


	public StatusCount(String status, Long count) {
		this.count = count;
		if (status!=null) {
		    this.status = status;
		} else {
			this.status = "";
		}
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the groupCount1
	 */
	public Long getGroupCount1() {
		return groupCount1;
	}

	/**
	 * @param groupCount1 the groupCount1 to set
	 */
	public void setGroupCount1(Long groupCount1) {
		this.groupCount1 = groupCount1;
	}

	/**
	 * @return the groupCount2
	 */
	public Long getGroupCount2() {
		return groupCount2;
	}

	/**
	 * @param groupCount2 the groupCount2 to set
	 */
	public void setGroupCount2(Long groupCount2) {
		this.groupCount2 = groupCount2;
	}


}

