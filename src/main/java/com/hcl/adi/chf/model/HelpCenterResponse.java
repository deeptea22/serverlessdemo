package com.hcl.adi.chf.model;
/*
 * Model class for changes in 
 * Help Center 
 * 
 */

import java.util.List;

public final class HelpCenterResponse {

	private List<HelpCenterPageText> helpCenterList;

	/**
	 * @return the helpCenterList
	 */
	public List<HelpCenterPageText> getHelpCenterList() {
		return helpCenterList;
	}

	/**
	 * @param helpCenterList the helpCenterList to set
	 */
	public void setHelpCenterList(List<HelpCenterPageText> helpCenterList) {
		this.helpCenterList = helpCenterList;
	}

	
	@Override
	public String toString() {
		return "HelpCenterResponse [helpCenterList=" + helpCenterList + "]";
	}
	
	
	
}
