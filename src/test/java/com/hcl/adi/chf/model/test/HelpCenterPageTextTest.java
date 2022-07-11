package com.hcl.adi.chf.model.test;

import org.junit.Test;

import com.hcl.adi.chf.model.HelpCenterPageText;

public class HelpCenterPageTextTest {
	@Test
	public void testHelpCenterPageText() {
		HelpCenterPageText helpCenterPageText = new HelpCenterPageText();
		helpCenterPageText.setHelpId(null);
		helpCenterPageText.setHelpTopic(null);
		helpCenterPageText.setHelpDesc(null);
		helpCenterPageText.setHelpUrl(null);
		helpCenterPageText.setHelpType(null);
		helpCenterPageText.setCreatedBy(null);
		helpCenterPageText.setCreatedTimestamp(null);
		helpCenterPageText.setUpdatedBy(null);
		helpCenterPageText.setUpdatedTimestamp(null);
				
		helpCenterPageText.getHelpId();
		helpCenterPageText.getHelpDesc();
		helpCenterPageText.getHelpTopic();
		helpCenterPageText.getHelpUrl();
		helpCenterPageText.getHelpType();
		helpCenterPageText.getCreatedBy();
		helpCenterPageText.getCreatedTimestamp();
		helpCenterPageText.getUpdatedBy();
		helpCenterPageText.getUpdatedTimestamp();
		helpCenterPageText.toString();
		helpCenterPageText.hashCode();
		helpCenterPageText.equals(helpCenterPageText);
		
	}
}
