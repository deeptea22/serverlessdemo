package com.hcl.adi.chf.lambda.test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.GetContactUsSubjectList;
import com.hcl.adi.chf.model.ContactUS;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class GetContactUsSubjectListTest {
	private static final Logger LOGGER = LogManager.getLogger(GetContactUsSubjectListTest.class.getName());
	private static Map<String, Integer> input;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		input = new HashMap<String, Integer>();
		
	}


    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("Your Function Name");

        return ctx;
    }

    @Test
	public void testGetAllActivatedInstitutionList() {
    	
        GetContactUsSubjectList handler = new GetContactUsSubjectList();
        Context ctx = createContext();

        Map<String,List<ContactUS>> contactUsSubjectListMap = handler.handleRequest(input, ctx);
        LOGGER.info(contactUsSubjectListMap);
     // validate output here.
     	Assert.assertNotNull(contactUsSubjectListMap);
    }
}

