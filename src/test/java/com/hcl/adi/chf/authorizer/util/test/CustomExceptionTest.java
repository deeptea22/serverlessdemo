package com.hcl.adi.chf.authorizer.util.test;

import org.junit.Test;

import com.hcl.adi.chf.authorizer.util.CustomException;

public class CustomExceptionTest {
	
	@Test
	public void testCustomException() {

		CustomException customEx = new CustomException(null, null);
		customEx = new CustomException(null, null, null);
		customEx.setErrorReason(null);
		customEx.setHttpStatus(null);
		customEx.setMessage(null);
		customEx.setStackTrace(null);
		
		customEx.getErrorReason();
		customEx.getHttpStatus();
		customEx.getMessage();
		customEx.getStackTrace();
		
	}

}
