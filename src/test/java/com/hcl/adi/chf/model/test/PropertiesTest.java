package com.hcl.adi.chf.model.test;

import org.junit.Test;

import com.hcl.adi.chf.model.Properties;

public class PropertiesTest {
	@Test
	public void testProperties() {
		Properties property = new Properties();
		property.setKey(null);
		property.setValue(null);
		property.setDate(null);
		property.setType(null);
	
				
		property.getKey();
		property.getValue();
		property.getDate();
		property.getType();		
		property.toString();
		
		
	}
}
