package com.hcl.adi.chf.lambda.test;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;
import com.hcl.adi.chf.lambda.UpdateInstitution;
import com.hcl.adi.chf.model.CustomResponse;
import com.hcl.adi.chf.model.Institution;

public class UpdateInstitutionTest {
	private static final Logger LOGGER = LogManager.getLogger(UpdateInstitutionTest.class.getName());
	private static Institution institution;

	@BeforeClass
	public static void createInput() throws IOException {
		// set up your sample input object here.
		institution = new Institution();
		institution.setInstitutionId(0);
		institution.setInstitutionName("ADI Institution");
		institution.setAddress("One Technology Way PO BOX 9106 Norwood, MA 02062");
		institution.setContactPerson("Karthick");
		institution.setContactNumber("+919650322465");
		institution.setInstitutionStatus("A");
		institution.setDeleteMarker("N");
		institution.setUpdatedBy("dinesh-kuma@hcl.com");
		institution.setAction("edit");
	}

	private Context createContext() {
		TestContext ctx = new TestContext();

		// customize your context here if needed.
		ctx.setFunctionName("Your Function Name");

		return ctx;
	}

	@Test
	public void testUpdateInstitution() {
		UpdateInstitution handler = new UpdateInstitution();
		Context ctx = createContext();

		CustomResponse output = handler.handleRequest(institution, ctx);
		LOGGER.info(output);

		// validate output here.
		Assert.assertEquals("OK", output.getDescription());
	}
}