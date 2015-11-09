/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.mcz.imagecapture.buisness;

/**
 *  Supporting class to display results of select count(*), fieldname group by fieldname queries.
 *
 * @author mole
 */
public class FieldCount {

	private String field;
	private Long count;

	public FieldCount(String field, Long count) {
		this.count = count;
		if (field!=null) {
		    this.field = field;
		} else {
			this.field = "";
		}
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

}
